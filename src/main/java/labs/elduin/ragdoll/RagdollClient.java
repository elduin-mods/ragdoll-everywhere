package labs.elduin.ragdoll;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

/** Local visual prototype. Vanilla handles movement, water and collisions. */
public class RagdollClient implements ClientModInitializer {
    public static boolean active;
    public static float roll, previousRoll;
    public static final float[] limbs = new float[4];
    private static final float[] velocity = new float[4];
    private static Perspective savedPerspective;
    private static int playerId = -1;
    private static int ticks;

    public static boolean applies(int id) { return active && id == playerId; }

    private static void stop(MinecraftClient client) {
        active = false;
        if (savedPerspective != null) client.options.setPerspective(savedPerspective);
        savedPerspective = null;
        playerId = -1;
    }

    @Override public void onInitializeClient() {
        KeyBinding toggle = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.ragdoll.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_I, "category.ragdoll"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null || !client.player.isAlive()) {
                stop(client); return;
            }
            while (toggle.wasPressed()) {
                if (active) { stop(client); continue; }
                if (client.getServer() == null) {
                    client.player.sendMessage(Text.literal("This prototype is for single-player worlds."), true);
                    continue;
                }
                active = true;
                playerId = client.player.getId();
                ticks = 0;
                roll = previousRoll = 0;
                java.util.Arrays.fill(limbs, 0);
                java.util.Arrays.fill(velocity, 0);
                savedPerspective = client.options.getPerspective();
                client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
                client.player.sendMessage(Text.literal("Flop! Press I to stand up."), true);
            }
            if (!active || client.isPaused()) return;
            if (client.player.getId() != playerId) { stop(client); return; }
            ticks++;
            previousRoll = roll;
            double speed = client.player.getVelocity().length();
            boolean resting = client.player.isOnGround() && speed < 0.03;
            // Settle on the ground, tumble in the air, bob more slowly in water.
            float target = resting ? 90 : roll + (client.player.isTouchingWater() ? 5 : 14);
            roll += (target - roll) * (resting ? 0.25f : 1);
            for (int i = 0; i < 4; i++) {
                float goal = (float)Math.sin(ticks * 0.22 + i * 1.8) * (resting ? 0.12f : 1.1f);
                velocity[i] = (velocity[i] + (goal - limbs[i]) * 0.16f) * 0.78f;
                limbs[i] += velocity[i];
            }
        });
    }
}
