package net.pdeins.lovecraftmod.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.pdeins.lovecraftmod.client.WriteAnimHudOverlay;
import net.pdeins.lovecraftmod.sound.ModSounds;

public class ProgressRecord {
    public static void changeProgressAndJournal(ServerPlayerEntity player, MinecraftServer server, String progress){
        //Set progress
        ProgressionData.setProgression(((IEntityDataSaver) player), progress);
        //Write journal
        JournalData.setJournalList(((IEntityDataSaver) player), progress, server);
    }

    public static void playSoundAndAnimation(ServerPlayerEntity player){
        //play sound
        playWriteSound(player);
        //play animation
        WriteAnimHudOverlay.showAnimation();
    }

    private static void playWriteSound(ServerPlayerEntity player) {
        player.playSound(
                ModSounds.WRITE_NOTE_SOUND,
                SoundCategory.PLAYERS,
                1.0f,
                1.0f
        );
    }
}
