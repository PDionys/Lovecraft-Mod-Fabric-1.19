package net.pdeins.lovecraftmod.event;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.SanityData;

import java.util.Random;

public class PlayerTickHandler implements ServerTickEvents.StartTick{
    private static int tickCount = 0;

    @Override
    public void onStartTick(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            //TODO check how it work in online
            SanityData.syncSanity(((IEntityDataSaver) player).getPersistentData().getInt("sanity"), player);
            //Set player position
            BlockPos playerPos = player.getBlockPos();
            //Set time and light level
            int lightLevel = player.world.getLightLevel(LightType.SKY, playerPos)
                    + player.world.getLightLevel(LightType.BLOCK, playerPos);
            long time = player.world.getTimeOfDay() % 24000;
            if(lightLevel <= 7 || (lightLevel <= 15 && (time >= 13000 && time <= 23000))){
                // condition when player in dark or night and dark
                //grow tick count
                tickCount++;
                //check if went 1 minute
                if(tickCount >= 1200){// 1200 ticks = 1 minute
                    // decrease sanity level by amount
                    IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
                    SanityData.removeSanity(dataPlayer, 1);
                    player.sendMessage(Text.literal("Remove Sanity"));
                    //reset tick count
                    tickCount = 0;
                }
            }else  tickCount = 0; // if day or light count = 0
//            if(new Random().nextFloat() <= 0.005f){
//
//            }
        }
    }
}
