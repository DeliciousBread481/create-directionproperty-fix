A mod mixin create to fix this issue

```
java.lang.IllegalArgumentException: Cannot get property DirectionProperty{name=facing, clazz=class net.minecraft.core.Direction, values=[north, east, south, west, up, down]} as it does not exist in Block{create:encased_chain_drive}
	at TRANSFORMER/minecraft@1.20.1/net.minecraft.world.level.block.state.StateHolder.m_61143_(StateHolder.java:98)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity.updateChute(EncasedFanBlockEntity.java:106)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity.onSpeedChanged(EncasedFanBlockEntity.java:102)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.content.kinetics.RotationPropagator.propagateNewSource(RotationPropagator.java:282)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.content.kinetics.RotationPropagator.propagateNewSource(RotationPropagator.java:284)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.content.kinetics.RotationPropagator.propagateNewSource(RotationPropagator.java:284)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.content.kinetics.RotationPropagator.propagateNewSource(RotationPropagator.java:284)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.content.kinetics.RotationPropagator.propagateNewSource(RotationPropagator.java:261)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.content.kinetics.RotationPropagator.mixinextras$bridge$propagateNewSource$33(RotationPropagator.java)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.content.kinetics.RotationPropagator.wrapOperation$hfn000$createbigcannons$handleAdded(RotationPropagator.java:3042)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.content.kinetics.RotationPropagator.handleAdded(RotationPropagator.java:211)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.content.kinetics.base.KineticBlockEntity.attachKinetics(KineticBlockEntity.java:361)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.content.kinetics.base.KineticBlockEntity.tick(KineticBlockEntity.java:97)
	at TRANSFORMER/create@6.0.8/com.simibubi.create.foundation.blockEntity.SmartBlockEntityTicker.m_155252_(SmartBlockEntityTicker.java:15)
	at TRANSFORMER/minecraft@1.20.1/net.minecraft.world.level.chunk.LevelChunk$BoundTickingBlockEntity.mixinextras$bridge$m_155252_$7(LevelChunk.java)
	at TRANSFORMER/neruina@3.2.2/com.bawnorton.neruina.handler.TickHandler.safelyTickBlockEntity(TickHandler.java:198)
```

## Create DirectionProperty Fix

A lightweight Mixin fix mod for Minecraft Forge 1.20.1 that addresses two issues in the [Create](https://github.com/Creators-of-Create/Create) mod related to `EncasedFanBlockEntity`:

### Issues Fixed

**1. Crash Fix**

During kinetic network propagation (`RotationPropagator.propagateNewSource`), the cached `BlockState` of an `EncasedFanBlockEntity` may become mismatched with the actual block at its position (e.g., becoming `encased_chain_drive`). This causes an `IllegalArgumentException` when attempting to read the `FACING` property. This mod adds defensive checks at the entry points of `updateChute()`, `getAirflowOriginSide()`, and `getAirFlowDirection()` to prevent the crash.

**2. Rendering Fix**

When a block entity type mismatch is detected (e.g., an `EncasedFanBlockEntity` exists at a position where the block is a chain drive), the mod automatically replaces it during `tick()` with the correct block entity type created by the block's own `newBlockEntity()`, restoring proper shaft rotation animation and rendering.

### Injection Points

| Method | Behavior |
|--------|----------|
| `tick()` | Detects block entity type mismatch and auto-replaces with the correct type |
| `updateChute()` | Safely returns when `FACING` property is absent, preventing crash |
| `onSpeedChanged()` | Logs a warning when `FACING` property is absent |
| `getAirflowOriginSide()` | Returns a safe fallback value when `FACING` property is absent |
| `getAirFlowDirection()` | Returns `null` when `FACING` property is absent |

  
