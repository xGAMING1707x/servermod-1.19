package net.einzinger.servermod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab SERVER_TAB = new CreativeModeTab("servertab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.EMERALD_BALL.get());
        }
    };
}
