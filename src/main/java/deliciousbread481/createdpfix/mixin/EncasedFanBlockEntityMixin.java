package deliciousbread481.createdpfix.mixin;  

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;  
import net.minecraft.world.level.block.state.BlockState;  
import net.minecraft.world.level.block.state.properties.BlockStateProperties;  

import org.spongepowered.asm.mixin.Mixin;  
import org.spongepowered.asm.mixin.injection.At;  
import org.spongepowered.asm.mixin.injection.Inject;  
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;  
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity")  
public abstract class EncasedFanBlockEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true, remap = false)  
    private void createdpfix$fixMismatchedBlockEntity(CallbackInfo ci) {  
        BlockEntity self = (BlockEntity) (Object) this;  
        BlockState state = self.getBlockState();  
        if (state.hasProperty(BlockStateProperties.FACING)) {  
            return;
        }  
  
        Level level = self.getLevel();  
        BlockPos pos = self.getBlockPos();  
        if (level != null && !level.isClientSide  
                && state.getBlock() instanceof EntityBlock entityBlock) {  
            BlockEntity newBE = entityBlock.newBlockEntity(pos, state);  
            if (newBE != null) {  
                org.apache.logging.log4j.LogManager.getLogger("CreateDPFix")  
                    .warn("Replacing mismatched EncasedFanBlockEntity at {} with correct type {} for block {}",  
                        pos, newBE.getClass().getSimpleName(), state.getBlock());  
                level.setBlockEntity(newBE);  
            }  
        }  
        ci.cancel();
    }
    
    @Inject(method = "updateChute", at = @At("HEAD"), cancellable = true, remap = false)  
    private void createdpfix$safeUpdateChute(CallbackInfo ci) {  
        BlockState state = ((BlockEntity) (Object) this).getBlockState();  
        if (!state.hasProperty(BlockStateProperties.FACING)) {  
            ci.cancel();  
        }  
    }  
    
    @Inject(method = "onSpeedChanged", at = @At("HEAD"), remap = false)  
    private void createdpfix$warnInvalidStateOnSpeedChanged(float prevSpeed, CallbackInfo ci) {  
        BlockState state = ((BlockEntity) (Object) this).getBlockState();  
        if (!state.hasProperty(BlockStateProperties.FACING)) {  
            org.apache.logging.log4j.LogManager.getLogger("CreateDPFix")  
                .warn("EncasedFanBlockEntity at {} has invalid BlockState: {} (no FACING property). "  
                    + "Skipping updateChute to prevent crash.",  
                    ((BlockEntity) (Object) this).getBlockPos(),  
                    state.getBlock());  
        }  
    }  
    
    @Inject(method = "getAirflowOriginSide", at = @At("HEAD"), cancellable = true, remap = false)  
    private void createdpfix$safeGetAirflowOriginSide(CallbackInfoReturnable<Direction> cir) {  
        BlockState state = ((BlockEntity) (Object) this).getBlockState();  
        if (!state.hasProperty(BlockStateProperties.FACING)) {  
            cir.setReturnValue(Direction.NORTH);  
        }  
    }
    
    @Inject(method = "getAirFlowDirection", at = @At("HEAD"), cancellable = true, remap = false)  
    private void createdpfix$safeGetAirFlowDirection(CallbackInfoReturnable<Direction> cir) {  
        BlockState state = ((BlockEntity) (Object) this).getBlockState();  
        if (!state.hasProperty(BlockStateProperties.FACING)) {  
            cir.setReturnValue(null);  
        }  
    }  
}