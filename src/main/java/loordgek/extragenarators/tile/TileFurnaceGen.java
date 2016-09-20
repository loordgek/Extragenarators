package loordgek.extragenarators.tile;

import loordgek.extragenarators.enums.EnumInvFlow;
import loordgek.extragenarators.util.item.InventorySimpleItemhander;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileFurnaceGen extends TileGenBase implements ITickable {
    public InventorySimpleItemhander fuelSlot = new InventorySimpleItemhander(64, 1, "FuelSlot", this);

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return  capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return  CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(fuelSlot);

        return super.getCapability(capability, facing);
    }

    public ItemStack getStack(){
        return fuelSlot.getStackInSlot(0);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        fuelSlot.deserializeNBT(compound.getCompoundTag("FuelSlot"));

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("FuelSlot", fuelSlot.serializeNBT());
        return compound;
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
                                fuelSlot.extractItem(0, 1, false);
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

    @Override
    public void OnInventoryChanged(ItemStack stack, int slot, String name, EnumInvFlow flow) {

    }
}
