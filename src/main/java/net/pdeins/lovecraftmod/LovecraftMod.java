package net.pdeins.lovecraftmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.pdeins.lovecraftmod.event.OnDeathSaveHandler;
import net.pdeins.lovecraftmod.event.PlayerTickHandler;
import net.pdeins.lovecraftmod.event.PlayerWorldJoinHandler;
import net.pdeins.lovecraftmod.item.ModItemGroup;
import net.pdeins.lovecraftmod.item.ModItems;
import net.pdeins.lovecraftmod.networking.ModPackets;
import net.pdeins.lovecraftmod.sound.ModSounds;
import net.pdeins.lovecraftmod.util.JournalData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LovecraftMod implements ModInitializer {
    public static final String MOD_ID = "lovecraftmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        //init mod group
        ModItemGroup.registerModItemGroup();
        //init all mod items
        ModItems.registerModItem();
        //init all packets client to server
        ModPackets.registerC2SPackets();
        //init all sounds
        ModSounds.initialize();
        //after death copy player parameters
        ServerPlayerEvents.COPY_FROM.register(new OnDeathSaveHandler());
        //Player is joining to the world
        ServerPlayConnectionEvents.JOIN.register(new PlayerWorldJoinHandler());
        //every tic check player
        ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
    }
}
