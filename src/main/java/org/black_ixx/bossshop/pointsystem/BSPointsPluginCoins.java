package org.black_ixx.bossshop.pointsystem;

import me.justeli.coins.Coins;
import net.milkbowl.vault.economy.EconomyResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BSPointsPluginCoins extends BSPointsPlugin {
    public BSPointsPluginCoins() {
        super("Coins");
    }
    
    private Coins getCoinsPlugin() {
    	Plugin plugin = Bukkit.getPluginManager().getPlugin("Coins");
    	
    	if (plugin != null && plugin instanceof JavaPlugin) {
            return (Coins) plugin;
        } else {
            return null;
        }
    }


    @Override
    public double getPoints(OfflinePlayer player) {
    	CompletableFuture<Double> future = new CompletableFuture<>();
    	getCoinsPlugin().economy().balance(player.getUniqueId(), balance -> {
        	future.complete(balance);
        });
        try {
			return future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return 0.0D;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return 0.0D;
		} 
    }

    @Override
    public double setPoints(OfflinePlayer player, double points) {
    	CompletableFuture<Double> future = new CompletableFuture<>();
    	getCoinsPlugin().economy().balance(player.getUniqueId(), balance -> {
        	future.complete(balance);
        });
        try {
            if (future.get() > points) {
            	getCoinsPlugin().economy().withdraw(player.getUniqueId(), future.get() - points, () -> {});
            } else {
            	getCoinsPlugin().economy().deposit(player.getUniqueId(), points - future.get(), () -> {});
            }
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} 

        return points;
    }

    @Override
    public double takePoints(OfflinePlayer player, double points) {
        getCoinsPlugin().economy().withdraw(player.getUniqueId(), points, () -> {});
    	CompletableFuture<Double> future = new CompletableFuture<>();
    	getCoinsPlugin().economy().balance(player.getUniqueId(), balance -> {
        	future.complete(balance);
        });
        try {
			return future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return 0.0D;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return 0.0D;
		}
    }

    @Override
    public double givePoints(OfflinePlayer player, double points) {
    	getCoinsPlugin().economy().deposit(player.getUniqueId(), points, () -> {});
    	CompletableFuture<Double> future = new CompletableFuture<>();
    	getCoinsPlugin().economy().balance(player.getUniqueId(), balance -> {
        	future.complete(balance);
        });
        try {
			return future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return 0.0D;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return 0.0D;
		}
    }

    @Override
    public boolean usesDoubleValues() {
        return false;
    }

}
