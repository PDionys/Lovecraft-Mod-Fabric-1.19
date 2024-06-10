package net.pdeins.lovecraftmod.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pdeins.lovecraftmod.networking.ModPackets;

public class SanityData {
    private static final int MAX_SANITY = 10;

    public static int addSanity(IEntityDataSaver player, int amount){
        NbtCompound nbt = player.getPersistentData();
        int sanity = nbt.getInt("sanity");

        if(sanity + amount >= MAX_SANITY){
            sanity = MAX_SANITY;
        }else {
            sanity += amount;
        }

        nbt.putInt("sanity", sanity);
        syncSanity(sanity, ((ServerPlayerEntity) player));
        return sanity;
    }

    public static int removeSanity(IEntityDataSaver player, int amount){
        NbtCompound nbt = player.getPersistentData();
        int sanity = nbt.getInt("sanity");

        if(sanity - amount <= 0){
            sanity = 0;
            ((ServerPlayerEntity) player).kill();
        }else {
            sanity -= amount;
        }

        nbt.putInt("sanity", sanity);
        syncSanity(sanity, ((ServerPlayerEntity) player));
        return sanity;
    }

    public static void syncSanity(int sanity, ServerPlayerEntity player){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(sanity);
        ServerPlayNetworking.send(player, ModPackets.SANITY_SYNC_ID, buf);
    }

    public static int getMaxSanity(){
        return MAX_SANITY;
    }

}
