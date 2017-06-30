package loordgek.extragenarators.tile;

import loordgek.extragenarators.nbt.NBTSave;
import loordgek.extragenarators.util.item.InventorySimpleItemHandler;
import loordgek.extragenarators.util.item.InventoryUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileFurnaceGen extends TileGenBase{

    @NBTSave
    public InventorySimpleItemHandler fuelSlot = new InventorySimpleItemHandler(64, 1, "FuelSlot", this){
        @Override
        public boolean isStackValidForSlot(int Slot, ItemStack stack) {
            return TileEntityFurnace.isItemFuel(stack);
        }
    };

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(fuelSlot);

        return super.getCapability(capability, facing);
    }

    protected ItemStack getStack() {
        return fuelSlot.getStackInSlot(0);
    }

    @Override
    protected void updateServerSide() {
        if (HasEnergy()) sendEnergy();
        if (!IsRunning()) {
            isburing = false;
            if (getStack() != null) {
                if (HasRoomForEnergy()) {
                    if (TileEntityFurnace.isItemFuel(getStack())) {
                        maxmultiplier = fuelSlot.extractItem(0, upgrademultiplier, true).stackSize;
                        runspeed = upgradespeed / maxmultiplier;
                        fire.setFiremax(TileEntityFurnace.getItemBurnTime(getStack()) * maxmultiplier);
                        fire.setFirecurrent(TileEntityFurnace.getItemBurnTime(getStack()) * maxmultiplier);
                        fuelSlot.extractItem(0, maxmultiplier, false);
                    }
                }
            }
        } else {
            Burn();
        }
    }

    @Override
    public void breakBlock() {
        super.breakBlock();
        InventoryUtil.dropInv(worldObj, InventoryUtil.getStacks(fuelSlot), pos);
    }

    @Override
    public Object getNBTClass() {
        return this;
    }
}
