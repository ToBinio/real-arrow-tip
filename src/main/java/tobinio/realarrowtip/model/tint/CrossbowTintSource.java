package tobinio.realarrowtip.model.tint;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Nullable;
import tobinio.realarrowtip.RealArrowTip;

import java.util.Optional;

/**
 * Created: 18.12.24
 *
 * @author Tobias Frischmann
 */
public record CrossbowTintSource(int defaultColor) implements TintSource {
    public static final MapCodec<CrossbowTintSource> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codecs.RGB.fieldOf("default").forGetter(CrossbowTintSource::defaultColor))
                    .apply(instance, CrossbowTintSource::new)
    );

    @Override
    public int getTint(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user) {
        ChargedProjectilesComponent chargedProjectiles = stack.getOrDefault(DataComponentTypes.CHARGED_PROJECTILES,
                ChargedProjectilesComponent.DEFAULT);
        Optional<ItemStack> tippedArrow = chargedProjectiles.getProjectiles()
                .stream()
                .filter(itemStack -> itemStack.isOf(Items.TIPPED_ARROW))
                .findFirst();

        return tippedArrow.map(RealArrowTip::getColorFromStack).orElse(this.defaultColor);
    }

    @Override
    public MapCodec<CrossbowTintSource> getCodec() {
        return CODEC;
    }
}
