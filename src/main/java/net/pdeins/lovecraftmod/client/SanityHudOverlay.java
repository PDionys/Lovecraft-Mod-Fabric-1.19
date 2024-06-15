package net.pdeins.lovecraftmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.pdeins.lovecraftmod.LovecraftMod;
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

    private static final int MAX_SANITY_FILL = 68;


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

        RenderSystem.setShaderTexture(0, SANITY);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        DrawableHelper.drawTexture(matrixStack, x - 92, y - 50, 0f, 0f, 10, 10, 10,10);
        RenderSystem.setShaderTexture(0, EMPTY_SANITY_LEFT);
        DrawableHelper.drawTexture(matrixStack, x - 92 + 1 + 10, y - 50, 0f, 0f, 10, 10, 10,10);
        RenderSystem.setShaderTexture(0, EMPTY_SANITY_CENTER);
        for(int i = 0; i < 5; i++)
            DrawableHelper.drawTexture(matrixStack, x - 92 + 1 + 20 + (i*10), y - 50, 0f, 0f, 10, 10, 10,10);
        RenderSystem.setShaderTexture(0, EMPTY_SANITY_RIGHT);
        DrawableHelper.drawTexture(matrixStack, x - 92 + 1 + 20 + 50, y - 50, 0f, 0f, 10, 10, 10,10);

        RenderSystem.setShaderTexture(0, SANITY_FILL);
        int one_sanity_segment = Math.round(MAX_SANITY_FILL / SanityData.getMaxSanity());
//        System.out.println(one_sanity_segment);
        if(((IEntityDataSaver) client.player).getPersistentData().getInt("sanity") == SanityData.getMaxSanity()){
            DrawableHelper.drawTexture(matrixStack, x - 92 + 1 + 11, y - 50, 0f, 0f, MAX_SANITY_FILL, 10, MAX_SANITY_FILL,10);
        }else{
            int current_sanity = one_sanity_segment * ((IEntityDataSaver) client.player).getPersistentData().getInt("sanity");
            DrawableHelper.drawTexture(matrixStack, x - 92 + 1 + 11, y - 50, 0f, 0f, current_sanity, 10, current_sanity,10);
        }
    }
}
