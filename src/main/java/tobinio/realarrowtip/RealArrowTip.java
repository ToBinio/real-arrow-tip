package tobinio.realarrowtip;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.client.render.item.tint.TintSourceTypes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tobinio.realarrowtip.model.ChargeTypeProperty;
import tobinio.realarrowtip.model.TintSource;

public class RealArrowTip implements ClientModInitializer {
    public static String modID = "real_arrow_tip";

    public static final Logger LOGGER = LoggerFactory.getLogger("Real Arrow Tip");

    @Override
    public void onInitializeClient() {
        SelectProperties.ID_MAPPER.put(id("charge_type"), ChargeTypeProperty.TYPE);
        TintSourceTypes.ID_MAPPER.put(id("projectile"), TintSource.CODEC);
    }

    public static Identifier id(String path) {
        return Identifier.of(modID, path);
    }

    public static int getColorFromStack(ItemStack stack) {
        PotionContentsComponent component = stack.get(DataComponentTypes.POTION_CONTENTS);
        if(component == null) return -1;

        return ColorHelper.fullAlpha(component.getColor());
    }
}
