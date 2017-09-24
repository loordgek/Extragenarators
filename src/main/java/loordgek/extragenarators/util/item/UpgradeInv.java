package loordgek.extragenarators.util.item;


public class UpgradeInv extends SimpleItemHandler{
    public UpgradeInv(int stacksize, int invsize, IInventoryOwner onwer) {
        super(stacksize, invsize, onwer);
    }

    @Override
    public void onSlotChanged() {
        getOwner().updateItemHandler();
    }
}
