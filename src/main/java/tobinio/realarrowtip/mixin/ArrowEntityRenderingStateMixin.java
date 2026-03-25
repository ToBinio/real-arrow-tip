package tobinio.realarrowtip.mixin;

import net.minecraft.client.renderer.entity.state.TippableArrowRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import tobinio.realarrowtip.HasColor;

/**
 * Created: 26.10.24
 *
 * @author Tobias Frischmann
 */
@Mixin (TippableArrowRenderState.class)
public class ArrowEntityRenderingStateMixin implements HasColor {

    @Shadow
    public boolean isTipped;
    @Unique
    int color;

    @Override
    public int real_arrow_tip$getColor() {
        return color;
    }

    @Override
    public void real_arrow_tip$setColor(int color) {
        this.color = color;
        this.isTipped = color != -1;
    }
}
