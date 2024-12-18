package tobinio.realarrowtip.model.charge;

import com.mojang.serialization.Codec;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.StringIdentifiable;

import java.util.List;

/**
 * Created: 18.12.24
 *
 * @author Tobias Frischmann
 */
public enum ChargeType implements StringIdentifiable {
    NONE("none"), ARROW("arrow"), SPECTRAL("spectral"), TIPPED("tipped"), ROCKET("rocket");

    public static final Codec<ChargeType> CODEC = StringIdentifiable.createCodec(ChargeType::values);
    private final String name;

    ChargeType(final String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
