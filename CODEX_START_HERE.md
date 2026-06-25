# Codex Start Here

Wenn in einer neuen Session auf diese Datei verwiesen wird, arbeite nach diesem Ablauf, bevor du Annahmen triffst oder Aenderungen machst.

## Ziel

Baue zuerst belastbaren Repo- und Workflow-Kontext auf, damit Ports, Fixes und Pushes in diesem Multi-Version-Repo versionssauber und release-hygienisch bleiben.

## Immer zuerst lesen

1. `NEXT_SESSION.md`
2. `MEMORY.local.md`
3. die betroffenen `CHANGELOG.md`-Dateien
4. die passende `README.md`
5. die betroffenen `build.gradle`- und `gradle.properties`-Dateien
6. erst danach die betroffenen Code- und Ressourcen-Dateien

## Repo-Grundsaetze

- Standard-Arbeitslinie ist `26.2`, ausser der User will explizit Legacy, Vergleich oder Rueckport.
- `26.2`, `26.1.1` und `26.1` sind Mojang-Mappings-Linien.
- `1.21.11` ist die Yarn-Mappings-Linie.
- Niemals blind Code zwischen `26.2` und `1.21.11` uebernehmen.
- Verhalten portieren, aber APIs, Methodennamen, Typen und Imports pro Linie sauber anpassen.
- `26.1.1` bleibt die letzte veroeffentlichte moderne Referenzlinie.

## Aktuell wichtige Versionsfakten

Stand dieser Datei: `2026-06-25`

- `26.2`
  - Minecraft `26.2`
  - Java `25`
  - Fabric Loader `0.19.3`
  - Fabric API `0.153.0+26.2`
  - Loom `1.17.12`
  - offizielle Mojang-Namen
- `26.1.1`
  - Minecraft `26.1.1`
  - Java `25`
  - Fabric Loader `0.18.6`
  - Fabric API `0.145.3+26.1.1`
  - Loom `1.15.5`
  - offizielle Mojang-Namen
- `26.1`
  - Minecraft `26.1`
  - Java `25`
  - Fabric Loader `0.18.5`
  - Fabric API `0.144.4+26.1`
  - Loom `1.15.5`
  - offizielle Mojang-Namen
- `1.21.11`
  - Minecraft `1.21.11`
  - Java `21`
  - Fabric Loader `0.18.4`
  - Fabric API `0.141.1+1.21.11`
  - Yarn `1.21.11+build.4`

## Push- und Release-Regeln

- Git-Operationen laufen am Root-Repo:
  - `C:\Users\me\Desktop\Topas Mods\MC MODS\Forks\diamondrails`
- Vor jedem Commit oder Push zuerst `git status --short` pruefen.
- Stage nur den verifizierten Aufgabenumfang.
- Niemals blind das gesamte Repo stagen, wenn lokale Laufzeit- oder Scratch-Daten herumliegen.
- Nicht committen oder pushen:
  - `**/.gradle/`
  - `**/build/`
  - `**/run/`
  - `.vscode/`
  - `legacy_git_backup_*`
  - lokale Testwelten oder andere Laufzeitreste
- Bei einem Versionsport ist der erwartete Scope normalerweise:
  - der neue oder geaenderte Versionsordner
  - Root-Doku wie `README.md`
  - CI wie `.github/workflows/build.yml`
  - sonst nur bewusst angefasste Shared-Dateien
- Unverwandte lokale Aenderungen nicht ungefragt aufraeumen und nicht versehentlich mit veroeffentlichen.
- Push nur, wenn der User explizit `push` oder `publish` sagt.
- Tags und Releases nur, wenn der User sie explizit will.
- Fuer Version-Tags dem vorhandenen Muster folgen:
  - `v1.4-mc1.21.11`
  - `v1.4-mc26.1`
  - `v1.4-mc26.1.1`
  - daraus fuer die neue Linie: `v1.4-mc26.2`

## Arbeitsweise fuer Aenderungen

1. Zuerst Maintainer-Daten und Version-Fakten lesen.
2. Dann die betroffenen Klassen und Ressourcen lesen.
3. Unterschiede zwischen Mojang- und Yarn-Linien bewusst benennen, wenn beide betroffen sind.
4. Erst dann editieren.
5. Bei user-facing oder workflow-relevanten Aenderungen auch Doku und Maintainer-Dateien nachziehen:
   - `README.md`
   - `CHANGELOG.md`
   - `MEMORY.local.md`
   - `NEXT_SESSION.md`
   - falls sinnvoll diese Datei

## Build- und Check-Regeln

- Wenn nur eine Linie geaendert wurde, mindestens diese Linie bauen.
- Wenn Shared-Buildlogik oder CI geaendert wurde, die betroffenen Linien gezielt gegenpruefen.
- Korrekte Java-Version pro Linie verwenden.
- `runClient` nicht selbst starten, ausser der User verlangt es ausdruecklich.
- Nach Aenderungen die exakten Test- oder Build-Befehle fuer die betroffene Linie nennen.

## Definition von "sauber fertig"

Eine Aufgabe gilt hier erst als sauber abgeschlossen, wenn:

- die betroffenen Linien korrekt umgesetzt sind
- Mapping-Unterschiede sauber beachtet wurden
- Doku und Maintainer-Daten nachgezogen wurden, falls die Aenderung workflow- oder user-facing war
- die passenden Builds erfolgreich gelaufen sind
- Commit-/Push-Scope sauber von lokalen Laufzeitdaten getrennt wurde
