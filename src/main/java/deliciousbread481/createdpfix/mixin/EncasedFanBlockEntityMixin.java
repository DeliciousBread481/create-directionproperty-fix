package deliciousbread481.createdpfix.mixin;  

import net.minecraft.core.Direction;  
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