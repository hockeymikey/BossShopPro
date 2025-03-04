package org.black_ixx.bossshop.pointsystem;

import com.vk2gpz.tokenenchant.api.ITokenEnchant;
import com.vk2gpz.tokenenchant.api.Addon;
import org.bukkit.OfflinePlayer;


public class BSPointsPluginTokenEnchant extends BSPointsPlugin {

    public BSPointsPluginTokenEnchant() {
        super("TokenEnchant", "TokenEnchants", "TE");
    }

    @Override
    public double getPoints(OfflinePlayer player) {
        return ITokenEnchant.getInstance().getTokens(player);
    }

    @Override
    public double setPoints(OfflinePlayer player, double points) {
    	ITokenEnchant.getInstance().setTokens(player, points);
        return points;
    }

    @Override
    public double takePoints(OfflinePlayer player, double points) {
    	ITokenEnchant.getInstance().removeTokens(player, points);
        return getPoints(player);
    }

    @Override
    public double givePoints(OfflinePlayer player, double points) {
    	ITokenEnchant.getInstance().addTokens(player, points);
        return getPoints(player);
    }

    @Override
    public boolean usesDoubleValues() {
        return true;
    }

}
