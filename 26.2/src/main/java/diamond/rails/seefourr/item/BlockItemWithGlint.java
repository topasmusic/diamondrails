package diamond.rails.seefourr.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class BlockItemWithGlint extends BlockItem {
    public BlockItemWithGlint(Block block, Item.Properties settings) {
        super(block, settings);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
