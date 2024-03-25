package net.illuminatijoe.atomicmining.item;

import net.illuminatijoe.atomicmining.AtomicMining;
import net.illuminatijoe.atomicmining.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AtomicMining.MODID);

    public static final RegistryObject<CreativeModeTab> ATOMIC_MINING_TAB = CREATIVE_MODE_TABS.register("atomic_mining_tab",
            () -> CreativeModeTab
                    .builder()
                    .icon(() -> new ItemStack(ModBlocks.ATOMICMINER.get()))
                    .title(Component.translatable("creative_tab.atomic_mining_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        for(RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
                            pOutput.accept(item.get());
                        }
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
 }
