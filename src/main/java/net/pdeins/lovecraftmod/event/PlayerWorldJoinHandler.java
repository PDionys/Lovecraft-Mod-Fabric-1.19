package net.pdeins.lovecraftmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.ProgressRecord;
import net.pdeins.lovecraftmod.util.ProgressionData;

public class PlayerWorldJoinHandler implements ServerPlayConnectionEvents.Join{

    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        ServerPlayerEntity player = handler.player;

        if (!ProgressionData.getProgression(((IEntityDataSaver) player), ProgressionData.getProgressIdElement(0))){
            ProgressRecord.changeProgressAndJournal(player, server, ProgressionData.getProgressIdElement(0));
            //Play "write" sound and animation after load screen
            ServerEntityEvents.ENTITY_LOAD.register(new  FirstLoadWorldHandler());
        }
    }
}
