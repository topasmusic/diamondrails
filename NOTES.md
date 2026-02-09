# DiamondRails project notes (2026-01-22)

## Status
- Mod builds and runs on Minecraft 1.21.11 (Fabric).
- Current mod version: **1.4** (see `gradle.properties`).

## Gameplay changes
- Custom rail top speeds (bps):
  - Diamond Rail: **32**
  - Enhanced Diamond Rail: **90**
  - Netherite Rail: **159**
- Player minecarts keep momentum when leaving mod rails and riding onto vanilla rails.
  - This applies **only** to player‑ridden carts.
  - Hopper/Furnace/other carts stay vanilla.
- Vanilla powered rails remain vanilla (8 bps), and vanilla behavior is unchanged unless a player cart has momentum from mod rails.

## Key code changes
- Speed logic is handled in these mixins:
  - `src/main/java/diamond/rails/seefourr/mixin/AbstractMinecartEntityMixin.java`
  - `src/main/java/diamond/rails/seefourr/mixin/DefaultMinecartControllerMixin.java`
  - `src/main/java/diamond/rails/seefourr/mixin/ExperimentalMinecartControllerMixin.java`
- Speed cap (default controller) is relaxed **only** when a player cart has last custom speed.
- `DiamondRailsSpeedAccessor` lives in:
  - `src/main/java/diamond/rails/seefourr/access/DiamondRailsSpeedAccessor.java`
- Temporary speedometer HUD was removed; client init now only sets block render layers.

## Build
- Build JAR: `.\gradlew build`
- Output: `build/libs/diamondrails-1.4.jar`

