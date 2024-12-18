package tobinio.realarrowtip.model.charge;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import org.jetbrains.annotations.Nullable;

/**
 * Created: 18.12.24
 *
 * @author Tobias Frischmann
 */
@Environment (EnvType.CLIENT)
public record CrossbowChargeTypeProperty() implements SelectProperty<ChargeType> {
    public static final SelectProperty.Type<CrossbowChargeTypeProperty, ChargeType> TYPE = SelectProperty.Type.create(
            MapCodec.unit(new CrossbowChargeTypeProperty()), ChargeType.CODEC
    );

    public ChargeType getValue(
            ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i,
            ModelTransformationMode modelTransformationMode
    ) {
        ChargedProjectilesComponent chargedProjectilesComponent = itemStack.get(DataComponentTypes.CHARGED_PROJECTILES);
        if (chargedProjectilesComponent == null || chargedProjectilesComponent.isEmpty()) {
            return ChargeType.NONE;
        } else {
            if (chargedProjectilesComponent.contains(Items.SPECTRAL_ARROW)) {
                return ChargeType.SPECTRAL;
            }

            if (chargedProjectilesComponent.contains(Items.TIPPED_ARROW)) {
                return ChargeType.TIPPED;
            }

            if (chargedProjectilesComponent.contains(Items.FIREWORK_ROCKET)) {
                return ChargeType.ROCKET;
            }

            return ChargeType.ARROW;
        }
    }

    @Override
    public SelectProperty.Type<CrossbowChargeTypeProperty, ChargeType> getType() {
        return TYPE;
    }
}