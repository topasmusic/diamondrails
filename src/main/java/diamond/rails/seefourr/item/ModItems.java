package diamond.rails.seefourr.item;

import diamond.rails.seefourr.Diamondrails;
import diamond.rails.seefourr.block.ModBlocks;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import java.util.function.Function;

public class ModItems {

    private static Item register(String id, Function<Item.Settings, Item> factory) {
        Identifier itemID = Identifier.of(Diamondrails.MOD_ID, id);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, itemID);
        Item.Settings settings = new Item.Settings().registryKey(key);
        Item item = factory.apply(settings);
        return Registry.register(Registries.ITEM, itemID, item);
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
