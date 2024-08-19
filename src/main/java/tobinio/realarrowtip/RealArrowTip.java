package tobinio.realarrowtip;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealArrowTip implements ClientModInitializer {
    public static String modID = "real_arrow_tip";

    public static final Logger LOGGER = LoggerFactory.getLogger("Real Arrow Tip");

    @Override
    public void onInitializeClient() {
        ModelPredicateProvider.initialize();
        ColorProvider.initialize();
    }

    public static Identifier id(String path) {
        return Identifier.of(modID, path);
    }

}
