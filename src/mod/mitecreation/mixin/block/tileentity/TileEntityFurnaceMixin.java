package mod.mitecreation.mixin.block.tileentity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value=TileEntityFurnace.class)
public class TileEntityFurnaceMixin extends TileEntityMixin {
    @Shadow
    private ItemStack[] furnaceItemStacks;
    @Shadow
    private int furnaceBurnTime;
    @Shadow
    private int furnaceCookTime;
    @Shadow
    private int heat_level;
    @Shadow
    private int currentItemBurnTime;
    
    @Overwrite
    public static int getHeatLevelRequired(int n) {
        if (n == Block.oreAdamantium.blockID) {
            return 4;
        }
        if (n == Block.oreMithril.blockID) {
            return 3;
        }
        if (n == Block.oreCopper.blockID || n == Block.oreSilver.blockID || n == Block.oreGold.blockID || n == Block.oreIron.blockID) {
            return 2;
        }
        if (n == Block.oreNetherQuartz.blockID || n == Block.oreEmerald.blockID || n == Block.oreDiamond.blockID || n == Block.oreRedstone.blockID) {
            return 2;
        }
        if (n == Block.oreLapis.blockID) {
            return 2;
        }
        if (n == Block.sandStone.blockID) {
            return 2;
        }
        return 1;
    }
    @Overwrite
    public void smeltItem(int n) {
        if (this.canSmelt(n)) {
            ItemStack itemStack = RecipesFurnace.smelting().getSmeltingResult(this.getInputItemStack(), n);
            if (this.furnaceItemStacks[2] == null) {
                this.furnaceItemStacks[2] = itemStack.copy();
            } else if (this.furnaceItemStacks[2].itemID == itemStack.itemID) {
                this.furnaceItemStacks[2].stackSize += itemStack.stackSize;
            }
            int n2 = this.getInputItemStack().itemID == Block.sand.blockID && itemStack.itemID == Block.sandStone.blockID ? 4 : (this.getInputItemStack().itemID == Block.sand.blockID && itemStack.itemID == Block.glass.blockID ? 4 : (this.getInputItemStack().itemID == Block.blockIron.blockID && itemStack.itemID == Item.ingotMithril.itemID ? 4 : (this.getInputItemStack().itemID == Block.blockMithril.blockID && itemStack.itemID == Item.ingotAdamantium.itemID ? 1 : 1)));
            ItemStack itemStack2 = this.getInputItemStack();
            itemStack2.stackSize -= n2;
            if (this.getInputItemStack().getItem() == Item.clay && itemStack.getItem() == Item.brick) {
                int n3 = Math.min(this.getOutputItemStack().getMaxStackSize() - this.getOutputItemStack().stackSize, this.getInputItemStack().stackSize);
                if (n3 > 3) {
                    n3 = 3;
                }
                itemStack2 = this.getOutputItemStack();
                itemStack2.stackSize += n3;
                itemStack2 = this.getInputItemStack();
                itemStack2.stackSize -= n3;
            }
            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }
    @Shadow
    private ItemStack getOutputItemStack() {
        return null;
    }
    @Shadow
    private ItemStack getInputItemStack() {
        return null;
    }
    @Shadow
    private boolean canSmelt(int n) {
        return false;
    }
    @Overwrite
    public void updateEntity() {
        if (this.worldObj.isRemote || this.furnaceBurnTime == 1 || !this.isFlooded() && !this.isSmotheredBySolidBlock()) {
            boolean bl = this.furnaceBurnTime > 0;
            boolean bl2 = false;
            if (this.furnaceBurnTime > 0) {
                --this.furnaceBurnTime;
            } else {
                this.heat_level = 0;
            }
            if (!this.worldObj.isRemote) {
                if (this.furnaceBurnTime == 0 && this.canSmelt(this.getFuelHeatLevel())) {
                    this.currentItemBurnTime = this.furnaceBurnTime = this.getItemBurnTime(this.furnaceItemStacks[1]);
                    if (this.furnaceBurnTime > 0) {
                        this.heat_level = this.getItemHeatLevel(this.furnaceItemStacks[1]);
                        bl2 = true;
                        if (this.furnaceItemStacks[1] != null) {
                            --this.furnaceItemStacks[1].stackSize;
                            if (this.furnaceItemStacks[1].stackSize == 0) {
                                Item item = this.furnaceItemStacks[1].getItem().getContainerItem();
                                ItemStack itemStack = this.furnaceItemStacks[1] = item != null ? new ItemStack(item) : null;
                            }
                        }
                    }
                }
                if (this.isBurning() && this.canSmelt(this.heat_level)) {
                    ++this.furnaceCookTime;
                    if (this.furnaceCookTime == this.furnaceItemStacks[0].getItem().getCookTime(this.heat_level) / this.heat_level) {
                        this.furnaceCookTime = 0;
                        this.smeltItem(this.heat_level);
                        bl2 = true;
                    }
                } else {
                    this.furnaceCookTime = 0;
                }
                if (bl != this.furnaceBurnTime > 0) {
                    bl2 = true;
                    BlockFurnace.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
                }
            }
            if (bl2) {
                super.onInventoryChanged();
            }
        } else {
            if (this.furnaceBurnTime > 0) {
                if (this.isFlooded()) {
//                    this.worldObj.blockFX(EnumBlockFX.steam, this.xCoord, this.yCoord, this.zCoord);
                }
                BlockFurnace.updateFurnaceBlockState(false, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
            this.furnaceBurnTime = 0;
            this.furnaceCookTime = 0;
        }
    }
    @Shadow
    private int getFuelHeatLevel() {
        return this.getItemHeatLevel(this.getFuelItemStack());
    }
    @Shadow
    private ItemStack getFuelItemStack() {
        return null;
    }
    @Shadow
    private int getItemBurnTime(ItemStack furnaceItemStack) {
        return 0;
    }
    @Shadow
    private int getItemHeatLevel(ItemStack furnaceItemStack) {
        return 0;
    }
    @Shadow
    private boolean isSmotheredBySolidBlock() {
        return false;
    }
    @Shadow
    private boolean isFlooded() {
        return false;
    }
    @Shadow
    private boolean isBurning() {
        return false;
    }
    @Overwrite
    public int d(int n) {
        return this.furnaceItemStacks[0] == null ? this.furnaceCookTime * n / 200 : this.furnaceCookTime * n / (this.furnaceItemStacks[0].getItem().getCookTime(this.heat_level) / Math.max(this.heat_level, 1));
    }
}

