package com.example.mymod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod(modid = MyMod.MODID, version = MyMod.VERSION)
public class MyMod {
    public static final String MODID = "mymod";
    public static final String VERSION = "1.0";

    private AutoMiner autoMiner;
    private AutoMinerGui autoMinerGui;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        autoMiner = new AutoMiner();
        autoMinerGui = new AutoMinerGui(autoMiner);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(autoMiner);
        MinecraftForge.EVENT_BUS.register(new ModIDHider());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Initialization code
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_M) {
            Minecraft.getMinecraft().displayGuiScreen(autoMinerGui);
        }
    }

    @SubscribeEvent
    public void onMouseInput(MouseEvent event) {
        if (event.button == 0 && event.buttonstate) {
            autoMiner.toggleMining();
        }
    }
}