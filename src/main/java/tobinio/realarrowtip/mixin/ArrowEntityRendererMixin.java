package tobinio.realarrowtip.mixin;

import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Redirect;
import tobinio.realarrowtip.RealArrowTip;

import static net.minecraft.client.render.entity.ArrowEntityRenderer.TEXTURE;

/**
 * Created: 02/10/2023
 * @author Tobias Frischmann
 */

@Mixin (ArrowEntityRenderer.class)
public abstract class ArrowEntityRendererMixin extends ProjectileEntityRenderer<ArrowEntity> {

    //todo only overwrite identifier

    public ArrowEntityRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ArrowEntity arrowEntity) {
        return arrowEntity.getColor() > 0 ? new Identifier(RealArrowTip.modID, "textures/entity/projectiles/tipped_arrow.png") : TEXTURE;
    }

}
