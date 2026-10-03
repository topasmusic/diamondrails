package diamond.rails.seefourr.mixin;

import diamond.rails.seefourr.access.DiamondRailsSpeedAccessor;
import diamond.rails.seefourr.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecart.class)
public abstract class AbstractMinecartEntityMixin implements DiamondRailsSpeedAccessor {
	@Unique
	private double diamondrails$lastCustomMaxSpeed = -1.0;

	@Unique
	private AbstractMinecart diamondrails$self() {
		return (AbstractMinecart) (Object) this;
	}

	@Shadow
	public abstract BlockPos getCurrentBlockPosOrRailBelow();

	@Unique
	private boolean diamondrails$hasPlayerPassenger() {
		return this.diamondrails$self().getFirstPassenger() instanceof Player;
	}

	@Unique
	private static double diamondrails$getRailSpeedBps(BlockState state) {
		if (state.is(ModBlocks.DIAMONDRAIL)) {
			return 32.0;
		}
		if (state.is(ModBlocks.ENHANCEDDIAMONDRAIL)) {
			return 90.0;
		}
		if (state.is(ModBlocks.NETHERITERAIL)) {
			return 159.0;
		}
		return -1.0;
	}

	@Unique
	@Override
	public double diamondrails$getLastCustomMaxSpeed() {
		return this.diamondrails$lastCustomMaxSpeed;
	}

	@Inject(method = "getMaxSpeed", at = @At("HEAD"), cancellable = true)
	private void diamondrails$maxSpeed(ServerLevel world, CallbackInfoReturnable<Double> cir) {
		BlockState blockState = world.getBlockState(this.getCurrentBlockPosOrRailBelow());
		double speed = diamondrails$getRailSpeedBps(blockState);

		if (speed <= 0.0) {
			if (!diamondrails$hasPlayerPassenger()) {
				this.diamondrails$lastCustomMaxSpeed = -1.0;
				return;
			}

			if (blockState.is(Blocks.POWERED_RAIL)) {
				this.diamondrails$lastCustomMaxSpeed = 8.0;
			}

			if (this.diamondrails$lastCustomMaxSpeed <= 0.0) {
				return;
			}

			double divisor = this.diamondrails$self().isInWater() ? 40.0 : 20.0;
			cir.setReturnValue(this.diamondrails$lastCustomMaxSpeed / divisor);
			return;
		}

		this.diamondrails$lastCustomMaxSpeed = speed;
		double divisor = this.diamondrails$self().isInWater() ? 40.0 : 20.0;
		cir.setReturnValue(speed / divisor);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void diamondrails$forceRailSpeed(CallbackInfo ci) {
		AbstractMinecart minecart = this.diamondrails$self();
		Level level = minecart.level();
		if (level.isClientSide()) {
			return;
		}

		BlockState state = level.getBlockState(this.getCurrentBlockPosOrRailBelow());
		double speed = diamondrails$getRailSpeedBps(state);
		if (speed <= 0.0) {
			return;
		}

		if (!state.getValue(PoweredRailBlock.POWERED)) {
			return;
		}

		Vec3 velocity = minecart.getDeltaMovement();
		double horizontalSpeed = velocity.horizontalDistance();
		if (horizontalSpeed < 1.0e-4) {
			return;
		}

		double divisor = minecart.isInWater() ? 40.0 : 20.0;
		double targetSpeed = speed / divisor;
		if (Math.abs(horizontalSpeed - targetSpeed) < 1.0e-6) {
			return;
		}

		minecart.setDeltaMovement(velocity.scale(targetSpeed / horizontalSpeed));
	}
}
