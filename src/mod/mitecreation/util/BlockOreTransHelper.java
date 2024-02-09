package mod.mitecreation.util;

import net.minecraft.BlockSubtypes;
import net.minecraft.Minecraft;

public class BlockOreTransHelper {
    public static String[] setRichOrPoor(String string) {
        String[] stringArray = new String[]{"ore/poor/" + string + "_poor","ore/normal/" + string + "_normal","ore/rich/" + string + "_rich"};
        return stringArray;
    }

    public static String isRichOrPoorHelper(Integer n) {
        String string;
        if (n == 2) {
            string = "poor";
        } else if (n == 1) {
            string = "normal";
        } else if (n == 0) {
            string = "rich";
        } else {
            Minecraft.setErrorMessage("isRichOrPoor:unhandled subtype");
            string = null;
        }
        return string;
    }

    public static BlockSubtypes subtypesInitializer() {
        BlockSubtypes subtypes = new BlockSubtypes(new String[]{"rich_ore", "normal_ore","poor_ore"});
        return subtypes;
    }
}