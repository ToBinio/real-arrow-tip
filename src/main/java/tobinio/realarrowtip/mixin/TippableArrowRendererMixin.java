package tobinio.realarrowtip.mixin;

import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.renderer.entity.state.TippableArrowRenderState;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.realarrowtip.HasColor;
import tobinio.realarrowtip.config.Config;

import static tobinio.realarrowtip.RealArrowTip.id;

/**
 * Created: 03/10/2023
 *
 * @author Tobias Frischmann
 */

@Mixin (TippableArrowRenderer.class)
public abstract class TippableArrowRendererMixin {
    @Mutable
    @Shadow
    @Final
    public static Identifier TIPPED_ARROW_LOCATION;

    @Inject (at = @At ("TAIL"), method = "extractRenderState(Lnet/minecraft/world/entity/projectile/arrow/Arrow;Lnet/minecraft/client/renderer/entity/state/TippableArrowRenderState;F)V")
    private void real_arrow_tip$updateRenderState(Arrow arrowEntity, TippableArrowRenderState arrowEntityRenderState, float f,
            CallbackInfo ci) {
        ((HasColor) arrowEntityRenderState).real_arrow_tip$setColor(arrowEntity.getColor());
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void real_arrow_tip$setTippedArrowTexture(CallbackInfo ci) {
        if (!Config.HANDLER.instance().renderCustomArrowEntity) {
            return;
        }

        TIPPED_ARROW_LOCATION = id("textures/entity/projectiles/tipped_arrow.png");
    }
}
