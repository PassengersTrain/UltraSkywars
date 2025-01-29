package io.github.Leonardo0013YT.UltraSkyWars.addons.leaderheads;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.data.SWPlayer;
import me.robin.leaderheads.datacollectors.OnlineDataCollector;
import me.robin.leaderheads.objects.BoardType;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SkyWarsElo extends OnlineDataCollector {

    private UltraSkyWars plugin;

    public SkyWarsElo(UltraSkyWars plugin) {
        super("usw-elo", "UltraSkyWars", BoardType.DEFAULT, plugin.getLang().get(null, "leaderheads.skywarselo.title"), "skywarselo", Arrays.asList(plugin.getLang().get(null, "leaderheads.skywarselo.lines.0"), plugin.getLang().get(null, "leaderheads.skywarselo.lines.1"), plugin.getLang().get(null, "leaderheads.skywarselo.lines.2"), plugin.getLang().get(null, "leaderheads.skywarselo.lines.3")));
        this.plugin = plugin;
    }

    @Override
    public Double getScore(Player p) {
        SWPlayer sw = plugin.getDb().getSWPlayer(p);
        if (sw == null) {
            return 0.0;
        }
        return (double) sw.getElo();
    }
}