package loordgek.extragenarators.util.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

public class Tank extends FluidTank{

    public Tank(int capacity) {
        super(capacity);
    }

    public Tank(@Nullable FluidStack fluidStack, int capacity) {
        super(fluidStack, capacity);
    }

    public Tank(Fluid fluid, int amount, int capacity) {
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

    public FluidStack drainMinInternal(int minDrain, int drain) {
        if (fluid != null && fluid.amount >= minDrain) {

            return drainInternal(drain, true);
        }
        return null;
    }

    public int drainMinInternalInt(int minDrain, int drain) {
        if (fluid != null && fluid.amount >= minDrain) {

            return drainInternal(drain, true).amount;
        }
        return 0;
    }
}
