package net.pdeins.lovecraftmod.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pdeins.lovecraftmod.util.JournalData;

public class OnDeathSaveHandler implements ServerPlayerEvents.CopyFrom{
    @Override
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        if(!alive){
            JournalData.saveJournalAfterDeath(oldPlayer, newPlayer);
        }
    }
}
