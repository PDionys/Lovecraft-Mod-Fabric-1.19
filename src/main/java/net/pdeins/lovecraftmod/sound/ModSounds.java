package net.pdeins.lovecraftmod.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.pdeins.lovecraftmod.LovecraftMod;

public class ModSounds {
    public static final SoundEvent OPEN_BOOK_SOUND = registerSound("open_book");
    public static final SoundEvent PAGE_TURN_SOUND = registerSound("page_turn");
    public static final SoundEvent WRITE_NOTE_SOUND = registerSound("write_note");

    private ModSounds(){

    }

    private static SoundEvent registerSound(String id){
        SoundEvent sound = SoundEvent.of(new Identifier(LovecraftMod.MOD_ID, id));
        return Registry.register(Registries.SOUND_EVENT, new Identifier(LovecraftMod.MOD_ID, id), sound);
    }

    public static void initialize(){
        LovecraftMod.LOGGER.info("Registering " + LovecraftMod.MOD_ID + " Sound");
    }
}
