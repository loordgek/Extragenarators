package loordgek.extragenarators.tile;

import loordgek.extragenarators.nbt.NBTSave;
import loordgek.extragenarators.util.item.SimpleItemHandler;
import loordgek.extragenarators.util.item.InventoryUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileFurnaceGen extends TileGenBase{

    @NBTSave
    public SimpleItemHandler fuelSlot = new SimpleItemHandler(64, 1, this){
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

    private ItemStack getStack() {
        return fuelSlot.getStackInSlot(0);
    }

    @Override
    protected void updateServerSide() {
        if (HasEnergy()) sendEnergy();
        if (!IsRunning()) {
            isburing = false;
            if (!getStack().isEmpty()) {
                if (HasRoomForEnergy()) {
                    if (TileEntityFurnace.isItemFuel(getStack())) {
                        maxmultiplier = fuelSlot.extractItem(0, upgrademultiplier, true).getCount();
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
        InventoryUtil.dropInv(world, fuelSlot, pos);
    }

    @Override
    public Object getNBTClass() {
        return this;
    }
}
