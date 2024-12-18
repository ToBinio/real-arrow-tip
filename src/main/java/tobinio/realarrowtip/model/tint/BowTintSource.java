package tobinio.realarrowtip.model.tint;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;

import static tobinio.realarrowtip.RealArrowTip.getColorFromStack;

/**
 * Created: 18.12.24
 *
 * @author Tobias Frischmann
 */
public record BowTintSource(int defaultColor) implements TintSource {
    public static final MapCodec<BowTintSource> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codecs.RGB.fieldOf("default").forGetter(BowTintSource::defaultColor))
                    .apply(instance, BowTintSource::new)
    );

    @Override
    public int getTint(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user) {
        if (user != null) {
            ItemStack projectile = user.getProjectileType(stack);

            if (user instanceof AbstractSkeletonEntity skeleton) {
                projectile = skeleton.createArrowProjectile(projectile, 0, stack).getItemStack();
            }

            return getColorFromStack(projectile);
        }

        return ColorHelper.fullAlpha(this.defaultColor);
    }

    @Override
    public MapCodec<BowTintSource> getCodec() {
        return CODEC;
    }
}
