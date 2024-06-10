package net.pdeins.lovecraftmod.event;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.SanityData;

import java.util.Random;

public class PlayerTickHandler implements ServerTickEvents.StartTick{
    @Override
    public void onStartTick(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            //TODO check how it work in online
            SanityData.syncSanity(((IEntityDataSaver) player).getPersistentData().getInt("sanity"), player);
            if(new Random().nextFloat() <= 0.005f){
                IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
                SanityData.removeSanity(dataPlayer, 1);
                player.sendMessage(Text.literal("Remove Sanity"));
            }
        }
    }
}
