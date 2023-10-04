package tobinio.realarrowtip;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealArrowTip implements ModInitializer {
    public static String modID = "real_arrow_tip";

    public static final Logger LOGGER = LoggerFactory.getLogger("Real Arrow Tip");

    @Override
    public void onInitialize() {
        ModelPredicateProviderRegistry.register(Items.BOW, new Identifier("arrow_type"), RealArrowTip::arrowTypeBow);
        ModelPredicateProviderRegistry.register(Items.CROSSBOW, new Identifier("arrow_type"), RealArrowTip::arrowTypeCrossbow);
    }


    // 0 = default arrow
    // 0.1 = tipped arrow
    // 0.2 = spectral arrow
    private static float arrowTypeBow(ItemStack stack, ClientWorld world, LivingEntity entity, int seed) {
        if (entity == null) return 0;

        Item item = entity.getProjectileType(stack).getItem();

        if (item == Items.TIPPED_ARROW) return 0.1f;

        if (item == Items.SPECTRAL_ARROW) return 0.2f;

        return 0;
    }

    private static float arrowTypeCrossbow(ItemStack stack, ClientWorld world, LivingEntity entity, int seed) {

        if (!CrossbowItem.isCharged(stack)) return 0;

        if (CrossbowItem.hasProjectile(stack, Items.TIPPED_ARROW)) return 0.1f;
        if (CrossbowItem.hasProjectile(stack, Items.SPECTRAL_ARROW)) return 0.2f;

        return 0;
    }
}
