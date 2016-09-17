package loordgek.extragenarators;

import loordgek.extragenarators.client.gui.GuiFurnaceGen;
import loordgek.extragenarators.client.gui.GuiUpgrade;
import loordgek.extragenarators.container.container.ContainerFurnaceGen;
import loordgek.extragenarators.container.container.ContainerUpgrade;
import loordgek.extragenarators.tile.TileFurnaceGen;
import loordgek.extragenarators.tile.TileGenBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHander implements IGuiHandler {
    public enum GuiIDS{
        upgradegui,
        furnacegengui,
        lavagengui,
        endergengui,
    }
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x,y,z));
        switch (GuiIDS.values()[ID]){
            case upgradegui:
                return new ContainerUpgrade(player ,(TileGenBase)tile);
            case furnacegengui:
                return new ContainerFurnaceGen(player ,(TileFurnaceGen)tile);
        }

        return null;
}
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        TileEntity tile = world.getTileEntity(new BlockPos(x,y,z));
        switch (GuiIDS.values()[ID]){
            case upgradegui:
                return new GuiUpgrade(player ,(TileGenBase)tile);
            case furnacegengui:
                return new GuiFurnaceGen(player ,(TileFurnaceGen)tile);
        }

            return null;
    }
}
