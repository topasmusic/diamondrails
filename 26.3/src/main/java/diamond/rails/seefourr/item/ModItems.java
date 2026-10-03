package diamond.rails.seefourr.item;

import diamond.rails.seefourr.Diamondrails;
import diamond.rails.seefourr.block.ModBlocks;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ModItems {
    private static Item register(String id, Function<Item.Properties, Item> factory) {
        Identifier itemId = Diamondrails.id(id);
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, itemId);
        Item.Properties settings = new Item.Properties().setId(key);
        Item item = factory.apply(settings);
        return Registry.register(BuiltInRegistries.ITEM, itemId, item);
    }

    public static final Item DIAMOND_RAIL = register(
            "diamond_rail",
            settings -> new BlockItem(ModBlocks.DIAMONDRAIL, settings)
    );

    public static final Item ENHANCED_DIAMOND_RAIL = register(
            "enhanced_diamond_rail",
            settings -> new BlockItemWithGlint(ModBlocks.ENHANCEDDIAMONDRAIL, settings)
    );

    public static final Item NETHERITE_RAIL = register(
            "netherite_rail",
            settings -> new BlockItem(ModBlocks.NETHERITERAIL, settings)
    );

    public static void registerModItems() {
        Diamondrails.LOGGER.info("Registering items for " + Diamondrails.MOD_ID);
    }
}
