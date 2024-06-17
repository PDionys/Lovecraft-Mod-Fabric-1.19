package net.pdeins.lovecraftmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.pdeins.lovecraftmod.client.WriteAnimHudOverlay;
import net.pdeins.lovecraftmod.sound.ModSounds;

public class FirstLoadWorldHandler implements ServerEntityEvents.Load{
    private static final int ONE_AND_HALF_SECOND = 1200;

    @Override
    public void onLoad(Entity entity, ServerWorld world) {
        if(entity.isPlayer()){
            try{
                Thread.sleep(ONE_AND_HALF_SECOND);
                //play sound
                playWriteSound((ServerPlayerEntity) entity);
                //play animation
                WriteAnimHudOverlay.showHud();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void playWriteSound(ServerPlayerEntity entity) {
        entity.playSound(
                ModSounds.WRITE_NOTE_SOUND,
                SoundCategory.PLAYERS,
                1.0f,
                1.0f
        );
    }
}
