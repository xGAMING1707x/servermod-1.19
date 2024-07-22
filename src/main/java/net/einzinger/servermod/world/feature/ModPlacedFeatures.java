package net.einzinger.servermod.world.feature;

import net.einzinger.servermod.ServerMod;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ServerMod.MOD_ID);

    public static final RegistryObject<PlacedFeature> ZINC_ORE_PLACED = PLACED_FEATURES.register("zinc_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.ZINC_ORE.getHolder().get(),
                    commonOrePlacement(20, //VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> NETHER_UINC_ORE_PLACED = PLACED_FEATURES.register("nether_zinc_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.NETHER_ZINC_ORE.getHolder().get(),
                    commonOrePlacement(20, //VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
    public static final RegistryObject<PlacedFeature> END_ZINC_ORE_PLACED = PLACED_FEATURES.register("end_zinc_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.END_ZINC_ORE.getHolder().get(),
                    commonOrePlacement(20, //VeinsPerChunk
                            HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));


    public static List<PlacementModifier> orePlacement(PlacementModifier modifier1, PlacementModifier modifier2){
        return List.of(modifier1, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int value, PlacementModifier modifier){
        return orePlacement(CountPlacement.of(value), modifier);
    }



    public static void register(IEventBus eventBus){
        PLACED_FEATURES.register(eventBus);
    }
}
