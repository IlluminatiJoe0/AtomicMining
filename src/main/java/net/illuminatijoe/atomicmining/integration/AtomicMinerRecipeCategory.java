package net.illuminatijoe.atomicmining.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.illuminatijoe.atomicmining.AtomicMining;
import net.illuminatijoe.atomicmining.block.ModBlocks;
import net.illuminatijoe.atomicmining.recipe.AtomicMinerRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AtomicMinerRecipeCategory implements IRecipeCategory<AtomicMinerRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(AtomicMining.MODID, "atomic_mining");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(AtomicMining.MODID, "textures/gui/atomic_miner_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public AtomicMinerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ATOMICMINER.get()));
    }

    @Override
    public RecipeType<AtomicMinerRecipe> getRecipeType() {
        return JEIAtomicMiningAddon.MININGTYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Atomic Miner");
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
    public void setRecipe(IRecipeLayoutBuilder builder, AtomicMinerRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 86, 15).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 86, 60).addItemStack(recipe.getResultItem());
    }
}
