package net.pdeins.lovecraftmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.pdeins.lovecraftmod.LovecraftMod;

public class WriteAnimHudOverlay implements HudRenderCallback {
    private static final Identifier[] WRITE_NOTE_ANIM = {
            new Identifier(LovecraftMod.MOD_ID, "textures/progress/write_note_0.png"),
            new Identifier(LovecraftMod.MOD_ID, "textures/progress/write_note_1.png")};
    private static long displayStartTime = 0;
    private static int FRAME;
    private final int textureSize = 32;
    private final long displayDuration = 1200;

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        if (displayStartTime > 0 && (System.currentTimeMillis() - displayStartTime) < displayDuration){
            MinecraftClient client = MinecraftClient.getInstance();
            if(client != null && client.player != null){
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();
                int x = width - (textureSize + 10);
                int y = height - (textureSize + 10);

                //TODO FIND SOME WAY TO MAKE ANIMATION MORE SMOOTH
                if(System.currentTimeMillis() - displayStartTime < displayDuration/3
                        || System.currentTimeMillis() - displayStartTime >= (displayDuration/3)*2)
                    FRAME = 0;
                else FRAME = 1;
                renderFrame(matrixStack, x, y, WRITE_NOTE_ANIM[FRAME]);
            }
        }
    }

    private void renderFrame(MatrixStack matrixStack, int x, int y, Identifier frame) {
        RenderSystem.setShaderTexture(0, frame);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        DrawableHelper.drawTexture(matrixStack, x, y, 0f, 0f, textureSize, textureSize, textureSize,textureSize);
    }

    public static void showAnimation(){
        displayStartTime = System.currentTimeMillis();
    }
}
