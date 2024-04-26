package tobinio.realarrowtip.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Created: 03/10/2023
 *
 * @author Tobias Frischmann
 */

@Mixin (ItemColors.class)
public abstract class ItemColorsMixin {
    @Inject (method = "create", at = @At (value = "RETURN"))
    private static void create(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir,
            @Local ItemColors itemColors) {

        itemColors.register((stack, tintIndex) -> {
            if (tintIndex != 1) return -1;

            if (stack.getHolder() instanceof LivingEntity livingEntity) {

                ItemStack projectile = livingEntity.getProjectileType(stack);

                if (livingEntity instanceof AbstractSkeletonEntity skeleton) {
                    projectile = skeleton.createArrowProjectile(projectile, 0).getItemStack();
                }

                PotionContentsComponent potion = projectile.get(DataComponentTypes.POTION_CONTENTS);

                if (potion != null) {
                    return ColorHelper.Argb.fullAlpha(potion.getColor());
                }
            }

            return -1;
        }, Items.CROSSBOW, Items.BOW);
    }
}
