package ru.nopanching.event;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Mod.EventBusSubscriber
public class EventHandler {
    public static boolean commandBreak = true;
    private static BlockPos lastBrokenBlockPos = null;

    @SubscribeEvent
    public static void onBlockHarvest(BlockEvent.BreakEvent event) {
        Block block = event.getState().getBlock();
        String blockName = block.getRegistryName().toString();
        ItemStack heldItemStack = event.getPlayer().getMainHandItem();

        if (isBreak(blockName) && !isAxe(heldItemStack) && commandBreak) {
            BlockPos blockPos = event.getPos();
            if (lastBrokenBlockPos == null || !lastBrokenBlockPos.equals(blockPos)) {
                lastBrokenBlockPos = blockPos;
            }
            event.getPlayer().getCommandSenderWorld().destroyBlock(blockPos, false);

            lastBrokenBlockPos = null;
        } else {
            lastBrokenBlockPos = null;
        }
    }

    @SubscribeEvent
    public static void timeBreakBlock(PlayerEvent.BreakSpeed event) {
        Block block = event.getState().getBlock();
        String blockName = block.getRegistryName().toString();
        ItemStack heldItemStack = event.getPlayer().getMainHandItem();
        if (isBreak(blockName) && !isAxe(heldItemStack) && commandBreak) {
            event.setNewSpeed(0.7F);
        }

    }

    private static boolean isBreak(String blockName) {
        String[] keywords = {"log", "wood", "fence", "planks", "stairs", "oak", "table", "plate",
                "door", "sign", "boat", "chest", "slab", "rail", "framed", "beam", "acacia", "crimson", "spruce", "warped", "jungle", "birch"};

        for (String keyword : keywords) {
            if (blockName.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAxe(ItemStack stack) {
        return stack.getItem() instanceof AxeItem;
    }


}
