package tobinio.realarrowtip.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static tobinio.realarrowtip.RealArrowTip.getColorFromStack;

/**
 * Created: 18.12.24
 *
 * @author Tobias Frischmann
 */
@Environment (EnvType.CLIENT)
public record ChargeTypeProperty() implements SelectProperty<String> {
    public static final SelectProperty.Type<ChargeTypeProperty, String> TYPE;
    public static final Codec<String> VALUE_CODEC;

    static {
        VALUE_CODEC = Codec.STRING;
        TYPE = SelectProperty.Type.create(
                MapCodec.unit(new ChargeTypeProperty()), VALUE_CODEC
        );
    }

    @Override
    public String getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user,
            int seed, ItemDisplayContext displayContext) {

        if (stack.isOf(Items.BOW)) {
            return getChargeTypeBow(stack, user);
        }
        return getChargeTypeCrossbow(stack);
    }

    private static @NotNull String getChargeTypeBow(ItemStack stack, @Nullable LivingEntity user) {
        if (user != null) {
            ItemStack projectile = user.getProjectileType(stack);

            if (user instanceof AbstractSkeletonEntity skeleton) {
                projectile = skeleton.createArrowProjectile(projectile, 0, stack).getItemStack();
            }

            return getProjectileType(projectile);
        }

        return "none";
    }

    private static String getChargeTypeCrossbow(ItemStack stack) {
        ChargedProjectilesComponent chargedProjectilesComponent = stack.get(DataComponentTypes.CHARGED_PROJECTILES);
        if (chargedProjectilesComponent == null || chargedProjectilesComponent.isEmpty()) {
            return "none";
        } else {
            ItemStack projectile = chargedProjectilesComponent.getProjectiles().getFirst();
            return getProjectileType(projectile);
        }
    }

    private static String getProjectileType(ItemStack projectile) {
        var color = getColorFromStack(projectile);

        // needed since stray & bogged shoot normal arrow with effect
        if(color != -1 && projectile.isOf(Items.ARROW)) {
            return Items.TIPPED_ARROW.getRegistryEntry().getIdAsString();
        }

        return projectile.getRegistryEntry().getIdAsString();
    }

    @Override
    public Codec<String> valueCodec() {
        return VALUE_CODEC;
    }

    @Override
    public SelectProperty.Type<ChargeTypeProperty, String> getType() {
        return TYPE;
    }
}