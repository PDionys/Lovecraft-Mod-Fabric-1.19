package net.pdeins.lovecraftmod.util;

import net.minecraft.nbt.NbtCompound;

public class SanityData {
    public static int addSanity(IEntityDataSaver player, int amount){
        NbtCompound nbt = player.getPersistentData();
        int sanity = nbt.getInt("sanity");

        if(sanity + amount >= 10){
            sanity = 10;
        }else {
            sanity += amount;
        }

        nbt.putInt("sanity", sanity);
        //TODO sync the data
        return sanity;
    }

    public static int removeSanity(IEntityDataSaver player, int amount){
        NbtCompound nbt = player.getPersistentData();
        int sanity = nbt.getInt("sanity");

        if(sanity + amount < 0){
            sanity = 0;
        }else {
            sanity -= amount;
        }

        nbt.putInt("sanity", sanity);
        //TODO sync the data
        return sanity;
    }

}
