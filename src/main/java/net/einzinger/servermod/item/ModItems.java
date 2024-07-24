package net.einzinger.servermod.item;

import net.einzinger.servermod.ServerMod;
import net.einzinger.servermod.block.ModBlocks;
import net.einzinger.servermod.item.custom.DiceItem;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ServerMod.MOD_ID);

    public static final RegistryObject<Item> EMERALD_BALL = ITEMS.register("emerald_ball",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SERVER_TAB)));

    public static final RegistryObject<Item> BLUEBERRY = ITEMS.register("blueberry",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SERVER_TAB).food(ModFoods.BLUEBERRY)));

    public static final RegistryObject<Item> BLUEBERRY_SEEDS = ITEMS.register("blueberry_seeds",
            () -> new ItemNameBlockItem(ModBlocks.BLUEBERRY_CROP.get(),
                    new Item.Properties().tab(ModCreativeModeTab.SERVER_TAB)));

    public static final RegistryObject<Item> ZINC_INGOT = ITEMS.register("zinc_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SERVER_TAB)));

    public static final RegistryObject<Item> RAW_ZINC = ITEMS.register("raw_zinc",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SERVER_TAB)));

    public static final RegistryObject<Item> DICE = ITEMS.register("dice",
            () -> new DiceItem(new Item.Properties().tab(ModCreativeModeTab.SERVER_TAB).stacksTo(1)));

    public static final RegistryObject<Item> ZINC_CUTTER = ITEMS.register("zinc_cutter",
            () -> new SwordItem(Tiers.WOOD, 0, 1,
                    new Item.Properties().stacksTo(1).durability(20)
                            .tab(ModCreativeModeTab.SERVER_TAB)));

    public static final RegistryObject<Item> ZINC_HAMMER = ITEMS.register("zinc_hammer",
            () -> new PickaxeItem(Tiers.STONE, 0, 1,
                    new Item.Properties().stacksTo(1).durability(20)
                            .tab(ModCreativeModeTab.SERVER_TAB)));

    public static final RegistryObject<Item> ZINC_PLATE = ITEMS.register("zinc_plate",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SERVER_TAB)));

    public static final RegistryObject<Item> ZINC_DUST = ITEMS.register("zinc_dust",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.SERVER_TAB)));

    public static final RegistryObject<Item> COFFEE_SEEDS = ITEMS.register("coffee_seeds",
            () -> new ItemNameBlockItem(ModBlocks.COFFEE_CROP.get(),
                    new Item.Properties().tab(ModCreativeModeTab.SERVER_TAB)));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
