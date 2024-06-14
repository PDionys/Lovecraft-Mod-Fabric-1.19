package net.pdeins.lovecraftmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.JournalData;
import net.pdeins.lovecraftmod.util.SanityData;

public class JournalC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        //Everything here is on the server!!
        JournalData.setJournalList(((IEntityDataSaver) player), "note.lovecraftmod.firstspawn_p1");
        JournalData.setJournalList(((IEntityDataSaver) player), "note.lovecraftmod.firstspawn_p2");
        //Send total amount
        player.sendMessage(Text.literal("Lines add to journal"));
    }
}
