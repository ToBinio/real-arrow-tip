package tobinio.realarrowtip.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.state.ArrowEntityRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.realarrowtip.HasColor;

/**
 * Created: 03/10/2023
 *
 * @author Tobias Frischmann
 */

@Mixin (ArrowEntityRenderer.class)
public abstract class ArrowEntityRendererMixin {
    @Inject (at = @At ("TAIL"), method = "updateRenderState(Lnet/minecraft/entity/projectile/ArrowEntity;Lnet/minecraft/client/render/entity/state/ArrowEntityRenderState;F)V")
    private void updateRenderState(ArrowEntity arrowEntity, ArrowEntityRenderState arrowEntityRenderState, float f,
            CallbackInfo ci) {
        ((HasColor) arrowEntityRenderState).real_arrow_tip$setColor(arrowEntity.getColor());
    }
}
