package diamond.rails.seefourr.mixin;

import diamond.rails.seefourr.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.ExperimentalMinecartController;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperimentalMinecartController.class)
public abstract class ExperimentalMinecartControllerMixin {
	@Inject(method = "getMaxSpeed", at = @At("HEAD"), cancellable = true)
	private void diamondrails$maxSpeed(ServerWorld world, CallbackInfoReturnable<Double> cir) {
		AbstractMinecartEntity minecart = ((MinecartControllerAccessor) (Object) this).diamondrails$getMinecart();
		BlockState blockState = world.getBlockState(minecart.getRailOrMinecartPos());
		double speed;
		if (blockState.isOf(ModBlocks.DIAMONDRAIL)) {
			speed = 32.0;
		} else if (blockState.isOf(ModBlocks.ENHANCEDDIAMONDRAIL)) {
			speed = 90.0;
		} else if (blockState.isOf(ModBlocks.NETHERITERAIL)) {
			speed = 159.0;
		} else {
			return;
		}

		double divisor = minecart.isTouchingWater() ? 40.0 : 20.0;
		cir.setReturnValue(speed / divisor);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void diamondrails$boostPoweredRails(CallbackInfo ci) {
		AbstractMinecartEntity minecart = ((MinecartControllerAccessor) (Object) this).diamondrails$getMinecart();
		BlockState state = minecart.getEntityWorld().getBlockState(minecart.getRailOrMinecartPos());
		double speed;
		if (state.isOf(ModBlocks.DIAMONDRAIL)) {
			speed = 32.0;
		} else if (state.isOf(ModBlocks.ENHANCEDDIAMONDRAIL)) {
			speed = 90.0;
		} else if (state.isOf(ModBlocks.NETHERITERAIL)) {
			speed = 159.0;
		} else {
			return;
		}

		if (state.contains(Properties.POWERED) && !state.get(Properties.POWERED)) {
			return;
		}

		Vec3d velocity = minecart.getVelocity();
		double horiz = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
		if (horiz < 1.0e-4) {
			return;
		}

		double divisor = minecart.isTouchingWater() ? 40.0 : 20.0;
		double max = speed / divisor;
		if (horiz >= max) {
			return;
		}

		double accel = 0.06 * (speed / 8.0);
		double newHoriz = Math.min(max, horiz + accel);
		double scale = newHoriz / horiz;
		minecart.setVelocity(velocity.x * scale, velocity.y, velocity.z * scale);
	}
}
