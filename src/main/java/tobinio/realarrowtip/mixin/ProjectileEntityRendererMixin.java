package tobinio.realarrowtip.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.model.ArrowEntityModel;
import net.minecraft.client.render.entity.state.ArrowEntityRenderState;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.realarrowtip.HasColor;
import tobinio.realarrowtip.RealArrowTip;

import java.util.Arrays;

import static tobinio.realarrowtip.RealArrowTip.id;

@Mixin (ProjectileEntityRenderer.class)
public abstract class ProjectileEntityRendererMixin<T extends PersistentProjectileEntity> {
    @Shadow
    @Final
    private ArrowEntityModel model;

    @Inject (at = @At (value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V"), method = "render(Lnet/minecraft/client/render/entity/state/ProjectileEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void render(ProjectileEntityRenderState projectileEntityRenderState, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

        if (projectileEntityRenderState instanceof ArrowEntityRenderState arrowEntity) {
            int color = ColorHelper.fullAlpha(((HasColor) arrowEntity).real_arrow_tip$getColor());

            if (color == -1)
                return;

            VertexConsumer headVertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(id(
                    "textures/entity/projectiles/tipped_arrow_head.png")));

            this.model.render(matrixStack, headVertexConsumer, i, OverlayTexture.DEFAULT_UV, color);
        }
    }
}
