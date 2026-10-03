# Next Session Notes

## Current Repo State

As of `2026-10-04`, `26.3` is the active default work line for this repo.

Current local port state:

- `26.3` was copied from the tracked `26.2` sources, without runtime/build files.
- Java code, recipes, assets, rail tags and mixin configuration are unchanged.
- Java `25`, Loader `0.19.5`, Fabric API `0.161.0+26.3`, Loom `1.17.21`, Gradle `9.6.0`.
- Offline clean build passed; all four Minecraft mixin targets were inspected in 26.3 bytecode.
- CI adds a separate `26.3` job; existing version jobs are unchanged.
- The user authorized the 26.3 release on GitHub, Modrinth and CurseForge after passing the ingame test. Release tag: `v1.4-mc26.3`.
- The user confirmed the ingame test passed on 2026-10-04. Separate dedicated-server testing was not reported.

Current tags:

- `v1.4-mc1.21.11`
- `v1.4-mc26.1`
- `v1.4-mc26.1.1`
- `v1.0`

Current pushed port state:

- `26.2` was added and pushed in commit `6dff0d9` on branch `1.21.x`
- the root workflow matrix now includes `26.2`
- `v1.4-mc26.2` and its public GitHub release were verified on 2026-10-04

## Version-Line Differences

`26.2`:

- previous maintained reference line
- Java `25`
- Minecraft `26.2`
- Fabric Loader `0.19.3`
- Fabric API `0.153.0+26.2`
- Loom `1.17.12`
- official Mojang-name environment

`26.1.1`:

- earlier shipped modern reference line
- Java `25`
- Minecraft `26.1.1`
- Fabric Loader `0.18.6`
- Fabric API `0.145.3+26.1.1`
- Loom `1.15.5`
- official Mojang-name environment

`26.1`:

- earlier modern baseline/reference line
- Java `25`
- Minecraft `26.1`
- Fabric Loader `0.18.5`
- Fabric API `0.144.4+26.1`
- Loom `1.15.5`
- official Mojang-name environment

`1.21.11`:

- legacy/reference line
- Java `21`
- Minecraft `1.21.11`
- Fabric Loader `0.18.4`
- Fabric API `0.141.1+1.21.11`
- Yarn `1.21.11+build.4`

## Push And Release Rules

- Git operations happen at the root repo:
  - `C:\Users\me\Desktop\Topas Mods\MC MODS\Forks\diamondrails`
- Before staging, always inspect:
  - `git status --short`
- Stage only the verified scope for the requested work.
- Never stage generated or local runtime data:
  - `**/.gradle/`
  - `**/build/`
  - `**/run/`
  - `.vscode/`
  - `legacy_git_backup_*`
- For a version port, expected scope is usually:
  - the new or changed version folder
  - `README.md`
  - `.github/workflows/build.yml`
  - any intentionally updated maintainer docs
- Leave unrelated local files alone unless the user explicitly asks for cleanup.
- Build affected lines before commit or push.
- Push only when the user explicitly asks.
- Tag and release only when the user explicitly asks.

## Verified State

- `26.2` build verified on `2026-06-25`
- local `26.2` artifacts were produced as:
  - `diamondrails-1.4-mc26.2.jar`
  - `diamondrails-1.4-mc26.2-sources.jar`

## Next Sensible Work

- For future gameplay changes, re-test all three custom rails, recipes/glint, powered/unpowered behavior,
  player passengers, vanilla-rail slowdown and experimental minecart behavior.
- If a release is requested, re-check/build the requested scope before using
  `v1.4-mc26.3` and the verified `26.3/build/libs` JARs. Follow existing releases.
- Use the shared local cache in each new PowerShell window:

```powershell
& 'C:\Users\me\Desktop\Topas Mods\MC MODS\Use-ModBuildEnvironment.ps1'
cd 'C:\Users\me\Desktop\Topas Mods\MC MODS\Forks\diamondrails\26.3'
.\gradlew.bat --no-daemon runClient
```

- Do not launch GUI tasks without the user's request. Read `MEMORY.local.md`
  and the root maintainer notes before the next change.
