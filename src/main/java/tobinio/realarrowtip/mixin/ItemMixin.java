package tobinio.realarrowtip.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created: 05/10/2023
 *
 * @author Tobias Frischmann
 */

@Mixin (Item.class)
public class ItemMixin {

    @Inject (method = "usageTick", at = @At (value = "HEAD"))
    protected void useOnEntity(World world, LivingEntity user, ItemStack stack, int remainingUseTicks,
            CallbackInfo ci) {

    }
}
