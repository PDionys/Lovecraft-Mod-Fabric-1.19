package net.pdeins.lovecraftmod.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class WriteAnimHudOverlay implements HudRenderCallback {
    private static long displayStartTime = 0;
    private final long displayDuration = 1200;

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        if (displayStartTime > 0 && (System.currentTimeMillis() - displayStartTime) < displayDuration){
            MinecraftClient client = MinecraftClient.getInstance();
            if(client != null && client.player != null){
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();
                String message = "Hello, this is a HUD message!";
                int x = width / 2 - client.textRenderer.getWidth(message) / 2;
                int y = height / 2;
                client.textRenderer.drawWithShadow(matrixStack, message, x, y, 0xFFFFFF);
            }
        }
    }

    public static void showHud(){
        displayStartTime = System.currentTimeMillis();
    }
}
