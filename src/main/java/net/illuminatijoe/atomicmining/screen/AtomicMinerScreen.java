package net.illuminatijoe.atomicmining.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.illuminatijoe.atomicmining.AtomicMining;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AtomicMinerScreen extends AbstractContainerScreen<AtomicMinerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(AtomicMining.MODID, "textures/gui/atomic_miner_gui.png");

    public AtomicMinerScreen(AtomicMinerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);

        int energyScaled = this.menu.getEnergyStoredScaled();

        //background
        guiGraphics.fill(this.leftPos + 156,
                this.topPos + 16 + (61 - energyScaled),
                this.leftPos + 164,
                this.topPos + 77,
                0xAACC2222);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 105, y + 33, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        int energyStored = this.menu.getEnergy();
        int maxEnergyStored = this.menu.getMaxEnergy();

        Component text = Component.literal("Energy: " + energyStored+ " / " + maxEnergyStored);
        if(isHovering(150, 12, 16, 66, mouseX, mouseY)) {
            guiGraphics.renderTooltip(this.font, text, mouseX, mouseY);
        }
    }
}
