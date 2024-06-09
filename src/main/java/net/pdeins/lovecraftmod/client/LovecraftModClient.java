package net.pdeins.lovecraftmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.pdeins.lovecraftmod.event.KeyInputHandler;
import net.pdeins.lovecraftmod.networking.ModPackets;

public class LovecraftModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();

        ModPackets.registerS2CPackets();
    }
}
