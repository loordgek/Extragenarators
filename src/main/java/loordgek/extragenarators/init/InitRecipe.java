package loordgek.extragenarators.init;

import loordgek.extragenarators.enums.EnumBlocks;
import loordgek.extragenarators.enums.EnumItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitRecipe {

    public static void RegisterRecipe(){
        GameRegistry.addShapedRecipe(new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 0)," R ", "GIG"," R ", 'R' ,Items.REDSTONE, 'G', Items.GLOWSTONE_DUST, 'I', Items.IRON_INGOT);
        GameRegistry.addShapedRecipe(new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 1)," R ", "GIG"," R ", 'R' ,Items.REDSTONE, 'G', new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 0), 'I', Items.IRON_INGOT);
        GameRegistry.addShapedRecipe(new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 2)," R ", "GIG"," R ", 'R' ,Items.REDSTONE, 'G', new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 1), 'I', Items.IRON_INGOT);
        GameRegistry.addShapedRecipe(new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 3)," R ", "GIG"," R ", 'R' ,Items.REDSTONE, 'G', new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 2), 'I', Items.IRON_INGOT);
        GameRegistry.addShapedRecipe(new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 4)," R ", "GIG"," R ", 'R' ,Items.REDSTONE, 'G', Items.GLOWSTONE_DUST, 'I', Items.GOLD_INGOT);
        GameRegistry.addShapedRecipe(new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 5)," R ", "GIG"," R ", 'R' ,Items.REDSTONE, 'G', new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 4), 'I', Items.IRON_INGOT);
        GameRegistry.addShapedRecipe(new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 6)," R ", "GIG"," R ", 'R' ,Items.REDSTONE, 'G', new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 5), 'I', Items.IRON_INGOT);
        GameRegistry.addShapedRecipe(new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 7)," R ", "GIG"," R ", 'R' ,Items.REDSTONE, 'G', new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 6), 'I', Items.IRON_INGOT);
        GameRegistry.addShapedRecipe(new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 8)," R ", "GIG"," R ", 'R' ,Items.REDSTONE, 'G', Items.GLOWSTONE_DUST, 'I', Items.DIAMOND);
        GameRegistry.addShapedRecipe(new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 9)," R ", "GIG"," R ", 'R' ,Items.REDSTONE, 'G', new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 8), 'I', Items.IRON_INGOT);
        GameRegistry.addShapedRecipe(new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 10)," R ", "GIG"," R ", 'R' ,Items.REDSTONE, 'G', new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 9), 'I', Items.IRON_INGOT);
        GameRegistry.addShapedRecipe(new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 11)," R ", "GIG"," R ", 'R' ,Items.REDSTONE, 'G', new ItemStack(EnumItems.ITEMUPGRADE.getItem(), 1, 10), 'I', Items.IRON_INGOT);

        GameRegistry.addShapedRecipe(new ItemStack(EnumBlocks.FURNACEGENBLOCK.getBlock(), 1,0), "III", "ÏGI", "RFR", 'R', Items.REDSTONE, 'I', Items.IRON_INGOT, 'G', Items.GOLD_INGOT, 'F', Blocks.FURNACE);
        GameRegistry.addShapedRecipe(new ItemStack(EnumBlocks.FURNACEGENBLOCK.getBlock(), 1,1), "III", "ÏBI", "RFR", 'R', Items.REDSTONE, 'I', Items.IRON_INGOT, 'B', Items.BUCKET, 'F', Blocks.FURNACE);
    }
}
