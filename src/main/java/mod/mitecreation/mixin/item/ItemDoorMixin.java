package mod.mitecreation.mixin.item;

import net.minecraft.Block;
import net.minecraft.ItemDoor;
import net.minecraft.Material;
import mod.mitecreation.materil.Materials;
import mod.mitecreation.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemDoor.class)
public class ItemDoorMixin {

    @Inject(method = "getBlock", at = @At("HEAD"), cancellable = true)
    private void getBlockCreation(CallbackInfoReturnable<Block> cir) {
        if (this.door_material == Materials.tungsten)
            cir.setReturnValue(Blocks.doorTungsten);
    }

    @Shadow
    private Material door_material;
}