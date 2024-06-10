package net.pdeins.lovecraftmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.pdeins.lovecraftmod.client.SanityHudOverlay;
import net.pdeins.lovecraftmod.event.KeyInputHandler;
import net.pdeins.lovecraftmod.networking.ModPackets;

public class LovecraftModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();

        ModPackets.registerS2CPackets();

        HudRenderCallback.EVENT.register(new SanityHudOverlay());
    }
}
