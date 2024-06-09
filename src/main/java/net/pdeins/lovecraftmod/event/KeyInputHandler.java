package net.pdeins.lovecraftmod.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_TEST = "key.category.lovecraftmod.test";
    public static final String KEY_TEST_LOGIC = "key.lovecraftmod.test_logic";

    public static KeyBinding testingKey;

    public static void registerKeyInput(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(testingKey.isPressed()){
                //Something is doing when the key is pressed
                client.player.sendMessage(Text.literal("Hello minecraft World" ));
                //count++;
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

        registerKeyInput();
    }

}
