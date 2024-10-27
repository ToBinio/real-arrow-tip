package tobinio.realarrowtip.mixin;

import net.minecraft.client.render.entity.state.ArrowEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import tobinio.realarrowtip.HasColor;

/**
 * Created: 26.10.24
 *
 * @author Tobias Frischmann
 */
@Mixin (ArrowEntityRenderState.class)
public class ArrowEntityRenderingStateMixin implements HasColor {

    @Shadow
    public boolean tipped;
    @Unique
    int color;

    @Override
    public int real_arrow_tip$getColor() {
        return color;
    }

    @Override
    public void real_arrow_tip$setColor(int color) {
        this.color = color;
        this.tipped = color != -1;
    }
}
