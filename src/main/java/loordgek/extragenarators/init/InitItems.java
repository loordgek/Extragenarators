package loordgek.extragenarators.init;

import loordgek.extragenarators.items.ItemMain;
import loordgek.extragenarators.items.ItemUpgrade;
import loordgek.extragenarators.ref.Reference;
import loordgek.extragenarators.util.SideUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class InitItems {
    public static final ItemMain itemupgrade = (new ItemUpgrade());

    public static void Init(){
        registeritem(itemupgrade, "upgradeitem");

    }
    private static Item registeritem(ItemMain item ,String name){
        GameRegistry.register(item , new ResourceLocation(Reference.RESOURCE.RESOURCE_PREFIX + name));
        if (SideUtil.isClient()){ModelLoader.setCustomModelResourceLocation(item,0, new ModelResourceLocation(name, "inventory"));}

        return item;
    }
}
