package net.pdeins.lovecraftmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.pdeins.lovecraftmod.sound.ModSounds;

public class PlayOpenBookSoundC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        //TODO make open sound louder
        player.playSound(
                ModSounds.OPEN_BOOK_SOUND,
                SoundCategory.PLAYERS,
                1f,1f
        );
    }
}
