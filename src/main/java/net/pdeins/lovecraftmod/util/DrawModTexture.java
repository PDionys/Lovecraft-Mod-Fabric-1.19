package net.pdeins.lovecraftmod.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DrawModTexture {
    public static void drawCustomTexture(MatrixStack matrices, Identifier identifier, int texturePosX, int texturePosY, int textureWidth, int textureHeight) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, identifier);
        DrawableHelper.drawTexture(matrices, texturePosX, texturePosY, 0f, 0f, textureWidth, textureHeight, textureWidth,textureHeight);
    }
}
