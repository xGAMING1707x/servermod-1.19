package net.einzinger.servermod.item;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties BLUEBERRY = (new FoodProperties.Builder())
            .nutrition(2)
            .saturationMod(.5f)
            .fast()
            .build();

}
