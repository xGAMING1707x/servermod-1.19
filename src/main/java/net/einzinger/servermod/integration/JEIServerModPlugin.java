package net.einzinger.servermod.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.einzinger.servermod.ServerMod;
import net.einzinger.servermod.recipe.ZincStationRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIServerModPlugin implements IModPlugin {

    public static RecipeType<ZincStationRecipe> PROCESSING_TYPE =
            new RecipeType<>(ZincStationRecipeCategory.UID, ZincStationRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ServerMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                ZincStationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<ZincStationRecipe> recipesProcessing = manager.getAllRecipesFor(ZincStationRecipe.Type.INSTANCE);
        registration.addRecipes(PROCESSING_TYPE, recipesProcessing);
    }
}
