package com.example.mymod;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoMiner {
    private final Minecraft mc = Minecraft.getMinecraft();
    private Block targetBlock;
    private int miningRadius = 5;
    private boolean isMining = false;
    private long lastMineTime = 0;
    private static final long MINE_DELAY = 250; // 250ms between each mine action

    public void setTargetBlock(Block block) {
        this.targetBlock = block;
    }

    public void setMiningRadius(int radius) {
        this.miningRadius = radius;
    }

    public void toggleMining() {
        isMining = !isMining;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START && isMining && targetBlock != null && mc.thePlayer != null && mc.theWorld != null) {
            BlockPos playerPos = mc.thePlayer.getPosition();
            for (int x = -miningRadius; x <= miningRadius; x++) {
                for (int y = -miningRadius; y <= miningRadius; y++) {
                    for (int z = -miningRadius; z <= miningRadius; z++) {
                        BlockPos pos = playerPos.add(x, y, z);
                        if (mc.theWorld.getBlockState(pos).getBlock() == targetBlock) {
                            mineBlock(pos);
                            return;
                        }
                    }
                }
            }
        }
    }

    private void mineBlock(BlockPos pos) {
        if (System.currentTimeMillis() - lastMineTime < MINE_DELAY) {
            return;
        }

        Vec3 blockVec = new Vec3(pos).addVector(0.5, 0.5, 0.5);
        Vec3 playerVec = mc.thePlayer.getPositionVector().addVector(0, mc.thePlayer.getEyeHeight(), 0);
        Vec3 lookVec = blockVec.subtract(playerVec).normalize();

        float yaw = (float) Math.toDegrees(Math.atan2(-lookVec.xCoord, lookVec.zCoord));
        float pitch = (float) Math.toDegrees(Math.asin(-lookVec.yCoord));

        // Smooth rotation
        float deltaYaw = yaw - mc.thePlayer.rotationYaw;
        float deltaPitch = pitch - mc.thePlayer.rotationPitch;

        mc.thePlayer.rotationYaw += deltaYaw * 0.15f;
        mc.thePlayer.rotationPitch += deltaPitch * 0.15f;

        // Simulate mining action
        if (Math.abs(deltaYaw) < 5 && Math.abs(deltaPitch) < 5) {
            mc.playerController.clickBlock(pos, mc.objectMouseOver.sideHit);
            mc.thePlayer.swingItem();
            lastMineTime = System.currentTimeMillis();
        }
    }
}