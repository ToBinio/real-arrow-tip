package tobinio.realarrowtip.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created: 05/10/2023
 *
 * @author Tobias Frischmann
 */
@Mixin (net.minecraft.item.BowItem.class)
public abstract class BowItemMixin extends ItemMixin {

    @Override
    protected void useOnEntity(World world, LivingEntity user, ItemStack stack, int remainingUseTicks,
            CallbackInfo ci) {

        stack.setHolder(user);
    }
}
