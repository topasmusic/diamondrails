package diamond.rails.seefourr.mixin;

import diamond.rails.seefourr.Diamondrails;
import diamond.rails.seefourr.access.DiamondRailsSpeedAccessor;
import diamond.rails.seefourr.block.ModBlocks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.DefaultMinecartController;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DefaultMinecartController.class)
public abstract class DefaultMinecartControllerMixin {
	private BlockState diamondrails$getRailState() {
		AbstractMinecartEntity minecart = ((MinecartControllerAccessor) (Object) this).diamondrails$getMinecart();
		return minecart.getEntityWorld().getBlockState(minecart.getRailOrMinecartPos());
	}

	@Redirect(
			method = "moveOnRail",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z",
					ordinal = 0
			)
	)
	private boolean diamondrails$isPoweredRail(BlockState state, Block block) {
		if (block == Blocks.POWERED_RAIL) {
			return state.isIn(Diamondrails.TAG_POWERED_RAILS);
		}
		return state.isOf(block);
	}

	@ModifyExpressionValue(
			method = "moveOnRail",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;hasPassengers()Z"
			)
	)
	private boolean diamondrails$disablePassengerSlowdown(boolean original) {
		AbstractMinecartEntity minecart = ((MinecartControllerAccessor) (Object) this).diamondrails$getMinecart();
		if (minecart.getFirstPassenger() instanceof net.minecraft.entity.player.PlayerEntity) {
			double lastSpeed = ((DiamondRailsSpeedAccessor) minecart).diamondrails$getLastCustomMaxSpeed();
			if (lastSpeed > 0.0) {
				return false;
			}
		}

		BlockState railState = diamondrails$getRailState();
		if (railState.isOf(ModBlocks.DIAMONDRAIL)
				|| railState.isOf(ModBlocks.ENHANCEDDIAMONDRAIL)
				|| railState.isOf(ModBlocks.NETHERITERAIL)) {
			return false;
		}
		return original;
	}

	@Redirect(
			method = "moveOnRail",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;",
					ordinal = 5
			)
	)
	private Vec3d diamondrails$boostPoweredRailAccel(Vec3d vec, double x, double y, double z) {
		Vec3d newVec = vec.add(x, y, z);
		BlockState railState = diamondrails$getRailState();
		if (railState.isOf(ModBlocks.DIAMONDRAIL)) {
			return newVec.multiply(32.0 / 8.0);
		} else if (railState.isOf(ModBlocks.ENHANCEDDIAMONDRAIL)) {
			return newVec.multiply(90.0 / 8.0);
		} else if (railState.isOf(ModBlocks.NETHERITERAIL)) {
			return newVec.multiply(159.0 / 8.0);
		}
		return newVec;
	}

	@Redirect(
			method = "moveOnRail",
			at = @At(
					value = "INVOKE",
					target = "Ljava/lang/Math;min(DD)D"
			)
	)
	private double diamondrails$relaxSpeedCap(double a, double b, net.minecraft.server.world.ServerWorld world) {
		AbstractMinecartEntity minecart = ((MinecartControllerAccessor) (Object) this).diamondrails$getMinecart();
		if (minecart.getFirstPassenger() instanceof net.minecraft.entity.player.PlayerEntity) {
			double lastSpeed = ((DiamondRailsSpeedAccessor) minecart).diamondrails$getLastCustomMaxSpeed();
			if (lastSpeed > 0.0) {
				double divisor = minecart.isTouchingWater() ? 40.0 : 20.0;
				double max = lastSpeed / divisor;
				return Math.min(max, b);
			}
		}

		BlockState railState = diamondrails$getRailState();
		if (railState.isOf(ModBlocks.DIAMONDRAIL)
				|| railState.isOf(ModBlocks.ENHANCEDDIAMONDRAIL)
				|| railState.isOf(ModBlocks.NETHERITERAIL)) {
			double speedBps;
			if (railState.isOf(ModBlocks.DIAMONDRAIL)) {
				speedBps = 32.0;
			} else if (railState.isOf(ModBlocks.ENHANCEDDIAMONDRAIL)) {
				speedBps = 90.0;
			} else {
				speedBps = 159.0;
			}
			double divisor = minecart.isTouchingWater() ? 40.0 : 20.0;
			double max = speedBps / divisor;
			return Math.min(max, b);
		}
		return Math.min(a, b);
	}
}
