package mekanism.common.util;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CapabilityUtils {

    private CapabilityUtils() {
    }

    @NotNull
    public static <T> LazyOptional<T> getCapability(@Nullable ICapabilityProvider provider, @Nullable Capability<T> cap, @Nullable Direction side) {
        if (provider == null || cap == null || !cap.isRegistered()) {
            return LazyOptional.empty();
        }
        return provider.getCapability(cap, side);
    }

    /**
     * Helper to add listeners that don't care about the data type to lazy optionals. This makes it so when we have {@code LazyOptional<?>} we can add a listener to it
     * without having to deal with the fact that one is "capture of ?" and the listener is "?".
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void addListener(@NotNull LazyOptional<?> lazyOptional, @NotNull NonNullConsumer listener) {
        lazyOptional.addListener(listener);
    }
}