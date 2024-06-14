package net.pdeins.lovecraftmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;

import java.io.ByteArrayInputStream;
import java.io.DataInput;

public class JournalSyncS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        if(client.player != null){
            try {
                NbtCompound nbt = buf.readNbt();
                NbtList nbtList = nbt.getList("journalList", 8);
                ((IEntityDataSaver) client.player).getPersistentData().put("journalList", nbtList);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
