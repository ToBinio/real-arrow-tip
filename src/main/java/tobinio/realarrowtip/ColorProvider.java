package tobinio.realarrowtip;


import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.ColorHelper;

import java.util.Optional;

/**
 * Created: 19.08.24
 *
 * @author Tobias Frischmann
 */
public class ColorProvider {
    public static void initialize() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (tintIndex != 1) return -1;

            if (stack.getHolder() instanceof LivingEntity livingEntity) {

                ItemStack projectile = livingEntity.getProjectileType(stack);

                if (livingEntity instanceof AbstractSkeletonEntity skeleton) {
                    projectile = skeleton.createArrowProjectile(projectile, 0, stack).getItemStack();
                }

                return getColorFromStack(projectile);
            }

            return -1;
        }, Items.BOW);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (tintIndex != 1) return -1;

            ChargedProjectilesComponent chargedProjectiles = stack.getOrDefault(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.DEFAULT);
            Optional<ItemStack> tippedArrow = chargedProjectiles.getProjectiles()
                    .stream()
                    .filter(itemStack -> itemStack.isOf(Items.TIPPED_ARROW))
                    .findFirst();

            return tippedArrow.map(ColorProvider::getColorFromStack).orElse(-1);
        }, Items.CROSSBOW);
    }

    private static int getColorFromStack(ItemStack stack) {
        return ColorHelper.Argb.fullAlpha((stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)).getColor());
    }
}
