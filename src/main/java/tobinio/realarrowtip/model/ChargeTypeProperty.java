package tobinio.realarrowtip.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static tobinio.realarrowtip.RealArrowTip.getColorFromStack;

/**
 * Created: 18.12.24
 *
 * @author Tobias Frischmann
 */
@Environment (EnvType.CLIENT)
public record ChargeTypeProperty() implements SelectItemModelProperty<String> {
    public static final SelectItemModelProperty.Type<ChargeTypeProperty, String> TYPE;
    public static final Codec<String> VALUE_CODEC;

    static {
        VALUE_CODEC = Codec.STRING;
        TYPE = SelectItemModelProperty.Type.create(
                MapCodec.unit(new ChargeTypeProperty()), VALUE_CODEC
        );
    }

    @Override
    public String get(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity user,
            int seed, ItemDisplayContext displayContext) {

        if (stack.is(Items.BOW)) {
            return getChargeTypeBow(stack, user);
        }
        return getChargeTypeCrossbow(stack);
    }

    private static @NotNull String getChargeTypeBow(ItemStack stack, @Nullable LivingEntity user) {
        if (user != null) {
            ItemStack projectile = user.getProjectile(stack);

            if (user instanceof AbstractSkeleton skeleton) {
                projectile = skeleton.getArrow(projectile, 0, stack).getPickupItemStackOrigin();
            }

            return getProjectileType(projectile);
        }

        return "none";
    }

    private static String getChargeTypeCrossbow(ItemStack stack) {
        ChargedProjectiles chargedProjectilesComponent = stack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedProjectilesComponent == null || chargedProjectilesComponent.isEmpty()) {
            return "none";
        } else {
            ItemStack projectile = chargedProjectilesComponent.itemCopies().getFirst();
            return getProjectileType(projectile);
        }
    }

    private static String getProjectileType(ItemStack projectile) {
        var color = getColorFromStack(projectile);

        // needed since stray & bogged shoot normal arrow with effect
        if(color != -1 && projectile.is(Items.ARROW)) {
            return Items.TIPPED_ARROW.builtInRegistryHolder().getRegisteredName();
        }

        return projectile.getItem().toString();
    }

    @Override
    public Codec<String> valueCodec() {
        return VALUE_CODEC;
    }

    @Override
    public SelectItemModelProperty.Type<ChargeTypeProperty, String> type() {
        return TYPE;
    }
}