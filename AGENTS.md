# AGENTS.md

MicroEJ standalone application template — an embedded Java application that runs directly on a VEE Port. Uses the `com.microej.gradle.application` plugin ([module natures](https://docs.microej.com/en/latest/SDK6UserGuide/moduleNatures.html)).

## Build Commands

```bash
./gradlew runOnSimulator          # Run on MicroEJ Simulator
./gradlew buildExecutable         # Build firmware for device
./gradlew runOnDevice             # Build + flash to connected device
./gradlew test                    # Unit tests on Simulator
```

EULA acceptance required: `-Daccept-microej-sdk-eula-v3-1c=YES` or env var `ACCEPT_MICROEJ_SDK_EULA_V3_1C=YES`.

For sub-projects, prefix with project path: `./gradlew :app:runOnSimulator`

See: [Run on Simulator](https://docs.microej.com/en/latest/SDK6UserGuide/runOnSimulator.html) · [Build Executable](https://docs.microej.com/en/latest/SDK6UserGuide/buildExecutable.html) · [Run on Device](https://docs.microej.com/en/latest/SDK6UserGuide/runOnDevice.html) · [Test a Project](https://docs.microej.com/en/latest/SDK6UserGuide/testProject.html)

## MicroEJ Dependency Namespaces

| Prefix | What it is | Example |
|--------|-----------|---------|
| `ej.api:*` | Foundation API (provided by VEE Port) | `ej.api:edc:1.3.7` |
| `ej.library.*:*` | Add-on library (bundled in app) | `ej.library.ui:mwt:3.6.2` |
| `ej.library.test:*` | Test library | `ej.library.test:junit:1.12.0` |

## Things You Can't Guess From the Code

**Build system:**
- **Gradle init script required.** MicroEJ repositories are not declared in the project — they require `~/.gradle/init.d/microej.init.gradle.kts`. The build fails without it.
- **VEE Port dependency uses `microejVee()`** — a MicroEJ-specific Gradle configuration (not standard `implementation`). Standalone apps require exactly one. It may be commented out in the template. See [Select a VEE Port](https://docs.microej.com/en/latest/SDK6UserGuide/selectVeePort.html).
- **Entry point is in `build.gradle.kts`**, not in a manifest or annotation. Look for `applicationEntryPoint` in the `microej {}` block. Must be a fully qualified class with `public static void main(String[] args)`. See [Application Entry Points](https://docs.microej.com/en/latest/ApplicationDeveloperGuide/classpath.html#application-entry-points).
- **eval vs prod:** `architectureUsage` in the `microej {}` block defaults to `"eval"` (time-limited). Set to `"prod"` for production (requires license). Can also be overridden via `-Dcom.microej.architecture.usage=prod`. Forgetting this causes silent runtime limits. See [Architecture Usage Selection](https://docs.microej.com/en/latest/SDK6UserGuide/selectVeePort.html#architecture-usage-selection).
- **MicroEJ ≠ standard Java.** MicroEJ uses EDC (Embedded Device Configuration), a subset of Java SE. Many standard APIs are **not available**: `java.util.stream`, `java.nio`, `java.util.function`, `java.time`, lambdas (no `invokedynamic`). Always check `ej.api:edc` for what is supported. See [EDC API](https://docs.microej.com/en/latest/ApplicationDeveloperGuide/classpath.html#embedded-device-configuration-edc).

**MicroEJ-specific files:**
- **`configuration/common.properties`:** Controls simulator behavior, memory sizing, BSP connection, and peripheral options. This is the MicroEJ convention, not `src/main/resources`.

**Testing:**
- **Tests use MicroEJ test engine**, not standard JUnit. Look for `useMicroejTestEngine(this)` in `build.gradle.kts`. Test dependencies need explicit `ej.library.test:junit`. See [Test a Project](https://docs.microej.com/en/latest/SDK6UserGuide/testProject.html).

## Application Sub-types

This template is for **standalone** apps ([Standalone Application guide](https://docs.microej.com/en/latest/ApplicationDeveloperGuide/standaloneApplication.html)). Other application sub-types exist:

- **Kernel** — also uses `com.microej.gradle.application` but adds `produceExecutableDuringBuild()`, `produceVirtualDeviceDuringBuild()`, and `com.microej.kernelapi:*` dependencies. See [Kernel-GREEN](https://github.com/MicroEJ/Kernel-GREEN).
- **Feature app** (multi-sandbox) — has `kernelGroup`/`kernelModule`/`kernelVersion` in `gradle.properties`, no `microejVee()`. See [Demo-Sandboxed-Applications](https://github.com/MicroEJ/Demo-Sandboxed-Applications) and [Sandboxed Application guide](https://docs.microej.com/en/latest/ApplicationDeveloperGuide/sandboxedApplication.html).
