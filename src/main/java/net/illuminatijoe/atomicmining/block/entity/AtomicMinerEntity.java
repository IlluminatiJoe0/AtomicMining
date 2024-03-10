package net.illuminatijoe.atomicmining.block.entity;

import net.illuminatijoe.atomicmining.AtomicMining;
import net.illuminatijoe.atomicmining.block.ModBlocks;
import net.illuminatijoe.atomicmining.block.custom.AtomicMiner;
import net.illuminatijoe.atomicmining.item.ModItems;
import net.illuminatijoe.atomicmining.recipe.AtomicMinerRecipe;
import net.illuminatijoe.atomicmining.screen.AtomicMinerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.CompressionEncoder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.Map;
import java.util.Optional;

public class AtomicMinerEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {

            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot){
                case 0 -> false;
                case 1 -> stack.getItem() == ModItems.DIAMONDATOM.get();
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (i) -> i == 2, (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (index) -> index == 1,
                            (index, stack) -> itemStackHandler.isItemValid(1, stack))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (i) -> i == 2, (i, s) -> false)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (i) -> i == 1,
                            (index, stack) -> itemStackHandler.isItemValid(1, stack))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemStackHandler, (index) -> index == 0 || index == 1,
                            (index, stack) -> itemStackHandler.isItemValid(0, stack) || itemStackHandler.isItemValid(1, stack))));

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 240;

    public AtomicMinerEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ATOMICMINER.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> AtomicMinerEntity.this.progress;
                    case 1 -> AtomicMinerEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AtomicMinerEntity.this.progress = value;
                    case 1 -> AtomicMinerEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Atomic Miner");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new AtomicMinerMenu(i, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(AtomicMiner.FACING);

                if(side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemStackHandler.serializeNBT());
        pTag.putInt("atomic_miner.progress", progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemStackHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("atomic_miner.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, AtomicMinerEntity atomicMinerEntity) {
        if(level.isClientSide()) {
            return;
        }

        if (hasRecipe(atomicMinerEntity)) {
            atomicMinerEntity.progress++;
            setChanged(level, blockPos, blockState);

            if(atomicMinerEntity.progress >= atomicMinerEntity.maxProgress){
                craftItem(atomicMinerEntity);
            }
        } else {
            atomicMinerEntity.resetProgress();
            setChanged(level, blockPos, blockState);
        }

    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(AtomicMinerEntity atomicMinerEntity) {
        Level level = atomicMinerEntity.level;
        SimpleContainer inventory = new SimpleContainer(atomicMinerEntity.itemStackHandler.getSlots());
        for (int i = 0; i < atomicMinerEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, atomicMinerEntity.itemStackHandler.getStackInSlot(i));
        }

        Optional<AtomicMinerRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(AtomicMinerRecipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(atomicMinerEntity)){
            atomicMinerEntity.itemStackHandler.extractItem(1, 0, false);
            atomicMinerEntity.itemStackHandler.setStackInSlot(2, new ItemStack(recipe.get().getResultItem().getItem(),
                    atomicMinerEntity.itemStackHandler.getStackInSlot(2).getCount() + 1));

            atomicMinerEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(AtomicMinerEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemStackHandler.getSlots());
        for (int i = 0; i < entity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemStackHandler.getStackInSlot(i));
        }

        Optional<AtomicMinerRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(AtomicMinerRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(2).getItem() == itemStack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }
}
