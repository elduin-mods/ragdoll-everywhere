# Ragdoll Everywhere

Press **I** to flop and tumble; press **I** again to stand up.

## Prototype status

Source draft for Minecraft Java **1.21.3**, Fabric Loader **0.16.9**, Fabric API **0.107.0+1.21.3**, Java **21**.
Compiled successfully in [GitHub Actions build #1](https://github.com/elduin-mods/ragdoll-everywhere/actions/runs/38789188911). Not tested in-game yet. This is a ragdoll-style visual prototype: spring-driven limb animation and whole-body tumbling. It is **not a full articulated physics ragdoll**. Vanilla movement, collision box, damage and water physics remain in use. You can walk while flopped. Only the local player in single-player is supported. Multiplayer activation is disabled.

I switches to third-person while active and restores the old view when disabled. Water slows the tumbling. Death, leaving the world, and player replacement clear the effect.

## Build

Install Java 21 and Gradle 8.10.2, then run `gradle build` in this folder. A Gradle wrapper is not included. The GitHub Actions workflow also builds on pushes and offers JARs under the run's Artifacts section if successful.

Install the resulting non-sources JAR into a separate Fabric 1.21.3 instance with Fabric API. Try it in a disposable world. Source archives are not installable mods; use the built JAR from the workflow artifact.

## Verification needed

- Verify both mixin targets at game launch; compilation passed.
- Press I while standing, running, jumping, falling and in water; toggle back.
- Confirm normal poses return after toggling and no other player's model changes.
- Test both skin widths, death, respawn, world changes, and perspective restoration.
- Armor and held-item animations may need additional pose synchronization.

## Seven-day request

Requested expiry date: October 22, 2026. Repository deletion or archival has **not** been scheduled because the intended expiration action has not been clarified. Build artifacts have seven-day retention; that does not delete or archive the repository.

## API references

- https://maven.fabricmc.net/docs/yarn-1.21.3+build.2/net/minecraft/client/render/entity/PlayerEntityRenderer.html
- https://maven.fabricmc.net/docs/yarn-1.21.3+build.2/net/minecraft/client/render/entity/state/PlayerEntityRenderState.html