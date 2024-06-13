package net.pdeins.lovecraftmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.pdeins.lovecraftmod.sound.ModSounds;

public class PlayPageTurnSoundC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender){
        player.getWorld().playSound(
                null,
                player.getBlockPos(),
                ModSounds.PAGE_TURN_SOUND,
                SoundCategory.PLAYERS,
                1f,1f
        );
    }
}
