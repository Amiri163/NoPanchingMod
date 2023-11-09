package ru.nopanching.event;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler {
    public static boolean commandBreak = true;
    public static int tick = 0;
    private static BlockPos lastBrokenBlockPos = null;

    @SubscribeEvent
    public static void onBlockHarvest(PlayerEvent.BreakSpeed event) {
        Block block = event.getState().getBlock();
        String blockName = block.getRegistryName().toString();
        ItemStack heldItemStack = event.getPlayer().getMainHandItem();

            if (blockName.equals("minecraft:oak_log") && !isAxe(heldItemStack) && commandBreak) {
                BlockPos blockPos = event.getPos();
                IWorld world = event.getEntity().getCommandSenderWorld();
            if (lastBrokenBlockPos == null || !lastBrokenBlockPos.equals(blockPos)) {
                tick = 0;
                lastBrokenBlockPos = blockPos;
            }
            tick++;
            event.setNewSpeed(0.7F);
            if (tick == 170) {
                world.destroyBlock(blockPos, false);
                tick = 0;
                lastBrokenBlockPos = null;
            }
        } else {
            tick = 0;
            lastBrokenBlockPos = null;
        }
    }

    private static boolean isAxe(ItemStack stack) {
        return stack.getItem() instanceof AxeItem;
    }


}
