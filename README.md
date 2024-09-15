# NoExpensive

The anvil has no expensive restrictions and compatible to any enchantment.

![Version](https://img.shields.io/github/v/tag/Enaium/fabric-mod-NoExpensive?label=version&style=flat-square&logo=github)
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/387108?style=flat-square&logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/noexpensive)

## Commands

- `/noexpensive maxLevel` Get the maximum level of the repair cost.
- `/noexpensive maxLevel <level>` Set the maximum level of the repair cost. (default: 39) If the level is set to 0, the
  repair cost will be auto calculated.
- `/noexpensive combineHigher` Get whether to combine the higher level enchantment.
- `/noexpensive combineHigher <true|false>` Set whether to combine the higher level enchantment. (default: false)
- `/noexpensive compatibility PUT <enchantment> <enchantment>` Put a compatibility between two enchantments.
- `/noexpensive compatibility REMOVE <enchantment> <enchantment>` Remove a compatibility between two enchantments.
- `/noexpensive compatibility list` List all compatibilities.
- `/noexpensive reload` Reload the config.
- `/noexpensive reset` Reset the config.

## Config

Location: `.minecraft/NoExpensive.json`, You can also use the command `/noexpensive reload` to reload the config, but
you must format the config correctly.

Mending and Infinity are compatible by default.
Multishot and Piercing are compatible by default.
Sharpness, Smite and Bane of Arthropods among themselves are compatible by default.
Protection, Projectile Protection, Blast Protection and Fire Protection among themselves are compatible by default.

```json
{
  "maxLevel": 39,
  "combineHigher": false,
  "compatibility": {
    "minecraft:mending": [
      "minecraft:infinity"
    ],
    "minecraft:multishot": [
      "minecraft:piercing"
    ],
    "minecraft:sharpness": [
      "minecraft:smite",
      "minecraft:bane_of_arthropods"
    ],
    "minecraft:smite": [
      "minecraft:bane_of_arthropods",
      "minecraft:sharpness"
    ],
    "minecraft:bane_of_arthropods": [
      "minecraft:sharpness",
      "minecraft:smite"
    ],
    "minecraft:density": [
      "minecraft:breach",
      "minecraft:sharpness",
      "minecraft:smite",
      "minecraft:bane_of_arthropods"
    ],
    "minecraft:breach": [
      "minecraft:density",
      "minecraft:sharpness",
      "minecraft:smite",
      "minecraft:bane_of_arthropods"
    ],
    "minecraft:protection": [
      "minecraft:projectile_protection",
      "minecraft:blast_protection",
      "minecraft:fire_protection"
    ],
    "minecraft:projectile_protection": [
      "minecraft:blast_protection",
      "minecraft:fire_protection",
      "minecraft:protection"
    ],
    "minecraft:blast_protection": [
      "minecraft:fire_protection",
      "minecraft:protection",
      "minecraft:projectile_protection"
    ],
    "minecraft:fire_protection": [
      "minecraft:protection",
      "minecraft:projectile_protection",
      "minecraft:blast_protection"
    ]
  }
}
```

## Legacy version

- The Compatibility config is number(enchantment id), not string(enchantment name), because the enchantment uses number
  to register in 1.7.x and below.