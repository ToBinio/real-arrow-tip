package tobinio.realarrowtip.model.tint;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tobinio.realarrowtip.RealArrowTip;

import java.util.Optional;

import static tobinio.realarrowtip.RealArrowTip.getColorFromStack;

/**
 * Created: 18.12.24
 *
 * @author Tobias Frischmann
 */
public record TintSource(int defaultColor) implements net.minecraft.client.render.item.tint.TintSource {
    public static final MapCodec<TintSource> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codecs.RGB.fieldOf("default").forGetter(TintSource::defaultColor))
                    .apply(instance, TintSource::new)
    );

    @Override
    public int getTint(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user) {
        if (stack.isOf(Items.BOW)) {
            return getTintBow(stack, user);
        }
        return getTintCrossbow(stack);
    }

    private int getTintBow(ItemStack stack, @Nullable LivingEntity user) {
        if (user != null) {
            ItemStack projectile = user.getProjectileType(stack);

            if (user instanceof AbstractSkeletonEntity skeleton) {
                projectile = skeleton.createArrowProjectile(projectile, 0, stack).getItemStack();
            }

            return getColorFromStack(projectile);
        }

        return ColorHelper.fullAlpha(this.defaultColor);
    }

    private @NotNull Integer getTintCrossbow(ItemStack stack) {
        ChargedProjectilesComponent chargedProjectiles = stack.getOrDefault(DataComponentTypes.CHARGED_PROJECTILES,
                ChargedProjectilesComponent.DEFAULT);
        Optional<ItemStack> tippedArrow = chargedProjectiles.getProjectiles()
                .stream()
                .filter(itemStack -> itemStack.isOf(Items.TIPPED_ARROW))
                .findFirst();

        return tippedArrow.map(RealArrowTip::getColorFromStack).orElse(this.defaultColor);
    }


    @Override
    public MapCodec<TintSource> getCodec() {
        return CODEC;
    }
}
