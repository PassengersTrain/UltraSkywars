package io.github.Leonardo0013YT.UltraSkyWars.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@Getter
public class GlassBlock {
    Material material;
    int data;

    public Material getMaterial() {
        return material;
    }

    public int getData() {
        return data;
    }
}