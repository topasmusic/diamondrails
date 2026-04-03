package diamond.rails.seefourr.block;

import diamond.rails.seefourr.Diamondrails;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    private static Block registerPoweredRail(String name, float strength) {
        Identifier id = Diamondrails.id(name);
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id);
        BlockBehaviour.Properties settings = BlockBehaviour.Properties.ofLegacyCopy(Blocks.RAIL)
                .setId(key)
                .sound(SoundType.METAL)
                .strength(strength)
                .noCollision();
        return Registry.register(BuiltInRegistries.BLOCK, id, new DiamondPoweredRailBlock(settings));
    }

    public static final Block DIAMONDRAIL = registerPoweredRail("diamond_rail", 3.0f);

    public static final Block ENHANCEDDIAMONDRAIL = registerPoweredRail("enhanced_diamond_rail", 5.0f);

    public static final Block NETHERITERAIL = registerPoweredRail("netherite_rail", 15.0f);

    public static void registerModBlocks() {
        Diamondrails.LOGGER.info("Registering blocks for " + Diamondrails.MOD_ID);
    }

    private static final class DiamondPoweredRailBlock extends PoweredRailBlock {
        private DiamondPoweredRailBlock(BlockBehaviour.Properties settings) {
            super(settings);
        }
    }
}
