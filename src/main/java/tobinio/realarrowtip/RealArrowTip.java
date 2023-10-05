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
        ModelPredicateProvider.initialize();
    }
}
