package com.example.mymod;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.init.Blocks;

import java.io.IOException;

public class AutoMinerGui extends GuiScreen {
    private final AutoMiner autoMiner;
    private GuiTextField blockIdField;
    private GuiTextField radiusField;
    private GuiButton startButton;

    public AutoMinerGui(AutoMiner autoMiner) {
        this.autoMiner = autoMiner;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();

        this.blockIdField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 2 - 50, 200, 20);
        this.blockIdField.setText("stone");

        this.radiusField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, this.height / 2, 200, 20);
        this.radiusField.setText("5");

        this.startButton = new GuiButton(2, this.width / 2 - 100, this.height / 2 + 50, "Start Mining");
        this.buttonList.add(this.startButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == this.startButton) {
            Block block = Block.getBlockFromName(this.blockIdField.getText());
            if (block != null) {
                autoMiner.setTargetBlock(block);
            } else {
                autoMiner.setTargetBlock(Blocks.stone);
            }

            try {
                int radius = Integer.parseInt(this.radiusField.getText());
                autoMiner.setMiningRadius(radius);
            } catch (NumberFormatException e) {
                autoMiner.setMiningRadius(5);
            }

            autoMiner.toggleMining();
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.blockIdField.drawTextBox();
        this.radiusField.drawTextBox();
        this.drawString(this.fontRendererObj, "Block ID:", this.width / 2 - 100, this.height / 2 - 65, 0xFFFFFF);
        this.drawString(this.fontRendererObj, "Mining Radius:", this.width / 2 - 100, this.height / 2 - 15, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.blockIdField.mouseClicked(mouseX, mouseY, mouseButton);
        this.radiusField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.blockIdField.textboxKeyTyped(typedChar, keyCode);
        this.radiusField.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}