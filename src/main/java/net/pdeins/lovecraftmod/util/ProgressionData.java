package net.pdeins.lovecraftmod.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class ProgressionData {
    public static void setProgression(IEntityDataSaver player, String id){
        NbtCompound nbt = player.getPersistentData();
        nbt.putBoolean(id, true);
        //sync
    }

    public static void syncProgression(ServerPlayerEntity player, String id){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(true);

    }
}
