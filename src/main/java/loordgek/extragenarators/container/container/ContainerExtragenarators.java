package loordgek.extragenarators.container.container;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import loordgek.extragenarators.container.slot.SlotInvHolder;
import loordgek.extragenarators.network.GuiSync;
import loordgek.extragenarators.network.GuiSyncPacket;
import loordgek.extragenarators.network.NetworkHandler;
import loordgek.extragenarators.network.NetworkUtils;
import loordgek.extragenarators.network.SyncField;
import loordgek.extragenarators.util.item.IInvHolder;
import loordgek.extragenarators.util.item.InventoryHolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/*
*    PneumaticCraft code. author = MineMaarten
*    https://github.com/MineMaarten/PneumaticCraft
*/
public abstract class ContainerExtragenarators<Tile extends IContainerGuiSync> extends Container {
    protected Tile te;

    private Multimap<IInvHolder, IInvHolder> inventoryShifting = HashMultimap.create();
    private final List<SyncField> syncFields = new ArrayList<>();
    protected final EntityPlayer player;
    InventoryHolder playerInv;
    private int timer;

    public ContainerExtragenarators(EntityPlayer player, Tile te) {
        this.te = te;
        this.player = player;
        if (te != null) addSyncFields(te);
        this.playerInv = new InventoryHolder(player.inventory);
    }

    protected void addPlayerSlots(InventoryHolder inventoryHolder, int x, int y) {
        addSlotMatrix(x,y, 18, 18, inventoryHolder, 9, 3, 9);
        addSlotRow(x,y + 58, 18, inventoryHolder, 0, 9);
    }

    protected void addSlotRow(int x, int y, int sep, IInvHolder holder, int startSlot, int amt) {
        addSlotMatrix(x, y, sep, sep, holder, startSlot, amt, 0);
    }

    protected void addSlotMatrix(int x, int y, int hSep, int vSep, IInvHolder holder, int startSlot, int col, int row) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                addSlotToContainer(new SlotInvHolder(holder, j + i * col + startSlot, x + hSep * j, y + vSep * i));
            }
        }
    }

    protected void addShiftClickingHandler(IInvHolder from, IInvHolder to){
        inventoryShifting.put(from, to);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (timer < 2)
            timer++;// this needs to be here. if i start to sync the from the start it does not work
        else
        for (int i = 0; i < syncFields.size(); i++) {
            if (syncFields.get(i).update()) {
                sendToCrafters(new GuiSyncPacket(i, syncFields.get(i)));
            }
        }
    }

    private void sendToCrafters(IMessage message) {
        for (IContainerListener crafter : listeners) {
            if (crafter instanceof EntityPlayerMP) {
                NetworkHandler.SentTo(message, (EntityPlayerMP) crafter);
            }
        }
    }

    private void addSyncField(SyncField field) {
        syncFields.add(field);
        field.setLazy(false);
    }

    private void addSyncFields(Object annotatedObject) {
        List<SyncField> fields = NetworkUtils.getSyncFields(annotatedObject, GuiSync.class);
        for (SyncField field : fields)
            addSyncField(field);
    }

    @SuppressWarnings("unchecked")
    public void Updatefield(int index, Object value) {
        syncFields.get(index).setValue(value);
        if (te != null) te.onGuiUpdate();
    }

    @Override
    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot slot = inventorySlots.get(index);
        if (slot instanceof SlotInvHolder){
            SlotInvHolder slotInvHolder = (SlotInvHolder)slot;
            if (!inventoryShifting.containsKey(slotInvHolder.getInvHolder())) return ItemStack.EMPTY;
            for (IInvHolder slotHandlers : inventoryShifting.get(slotInvHolder.getInvHolder())){
                if (!slotHandlers.handleShiftClick(slotInvHolder, playerIn).isPresent()){
                    return ItemStack.EMPTY;
                }
            }
        }
        return ItemStack.EMPTY;
    }
}


