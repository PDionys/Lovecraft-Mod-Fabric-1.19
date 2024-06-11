package net.pdeins.lovecraftmod.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.pdeins.lovecraftmod.LovecraftMod;

public class ModSounds {
//    public static final Identifier OPEN_BOOK_SOUND_ID = new Identifier(LovecraftMod.MOD_ID, "open_book");
//    public static SoundEvent OPEN_BOOK_SOUND_EVENT = SoundEvent.of(OPEN_BOOK_SOUND_ID);
    public static final SoundEvent OPEN_BOOK_SOUND = registerSound("open_book");

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
