package net.einzinger.servermod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.einzinger.servermod.ServerMod;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CoffeeMachineRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public CoffeeMachineRecipe(ResourceLocation id, ItemStack output,
                               NonNullList<Ingredient> recipeItems){
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }



    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }
        boolean slot0matches = recipeItems.get(0).test(pContainer.getItem(0));
        boolean slot1matches = recipeItems.get(1).test(pContainer.getItem(1));

        return slot0matches && slot1matches;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CoffeeMachineRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String Id = "coffee_brewing";
    }

    public static class Serializer implements RecipeSerializer<CoffeeMachineRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(ServerMod.MOD_ID, "coffee_brewing");

        @Override
        public CoffeeMachineRecipe fromJson(ResourceLocation pRecipeID, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);


            for (int i = 0; i < inputs.size(); ++i){
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new CoffeeMachineRecipe(pRecipeID, output, inputs);
        }

        @Override
        public @Nullable CoffeeMachineRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); ++i){
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new CoffeeMachineRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, CoffeeMachineRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()){
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
