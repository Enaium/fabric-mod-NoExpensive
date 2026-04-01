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

## Configuration

This mod uses the MineConf for configuration.

### Commands

| Config         | Description                                                                 |
|----------------|-----------------------------------------------------------------------------|
| max_level      | The maximum repair cost level (default: 39). Set to 0 for auto calculation. |
| combine_higher | Enable or disable combining higher-level enchantments (default: false).     |
| compatibility  | Allow two enchantments to be compatible.                                    |

### Default Compatibilities

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
