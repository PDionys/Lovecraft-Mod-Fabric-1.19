package net.pdeins.lovecraftmod.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;


import net.minecraft.text.Text;
import net.pdeins.lovecraftmod.client.screen.JournalScreen;
import net.pdeins.lovecraftmod.networking.ModPackets;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.JournalData;
import net.pdeins.lovecraftmod.util.SanityData;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_TEST = "key.category.lovecraftmod.test";
    public static final String KEY_TEST_LOGIC = "key.lovecraftmod.test_logic";
    public static final String KEY_OPEN_JOURNAL = "key.lovecraftmod.open_journal";

    public static KeyBinding testingKey;
    public static KeyBinding journalKey;

    public static void registerKeyInput(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(testingKey.wasPressed()){
                //Something is doing when the key is pressed

                ClientPlayNetworking.send(ModPackets.PLUS_SANITY_ID, PacketByteBufs.create());
//                ClientPlayNetworking.send(ModPackets.PLAY_SOUND_PAGE_TURN_ID, PacketByteBufs.create());
                ClientPlayNetworking.send(ModPackets.ADD_JOURNAL_ID, PacketByteBufs.create());
            }

            if(journalKey.wasPressed()){
                //Get Journal List
                HashMap<Integer, String> pages = JournalData.getJournalList(((IEntityDataSaver) client.player));
                //Journal key LOGIC
                MinecraftClient.getInstance().setScreen(
                        new JournalScreen(Text.translatable("screen.lovecraftmod.screen_title"), 0, pages)
                );
                ClientPlayNetworking.send(ModPackets.PLAY_SOUND_BOOK_OPEN_ID, PacketByteBufs.create());
            }
        });
    }

    public static void register(){
        testingKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
           KEY_TEST_LOGIC,
           InputUtil.Type.KEYSYM,
           GLFW.GLFW_KEY_O,
           KEY_CATEGORY_TEST
        ));

        journalKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_JOURNAL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                //TODO change category
                KEY_CATEGORY_TEST
        ));

        registerKeyInput();
    }

}
