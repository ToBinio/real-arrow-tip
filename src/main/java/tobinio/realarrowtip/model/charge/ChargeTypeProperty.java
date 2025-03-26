package tobinio.realarrowtip.model.charge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created: 18.12.24
 *
 * @author Tobias Frischmann
 */
@Environment (EnvType.CLIENT)
public record ChargeTypeProperty() implements SelectProperty<ChargeType> {
    public static final SelectProperty.Type<ChargeTypeProperty, ChargeType> TYPE;
    public static final Codec<ChargeType> VALUE_CODEC;

    static {
        VALUE_CODEC = ChargeType.CODEC;
        TYPE = SelectProperty.Type.create(
                MapCodec.unit(new ChargeTypeProperty()), VALUE_CODEC
        );
    }

    @Override
    public ChargeType getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user,
            int seed, ItemDisplayContext displayContext) {
        if (stack.isOf(Items.BOW)) {
            return getChargeTypeBow(stack, user);
        }
        return getChargeTypeCrossbow(stack);
    }

    private static @NotNull ChargeType getChargeTypeBow(ItemStack stack, @Nullable LivingEntity user) {
        if (user != null) {
            ItemStack projectile = user.getProjectileType(stack);

            if (user instanceof AbstractSkeletonEntity skeleton) {
                projectile = skeleton.createArrowProjectile(projectile, 0, stack).getItemStack();
            }

            if (projectile.isOf(Items.SPECTRAL_ARROW)) {
                return ChargeType.SPECTRAL;
            }

            if (projectile.isOf(Items.TIPPED_ARROW) || projectile.get(DataComponentTypes.POTION_CONTENTS) != null) {
                return ChargeType.TIPPED;
            }

            if (projectile.isOf(Items.FIREWORK_ROCKET)) {
                return ChargeType.ROCKET;
            }

            return ChargeType.ARROW;
        }

        return ChargeType.NONE;
    }

    private static @NotNull ChargeType getChargeTypeCrossbow(ItemStack stack) {
        ChargedProjectilesComponent chargedProjectilesComponent = stack.get(DataComponentTypes.CHARGED_PROJECTILES);
        if (chargedProjectilesComponent == null || chargedProjectilesComponent.isEmpty()) {
            return ChargeType.NONE;
        } else {
            if (chargedProjectilesComponent.contains(Items.SPECTRAL_ARROW)) {
                return ChargeType.SPECTRAL;
            }

            if (chargedProjectilesComponent.contains(Items.TIPPED_ARROW)) {
                return ChargeType.TIPPED;
            }

            if (chargedProjectilesComponent.contains(Items.FIREWORK_ROCKET)) {
                return ChargeType.ROCKET;
            }

            return ChargeType.ARROW;
        }
    }

    @Override
    public Codec<ChargeType> valueCodec() {
        return VALUE_CODEC;
    }

    @Override
    public SelectProperty.Type<ChargeTypeProperty, ChargeType> getType() {
        return TYPE;
    }
}