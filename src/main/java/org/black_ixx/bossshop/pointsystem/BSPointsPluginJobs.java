package org.black_ixx.bossshop.pointsystem;

import com.gamingmesh.jobs.Jobs;
import org.bukkit.OfflinePlayer;

public class BSPointsPluginJobs extends BSPointsPlugin {

    public BSPointsPluginJobs() {
        super("Jobs", "JobsReborn");
    }

    @Override
    public double getPoints(OfflinePlayer player) {
        return Jobs.getJobsDAO().getPlayerPoints(Jobs.getPlayerManager().getJobsPlayer(player.getPlayer())).getCurrentPoints();
    }

    @Override
    public double setPoints(OfflinePlayer player, double points) {
        Jobs.getJobsDAO().getPlayerPoints(Jobs.getPlayerManager().getJobsPlayer(player.getPlayer())).setPoints(points);
        return points;
    }

    @Override
    public double takePoints(OfflinePlayer player, double points) {
        Jobs.getJobsDAO().getPlayerPoints(Jobs.getPlayerManager().getJobsPlayer(player.getPlayer())).takePoints(points);
        return getPoints(player);
    }

    @Override
    public double givePoints(OfflinePlayer player, double points) {
        Jobs.getJobsDAO().getPlayerPoints(Jobs.getPlayerManager().getJobsPlayer(player.getPlayer())).addPoints(points);
        return getPoints(player);
    }

    @Override
    public boolean usesDoubleValues() {
        return true;
    }

}
