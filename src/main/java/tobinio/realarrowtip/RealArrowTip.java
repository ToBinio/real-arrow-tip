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
import tobinio.realarrowtip.model.charge.BowChargeTypeProperty;
import tobinio.realarrowtip.model.charge.CrossbowChargeTypeProperty;
import tobinio.realarrowtip.model.tint.BowTintSource;
import tobinio.realarrowtip.model.tint.CrossbowTintSource;

public class RealArrowTip implements ClientModInitializer {
    public static String modID = "real_arrow_tip";

    public static final Logger LOGGER = LoggerFactory.getLogger("Real Arrow Tip");

    @Override
    public void onInitializeClient() {
        SelectProperties.ID_MAPPER.put(id("crossbow_charge_type"), CrossbowChargeTypeProperty.TYPE);
        SelectProperties.ID_MAPPER.put(id("bow_charge_type"), BowChargeTypeProperty.TYPE);

        TintSourceTypes.ID_MAPPER.put(id("bow"), BowTintSource.CODEC);
        TintSourceTypes.ID_MAPPER.put(id("crossbow"), CrossbowTintSource.CODEC);
    }

    public static Identifier id(String path) {
        return Identifier.of(modID, path);
    }

    public static int getColorFromStack(ItemStack stack) {
        return ColorHelper.fullAlpha((stack.getOrDefault(DataComponentTypes.POTION_CONTENTS,
                PotionContentsComponent.DEFAULT)).getColor());
    }
}
