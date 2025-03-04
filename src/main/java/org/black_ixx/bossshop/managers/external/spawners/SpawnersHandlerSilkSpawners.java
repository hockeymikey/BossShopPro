package org.black_ixx.bossshop.managers.external.spawners;


import de.dustplanet.util.SilkUtil;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;


public class SpawnersHandlerSilkSpawners implements ISpawnerHandler, ISpawnEggHandler {

    private SilkUtil util;


    public SpawnersHandlerSilkSpawners() {
        util = SilkUtil.hookIntoSilkSpanwers();
    }

    public ItemStack transformSpawner(ItemStack i, String entityName) {
        EntityType type = InputReader.readEntityType(entityName);
        if (type == null) {
            return null;
        }
        return util.newSpawnerItem(entityName, util.getCustomSpawnerName(entityName), i.getAmount(), false);
    }

    public ItemStack transformEgg(ItemStack i, String entityName) {
        EntityType type = InputReader.readEntityType(entityName);
        if (type == null) {
            return null;
        }
        return util.newEggItem(entityName, i.getAmount(), null);
    }


    public String readSpawner(ItemStack i) {
        String entityid = util.getStoredSpawnerItemEntityID(i);
        String creaturename = util.getCreatureName(entityid);
        return creaturename;
    }

    public String readEgg(ItemStack i) {
    	String entityid = util.getStoredEggEntityID(i);
        String creaturename = util.getCreatureName(entityid);
        return creaturename;
    }
}
