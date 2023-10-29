package org.black_ixx.bossshop.managers.misc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class PacketManager {

    private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    private static final List<ItemStack> emptyInventory;
    private static final PacketContainer inventoryClearPacket;

    private static final HashMap<Player, Integer> hiddenInventoriesPlayers = new HashMap<>();

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
        hiddenInventoriesPlayers.put(player, player.getOpenInventory().getTopInventory().getSize());

    }

    public static void restorePlayerInventory(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.setContents(inventory.getContents());
        hiddenInventoriesPlayers.remove(player);
    }

    public static void cancelPacketsForHiddenInventories(JavaPlugin plugin) {
        Bukkit.broadcastMessage("LOADED!");
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.WINDOW_CLICK) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if(!hiddenInventoriesPlayers.containsKey(event.getPlayer()))
                    return;

                if(event.getPacket().getIntegers().getValues().get(2) >= hiddenInventoriesPlayers.get(event.getPlayer())) {
                    event.setCancelled(true);
                }
            }
        });
    }

}
