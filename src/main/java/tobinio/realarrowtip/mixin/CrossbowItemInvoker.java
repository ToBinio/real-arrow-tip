package tobinio.realarrowtip.mixin;

import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

/**
 * Created: 04/10/2023
 *
 * @author Tobias Frischmann
 */

@Mixin (CrossbowItem.class)
public interface CrossbowItemInvoker {
    @Invoker ("getProjectiles")
    static List<ItemStack> invokerGetProjectiles(ItemStack crossbow) {
        throw new AssertionError();
    }
}
