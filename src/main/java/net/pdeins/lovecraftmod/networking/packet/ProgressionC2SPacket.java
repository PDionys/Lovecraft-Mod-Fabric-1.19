package net.pdeins.lovecraftmod.networking.packet;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.ProgressionData;
import net.pdeins.lovecraftmod.util.SanityData;

public class ProgressionC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        //Everything here is on the server!!
        ProgressionData.setProgression(((IEntityDataSaver) player), buf.readString());
        player.sendMessage(Text.literal("You join to the world a first time!!!"));
    }
}
