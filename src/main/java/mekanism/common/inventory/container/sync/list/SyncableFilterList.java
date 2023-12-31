package mekanism.common.inventory.container.sync.list;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import mekanism.common.content.filter.IFilter;
import mekanism.common.network.to_client.container.property.list.FilterListPropertyData;
import mekanism.common.network.to_client.container.property.list.ListPropertyData;
import org.jetbrains.annotations.NotNull;

/**
 * Version of {@link net.minecraft.world.inventory.DataSlot} for handling filter lists
 */
public class SyncableFilterList<FILTER extends IFilter<?>> extends SyncableList<FILTER> {

    public static <FILTER extends IFilter<?>> SyncableFilterList<FILTER> create(Supplier<@NotNull List<FILTER>> getter, Consumer<@NotNull List<FILTER>> setter) {
        return new SyncableFilterList<>(getter, setter);
    }

    private SyncableFilterList(Supplier<@NotNull List<FILTER>> getter, Consumer<@NotNull List<FILTER>> setter) {
        super(getter, setter);
    }

    @Override
    public ListPropertyData<FILTER> getPropertyData(short property, DirtyType dirtyType) {
        return new FilterListPropertyData<>(property, get());
    }
}