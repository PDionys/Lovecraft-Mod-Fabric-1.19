package net.pdeins.lovecraftmod.event;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.RaycastEntity;
import net.pdeins.lovecraftmod.util.SanityData;

import java.util.Random;

public class PlayerTickHandler implements ServerTickEvents.StartTick{
    private static int tickCount = 0;

    @Override
    public void onStartTick(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            //TODO check how it work in online
            SanityData.syncSanity(((IEntityDataSaver) player).getPersistentData().getInt("sanity"), player);
            // Method for decrease sanity in the dark
            sanytiDecreaseInTheDark(player);
            // Method for decrease sanity when looking on target mob
            sanityDecreaseLookingOnMob(player);
        }
    }

    private static void sanityDecreaseLookingOnMob(ServerPlayerEntity player) {
        //Method which return entity on raycast
        EntityHitResult hitResult = RaycastEntity.raycastEntity(player, 100);
        // Check if return is not null
        if(hitResult != null) {
            // Get entity from hit
            Entity entity = hitResult.getEntity();
            // Check if entity is needed target(s)
            if(entity instanceof CowEntity || entity instanceof PigEntity){
                // Decrease sanity if player watch on target
                player.sendMessage(Text.literal("Its a cow or pig!!!"));
                //TODO make normal condition for decrease
                decreaseSanity(player);
            }else player.sendMessage(Text.literal("Its a NOT cow!!!"));
        }
    }

    private static void sanytiDecreaseInTheDark(ServerPlayerEntity player) {
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
                decreaseSanity(player);
                //reset tick count
                tickCount = 0;
            }
        }else  tickCount = 0; // if day or light count = 0
    }

    private static void decreaseSanity(ServerPlayerEntity player) {
        IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
        SanityData.removeSanity(dataPlayer, 1);
        player.sendMessage(Text.literal("Remove Sanity"));
    }
}
