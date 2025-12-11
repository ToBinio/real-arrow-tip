# Real Arrow Tip

a fabric mod which makes the bow display the correct arrow

The time has ended where your bow simply shoots a different arrow than displayed!

## Features

all arrows show their true selves

- Bow displays correct colored tip when shooting tipped arrows
- Bow displays spectral arrows
- Crossbow displays correct colored tip when shooting tipped arrows
- Crossbow displays spectral arrows
- Arrow entity displays colored tip when it's a tipped arrow

## Texturepacks supporting this mod

* [Bray's Better 3D Bow](https://modrinth.com/resourcepack/brays-better-3d-bow)

---

## Adding Support for This Mod in a Texture Pack

> Notice:\
> This mod does not provide support for third-party texture packs or modded arrows.\
> Maintaining compatibility across numerous packs and licenses is outside the project’s scope.\
> You are welcome to publish your own resource packs that add compatibility for texture packs or additional arrows.
If you do publish such a pack, feel free to notify me so it can be added to the compatibility list.

### Changing bow textures

To change the texture of the Bow you just need to overwrite the textures in the `real_arrow_tip` namespace see [Texturepack](src/main/resources/assets/real_arrow_tip)

If the bow needs to a custom Item model file, you need to update `real_arrow_tip/items/bow.json` and `real_arrow_tip/items/crossbow.json` since these files will be used instead of the `minecraft/items/`. 

## Supporting more Arrows 

This mod adds a new `real_arrow_tip:charge_type` for the crossbow and bow item. It sets the item identifier of the currently used arrow as its value, making it possible to support any arrow.

The texture pack needs to include the required arrow textures (see this mod’s resource pack for examples) and update `real_arrow_tip/items/bow.json` and `real_arrow_tip/items/crossbow.json` to reference the new textures.

To create these `.json` files, you can use `item-model-generator.py.` Simply add the new arrows, and the `.json` files will be generated.
