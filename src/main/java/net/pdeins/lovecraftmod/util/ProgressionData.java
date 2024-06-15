package net.pdeins.lovecraftmod.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pdeins.lovecraftmod.networking.ModPackets;

public class ProgressionData {
    private static final String SPAWNIN_WORLD = "spawnin_world";

    public static void setProgression(IEntityDataSaver player, String id){
        NbtCompound nbt = player.getPersistentData();
        nbt.putBoolean(id, true);
        //sync
        syncProgression(((ServerPlayerEntity) player), id);
    }

    public static boolean getProgression(IEntityDataSaver player, String id){
        NbtCompound nbt = player.getPersistentData();
        return nbt.getBoolean(id);
    }

    public static void syncProgression(ServerPlayerEntity player, String id){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(id);
        ServerPlayNetworking.send(player, ModPackets.PROGRESS_SYNC_ID, buf);
    }

    public static void saveProgressAfterDeath(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, String id){
        NbtCompound oldNbt = ((IEntityDataSaver) oldPlayer).getPersistentData();
        NbtCompound newNbt = ((IEntityDataSaver) newPlayer).getPersistentData();

        newNbt.putBoolean(id, oldNbt.getBoolean(id));
    }

    public static String getSpawninWorld(){
        return SPAWNIN_WORLD;
    }
}
