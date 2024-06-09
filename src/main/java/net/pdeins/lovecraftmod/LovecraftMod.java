package net.pdeins.lovecraftmod;

import net.fabricmc.api.ModInitializer;
import net.pdeins.lovecraftmod.item.ModItemGroup;
import net.pdeins.lovecraftmod.item.ModItems;
import net.pdeins.lovecraftmod.networking.ModPackets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LovecraftMod implements ModInitializer {
    public static final String MOD_ID = "lovecraftmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItemGroup.registerModItemGroup();

        ModItems.registerModItem();

        ModPackets.registerC2SPackets();
    }
}
