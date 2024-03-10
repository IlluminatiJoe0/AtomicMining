package net.illuminatijoe.atomicmining.recipe;

import net.illuminatijoe.atomicmining.AtomicMining;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AtomicMining.MODID);

    public static final RegistryObject<RecipeSerializer<AtomicMinerRecipe>> ATOMIC_MINER_SERIALIZER =
            SERIALIZERS.register("atomic_mining", () -> AtomicMinerRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
