package mekanism.common.inventory.slot;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import org.jetbrains.annotations.Nullable;

public class FormulaicCraftingSlot extends BasicInventorySlot {

    public static FormulaicCraftingSlot at(BooleanSupplier autoModeSupplier, @Nullable IContentsListener listener, int x, int y) {
        Objects.requireNonNull(autoModeSupplier, "Auto mode supplier cannot be null");
        return new FormulaicCraftingSlot(autoModeSupplier, listener, x, y);
    }

    private FormulaicCraftingSlot(BooleanSupplier autoModeSupplier, @Nullable IContentsListener listener, int x, int y) {
        super(alwaysTrueBi, (stack, automationType) -> automationType == AutomationType.INTERNAL || !autoModeSupplier.getAsBoolean(), alwaysTrue, listener, x, y);
        setSlotType(ContainerSlotType.VALIDITY);
    }
}