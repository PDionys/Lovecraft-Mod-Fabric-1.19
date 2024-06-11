package net.pdeins.lovecraftmod.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pdeins.lovecraftmod.LovecraftMod;

public class JournalScreen extends Screen {
    private static final Identifier JOURNAL_BODY = new Identifier(LovecraftMod.MOD_ID,
            "textures/screen/journal_body.png");
    private static int texturePosX = 0;
    private static final int texturePosY = 0;

    public JournalScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();

        texturePosX = this.width/2 - 75;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        //draw texture and background
        renderBackground(matrices);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        //fill screen with slightly visible black color
        fill(matrices, 0,0, this.width, this.height, 0xAA000000);

        //add texture of journal
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, JOURNAL_BODY);
        DrawableHelper.drawTexture(matrices, texturePosX, texturePosY, 0f, 0f, 150, 180, 150,180);

    }
}
