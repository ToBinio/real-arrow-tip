package tobinio.realarrowtip.mixin;

import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.state.ArrowEntityRenderState;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.realarrowtip.HasColor;

import static tobinio.realarrowtip.RealArrowTip.id;

/**
 * Created: 03/10/2023
 *
 * @author Tobias Frischmann
 */

@Mixin (ArrowEntityRenderer.class)
public abstract class ArrowEntityRendererMixin {
    @Mutable
    @Shadow
    @Final
    public static Identifier TIPPED_TEXTURE;

    @Inject (at = @At ("TAIL"), method = "updateRenderState(Lnet/minecraft/entity/projectile/ArrowEntity;Lnet/minecraft/client/render/entity/state/ArrowEntityRenderState;F)V")
    private void real_arrow_tip$updateRenderState(ArrowEntity arrowEntity, ArrowEntityRenderState arrowEntityRenderState, float f,
            CallbackInfo ci) {
        ((HasColor) arrowEntityRenderState).real_arrow_tip$setColor(arrowEntity.getColor());
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void real_arrow_tip$setTippedArrowTexture(CallbackInfo ci) {
        TIPPED_TEXTURE = id("textures/entity/projectiles/tipped_arrow.png");
    }
}
