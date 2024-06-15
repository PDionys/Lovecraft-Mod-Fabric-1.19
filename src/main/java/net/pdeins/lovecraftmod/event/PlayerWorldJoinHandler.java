package net.pdeins.lovecraftmod.event;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pdeins.lovecraftmod.networking.ModPackets;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.JournalData;
import net.pdeins.lovecraftmod.util.ProgressionData;

public class PlayerWorldJoinHandler implements ServerPlayConnectionEvents.Join{
    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        ServerPlayerEntity player = handler.player;
        NbtCompound nbt = ((IEntityDataSaver) player).getPersistentData();

        if (!nbt.getBoolean("spawnin_world")){
            //Set progress
            ProgressionData.setProgression(((IEntityDataSaver) player), "spawnin_world");
            //Write journal
            JournalData.setJournalList(((IEntityDataSaver) player), "note.lovecraftmod.firstspawn_p1");
            JournalData.setJournalList(((IEntityDataSaver) player), "note.lovecraftmod.firstspawn_p2");
            //Play animation
            //Play "write" sound
        }
    }
}
