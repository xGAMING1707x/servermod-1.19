package net.einzinger.servermod.block;

import net.einzinger.servermod.ServerMod;
import net.einzinger.servermod.block.custom.*;
import net.einzinger.servermod.item.ModCreativeModeTab;
import net.einzinger.servermod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ServerMod.MOD_ID);

    public static final RegistryObject<Block> ZINC_BLOCK = registerBlock("zinc_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Item> ZINC_BLOCK_ITEM = registerBlockItem("zinc_block", ZINC_BLOCK, ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> ZINC_ORE = registerBlock("zinc_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)));
    public static final RegistryObject<Item> ZINC_ORE_ITEM = registerBlockItem("zinc_ore", ZINC_ORE, ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> DEEPSLATE_ZINC_ORE = registerBlock("deepslate_zinc_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)));
    public static final RegistryObject<Item> DEEPSLATE_ZINC_ORE_ITEM = registerBlockItem("deepslate_zinc_ore", DEEPSLATE_ZINC_ORE, ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> NETHERRACK_ZINC_ORE = registerBlock("netherrack_zinc_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)));
    public static final RegistryObject<Item> NETHERRACK_ZINC_ORE_ITEM = registerBlockItem("netherrack_zinc_ore", NETHERRACK_ZINC_ORE, ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> END_ZINC_ORE = registerBlock("end_zinc_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)));
    public static final RegistryObject<Item> END_ZINC_ORE_ITEM = registerBlockItem("end_zinc_ore", END_ZINC_ORE, ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> ZINC_LAMP = registerBlock("zinc_lamp",
            () -> new ZincLampBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(2f).requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(ZincLampBlock.LIT) ? 15 : 0)));
    public static final RegistryObject<Item> ZINC_LAMP_ITEM = registerBlockItem("zinc_lamp", ZINC_LAMP, ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> BLUEBERRY_CROP = BLOCKS.register("blueberry_crop",
            () -> new BlueBerryCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    public static final RegistryObject<Block> ZINC_STATION = registerBlock("zinc_station",
            () -> new ZincStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()));
    public static final RegistryObject<Item> ZINC_STATION_ITEM = registerBlockItem("zinc_station", ZINC_STATION, ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> CUP = registerBlock("empty_cup",
            () -> new CupBlock(BlockBehaviour.Properties.of(Material.WOOD)
                    .destroyTime(20f)));
    public static final RegistryObject<Item> CUP_ITEM = registerBlockItem("empty_cup", CUP, ModCreativeModeTab.SERVER_TAB, 16);

    public static final RegistryObject<Block> COFFEE_MACHINE = registerBlock("coffee_machine",
            () -> new CoffeeMachineBlock(BlockBehaviour.Properties.copy(Blocks.GRAY_CONCRETE)
                    .noOcclusion()));
    public static final RegistryObject<Item> COFFEE_MACHINE_ITEM = registerBlockItem("coffee_machine", COFFEE_MACHINE, ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> COFFEE_CROP = BLOCKS.register("coffe_crop",
            () -> new CoffeeCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));



    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block/*, CreativeModeTab tab*/){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        //registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){

        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab, int stacksTo){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab).stacksTo(stacksTo)));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
