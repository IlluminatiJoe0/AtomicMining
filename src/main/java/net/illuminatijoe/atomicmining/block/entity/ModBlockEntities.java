package net.illuminatijoe.atomicmining.block.entity;

import net.illuminatijoe.atomicmining.AtomicMining;
import net.illuminatijoe.atomicmining.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BlOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AtomicMining.MODID);

    public static final RegistryObject<BlockEntityType<AtomicMinerEntity>> ATOMICMINER =
            BlOCK_ENTITIES.register("atomic_miner", () ->
                    BlockEntityType.Builder.of(AtomicMinerEntity::new,
                            ModBlocks.ATOMICMINER.get()).build(null));

    public static void register(IEventBus eventBus){
        BlOCK_ENTITIES.register(eventBus);
    }
}
