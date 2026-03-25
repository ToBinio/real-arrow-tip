package tobinio.realarrowtip;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tobinio.realarrowtip.config.Config;
import tobinio.realarrowtip.model.ChargeTypeProperty;
import tobinio.realarrowtip.model.TintSource;

public class RealArrowTip implements ClientModInitializer {
    public static String MOD_ID = "real_arrow_tip";

    public static final Logger LOGGER = LoggerFactory.getLogger("Real Arrow Tip");

    @Override
    public void onInitializeClient() {
        Config.HANDLER.load();

        SelectItemModelProperties.ID_MAPPER.put(id("charge_type"), ChargeTypeProperty.TYPE);
        ItemTintSources.ID_MAPPER.put(id("projectile"), TintSource.CODEC);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static int getColorFromStack(ItemStack stack) {
        PotionContents component = stack.get(DataComponents.POTION_CONTENTS);
        if(component == null) return -1;

        return ARGB.opaque(component.getColor());
    }
}
