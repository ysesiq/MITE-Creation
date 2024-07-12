package mod.mitecreation;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.EnumQuality;
import net.xiaoyu233.fml.util.EnumExtends;

public class EarlyRiser implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        EnumExtends.EQUIPMENT_MATERIAL.addEnum("tungsten", () -> new Object[]{32.0F, 35, EnumQuality.masterwork, "tungsten"});
        EnumExtends.EQUIPMENT_MATERIAL.addEnum("stone", () -> new Object[]{2.0F, 0, EnumQuality.poor, "stone"});
    }
}
