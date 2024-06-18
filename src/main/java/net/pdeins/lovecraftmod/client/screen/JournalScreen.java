package net.pdeins.lovecraftmod.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pdeins.lovecraftmod.LovecraftMod;
import net.pdeins.lovecraftmod.networking.ModPackets;
import net.pdeins.lovecraftmod.util.DrawModTexture;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
//TODO clear this class make more readable
public class JournalScreen extends Screen {
    //textures
    private static final Identifier JOURNAL_BODY = new Identifier(LovecraftMod.MOD_ID,
            "textures/screen/journal_body.png");
    private static final Identifier PAGE_TURN_NEXT = new Identifier(LovecraftMod.MOD_ID,
            "textures/screen/page_turn_next.png");
    private static final Identifier PAGE_TURN_NEXT_HOVER = new Identifier(LovecraftMod.MOD_ID,
            "textures/screen/page_turn_next_hover.png");
    private static final Identifier PAGE_TURN_PREV = new Identifier(LovecraftMod.MOD_ID,
            "textures/screen/page_turn_prev.png");
    private static final Identifier PAGE_TURN_PREV_HOVER = new Identifier(LovecraftMod.MOD_ID,
            "textures/screen/page_turn_prev_hover.png");
    //textures parameters
    private static final int texturePosY = 0;
    private static int nextPosX, nextPosY, prevPosX, prevPosY, texturePosX, pageType;
    private static final int journalTextureWidth = 160, journalTextureHeight = 190,
    nextPageTextureWidth = 12, nextPageTextureHeight = 12;
    //create dictionary of pages like: 0 : "Page 1 text", 1 : "Page 2 text" etc.
    private final HashMap<Integer, String> pages;
    private int pageNum;

    public JournalScreen(Text title, int pageNum, HashMap<Integer, String> pages) {
        super(title);

        this.pages = pages;
        this.pageNum = pageNum;
    }

    @Override
    protected void init() {
        super.init();

        texturePosX = this.width/2 - 80;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        //draw texture and background
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        //render button for page turn
        if(pageNum == 0){
            pageType = 1;
            renderNextPageTexture(matrices, texturePosX, texturePosY, mouseX, mouseY);
        }else if(pageNum == pages.size()-1){
            pageType = -1;
            renderPrevPageTexture(matrices, texturePosX, texturePosY, mouseX, mouseY);
        }else{
            pageType = 0;
            renderNextPageTexture(matrices, texturePosX, texturePosY, mouseX, mouseY);
            renderPrevPageTexture(matrices, texturePosX, texturePosY, mouseX, mouseY);
        }

        //draw text;
        final MultilineText multilineText = MultilineText.create(textRenderer,
                Text.of(pages.get(pageNum)), 115);
        multilineText.draw(matrices, texturePosX+25,texturePosY+20, 10,0xFF000000);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        //check if it in bound of right texture
        if(isWithinBounds(mouseX, mouseY, nextPosX, nextPosY) && (pageType == 1 || pageType == 0)){
            //perform page flip
            onTextureClick(1);
            return true;
        }else if(isWithinBounds(mouseX, mouseY, prevPosX, prevPosY) && (pageType == -1 || pageType == 0)){
            onTextureClick(0);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isWithinBounds(double mouseX, double mouseY, int buttonPosX, int buttonPosY){
        return mouseX>= buttonPosX && mouseX<=(buttonPosX +12) && mouseY>= buttonPosY && mouseY <= buttonPosY +12;
    }

    private void onTextureClick(int direction){
        //save cursor position
        double mouseX = client.mouse.getX();
        double mouseY = client.mouse.getY();
        //render new page
        MinecraftClient.getInstance().setScreen(null);
        switch (direction){
            case 0:
                pageNum--;
                break;
            case 1:
                pageNum++;
                break;
            default:
                break;
        }
        MinecraftClient.getInstance().setScreen(
                new JournalScreen(Text.translatable("screen.lovecraftmod.screen_title"), pageNum, this.pages)
        );
        //set old cursor position
        GLFW.glfwSetCursorPos(client.getWindow().getHandle(), mouseX, mouseY);
        //play sound of page turn
        ClientPlayNetworking.send(ModPackets.PLAY_SOUND_PAGE_TURN_ID, PacketByteBufs.create());
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        //fill screen with slightly visible black color
        fill(matrices, 0,0, this.width, this.height, 0xAA000000);
        //add texture of journal
        DrawModTexture.drawCustomTexture(matrices, JOURNAL_BODY, texturePosX, texturePosY, journalTextureWidth, journalTextureHeight);
    }

    private void renderNextPageTexture(MatrixStack matrices, int pagePosX, int pagePosY, int mouseX, int mouseY){
        //add texture for page flip
        nextPosX = pagePosX + 110;
        nextPosY = pagePosY + 165;

        if(isWithinBounds(mouseX, mouseY, nextPosX, nextPosY)) {
            DrawModTexture.drawCustomTexture(matrices, PAGE_TURN_NEXT_HOVER, nextPosX, nextPosY,
                    nextPageTextureWidth, nextPageTextureHeight);
        }else {
            DrawModTexture.drawCustomTexture(matrices, PAGE_TURN_NEXT, nextPosX, nextPosY,
                    nextPageTextureWidth, nextPageTextureHeight);
        }
    }
    private void renderPrevPageTexture(MatrixStack matrices, int pagePosX, int pagePosY, int mouseX, int mouseY){
        prevPosX = pagePosX + 90;
        prevPosY = pagePosY + 165;

        if(isWithinBounds(mouseX, mouseY, prevPosX, prevPosY)){
            DrawModTexture.drawCustomTexture(matrices, PAGE_TURN_PREV_HOVER, prevPosX, prevPosY,
                    nextPageTextureWidth, nextPageTextureHeight);
        }else {
            DrawModTexture.drawCustomTexture(matrices, PAGE_TURN_PREV, prevPosX, prevPosY,
                    nextPageTextureWidth, nextPageTextureHeight);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}