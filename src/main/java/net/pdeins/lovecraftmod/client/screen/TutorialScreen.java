package net.pdeins.lovecraftmod.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pdeins.lovecraftmod.LovecraftMod;

public class TutorialScreen extends Screen {
    private static final Identifier JOURNAL_BODY = new Identifier(LovecraftMod.MOD_ID,
            "textures/screen/journal_body.png");

    public TutorialScreen() {
        super(Text.literal("My tutorial screen"));
    }

    public ButtonWidget button;

    @Override
    protected void init() {
//        this.width = 100;
//        this.height = 100;

        button = ButtonWidget.builder(Text.literal("Button"), button1 -> {
            System.out.println("Button clicked!");
            MinecraftClient.getInstance().setScreen(null);
        })
        .dimensions(width/2 - 205, 20, 200, 20)
        .tooltip(Tooltip.of(Text.literal("Tooltip of button")))
        .build();

        addDrawableChild(button);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        super.render(matrices, mouseX, mouseY, delta);

        //drawCenteredTextWithShadow(matrices, this.textRenderer, this.title, 100,100,0xFFFFFF);
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
//        fill(matrices, 0,0,this.width,this.height, 0xAA000000);
        RenderSystem.setShaderTexture(0, JOURNAL_BODY);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        DrawableHelper.drawTexture(matrices, 100,100,0,0,126,126, 126,126);
    }
}
