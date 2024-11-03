package mod.mitecreation.block;

import mod.mitecreation.init.RegistryInit;
import net.minecraft.*;

import java.util.Random;

public class BlockSandGravel extends BlockFalling {
    public BlockSandGravel(int par1) {
        super(par1, Material.sand, (new BlockConstants()).setUseNewSandPhysics());
    }

    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        if (!info.wasExploded() && info.wasHarvestedByPlayer()) {
            int fortune = info.getHarvesterFortune();
            if (fortune > 3) {
                fortune = 3;
            }

            Random rand = info.world.rand;
            if (rand.nextInt(12 - fortune * 2) > 2) {
                return super.dropBlockAsEntityItem(info);
            } else {
                int id_dropped;
                if (rand.nextInt(3) > 0) {
                    if (rand.nextInt(16) == 0) {
                        id_dropped = info.wasExploded() ? Item.chipFlint.itemID : Item.flint.itemID;
                    } else {
                        if (info.wasExploded()) {
                            return super.dropBlockAsEntityItem(info);
                        }

                        id_dropped = Item.chipFlint.itemID;
                    }
                } else if (rand.nextInt(3) > 0) {
                    id_dropped = RegistryInit.rawGoldNugget.itemID;
                } else if (rand.nextInt(3) > 0) {
                    id_dropped = RegistryInit.rawCopperNugget.itemID;
                } else if (rand.nextInt(3) > 0) {
                    id_dropped = RegistryInit.rawSilverNugget.itemID;
                } else if (rand.nextInt(3) > 0) {
                    id_dropped = info.wasExploded() ? -1 : Item.shardObsidian.itemID;
                } else if (rand.nextInt(3) > 0) {
                    id_dropped = info.wasExploded() ? -1 : Item.shardDiamond.itemID;
                } else if (rand.nextInt(3) > 0) {
                    id_dropped = RegistryInit.rawMithrilNugget.itemID;
                } else {
                    id_dropped = RegistryInit.rawAdamantiumNugget.itemID;
                }

                if (id_dropped != -1) {
                    DedicatedServer.incrementTournamentScoringCounter(info.getResponsiblePlayer(), Item.getItem(id_dropped));
                }

                if (info.wasHarvestedByPlayer() && (id_dropped == Item.chipFlint.itemID || id_dropped == Item.flint.itemID)) {
                    info.getResponsiblePlayer().triggerAchievement(AchievementList.flintFinder);
                }

                return this.dropBlockAsEntityItem(info, id_dropped);
            }
        } else {
            return super.dropBlockAsEntityItem(info);
        }
    }
}
