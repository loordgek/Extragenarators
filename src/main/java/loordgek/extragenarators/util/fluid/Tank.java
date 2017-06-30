package loordgek.extragenarators.util.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

public class AdvFluidTank extends FluidTank{


    public AdvFluidTank(int capacity) {
        super(capacity);
    }

    public AdvFluidTank(@Nullable FluidStack fluidStack, int capacity) {
        super(fluidStack, capacity);
    }

    public AdvFluidTank(Fluid fluid, int amount, int capacity) {
        super(fluid, amount, capacity);
    }

    public boolean isEmpty() {
        return fluid == null || fluid.amount <= 0;
    }

    public boolean isFull() {
        return fluid != null && fluid.amount >= capacity;
    }

    public int getFreeSpace() {
        return getCapacity() - getFluidAmount();
    }
}
