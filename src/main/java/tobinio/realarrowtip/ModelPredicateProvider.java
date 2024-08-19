package tobinio.realarrowtip;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

/**
 * Created: 05/10/2023
 *
 * @author Tobias Frischmann
 */
public class ModelPredicateProvider {

    public static void initialize() {
        initializeBow();
        initializeCrossbow();
    }

    private static void initializeBow() {
        ModelPredicateProviderRegistry.register(Items.BOW, Identifier.ofVanilla("spectral"), (stack, world, entity, seed) -> {
            if (entity == null || !entity.isUsingItem() || entity.getActiveItem() != stack) return 0;

            return entity.getProjectileType(stack).isOf(Items.SPECTRAL_ARROW) ? 1 : 0;
        });

        ModelPredicateProviderRegistry.register(Items.BOW, Identifier.ofVanilla("tipped"), (stack, world, entity, seed) -> {
            if (entity == null || !entity.isUsingItem() || entity.getActiveItem() != stack) return 0;


            ItemStack projectile = entity.getProjectileType(stack);

            if (entity instanceof AbstractSkeletonEntity skeleton) {
                projectile = skeleton.createArrowProjectile(projectile, 0, stack).getItemStack();
            }

            if (projectile.isOf(Items.TIPPED_ARROW) || projectile.get(DataComponentTypes.POTION_CONTENTS) != null) {
                stack.setHolder(entity);
                return 1;
            }

            return 0;
        });
    }

    private static void initializeCrossbow() {
        ModelPredicateProviderRegistry.register(Items.CROSSBOW, Identifier.ofVanilla("spectral"), (stack, world, entity, seed) -> {
            if (!CrossbowItem.isCharged(stack)) return 0;

            ChargedProjectilesComponent chargedProjectiles = stack.getOrDefault(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.DEFAULT);
            var isSpectral = chargedProjectiles.getProjectiles()
                    .stream()
                    .anyMatch(itemStack -> itemStack.isOf(Items.SPECTRAL_ARROW));

            return isSpectral ? 1 : 0;
        });

        ModelPredicateProviderRegistry.register(Items.CROSSBOW, Identifier.ofVanilla("tipped"), (stack, world, entity, seed) -> {
            if (!CrossbowItem.isCharged(stack)) return 0;

            ChargedProjectilesComponent chargedProjectiles = stack.getOrDefault(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.DEFAULT);
            var isTipped = chargedProjectiles.getProjectiles()
                    .stream()
                    .anyMatch(itemStack -> itemStack.isOf(Items.TIPPED_ARROW));

            if (isTipped) {
                stack.setHolder(entity);
                return 1;
            }

            return 0;
        });
    }
}
