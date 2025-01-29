package io.github.Leonardo0013YT.UltraSkyWars.tops;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopPlayer {

    private String type;
    private String uuid;
    private String name;
    private int position;
    private int amount;
    private String name1;

    public TopPlayer(String name, String uuid, String name1, int position, int amount) {
        this.name = name;
        this.uuid = uuid;
        this.name1 = name1;
        this.amount = amount;
        this.position = position;
    }

    @Override
    public String toString() {
        return type + ":" + uuid + ":" + amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}