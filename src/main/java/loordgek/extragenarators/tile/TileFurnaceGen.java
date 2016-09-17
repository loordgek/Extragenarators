package loordgek.extragenarators.tile;

import loordgek.extragenarators.util.LogHelper;
import loordgek.extragenarators.util.item.InventorySimpleIinv;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileFurnaceGen extends TileGenBase implements ITickable {
    private InventorySimpleIinv inventorySimpleIinv = new InventorySimpleIinv("upgrade_inv", 64, 1, this);
    public InvWrapper invWrapper = new InvWrapper(inventorySimpleIinv);

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return  capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return  CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(invWrapper);

        return super.getCapability(capability, facing);
    }

    public ItemStack getStack(){
        return invWrapper.getStackInSlot(0);
    }

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            if (HasRoomForEnergy()){
                if (!IsRunning()){

                    if (getStack() !=  null) {
                        if(TileEntityFurnace.isItemFuel(getStack())) {
                                burntime = TileEntityFurnace.getItemBurnTime(getStack());
                                currentburntime = TileEntityFurnace.getItemBurnTime(getStack());
                                invWrapper.extractItem(0, 1, false);
                        }
                    }
                }
                else {
                    currentburntime -= 1;
                    if (HasRoomForEnergy()){
                        energyStorage.receiveEnergy(AddPowerToStorage(), false);

                    }
                    if (currentburntime == 0)
                        burntime = 0;
                }
            }
        }
    }
}
