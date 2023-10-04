package tobinio.realarrowtip.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Created: 03/10/2023
 * @author Tobias Frischmann
 */

@Mixin (ItemColors.class)
public abstract class ItemColorsMixin {
    @Inject (method = "create", at = @At (value = "RETURN"))
    private static void create(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir,
            @Local ItemColors itemColors) {

        itemColors.register((stack, tintIndex) -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;

            if (player == null || tintIndex != 1) return -1;

            ItemStack projectileType = player.getProjectileType(stack);

            if (!(projectileType.getItem() instanceof TippedArrowItem)) return -1;

            return PotionUtil.getColor(projectileType);
        }, Items.BOW);

        itemColors.register((stack, tintIndex) -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;

            if (player == null || tintIndex != 1) return -1;

            Optional<ItemStack> tippedArrow = CrossbowItemInvoker.invokerGetProjectiles(stack)
                    .stream()
                    .filter(itemStack -> itemStack.isOf(Items.TIPPED_ARROW))
                    .findFirst();

            return tippedArrow.map(PotionUtil::getColor).orElse(-1);
        }, Items.CROSSBOW);
    }
}
