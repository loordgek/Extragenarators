package loordgek.extragenarators.items.itemblock;

import loordgek.extragenarators.enums.EnumGenarator;
import loordgek.extragenarators.ref.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ItemBlockGenBase extends ItemBlockMain{
    public ItemBlockGenBase(Block block) {
        super(block);
        this.setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return String.format("item.%s%s.%s", Reference.RESOURCE.RESOURCE_PREFIX, Reference.BLOCKS.GENBASE, EnumGenarator.EnumLookup[MathHelper.clamp_int(stack.getItemDamage(), 0,EnumGenarator.getlenth() - 1)]);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}

