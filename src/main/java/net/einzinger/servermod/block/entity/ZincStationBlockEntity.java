package net.einzinger.servermod.block.entity;

import net.einzinger.servermod.block.custom.ZincStationBlock;
import net.einzinger.servermod.item.ModItems;
import net.einzinger.servermod.recipe.ZincStationRecipe;
import net.einzinger.servermod.screen.ZincStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ZincStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }




        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch(slot) {
                // Tool ONLY can be inserted in slot 0
                case 0 -> isValidTool(stack.getItem());
                case 1 -> !isValidTool(stack.getItem());
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
            //return true;
        }


    };


    private Item[] validTools = new Item[]{
            ModItems.ZINC_CUTTER.get(),
            ModItems.ZINC_HAMMER.get(),
    };

    private boolean isValidTool(Item item){
        for (Item validTool : validTools) {
            if (item == validTool) return true;
        }
        return false;
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2, (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 2,
                            (index, stack) -> false)),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> false)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false,
                            (index, stack) -> itemHandler.isItemValid(1, stack))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> false,
                            (index, stack) -> itemHandler.isItemValid(0, stack))));

    protected  final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;


    public ZincStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ZINC_STATION.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> ZincStationBlockEntity.this.progress;
                    case 1-> ZincStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> ZincStationBlockEntity.this.progress = value;
                    case 1 -> ZincStationBlockEntity.this.maxProgress = value;
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
        return Component.translatable("interface.servermod.zinc_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ZincStationMenu(id, inventory, this, this.data);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(ZincStationBlock.FACING);

                if (side == Direction.UP || side == Direction.DOWN) {
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
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("zinc_station.progress", this.progress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("zinc_station.progress");
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ZincStationBlockEntity pEntity) {
        if(level.isClientSide()){
            return;
        }

        if(hasRecipe(pEntity)){
            /* Initializing the block inventory */
            SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
            for (int i = 0; i < pEntity.itemHandler.getSlots(); i++){
                inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
            }

            Optional<ZincStationRecipe> recipe = level.getRecipeManager()
                    .getRecipeFor(ZincStationRecipe.Type.INSTANCE, inventory, level);


            if(canInsertAmountIntoOutputSlot(inventory) && recipe.isPresent() && canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem())){
                int durability = 1;
                ItemStack stack = pEntity.itemHandler.getStackInSlot(0);
                if(pEntity.isValidTool(stack.getItem())){
                    // Item in Slot 0 is a valid Tool

                    int maxDurability = stack.getMaxDamage();
                    durability = maxDurability - stack.getDamageValue();
                }
                if(durability >= 1){
                    pEntity.progress++;
                    setChanged(level, pos, state);

                    if(pEntity.progress >= pEntity.maxProgress){
                        craftItem(pEntity, recipe.get());
                    }
                } else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                    pEntity.resetProgress();
                    setChanged(level, pos, state);
                }
            }
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(ZincStationBlockEntity pEntity, ZincStationRecipe recipe) {
        if(hasRecipe(pEntity)){
            /* Initializing the block inventory */
            SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
            for (int i = 0; i < pEntity.itemHandler.getSlots(); i++){
                inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
            }

            if(canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory, recipe.getResultItem())){
                pEntity.itemHandler.extractItem(1, 1, false);
                int itemCount = pEntity.itemHandler.getStackInSlot(2).getCount(); // How many items are there NOW

                pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.getResultItem().getItem(),
                        itemCount + recipe.getResultItem().getCount()));

                if(pEntity.isValidTool(pEntity.itemHandler.getStackInSlot(0).getItem())){
                    // used Item in Slot 0 is a valid Tool

                    pEntity.itemHandler.getStackInSlot(0).setDamageValue(pEntity.itemHandler.getStackInSlot(0).getDamageValue() + 1);

                } else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                }

                pEntity.resetProgress();
            }
        }

    }

    private static boolean hasRecipe(ZincStationBlockEntity entity) {
        Level level = entity.level;

        /* Initializing the block inventory */
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++){
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<ZincStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ZincStationRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent();


    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(2).getItem() == stack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }
}
