package net.einzinger.servermod.block;

import net.einzinger.servermod.ServerMod;
import net.einzinger.servermod.block.custom.BlueBerryCropBlock;
import net.einzinger.servermod.block.custom.ZincLampBlock;
import net.einzinger.servermod.block.custom.ZincStationBlock;
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
                    .strength(3f).requiresCorrectToolForDrops()),
                    ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> ZINC_ORE = registerBlock("zinc_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)),
                    ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> DEEPSLATE_ZINC_ORE = registerBlock("deepslate_zinc_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)),
            ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> NETHERRACK_ZINC_ORE = registerBlock("netherrack_zinc_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)),
            ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> END_ZINC_ORE = registerBlock("end_zinc_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f).requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)),
            ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> ZINC_LAMP = registerBlock("zinc_lamp",
            () -> new ZincLampBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(2f).requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(ZincLampBlock.LIT) ? 15 : 0)),
            ModCreativeModeTab.SERVER_TAB);

    public static final RegistryObject<Block> BLUEBERRY_CROP = BLOCKS.register("blueberry_crop",
            () -> new BlueBerryCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    public static final RegistryObject<Block> ZINC_STATION = registerBlock("zinc_station",
            () -> new ZincStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()),
            ModCreativeModeTab.SERVER_TAB);




    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){

        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
