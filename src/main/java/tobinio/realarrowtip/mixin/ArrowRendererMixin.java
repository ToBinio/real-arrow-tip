package tobinio.realarrowtip.mixin;

import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.entity.state.TippableArrowRenderState;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.realarrowtip.HasColor;
import tobinio.realarrowtip.config.Config;

import static tobinio.realarrowtip.RealArrowTip.id;

@Mixin (ArrowRenderer.class)
public abstract class ArrowRendererMixin<S extends ArrowRenderState> {
    @Shadow
    @Final
    private ArrowModel model;

    @Inject (at = @At (value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"), method = "submit(Lnet/minecraft/client/renderer/entity/state/ArrowRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V")
    private void real_arrow_tip$submit(S projectileEntityRenderState, PoseStack matrixStack,
            SubmitNodeCollector orderedRenderCommandQueue, CameraRenderState cameraRenderState, CallbackInfo ci) {

        if (!Config.HANDLER.instance().renderCustomArrowEntity) {
            return;
        }

        if (projectileEntityRenderState instanceof TippableArrowRenderState arrowEntity) {
            int color = ARGB.opaque(((HasColor) arrowEntity).real_arrow_tip$getColor());

            if (color == -1)
                return;

            orderedRenderCommandQueue.submitModel(
                    this.model,
                    projectileEntityRenderState,
                    matrixStack,
                    RenderTypes.entityCutout(id(
                            "textures/entity/projectiles/tipped_arrow_head.png")),
                    projectileEntityRenderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    color,
                    null,
                    projectileEntityRenderState.outlineColor,
                    null
            );
        }
    }
}
