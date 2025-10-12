package tobinio.realarrowtip.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.model.ArrowEntityModel;
import net.minecraft.client.render.entity.state.ArrowEntityRenderState;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.realarrowtip.HasColor;
import tobinio.realarrowtip.config.Config;

import static tobinio.realarrowtip.RealArrowTip.id;

@Mixin (ProjectileEntityRenderer.class)
public abstract class ProjectileEntityRendererMixin<S extends ProjectileEntityRenderState> {
    @Shadow
    @Final
    private ArrowEntityModel model;

    @Inject (at = @At (value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V"), method = "render(Lnet/minecraft/client/render/entity/state/ProjectileEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V")
    private void real_arrow_tip$render(S projectileEntityRenderState, MatrixStack matrixStack,
            OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState, CallbackInfo ci) {

        if (!Config.HANDLER.instance().renderCustomArrowEntity) {
            return;
        }

        if (projectileEntityRenderState instanceof ArrowEntityRenderState arrowEntity) {
            int color = ColorHelper.fullAlpha(((HasColor) arrowEntity).real_arrow_tip$getColor());

            if (color == -1)
                return;

            orderedRenderCommandQueue.submitModel(
                        this.model,
                        projectileEntityRenderState,
                        matrixStack,
                        RenderLayer.getEntityCutout(id(
                                "textures/entity/projectiles/tipped_arrow_head.png")),
                        projectileEntityRenderState.light,
                        OverlayTexture.DEFAULT_UV,
                        color,
                        null,
                        projectileEntityRenderState.outlineColor,
                        null
                );
        }
    }
}
