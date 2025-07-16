# NoExpensive

[English](README.md) | [中文 (Chinese)](README.zh.md)

NoExpensive is a Minecraft mod that removes the anvil's expensive restrictions and allows compatibility between any
enchantments. It is designed to be lightweight, highly compatible, and easy to configure for both modern and legacy
Minecraft versions.

[![Version](https://img.shields.io/github/v/tag/Enaium/fabric-mod-NoExpensive?label=version&style=flat-square&logo=github)](https://github.com/Enaium/fabric-mod-NoExpensive/releases)
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/387108?style=flat-square&logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/noexpensive)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/2nz0kJ1N?style=flat-square&logo=modrinth)](https://modrinth.com/mod/noexpensive)

---

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Commands](#commands)
- [Configuration](#configuration)
- [Compatibility](#compatibility)
- [Legacy Version Notes](#legacy-version-notes)
- [Support & Contributing](#support--contributing)

---

## Features

- Removes the "Too Expensive!" anvil restriction
- Allows any enchantment combinations (including normally incompatible ones)
- Configurable maximum repair cost
- Custom enchantment compatibility rules
- Supports both modern and legacy Minecraft versions

---

## Installation

1. Download the latest release
   from [CurseForge](https://www.curseforge.com/minecraft/mc-mods/noexpensive), [Modrinth](https://modrinth.com/mod/noexpensive),
   or [GitHub Releases](https://github.com/Enaium/fabric-mod-NoExpensive/releases).
2. Place the mod JAR file into your Minecraft `mods` folder.
3. Launch Minecraft with Fabric or your preferred mod loader.

---

## Commands

| Command                                                         | Description                                                                     |
|-----------------------------------------------------------------|---------------------------------------------------------------------------------|
| `/noexpensive maxLevel`                                         | Get the current maximum repair cost level.                                      |
| `/noexpensive maxLevel <level>`                                 | Set the maximum repair cost level (default: 39). Set to 0 for auto calculation. |
| `/noexpensive combineHigher`                                    | Get whether combining higher-level enchantments is enabled.                     |
| `/noexpensive combineHigher <true/false>`                       | Enable or disable combining higher-level enchantments (default: false).         |
| `/noexpensive compatibility PUT <enchantment> <enchantment>`    | Allow two enchantments to be compatible.                                        |
| `/noexpensive compatibility REMOVE <enchantment> <enchantment>` | Remove compatibility between two enchantments.                                  |
| `/noexpensive compatibility list`                               | List all custom compatibilities.                                                |
| `/noexpensive reload`                                           | Reload the configuration file.                                                  |
| `/noexpensive reset`                                            | Reset the configuration to default.                                             |

---

## Configuration

- **Location:** `.minecraft/NoExpensive.json`
- You can reload the config in-game with `/noexpensive reload`.
- Ensure the config file is properly formatted JSON.

### Example Config

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

#### Default Compatibilities

- **Mending** and **Infinity** are compatible by default.
- **Multishot** and **Piercing** are compatible by default.
- **Sharpness**, **Smite**, and **Bane of Arthropods** are mutually compatible by default.
- **Protection**, **Projectile Protection**, **Blast Protection**, and **Fire Protection** are mutually compatible by
  default.

---

## Compatibility

- Supports a wide range of Minecraft versions (see folders for specific support).
- Designed for Fabric mod loader, but may work with others if compatible.
- For legacy versions (1.7.x and below), enchantment IDs are numbers, not strings.

---

## Legacy Version Notes

- In 1.7.x and below, the compatibility config uses numeric enchantment IDs instead of string names due to how
  enchantments are registered in those versions.

---

## Support & Contributing

- For bug reports, feature requests, or questions, please open
  an [issue](https://github.com/Enaium/fabric-mod-NoExpensive/issues).
- Contributions are welcome! Feel free to submit pull requests.

---

Enjoy a restriction-free anvil experience!
