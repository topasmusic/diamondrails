# Next Session Notes

## Current Repo State

As of `2026-06-25`, `26.2` is the active default work line for this repo.

Current tags:

- `v1.4-mc1.21.11`
- `v1.4-mc26.1`
- `v1.4-mc26.1.1`
- `v1.0`

Current pushed port state:

- `26.2` was added and pushed in commit `6dff0d9` on branch `1.21.x`
- the root workflow matrix now includes `26.2`
- no `v1.4-mc26.2` tag or GitHub release exists yet

## Version-Line Differences

`26.2`:

- active default line
- Java `25`
- Minecraft `26.2`
- Fabric Loader `0.19.3`
- Fabric API `0.153.0+26.2`
- Loom `1.17.12`
- official Mojang-name environment

`26.1.1`:

- last shipped modern reference line
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

- validate the `26.2` port in-game
- if the next request is a release, create `v1.4-mc26.2` only after re-checking repo status and building the intended release scope
- if the next request touches both `26.2` and `1.21.11`, re-read `CODEX_START_HERE.md` first and port behavior deliberately rather than mirroring code
