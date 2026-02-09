package diamond.rails.seefourr.block;

import diamond.rails.seefourr.Diamondrails;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    private static Block registerPoweredRail(String name, float strength) {
        Identifier id = Identifier.of(Diamondrails.MOD_ID, name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        AbstractBlock.Settings settings = AbstractBlock.Settings.copy(Blocks.RAIL)
                .registryKey(key)
                .sounds(BlockSoundGroup.METAL)
                .strength(strength)
                .noCollision();
        Block block = new PoweredRailBlock(settings);
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static final Block DIAMONDRAIL = registerPoweredRail("diamond_rail", 3.0f);

    public static final Block ENHANCEDDIAMONDRAIL = registerPoweredRail("enhanced_diamond_rail", 5.0f);

    public static final Block NETHERITERAIL = registerPoweredRail("netherite_rail", 15.0f);

    public static void registerModBlocks() {
        Diamondrails.LOGGER.info("Registering blocks for " + Diamondrails.MOD_ID);
    }
}
