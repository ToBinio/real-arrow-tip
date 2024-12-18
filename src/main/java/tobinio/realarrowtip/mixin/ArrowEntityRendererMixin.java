package tobinio.realarrowtip.mixin;

import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.state.ArrowEntityRenderState;
import net.minecraft.entity.projectile.ArrowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.realarrowtip.HasColor;

/**
 * Created: 03/10/2023
 *
 * @author Tobias Frischmann
 */

@Mixin (ArrowEntityRenderer.class)
public abstract class ArrowEntityRendererMixin {
    @Inject (at = @At ("TAIL"), method = "updateRenderState(Lnet/minecraft/entity/projectile/ArrowEntity;Lnet/minecraft/client/render/entity/state/ArrowEntityRenderState;F)V")
    private void updateRenderState(ArrowEntity arrowEntity, ArrowEntityRenderState arrowEntityRenderState, float f,
            CallbackInfo ci) {
        ((HasColor) arrowEntityRenderState).real_arrow_tip$setColor(arrowEntity.getColor());
    }
}
