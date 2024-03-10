package net.illuminatijoe.atomicmining.item;

import net.illuminatijoe.atomicmining.AtomicMining;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AtomicMining.MODID);

    public static final RegistryObject<Item> DIAMONDATOM = ITEMS.register("diamond_atom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ATOMICCOLLAPSER = ITEMS.register("atomic_collapser",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
