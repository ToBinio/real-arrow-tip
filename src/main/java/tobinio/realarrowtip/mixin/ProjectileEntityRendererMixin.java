package tobinio.realarrowtip.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin (ProjectileEntityRenderer.class)
public abstract class ProjectileEntityRendererMixin<T extends PersistentProjectileEntity> {

    @Shadow
    public abstract void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, int x,
            int y, int z, float u, float v, int normalX, int normalZ, int normalY, int light);

    @Redirect (at = @At (value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ProjectileEntityRenderer;vertex(Lorg/joml/Matrix4f;Lorg/joml/Matrix3f;Lnet/minecraft/client/render/VertexConsumer;IIIFFIIII)V", ordinal = 8), method = "render(Lnet/minecraft/entity/projectile/PersistentProjectileEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void render1(ProjectileEntityRenderer<T> instance, Matrix4f positionMatrix, Matrix3f normalMatrix,
            VertexConsumer vertexConsumer, int x, int y, int z, float u, float v, int normalX, int normalZ, int normalY,
            int light) {

        this.vertexTint(positionMatrix, normalMatrix, vertexConsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, light);
    }

    @Redirect (at = @At (value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ProjectileEntityRenderer;vertex(Lorg/joml/Matrix4f;Lorg/joml/Matrix3f;Lnet/minecraft/client/render/VertexConsumer;IIIFFIIII)V", ordinal = 9), method = "render(Lnet/minecraft/entity/projectile/PersistentProjectileEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void render2(ProjectileEntityRenderer<T> instance, Matrix4f positionMatrix, Matrix3f normalMatrix,
            VertexConsumer vertexConsumer, int x, int y, int z, float u, float v, int normalX, int normalZ, int normalY,
            int light) {

        this.vertexTint(positionMatrix, normalMatrix, vertexConsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, light);
    }

    @Redirect (at = @At (value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ProjectileEntityRenderer;vertex(Lorg/joml/Matrix4f;Lorg/joml/Matrix3f;Lnet/minecraft/client/render/VertexConsumer;IIIFFIIII)V", ordinal = 10), method = "render(Lnet/minecraft/entity/projectile/PersistentProjectileEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void render3(ProjectileEntityRenderer<T> instance, Matrix4f positionMatrix, Matrix3f normalMatrix,
            VertexConsumer vertexConsumer, int x, int y, int z, float u, float v, int normalX, int normalZ, int normalY,
            int light) {

        this.vertexTint(positionMatrix, normalMatrix, vertexConsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, light);
    }

    @Redirect (at = @At (value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ProjectileEntityRenderer;vertex(Lorg/joml/Matrix4f;Lorg/joml/Matrix3f;Lnet/minecraft/client/render/VertexConsumer;IIIFFIIII)V", ordinal = 11), method = "render(Lnet/minecraft/entity/projectile/PersistentProjectileEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void render4(ProjectileEntityRenderer<T> instance, Matrix4f positionMatrix, Matrix3f normalMatrix,
            VertexConsumer vertexConsumer, int x, int y, int z, float u, float v, int normalX, int normalZ, int normalY,
            int light) {

        this.vertexTint(positionMatrix, normalMatrix, vertexConsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, light);
    }

    private void vertexTint(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, int x, int y,
            int z, float u, float v, int normalX, int normalZ, int normalY, int light) {
        vertexConsumer.vertex(positionMatrix, (float) x, (float) y, (float) z).color(255, 0, 255, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, (float) normalX, (float) normalY, (float) normalZ).next();
    }
}
