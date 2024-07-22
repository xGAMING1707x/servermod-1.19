package net.einzinger.servermod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DiceItem extends Item {
    public DiceItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
            outputRandomNumber(player);
            player.getCooldowns().addCooldown(this, 20);
        }

        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            components.add(Component.translatable("tooltip_dice").withStyle(ChatFormatting.AQUA));
        } else {
            components.add(Component.translatable("tooltip_shift").withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(stack, level, components, flag);
    }

    private int getRandomNumber(){
        return ThreadLocalRandom.current().nextInt(0, 11);
    }

    private void outputRandomNumber(Player player){
        Component chatMessage = Component.translatable("dice_chatMessage");
        String number = String.valueOf(getRandomNumber());

        Component message = Component.literal("")
                .append(chatMessage)
                .append(number);
        player.sendSystemMessage(message);
    }
}
