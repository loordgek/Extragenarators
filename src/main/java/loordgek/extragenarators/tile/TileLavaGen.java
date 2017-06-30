package loordgek.extragenarators.tile;

import loordgek.extragenarators.nbt.NBTSave;
import loordgek.extragenarators.network.GuiSync;
import loordgek.extragenarators.util.fluid.Tank;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileLavaGen extends TileGenBase {

    @NBTSave
    @GuiSync
    public Tank tank = new Tank(64000) {
        @Override
        protected void onContentsChanged() {
            markDirty();
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid() == FluidRegistry.LAVA;
        }

        @Override
        public boolean canDrain() {
            return false;
        }
    };

    public TileLavaGen() {
        super();
        tank.setTileEntity(this);
    }

    @Override
    protected void updateServerSide() {
        if (HasEnergy()) sendEnergy();
        if (!IsRunning()) {
            if (HasRoomForEnergy()){
                if (!tank.isEmpty()) {
                    maxmultiplier = tank.drainMinInternalInt(100, upgrademultiplier * 100) / 100;
                    fire.setFiremax(200 * maxmultiplier);
                    fire.setFirecurrent(200 * maxmultiplier);
                }
            }
        }
        else Burn();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public Object getNBTClass() {
        return this;
    }
}
