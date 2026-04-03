package diamond.rails.seefourr;

import diamond.rails.seefourr.item.ModItems;
import diamond.rails.seefourr.block.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Diamondrails implements ModInitializer{
    public static final String MOD_ID = "diamondrails";
    public static final Logger LOGGER = LoggerFactory.getLogger("diamondrails");

    public static final TagKey<Block> TAG_POWERED_RAILS = TagKey.of(
            RegistryKeys.BLOCK, Identifier.of("diamondrails", "powered_rails")
    );

    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
            content.addAfter(Items.POWERED_RAIL, ModItems.DIAMOND_RAIL);
            content.addAfter(ModItems.DIAMOND_RAIL, ModItems.ENHANCED_DIAMOND_RAIL);
            content.addAfter(ModItems.ENHANCED_DIAMOND_RAIL, ModItems.NETHERITE_RAIL);
        });
        // ^^ this should really be in the ModItems class but I'm not really bothered as I have 3 items
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
    }
}
