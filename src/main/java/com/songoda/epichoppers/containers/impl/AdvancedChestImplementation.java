package com.songoda.epichoppers.containers.impl;

import com.songoda.epichoppers.containers.CustomContainer;
import com.songoda.epichoppers.containers.IContainer;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import us.lynuxcraft.deadsilenceiv.advancedchests.AdvancedChestsAPI;
import us.lynuxcraft.deadsilenceiv.advancedchests.chest.AdvancedChest;

public class AdvancedChestImplementation implements IContainer {

    @Override
    public CustomContainer getCustomContainer(Block block) {
        return new Container(block);
    }

    class Container extends CustomContainer {
        private final AdvancedChest advancedChest;

        public Container(Block block) {
            super(block);
            this.advancedChest = AdvancedChestsAPI.getChestManager().getAdvancedChest(block.getLocation());
        }

        @Override
        public boolean addToContainer(ItemStack itemToMove) {
            return AdvancedChestsAPI.addItemToChest(advancedChest, itemToMove);
        }

        @Override
        public ItemStack[] getItems() {
            return advancedChest.getAllItems().toArray(new ItemStack[0]);
        }

        @Override
        public void removeFromContainer(ItemStack itemToMove, int amountToMove) {
            for (ItemStack item : advancedChest.getAllItems()) {
                if (item == null) return;
                if (itemToMove.getType() == item.getType()) {
                    item.setAmount(item.getAmount() - amountToMove);

                    if (item.getAmount() <= 0)
                        advancedChest.getAllItems().remove(item);
                    return;
                }
            }
        }

        @Override
        public boolean isContainer() {
            return advancedChest != null;
        }
    }
}
