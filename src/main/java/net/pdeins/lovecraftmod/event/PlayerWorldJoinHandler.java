package net.pdeins.lovecraftmod.event;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.JournalData;
import net.pdeins.lovecraftmod.util.ProgressionData;

public class PlayerWorldJoinHandler implements ServerPlayConnectionEvents.Join{

    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        ServerPlayerEntity player = handler.player;

        if (!ProgressionData.getProgression(((IEntityDataSaver) player), ProgressionData.getSpawninWorld())){
            //Set progress
            ProgressionData.setProgression(((IEntityDataSaver) player), ProgressionData.getSpawninWorld());
            //Write journal
            JournalData.setJournalList(((IEntityDataSaver) player), "note.lovecraftmod.firstspawn_p1");
            JournalData.setJournalList(((IEntityDataSaver) player), "note.lovecraftmod.firstspawn_p2");
            //Play animation
            //Play "write" sound
        }
    }
}
