package diamond.rails.seefourr.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import diamond.rails.seefourr.Diamondrails;
import diamond.rails.seefourr.access.DiamondRailsSpeedAccessor;
import diamond.rails.seefourr.block.ModBlocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.OldMinecartBehavior;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OldMinecartBehavior.class)
public abstract class DefaultMinecartControllerMixin {
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
	private AbstractMinecart diamondrails$getMinecart() {
		return ((MinecartControllerAccessor) (Object) this).diamondrails$getMinecart();
	}

	@Unique
	private BlockState diamondrails$getRailState() {
		AbstractMinecart minecart = diamondrails$getMinecart();
		return minecart.level().getBlockState(minecart.getCurrentBlockPosOrRailBelow());
	}

	@Redirect(
			method = "moveAlongTrack",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z",
					ordinal = 0
			)
	)
	private boolean diamondrails$isPoweredRail(BlockState state, Object block) {
		if (block == Blocks.POWERED_RAIL) {
			return state.is(Diamondrails.TAG_POWERED_RAILS);
		}
		return state.is((Block) block);
	}

	@ModifyExpressionValue(
			method = "moveAlongTrack",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/vehicle/minecart/AbstractMinecart;isVehicle()Z"
			)
	)
	private boolean diamondrails$disablePassengerSlowdown(boolean original) {
		AbstractMinecart minecart = diamondrails$getMinecart();
		if (minecart.getFirstPassenger() instanceof Player) {
			double lastSpeed = ((DiamondRailsSpeedAccessor) minecart).diamondrails$getLastCustomMaxSpeed();
			if (lastSpeed > 0.0) {
				return false;
			}
		}

		if (diamondrails$getRailSpeedBps(diamondrails$getRailState()) > 0.0) {
			return false;
		}

		return original;
	}

	@Redirect(
			method = "moveAlongTrack",
			at = @At(
					value = "INVOKE",
					target = "Ljava/lang/Math;min(DD)D"
			)
	)
	private double diamondrails$relaxSpeedCap(double a, double b) {
		AbstractMinecart minecart = diamondrails$getMinecart();
		if (minecart.getFirstPassenger() instanceof Player) {
			double lastSpeed = ((DiamondRailsSpeedAccessor) minecart).diamondrails$getLastCustomMaxSpeed();
			if (lastSpeed > 0.0) {
				double divisor = minecart.isInWater() ? 40.0 : 20.0;
				return Math.min(lastSpeed / divisor, b);
			}
		}

		double speedBps = diamondrails$getRailSpeedBps(diamondrails$getRailState());
		if (speedBps > 0.0) {
			double divisor = minecart.isInWater() ? 40.0 : 20.0;
			return Math.min(speedBps / divisor, b);
		}

		return Math.min(a, b);
	}
}
