package net.illuminatijoe.atomicmining.item;

import net.illuminatijoe.atomicmining.AtomicMining;
import net.illuminatijoe.atomicmining.block.custom.SpeedUpgradeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AtomicMining.MODID);

    public static final RegistryObject<Item> ATOMICCOLLAPSER = ITEMS.register("atomic_collapser",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> DIAMONDATOM = ITEMS.register("diamond_atom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COALATOM = ITEMS.register("coal_atom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EMERALDATOM = ITEMS.register("emerald_atom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLDATOM = ITEMS.register("gold_atom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRONATOM = ITEMS.register("iron_atom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LAPISATOM = ITEMS.register("lapis_atom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NETHERITEATOM = ITEMS.register("netherite_atom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NETHERSTARATOM = ITEMS.register("nether_star_atom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NETHERQUARTZATOM = ITEMS.register("quartz_atom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REDSTONEATOM = ITEMS.register("redstone_atom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPERATOM = ITEMS.register("copper_atom",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPEEDUPGRADE = ITEMS.register("speed_upgrade",
            () -> new SpeedUpgradeItem(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
