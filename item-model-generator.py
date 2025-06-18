import os
import json

MODEL_DIR = "./src/main/resources/assets/real_arrow_tip/models/item/"

ARROW_TYPES = [
    {"item": "minecraft:spectral_arrow", "name": "spectral", "tint": None},
    {"item": "minecraft:tipped_arrow", "name": "tipped", "tint": -13083194},
]


def generate_bow_arrow_json(arrow_data):
    paths = [
        f"real_arrow_tip:item/bow_pulling_0_{arrow_data["name"]}",
        f"real_arrow_tip:item/bow_pulling_1_{arrow_data["name"]}",
        f"real_arrow_tip:item/bow_pulling_2_{arrow_data["name"]}",
    ]

    range_dispatch = {
        "type": "minecraft:range_dispatch",
        "property": "minecraft:use_duration",
        "scale": 0.05,
        "entries": [
            {
                "model": {
                    "type": "minecraft:model",
                    "model": paths[1],
                },
                "threshold": 0.65,
            },
            {
                "model": {
                    "type": "minecraft:model",
                    "model": paths[2],
                },
                "threshold": 0.9,
            },
        ],
        "fallback": {
            "type": "minecraft:model",
            "model": paths[0],
        },
    }

    if arrow_data["tint"] is not None:
        for entry in range_dispatch["entries"]:
            entry["model"]["tints"] = [{"type": "real_arrow_tip:projectile", "default": arrow_data["tint"]}]
        range_dispatch["fallback"]["tints"] = [{"type": "real_arrow_tip:projectile", "default": arrow_data["tint"]}]

    return {
        "when": arrow_data["item"],
        "model": range_dispatch,
    }


def create_bow():
    json_structure = {
        "model": {
            "type": "minecraft:condition",
            "property": "minecraft:using_item",
            "on_false": {
                "type": "minecraft:model",
                "model": "minecraft:item/bow"
            },
            "on_true": {
                "type": "minecraft:select",
                "property": "real_arrow_tip:charge_type",
                "cases": [],
                "fallback": {
                    "type": "minecraft:range_dispatch",
                    "property": "minecraft:use_duration",
                    "scale": 0.05,
                    "entries": [
                        {
                            "model": {
                                "type": "minecraft:model",
                                "model": "minecraft:item/bow_pulling_1"
                            },
                            "threshold": 0.65
                        },
                        {
                            "model": {
                                "type": "minecraft:model",
                                "model": "minecraft:item/bow_pulling_2"
                            },
                            "threshold": 0.9
                        }
                    ],
                    "fallback": {
                        "type": "minecraft:model",
                        "model": "minecraft:item/bow_pulling_0"
                    },
                },
            },
        }
    }

    for arrow in ARROW_TYPES:
        arrow_json = generate_bow_arrow_json(arrow)
        if arrow_json:
            json_structure["model"]["on_true"]["cases"].append(arrow_json)

    output_file = "generated_bow.json"
    with open(output_file, "w", encoding="utf-8") as f:
        json.dump(json_structure, f, indent=2)

    print(f"created: {output_file}")


def generate_crossbow_arrow_json(arrow_data, namespace, multishot_namespace):
    model = {
        "model": {
            "type": "minecraft:condition",
            "property": "minecraft:component",
            "predicate": "minecraft:enchantments",
            "value": [
                {
                    "enchantments": "minecraft:multishot"
                }
            ],
            "on_true": {
                "type": "minecraft:model",
                "model": f"{multishot_namespace}:item/crossbow_{arrow_data["name"]}_multishot",
            },
            "on_false": {
                "type": "minecraft:model",
                "model": f"{namespace}:item/crossbow_{arrow_data["name"]}",
            }
        },
        "when": arrow_data["item"]
    }

    if arrow_data["tint"] is not None:
        model["model"]["on_true"]["tints"] = [{"type": "real_arrow_tip:projectile", "default": arrow_data["tint"]}]
        model["model"]["on_false"]["tints"] = [{"type": "real_arrow_tip:projectile", "default": arrow_data["tint"]}]

    return model


def create_crossbow():
    json_structure = {
        "model": {
            "type": "minecraft:select",
            "property": "real_arrow_tip:charge_type",
            "cases": [],
            "fallback": {
                "type": "minecraft:condition",
                "property": "minecraft:using_item",
                "on_false": {
                    "type": "minecraft:model",
                    "model": "minecraft:item/crossbow"
                },
                "on_true": {
                    "type": "minecraft:range_dispatch",
                    "entries": [
                        {
                            "model": {
                                "type": "minecraft:model",
                                "model": "minecraft:item/crossbow_pulling_1"
                            },
                            "threshold": 0.58
                        },
                        {
                            "model": {
                                "type": "minecraft:model",
                                "model": "minecraft:item/crossbow_pulling_2"
                            },
                            "threshold": 1
                        }
                    ],
                    "fallback": {
                        "type": "minecraft:model",
                        "model": "minecraft:item/crossbow_pulling_0"
                    },
                    "property": "minecraft:crossbow/pull"
                },
            },
        }
    }

    arrow_json = generate_crossbow_arrow_json({"item": "minecraft:arrow", "name": "arrow", "tint": None}, "minecraft","real_arrow_tip")
    json_structure["model"]["cases"].append(arrow_json)
    arrow_json = generate_crossbow_arrow_json({"item": "minecraft:firework_rocket", "name": "firework", "tint": None}, "minecraft","real_arrow_tip")
    json_structure["model"]["cases"].append(arrow_json)

    for arrow in ARROW_TYPES:
        arrow_json = generate_crossbow_arrow_json(arrow, "real_arrow_tip","real_arrow_tip")
        if arrow_json:
            json_structure["model"]["cases"].append(arrow_json)

    output_file = "generated_crossbow.json"
    with open(output_file, "w", encoding="utf-8") as f:
        json.dump(json_structure, f, indent=2)

    print(f"created: {output_file}")


create_bow()
create_crossbow()
