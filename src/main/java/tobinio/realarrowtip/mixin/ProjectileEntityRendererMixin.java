package tobinio.realarrowtip.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.realarrowtip.RealArrowTip;

@Mixin (ProjectileEntityRenderer.class)
public abstract class ProjectileEntityRendererMixin<T extends PersistentProjectileEntity> {
    @Inject (at = @At (value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", shift = At.Shift.BEFORE), method = "render(Lnet/minecraft/entity/projectile/PersistentProjectileEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void render(T persistentProjectileEntity, float f, float g, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

        if (persistentProjectileEntity instanceof ArrowEntity arrowEntity) {

            int color = ColorHelper.Argb.fullAlpha(arrowEntity.getColor());

            if (color == -1)
                return;

            int red = ColorHelper.Argb.getRed(color);
            int blue = ColorHelper.Argb.getBlue(color);
            int green = ColorHelper.Argb.getGreen(color);

            VertexConsumer headVertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(new Identifier(RealArrowTip.modID, "textures/entity/projectiles/tipped_arrow_head.png")));

            for (int u = 0; u < 4; ++u) {
                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
                vertexTint(matrixStack, headVertexConsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, i, red, green, blue);
                vertexTint(matrixStack, headVertexConsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, i, red, green, blue);
                vertexTint(matrixStack, headVertexConsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, i, red, green, blue);
                vertexTint(matrixStack, headVertexConsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, i, red, green, blue);
            }
        }
    }

    @Unique
    private void vertexTint(MatrixStack matrixStack, VertexConsumer vertexConsumer, int x, int y,
            int z, float u, float v, int normalX, int normalZ, int normalY, int light, int red, int green, int blue) {
        vertexConsumer.vertex(matrixStack.peek(), (float) x, (float) y, (float) z)
                .color(red, green, blue, 255)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(matrixStack.peek(), (float) normalX, (float) normalY, (float) normalZ)
                .next();
    }
}
