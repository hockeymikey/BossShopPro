package org.black_ixx.bossshop.managers.misc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacketManager {

    private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    private static final List<ItemStack> emptyInventory;
    private static final PacketContainer inventoryClearPacket;

    static {
        emptyInventory = new ArrayList<>(45);
        for(int i = 0; i < 45; i++) {
            emptyInventory.add(new ItemStack(Material.AIR));
        }
        inventoryClearPacket = new PacketContainer(PacketType.Play.Server.WINDOW_ITEMS);
        inventoryClearPacket.getIntegers().write(0, 0);
        inventoryClearPacket.getIntegers().write(1, -1);
        inventoryClearPacket.getItemListModifier().write(0, emptyInventory);
        inventoryClearPacket.getItemModifier().write(0, new ItemStack(Material.AIR));
    }

    public static void clearPlayerInventory(Player player) {
        protocolManager.sendServerPacket(player, inventoryClearPacket);

    }
    public static void restorePlayerInventory(Player player) {
        /*PacketContainer inventoryRestorePacket = new PacketContainer(PacketType.Play.Server.WINDOW_ITEMS);
        inventoryClearPacket.getIntegers().write(0, 0);
        inventoryClearPacket.getIntegers().write(1, -1);
        List<ItemStack> playerItems = new ArrayList<>(45);
        for(int i = 0; i < 45; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if(item == null)
                item = new ItemStack(Material.AIR);
            playerItems.add(item);
        }
        inventoryClearPacket.getItemListModifier().write(0, playerItems);
        inventoryClearPacket.getItemModifier().write(0, new ItemStack(Material.AIR));
        protocolManager.sendServerPacket(player, inventoryRestorePacket);*/
        PlayerInventory inventory = player.getInventory();
        inventory.setContents(inventory.getContents());
    }

}
