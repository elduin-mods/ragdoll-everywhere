package labs.elduin.ragdoll.mixin;
import labs.elduin.ragdoll.RagdollClient;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererMixin {
    @Inject(method = "setupTransforms(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;FF)V", at = @At("TAIL"))
    private void ragdoll$transform(PlayerEntityRenderState state, MatrixStack matrices,
                                   float bodyYaw, float baseHeight, CallbackInfo ci) {
        if (!RagdollClient.applies(state.id)) return;
        // Rotate about the middle of the model instead of the feet.
        matrices.translate(0, 0.85, 0);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(RagdollClient.roll));
        matrices.translate(0, -0.85, 0);
    }
}
