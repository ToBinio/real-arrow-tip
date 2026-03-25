package tobinio.realarrowtip.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.resources.model.ClientItemInfoLoader;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.realarrowtip.RealArrowTip;
import tobinio.realarrowtip.config.Config;

/**
 * Created: 05.10.25
 *
 * @author Tobias Frischmann
 */
@Mixin (ClientItemInfoLoader.class)
public class ClientItemInfoLoaderMixin {

    @Inject(method = "method_65926", at= @At (value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/Resource;openAsReader()Ljava/io/BufferedReader;"))
    private static void real_arrow_tip$useOwnItemModels(Identifier itemId, Resource resource,
            RegistryAccess.Frozen immutable, CallbackInfoReturnable<ClientItemInfoLoader.PendingLoad> cir,
            @Local(ordinal = 1) LocalRef<Identifier> identifier) {
        Config config = Config.HANDLER.instance();

        if (identifier.get().getNamespace().equals(RealArrowTip.MOD_ID)) {
            if( config.ignoredItems.contains(identifier.get().getPath())) {
                return;
            }

            identifier.set(Identifier.parse(identifier.get().getPath()));
        }
    }
}
