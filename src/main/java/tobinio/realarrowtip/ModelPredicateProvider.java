package tobinio.realarrowtip;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
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
            if (entity == null) return 0;

            return entity.getProjectileType(stack).isOf(Items.SPECTRAL_ARROW) ? 1 : 0;
        });

        ModelPredicateProviderRegistry.register(Items.BOW, new Identifier("tipped"), (stack, world, entity, seed) -> {
            if (entity == null) return 0;

            if (entity instanceof StrayEntity)
                return 1;

            return entity.getProjectileType(stack).isOf(Items.TIPPED_ARROW) ? 1 : 0;
        });
    }

    private static void initializeCrossbow() {
        ModelPredicateProviderRegistry.register(Items.CROSSBOW, new Identifier("spectral"), (stack, world, entity, seed) -> {
            if (!CrossbowItem.isCharged(stack)) return 0;

            return CrossbowItem.hasProjectile(stack, Items.SPECTRAL_ARROW) ? 1 : 0;
        });

        ModelPredicateProviderRegistry.register(Items.CROSSBOW, new Identifier("tipped"), (stack, world, entity, seed) -> {
            if (!CrossbowItem.isCharged(stack)) return 0;

            return CrossbowItem.hasProjectile(stack, Items.TIPPED_ARROW) ? 1 : 0;
        });
    }
}
