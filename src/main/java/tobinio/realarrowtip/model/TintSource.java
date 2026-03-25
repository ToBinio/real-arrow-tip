package tobinio.realarrowtip.model;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.ARGB;
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
public record TintSource(int defaultColor) implements net.minecraft.client.color.item.ItemTintSource {
    public static final MapCodec<TintSource> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").forGetter(TintSource::defaultColor))
                    .apply(instance, TintSource::new)
    );

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity user) {
        if (stack.is(Items.BOW)) {
            return getTintBow(stack, user);
        }
        return getTintCrossbow(stack);
    }

    private int getTintBow(ItemStack stack, @Nullable LivingEntity user) {
        if (user != null) {
            ItemStack projectile = user.getProjectile(stack);

            if (user instanceof AbstractSkeleton skeleton) {
                projectile = skeleton.getArrow(projectile, 0, stack).getPickupItemStackOrigin();
            }

            return getColorFromStack(projectile);
        }

        return ARGB.opaque(this.defaultColor);
    }

    private @NotNull Integer getTintCrossbow(ItemStack stack) {
        ChargedProjectiles chargedProjectiles = stack.getOrDefault(DataComponents.CHARGED_PROJECTILES,
                ChargedProjectiles.EMPTY);

        return chargedProjectiles.itemCopies()
                .stream()
                .map(RealArrowTip::getColorFromStack)
                .filter(color -> color != -1)
                .findFirst()
                .orElse(this.defaultColor);
    }


    @Override
    public MapCodec<TintSource> type() {
        return CODEC;
    }
}
