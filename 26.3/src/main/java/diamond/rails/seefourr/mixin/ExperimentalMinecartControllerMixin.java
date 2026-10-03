package diamond.rails.seefourr.mixin;

import diamond.rails.seefourr.Diamondrails;
import net.minecraft.world.entity.vehicle.minecart.NewMinecartBehavior;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NewMinecartBehavior.class)
public abstract class ExperimentalMinecartControllerMixin {
	@Redirect(
			method = {"calculateHaltTrackSpeed", "calculateBoostTrackSpeed"},
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
}
