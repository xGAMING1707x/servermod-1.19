package net.einzinger.servermod.recipe;

import net.einzinger.servermod.ServerMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ServerMod.MOD_ID);


    public static final RegistryObject<RecipeSerializer<ZincStationRecipe>> ZINC_PROCESSING_SERIALIZER =
            SERIALIZERS.register("zinc_processing", () -> ZincStationRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<CoffeeMachineRecipe>> COFFEE_BREWING_SERIALIZER =
            SERIALIZERS.register("coffee_brewing", () -> CoffeeMachineRecipe.Serializer.INSTANCE);


    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
