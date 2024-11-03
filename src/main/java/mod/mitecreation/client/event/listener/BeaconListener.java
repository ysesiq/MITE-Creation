package mod.mitecreation.client.event.listener;

import mod.mitecreation.init.RegistryInit;
import moddedmite.rustedironcore.api.event.listener.IBeaconUpdateHandler;
import net.minecraft.Block;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.TileEntityBeacon;

public class BeaconListener implements IBeaconUpdateHandler {
    @Override
    public boolean onBlockValidModify(TileEntityBeacon tileEntityBeacon, int blockID, boolean original) {
        if (blockID == Block.blockAncientMetal.blockID || blockID == RegistryInit.blockTungsten.blockID) {
            return true;
        }
        return original;
    }

    @Override
    public boolean onItemValidModify(TileEntityBeacon tileEntityBeacon, ItemStack itemStack, boolean original) {
        Item item = itemStack.getItem();
        return original || item == RegistryInit.ingotTungsten;
    }
}
