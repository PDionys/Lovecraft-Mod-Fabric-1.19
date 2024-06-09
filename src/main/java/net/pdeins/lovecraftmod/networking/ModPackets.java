package net.pdeins.lovecraftmod.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.pdeins.lovecraftmod.LovecraftMod;
import net.pdeins.lovecraftmod.networking.packet.ExampleC2SPacket;

public class ModPackets {
    public static final Identifier PLUS_SANITY_ID = new Identifier(LovecraftMod.MOD_ID, "plus_sanity");
    public static final Identifier SANITY_SYNC_ID = new Identifier(LovecraftMod.MOD_ID, "sanity_sync");
    public static final Identifier EXAMPLE_ID = new Identifier(LovecraftMod.MOD_ID, "example");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(EXAMPLE_ID, ExampleC2SPacket::receive);
    }

    public static void registerS2CPackets(){

    }

}
