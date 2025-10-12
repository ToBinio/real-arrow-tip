package tobinio.realarrowtip.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.minecraft.text.Text;

/**
 * Created: 12.10.25
 *
 * @author Tobias Frischmann
 */

public class ConfigMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parentScreen -> YetAnotherConfigLib.create(Config.HANDLER,
                (defaults, config, builder) -> builder.title(Text.translatable("real-arrow-tip.config.title"))
                        .category(ConfigCategory.createBuilder()
                                .name(Text.translatable("real-arrow-tip.config.category-title"))
                                .tooltip(Text.literal("All the settings"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("real-arrow-tip.config.render-custom-arrow-entity.name"))
                                        .description(OptionDescription.of(Text.translatable(
                                                "real-arrow-tip.config.render-custom-arrow-entity.description")))
                                        .binding(defaults.renderCustomArrowEntity,
                                                () -> config.renderCustomArrowEntity,
                                                newVal -> config.renderCustomArrowEntity = newVal)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(ListOption.<String>createBuilder()
                                        .name(Text.translatable("real-arrow-tip.config.config.ignored-items.name"))
                                        .description(OptionDescription.of(Text.translatable(
                                                "real-arrow-tip.config.config.ignored-items.description")))
                                        .binding(defaults.ignoredItems,
                                                () -> config.ignoredItems,
                                                newVal -> config.ignoredItems = newVal)
                                        .controller(StringControllerBuilder::create)
                                        .initial("")
                                        .collapsed(true)
                                        .build())
                                .build())
        ).generateScreen(parentScreen);
    }
}
