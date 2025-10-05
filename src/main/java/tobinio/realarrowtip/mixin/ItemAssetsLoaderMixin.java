package tobinio.realarrowtip.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.item.ItemAssetsLoader;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.realarrowtip.RealArrowTip;

/**
 * Created: 05.10.25
 *
 * @author Tobias Frischmann
 */
@Mixin (ItemAssetsLoader.class)
public class ItemAssetsLoaderMixin {

    @Inject(method = "method_65926", at= @At (value = "INVOKE", target = "Lnet/minecraft/resource/Resource;getReader()Ljava/io/BufferedReader;"))
    private static void real_arrow_tip$useOwnItemModels(Identifier itemId, Resource resource,
            DynamicRegistryManager.Immutable immutable, CallbackInfoReturnable<ItemAssetsLoader.Definition> cir,
            @Local(ordinal = 1) LocalRef<Identifier> identifier) {
        if (identifier.get().getNamespace().equals(RealArrowTip.MOD_ID)) {
            identifier.set(Identifier.of(identifier.get().getPath()));
        }
    }
}
