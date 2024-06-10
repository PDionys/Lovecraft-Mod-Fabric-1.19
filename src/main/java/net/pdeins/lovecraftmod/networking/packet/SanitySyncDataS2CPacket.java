package net.pdeins.lovecraftmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;

public class SanitySyncDataS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        if(client.player != null)
            ((IEntityDataSaver) client.player).getPersistentData().putInt("sanity", buf.readInt());
    }
}
