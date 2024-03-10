package net.illuminatijoe.atomicmining.block;

import net.illuminatijoe.atomicmining.AtomicMining;
import net.illuminatijoe.atomicmining.block.custom.AtomicMiner;
import net.illuminatijoe.atomicmining.item.ModCreativeModTabs;
import net.illuminatijoe.atomicmining.item.ModItems;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AtomicMining.MODID);

    public static final RegistryObject<Block> ENDERBLOCK = registerBlock("ender_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ATOMICMINER = registerBlock("atomic_miner",
            () -> new AtomicMiner(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(2f).requiresCorrectToolForDrops().noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
