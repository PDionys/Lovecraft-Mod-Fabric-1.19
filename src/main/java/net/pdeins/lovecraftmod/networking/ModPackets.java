package net.pdeins.lovecraftmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.pdeins.lovecraftmod.LovecraftMod;
import net.pdeins.lovecraftmod.networking.packet.*;

public class ModPackets {
    public static final Identifier PLUS_SANITY_ID = new Identifier(LovecraftMod.MOD_ID, "plus_sanity");
    public static final Identifier ADD_JOURNAL_ID = new Identifier(LovecraftMod.MOD_ID, "add_journal");
    public static final Identifier SANITY_SYNC_ID = new Identifier(LovecraftMod.MOD_ID, "sanity_sync");
    public static final Identifier JOURNAL_SYNC_ID = new Identifier(LovecraftMod.MOD_ID, "journal_sync");
    public static final Identifier EXAMPLE_ID = new Identifier(LovecraftMod.MOD_ID, "example");
    public static final Identifier PLAY_SOUND_BOOK_OPEN_ID = new Identifier(LovecraftMod.MOD_ID, "play_sound_book_open");
    public static final Identifier PLAY_SOUND_PAGE_TURN_ID = new Identifier(LovecraftMod.MOD_ID, "play_sound_page_turn");

    public static void registerC2SPackets(){
        //ServerPlayNetworking.registerGlobalReceiver(EXAMPLE_ID, ExampleC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PLUS_SANITY_ID, SanityC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PLAY_SOUND_BOOK_OPEN_ID, PlayOpenBookSoundC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PLAY_SOUND_PAGE_TURN_ID, PlayPageTurnSoundC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ADD_JOURNAL_ID, JournalC2SPacket::receive);
    }

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(SANITY_SYNC_ID, SanitySyncDataS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(JOURNAL_SYNC_ID, JournalSyncS2CPacket::receive);
    }

}
