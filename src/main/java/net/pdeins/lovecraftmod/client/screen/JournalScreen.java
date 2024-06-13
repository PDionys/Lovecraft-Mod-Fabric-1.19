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
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

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
    private static int nextPosX, nextPosY, prevPosX, prevPosY, texturePosX;
    private boolean prevButton, nextButton;
    //create dictionary of pages like: 0 : "Page 1 text", 1 : "Page 2 text" etc.
    private HashMap<Integer, String> pages = new HashMap<>();
    private int pageNum = 0;
    //create method of construct this dictionary from ListString
    //create functionality of page changes

    public JournalScreen(Text title, int pageNum) {
        super(title);

        pages.put(0, "note.lovecraftmod.firstspawn_p1");
        pages.put(1, "note.lovecraftmod.firstspawn_p2");

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
            nextButton=true;
            prevButton = false;
            //render one clickable texture
            renderNextPageTexture(matrices, texturePosX, texturePosY, mouseX, mouseY);
        }else if(pageNum == pages.size()-1){
            nextButton = false;
            prevButton = true;
//            renderNextPageTexture(matrices, texturePosX, texturePosY, mouseX, mouseY);
            renderPrevPageTexture(matrices, texturePosX, texturePosY, mouseX, mouseY);
        }

        //draw text;
        final MultilineText multilineText = MultilineText.create(textRenderer,
                Text.translatable(pages.get(pageNum)), 115);
        multilineText.draw(matrices, texturePosX+25,texturePosY+20, 10,0xFF000000);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        //check if it in bound of right texture
        if(isWithinNextBounds(mouseX, mouseY) && nextButton){
            //perform page flip
            onTextureClick(1);
            return true;
        }else if(isWithinPrevBounds(mouseX,mouseY) && prevButton){
            onTextureClick(0);
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
    private boolean isWithinNextBounds(double mouseX, double mouseY){
        return mouseX>= nextPosX && mouseX<=(nextPosX +12) && mouseY>= nextPosY && mouseY <= nextPosY +12;
    }
    private boolean isWithinPrevBounds(double mouseX, double mouseY){
        return mouseX>= prevPosX && mouseX<=(prevPosX +12) && mouseY>= prevPosY && mouseY <= prevPosY +12;
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
                new JournalScreen(Text.translatable("screen.lovecraftmod.screen_title"), pageNum)
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
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, JOURNAL_BODY);
        DrawableHelper.drawTexture(matrices, texturePosX, texturePosY, 0f, 0f, 160, 190, 160,190);

    }

    private void renderNextPageTexture(MatrixStack matrices, int pagePosX, int pagePosY, int mouseX, int mouseY){
        //add texture for page flip
        nextPosX = pagePosX + 110;
        nextPosY = pagePosY+165;

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        if(isWithinNextBounds(mouseX, mouseY)) {
            RenderSystem.setShaderTexture(0, PAGE_TURN_NEXT_HOVER);
            DrawableHelper.drawTexture(matrices, nextPosX, nextPosY, 0f, 0f,
                    12, 12, 12, 12);
        }else {
            RenderSystem.setShaderTexture(0, PAGE_TURN_NEXT);
            DrawableHelper.drawTexture(matrices, nextPosX, nextPosY, 0f, 0f,
                    12, 12, 12, 12);
        }
    }
    private void renderPrevPageTexture(MatrixStack matrices, int pagePosX, int pagePosY, int mouseX, int mouseY){
        prevPosX = pagePosX + 90;
        prevPosY = pagePosY + 165;

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        if(isWithinPrevBounds(mouseX, mouseY)){
            RenderSystem.setShaderTexture(0, PAGE_TURN_PREV_HOVER);
            DrawableHelper.drawTexture(matrices, prevPosX, prevPosY, 0f, 0f,
                    12, 12, 12, 12);
        }else {
            RenderSystem.setShaderTexture(0, PAGE_TURN_PREV);
            DrawableHelper.drawTexture(matrices, prevPosX, prevPosY, 0f, 0f,
                    12, 12, 12, 12);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
