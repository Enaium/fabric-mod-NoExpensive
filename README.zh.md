# NoExpensive

[English](README.md) | [中文 (Chinese)](README.zh.md)

NoExpensive 是一个 Minecraft 模组，移除了铁砧的“过于昂贵”限制，并允许任意附魔之间的兼容。该模组轻量、兼容性强，且易于配置，支持现代与旧版
Minecraft。

[![Version](https://img.shields.io/github/v/tag/Enaium/fabric-mod-NoExpensive?label=version&style=flat-square&logo=github)](https://github.com/Enaium/fabric-mod-NoExpensive/releases)
[![CurseForge 下载量](https://img.shields.io/curseforge/dt/387108?style=flat-square&logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/noexpensive)
[![Modrinth 下载量](https://img.shields.io/modrinth/dt/2nz0kJ1N?style=flat-square&logo=modrinth)](https://modrinth.com/mod/noexpensive)

---

## 目录

- [功能特性](#功能特性)
- [安装方法](#安装方法)
- [指令说明](#指令说明)
- [配置文件](#配置文件)
- [兼容性](#兼容性)
- [旧版本说明](#旧版本说明)
- [支持与贡献](#支持与贡献)

---

## 功能特性

- 移除铁砧“过于昂贵！”限制
- 允许任意附魔组合（包括原本不兼容的）
- 可配置最大修复消耗等级
- 自定义附魔兼容性规则
- 支持现代与旧版 Minecraft

---

## 安装方法

1.

前往 [CurseForge](https://www.curseforge.com/minecraft/mc-mods/noexpensive)、[Modrinth](https://modrinth.com/mod/noexpensive)
或 [GitHub Releases](https://github.com/Enaium/fabric-mod-NoExpensive/releases) 下载最新版。

2. 将模组 JAR 文件放入你的 Minecraft `mods` 文件夹。
3. 使用 Fabric 或其他兼容的模组加载器启动游戏。

---

## 指令说明

| 指令                                            | 说明                         |
|-----------------------------------------------|----------------------------|
| `/noexpensive maxLevel`                       | 获取当前最大修复消耗等级。              |
| `/noexpensive maxLevel <level>`               | 设置最大修复消耗等级（默认 39，0 为自动计算）。 |
| `/noexpensive combineHigher`                  | 查询是否允许合成更高等级附魔。            |
| `/noexpensive combineHigher <true/false>`     | 设置是否允许合成更高等级附魔（默认 false）。  |
| `/noexpensive compatibility PUT <附魔> <附魔>`    | 允许两个附魔兼容。                  |
| `/noexpensive compatibility REMOVE <附魔> <附魔>` | 移除两个附魔的兼容性。                |
| `/noexpensive compatibility list`             | 列出所有自定义兼容性。                |
| `/noexpensive reload`                         | 重新加载配置文件。                  |
| `/noexpensive reset`                          | 重置配置为默认值。                  |

---

## 配置文件

- **位置：** `.minecraft/NoExpensive.json`
- 可通过 `/noexpensive reload` 指令在游戏内重新加载配置。
- 请确保配置文件为合法 JSON 格式。

### 配置示例

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

#### 默认兼容性

- **经验修补（Mending）** 与 **无限（Infinity）** 默认兼容。
- **多重射击（Multishot）** 与 **穿透（Piercing）** 默认兼容。
- **锋利（Sharpness）**、**亡灵杀手（Smite）**、**节肢杀手（Bane of Arthropods）** 彼此默认兼容。
- **保护（Protection）**、**弹射物保护（Projectile Protection）**、**爆炸保护（Blast Protection）**、**火焰保护（Fire Protection）
  ** 彼此默认兼容。

---

## 兼容性

- 支持多种 Minecraft 版本（详见各文件夹）。
- 主要为 Fabric 加载器设计，理论上兼容其他加载器。
- 旧版（1.7.x 及以下）配置文件中的附魔 ID 使用数字而非字符串。

---

## 旧版本说明

- 在 1.7.x 及以下版本，兼容性配置需使用数字型附魔 ID，因为这些版本的附魔注册方式不同。

---

## 支持与贡献

- 如需报告 Bug、请求新功能或有疑问，请在 [GitHub Issues](https://github.com/Enaium/fabric-mod-NoExpensive/issues) 提出。
- 欢迎贡献代码，欢迎提交 Pull Request！

---

祝你享受无“过于昂贵”限制的铁砧体验！ 