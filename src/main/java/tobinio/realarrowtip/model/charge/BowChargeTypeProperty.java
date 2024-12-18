package tobinio.realarrowtip.model.charge;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;

import static tobinio.realarrowtip.RealArrowTip.getColorFromStack;

/**
 * Created: 18.12.24
 *
 * @author Tobias Frischmann
 */
@Environment (EnvType.CLIENT)
public record BowChargeTypeProperty() implements SelectProperty<ChargeType> {
    public static final Type<BowChargeTypeProperty, ChargeType> TYPE = Type.create(
            MapCodec.unit(new BowChargeTypeProperty()), ChargeType.CODEC
    );

    public ChargeType getValue(
            ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i,
            ModelTransformationMode modelTransformationMode
    ) {

        if (livingEntity != null) {
            ItemStack projectile = livingEntity.getProjectileType(itemStack);

            if (livingEntity instanceof AbstractSkeletonEntity skeleton) {
                projectile = skeleton.createArrowProjectile(projectile, 0, itemStack).getItemStack();
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

    @Override
    public Type<BowChargeTypeProperty, ChargeType> getType() {
        return TYPE;
    }
}