package diamond.rails.seefourr.mixin;

import diamond.rails.seefourr.access.DiamondRailsSpeedAccessor;
import diamond.rails.seefourr.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntityMixin extends Entity implements DiamondRailsSpeedAccessor {
	@Unique
	private double diamondrails$lastCustomMaxSpeed = -1.0;

	@Shadow
	public abstract BlockPos getRailOrMinecartPos();

	public AbstractMinecartEntityMixin(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@Unique
	private boolean diamondrails$hasPlayerPassenger() {
		Entity passenger = this.getFirstPassenger();
		return passenger instanceof PlayerEntity;
	}

	@Unique
	@Override
	public double diamondrails$getLastCustomMaxSpeed() {
		return this.diamondrails$lastCustomMaxSpeed;
	}

	@Inject(method = "getMaxSpeed", at = @At("HEAD"), cancellable = true)
	private void diamondrails$maxSpeed(ServerWorld world, CallbackInfoReturnable<Double> cir) {
		BlockState blockState = world.getBlockState(this.getRailOrMinecartPos());
		double speed;
		if (blockState.isOf(ModBlocks.DIAMONDRAIL)) {
			speed = 32.0;
		} else if (blockState.isOf(ModBlocks.ENHANCEDDIAMONDRAIL)) {
			speed = 90.0;
		} else if (blockState.isOf(ModBlocks.NETHERITERAIL)) {
			speed = 159.0;
		} else {
			if (!diamondrails$hasPlayerPassenger()) {
				this.diamondrails$lastCustomMaxSpeed = -1.0;
				return;
			}
			if (blockState.isOf(Blocks.POWERED_RAIL)) {
				this.diamondrails$lastCustomMaxSpeed = 8.0;
			}
			if (this.diamondrails$lastCustomMaxSpeed <= 0.0) {
				return;
			}
			double divisor = this.isTouchingWater() ? 40.0 : 20.0;
			cir.setReturnValue(this.diamondrails$lastCustomMaxSpeed / divisor);
			return;
		}

		this.diamondrails$lastCustomMaxSpeed = speed;
		double divisor = this.isTouchingWater() ? 40.0 : 20.0;
		cir.setReturnValue(speed / divisor);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void diamondrails$forceRailSpeed(CallbackInfo ci) {
		World world = this.getEntityWorld();
		if (world.isClient()) {
			return;
		}

		BlockState state = world.getBlockState(this.getRailOrMinecartPos());
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

		Vec3d velocity = this.getVelocity();
		double horiz = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
		if (horiz < 1.0e-4) {
			return;
		}

		double divisor = this.isTouchingWater() ? 40.0 : 20.0;
		double target = speed / divisor;
		if (horiz == target) {
			return;
		}

		double scale = target / horiz;
		this.setVelocity(velocity.x * scale, velocity.y, velocity.z * scale);
	}
}
