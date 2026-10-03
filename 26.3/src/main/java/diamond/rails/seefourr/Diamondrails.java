package diamond.rails.seefourr;

import diamond.rails.seefourr.block.ModBlocks;
import diamond.rails.seefourr.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Diamondrails implements ModInitializer {
    public static final String MOD_ID = "diamondrails";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final ResourceKey<CreativeModeTab> REDSTONE_TAB = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            Identifier.withDefaultNamespace("redstone_blocks")
    );

    public static final TagKey<Block> TAG_POWERED_RAILS = TagKey.create(
            Registries.BLOCK,
            id("powered_rails")
    );

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModBlocks.registerModBlocks();
        ModItems.registerModItems();

        CreativeModeTabEvents.modifyOutputEvent(REDSTONE_TAB).register(output ->
                output.insertAfter(
                        Items.POWERED_RAIL,
                        ModItems.DIAMOND_RAIL,
                        ModItems.ENHANCED_DIAMOND_RAIL,
                        ModItems.NETHERITE_RAIL
                )
        );
    }
}
