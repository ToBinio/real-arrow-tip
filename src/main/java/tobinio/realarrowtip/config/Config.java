package tobinio.realarrowtip.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.List;

import static tobinio.realarrowtip.RealArrowTip.MOD_ID;
import static tobinio.realarrowtip.RealArrowTip.id;

/**
 * Created: 12.10.25
 *
 * @author Tobias Frischmann
 */
public class Config {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(id("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("%s.json5".formatted(MOD_ID)))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public boolean renderCustomArrowEntity = true;

    @SerialEntry
    public List<String> ignoredItems = new ArrayList<>();
}
