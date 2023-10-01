package tobinio.realarrowtip.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin (net.minecraft.client.render.entity.ProjectileEntityRenderer.class)
public class ProjectileEntityRenderer {

    @Inject (at = @At (value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ProjectileEntityRenderer;vertex(Lorg/joml/Matrix4f;Lorg/joml/Matrix3f;Lnet/minecraft/client/render/VertexConsumer;IIIFFIIII)V", ordinal = 9), method = "render")
    void 
}
