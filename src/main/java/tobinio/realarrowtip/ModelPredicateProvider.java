package tobinio.realarrowtip;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.item.BowItem;
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
        ModelPredicateProviderRegistry.register(Items.BOW, new Identifier("spectral"), (stack, world, entity, seed) -> {
            if (entity == null || !entity.isUsingItem() || entity.getActiveItem() != stack) return 0;

            return entity.getProjectileType(stack).isOf(Items.SPECTRAL_ARROW) ? 1 : 0;
        });

        ModelPredicateProviderRegistry.register(Items.BOW, new Identifier("tipped"), (stack, world, entity, seed) -> {
            if (entity == null || !entity.isUsingItem() || entity.getActiveItem() != stack) return 0;


            ItemStack projectile = entity.getProjectileType(stack);

            if (entity instanceof AbstractSkeletonEntity skeleton) {
                projectile = skeleton.createArrowProjectile(projectile, 0).getItemStack();
            }

            if (projectile.isOf(Items.TIPPED_ARROW) || projectile.get(DataComponentTypes.POTION_CONTENTS) != null) {
                stack.setHolder(entity);
                return 1;
            }

            return 0;
        });
    }

    private static void initializeCrossbow() {
        ModelPredicateProviderRegistry.register(Items.CROSSBOW, new Identifier("spectral"), (stack, world, entity, seed) -> {
            if (!CrossbowItem.isCharged(stack) || entity == null) return 0;

            return entity.getProjectileType(stack).isOf(Items.SPECTRAL_ARROW) ? 1 : 0;
        });

        ModelPredicateProviderRegistry.register(Items.CROSSBOW, new Identifier("tipped"), (stack, world, entity, seed) -> {
            if (!CrossbowItem.isCharged(stack) || entity == null) return 0;

            if (entity.getProjectileType(stack).isOf(Items.TIPPED_ARROW)) {
                stack.setHolder(entity);
                return 1;
            }

            return 0;
        });
    }
}
