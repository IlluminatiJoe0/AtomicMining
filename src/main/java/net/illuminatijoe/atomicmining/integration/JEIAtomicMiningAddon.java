package net.illuminatijoe.atomicmining.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.illuminatijoe.atomicmining.AtomicMining;
import net.illuminatijoe.atomicmining.recipe.AtomicMinerRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIAtomicMiningAddon implements IModPlugin {


    public static RecipeType<AtomicMinerRecipe> MININGTYPE =
            new RecipeType<>(AtomicMinerRecipeCategory.UID, AtomicMinerRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(AtomicMining.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                AtomicMinerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<AtomicMinerRecipe> recipesInfusing = rm.getAllRecipesFor(AtomicMinerRecipe.Type.INSTANCE);
        registration.addRecipes(MININGTYPE, recipesInfusing);
    }
}
