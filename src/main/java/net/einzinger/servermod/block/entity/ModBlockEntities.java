package net.einzinger.servermod.block.entity;

import net.einzinger.servermod.ServerMod;
import net.einzinger.servermod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ServerMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<ZincStationBlockEntity>> ZINC_STATION =
            BLOCK_ENTITIES.register("zinc_station", () ->
                    BlockEntityType.Builder.of(ZincStationBlockEntity::new,
                            ModBlocks.ZINC_STATION.get()).build(null));


    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
