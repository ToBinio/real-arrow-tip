package tobinio.realarrowtip;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealArrowTip implements ModInitializer {
    public static String modID = "real_arrow_tip";

    public static final Logger LOGGER = LoggerFactory.getLogger("Real Arrow Tip");

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {


        // 0 = default arrow
        // 0.1 = tipped arrow
        // 0.2 = spectral arrow

        ModelPredicateProviderRegistry.register(Items.BOW, new Identifier("arrow_type"), (stack, world, entity, seed) -> {
            if (entity == null)
                return 0;

            Item item = entity.getProjectileType(stack).getItem();

            if (item == Items.TIPPED_ARROW)
                return 0.1f;

            if (item == Items.SPECTRAL_ARROW)
                return 0.2f;

            return 0;
        });

    }
}
