package labs.elduin.ragdoll.mixin;
import labs.elduin.ragdoll.RagdollClient;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public class PlayerModelMixin {
    @Inject(method = "setAngles(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)V", at = @At("TAIL"))
    private void ragdoll$limbs(PlayerEntityRenderState state, CallbackInfo ci) {
        if (!RagdollClient.applies(state.id)) return;
        PlayerEntityModel model = (PlayerEntityModel)(Object)this;
        model.leftArm.pitch = RagdollClient.limbs[0];
        model.rightArm.pitch = RagdollClient.limbs[1];
        model.leftLeg.pitch = RagdollClient.limbs[2];
        model.rightLeg.pitch = RagdollClient.limbs[3];
        model.leftArm.roll = 0.6f;
        model.rightArm.roll = -0.6f;
        model.leftSleeve.copyTransform(model.leftArm);
        model.rightSleeve.copyTransform(model.rightArm);
        model.leftPants.copyTransform(model.leftLeg);
        model.rightPants.copyTransform(model.rightLeg);
    }
}
