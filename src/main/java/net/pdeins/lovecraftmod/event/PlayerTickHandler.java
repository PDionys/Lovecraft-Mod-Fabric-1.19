package net.pdeins.lovecraftmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.JournalData;
import net.pdeins.lovecraftmod.util.RaycastEntity;
import net.pdeins.lovecraftmod.util.SanityData;

public class PlayerTickHandler implements ServerTickEvents.StartTick{
    private static int darkTickCount, mobTickCount = 0;

    @Override
    public void onStartTick(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            //TODO check how it work in online
            //Sync server data with client
            SanityData.syncSanity(((IEntityDataSaver) player).getPersistentData().getInt("sanity"), player);
            JournalData.syncJournal(player, ((IEntityDataSaver) player).getPersistentData());
            // Method for decrease sanity in the dark
            sanityDecreaseInTheDark(player);
            // Method for decrease sanity when looking on target mob
            sanityDecreaseLookingOnMob(player);
        }
    }

    private static void sanityDecreaseLookingOnMob(ServerPlayerEntity player) {
        //Method which return entity on raycast
        EntityHitResult hitResult = RaycastEntity.raycastEntity(player, 250); // in this case 250 = 16 blocks away
        // Check if return is not null
        if(hitResult != null) {
            // Get entity from hit
            Entity entity = hitResult.getEntity();
            // Check if entity is needed target(s)
            boolean fearTarget = entity instanceof ZombieEntity
                    || entity instanceof SkeletonEntity
                    || entity instanceof SpiderEntity
                    || entity instanceof CreeperEntity
                    || entity instanceof EndermanEntity;
            if(fearTarget){// Decrease sanity if player watch on target
                //grow tick count
                mobTickCount++;
                //TODO make normal condition for decrease
                if (mobTickCount >= 20){ // after 1s looking at mob
                    // decrease sanity level by amount
                    decreaseSanity(player);
                    mobTickCount = 0;
                }
            }else mobTickCount = 0; // if its a mob but not fearful reset ticks
        }else mobTickCount = 0;// if mob hasnt been detected tick reset
    }

    private static void sanityDecreaseInTheDark(ServerPlayerEntity player) {
        //Set player position
        BlockPos playerPos = player.getBlockPos();
        //Set time and light level
        int lightLevel = player.world.getLightLevel(LightType.SKY, playerPos)
                + player.world.getLightLevel(LightType.BLOCK, playerPos);
        long time = player.world.getTimeOfDay() % 24000;
        if(lightLevel <= 7 || (lightLevel <= 15 && (time >= 13000 && time <= 23000))){
            // condition when player in dark or night and dark
            //grow tick count
            darkTickCount++;
            //check if went 1 minute
            if(darkTickCount >= 160){//darkTickCount >= 1200 - 1200 ticks = 1 minute (20 ticks - 1s)
                // decrease sanity level by amount
                decreaseSanity(player);
                //reset tick count
                darkTickCount = 0;
            }
        }else  darkTickCount = 0; // if day or light count = 0
    }

    private static void decreaseSanity(ServerPlayerEntity player) {
        IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
        SanityData.removeSanity(dataPlayer, 1);
        player.sendMessage(Text.literal("Remove Sanity"));
    }
}
