# Real Arrow Tip

a fabric mod which makes the bow display the correct arrow

The time has ended where your bow simply shoots a different arrow than displayed!

## features

all arrows show their true selves

- Bow displays correct colored tip when shooting tipped arrows
- Bow displays spectral arrows
- Crossbow displays correct colored tip when shooting tipped arrows
- Crossbow displays spectral arrows
- Arrow entity displays colored tip when it's a tipped arrow

---

## Supporting Mod in Texturepacks

Adding support for this mod in your own Texturepack can easily be done by overwriting the entries in the `real_arrow_tip` namespace see [Texturepack](src/main/resources/assets/real_arrow_tip)

## Supporting more Arrows 

This mod adds a new `real_arrow_tip:charge_type` for the crossbow and bow item. It sets the item identifier of the currently used arrow as its value, making it possible to support any arrow.

The texture pack needs to include the required arrow textures (see this mod’s resource pack for examples) and update `real_arrow_tip/items/bow.json` and `real_arrow_tip/items/crossbow.json` to reference the new textures.

To create these `.json` files, you can use `item-model-generator.py.` Simply add the new arrows, and the `.json` files will be generated.

> Notice:<br>
> Supporting other Texturepacks or modded arrows is outside the scope of this mod.
> Feel free to publish any resource pack that adds compatibility for Texturepacks or modded arrows.
