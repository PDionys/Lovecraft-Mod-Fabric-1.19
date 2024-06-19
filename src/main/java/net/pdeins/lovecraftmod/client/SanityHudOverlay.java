package net.pdeins.lovecraftmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.pdeins.lovecraftmod.LovecraftMod;
import net.pdeins.lovecraftmod.util.DrawModTexture;
import net.pdeins.lovecraftmod.util.IEntityDataSaver;
import net.pdeins.lovecraftmod.util.SanityData;
//TODO clear this class make more readable
public class SanityHudOverlay implements HudRenderCallback {
    private static final Identifier SANITY = new Identifier(LovecraftMod.MOD_ID,
            "textures/sanity/sanity.png");
    private static final Identifier EMPTY_SANITY_RIGHT = new Identifier(LovecraftMod.MOD_ID,
            "textures/sanity/empty_sanity_right.png");
    private static final Identifier EMPTY_SANITY_LEFT = new Identifier(LovecraftMod.MOD_ID,
            "textures/sanity/empty_sanity_left.png");
    private static final Identifier EMPTY_SANITY_CENTER = new Identifier(LovecraftMod.MOD_ID,
            "textures/sanity/empty_sanity_center.png");
    private static final Identifier SANITY_FILL = new Identifier(LovecraftMod.MOD_ID,
            "textures/sanity/sanity_fill.png");

    private static final int MAX_SANITY_FILL = 68, sanityTextureSize = 10;


    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if(client != null){
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width / 2;
            y = height;
        }
        int sanityPosX = x - 92;
        int sanityPosY = y - 50;

        DrawModTexture.drawCustomTexture(matrixStack, SANITY, sanityPosX, sanityPosY, sanityTextureSize, sanityTextureSize);
        DrawModTexture.drawCustomTexture(matrixStack, EMPTY_SANITY_LEFT, sanityPosX + 11, sanityPosY,
                sanityTextureSize, sanityTextureSize);

        for(int i = 0; i < 5; i++)
            DrawModTexture.drawCustomTexture(matrixStack, EMPTY_SANITY_CENTER, sanityPosX + 21 + (i*10), sanityPosY,
                    sanityTextureSize, sanityTextureSize);

        DrawModTexture.drawCustomTexture(matrixStack, EMPTY_SANITY_RIGHT, sanityPosX + 71, sanityPosY,
                sanityTextureSize, sanityTextureSize);

        int one_sanity_segment = Math.round(MAX_SANITY_FILL / SanityData.getMaxSanity());
        if(((IEntityDataSaver) client.player).getPersistentData().getInt("sanity") == SanityData.getMaxSanity()){
            DrawModTexture.drawCustomTexture(matrixStack, SANITY_FILL, sanityPosX + 12, sanityPosY,
                    MAX_SANITY_FILL, sanityTextureSize);
        }else{
            int current_sanity = one_sanity_segment * ((IEntityDataSaver) client.player).getPersistentData().getInt("sanity");
            DrawModTexture.drawCustomTexture(matrixStack, SANITY_FILL, sanityPosX + 12, sanityPosY,
                    current_sanity, sanityTextureSize);
        }
    }
}
