package net.einzinger.servermod.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.einzinger.servermod.ServerMod;
import net.einzinger.servermod.block.ModBlocks;
import net.einzinger.servermod.recipe.ZincStationRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ZincStationRecipeCategory implements IRecipeCategory<ZincStationRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(ServerMod.MOD_ID, "zinc_processing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ServerMod.MOD_ID, "textures/gui/zinc_station_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ZincStationRecipeCategory(IGuiHelper helper){
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ZINC_STATION.get()));
    }

    @Override
    public RecipeType<ZincStationRecipe> getRecipeType() {
        return JEIServerModPlugin.PROCESSING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("interface.servermod.zinc_station");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, ZincStationRecipe recipe, IFocusGroup iFocusGroup) {
        /* INPUT SLOTS */
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 12, 15).addIngredients(recipe.getIngredients().get(0));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 86, 15).addIngredients(recipe.getIngredients().get(1));

        /* OUTPUT SLOTS */
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 86, 60).addItemStack(recipe.getResultItem());
    }
}
