package mekanism.common.integration.computer.opencomputers2;

import java.util.Optional;
import li.cil.oc2.api.bus.device.rpc.RPCParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MekanismRPCParameter implements RPCParameter {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<String> name;
    private final Class<?> type;

    MekanismRPCParameter(Class<?> type, @Nullable String name) {
        this.type = type;
        this.name = Optional.ofNullable(name);
    }

    @NotNull
    @Override
    public Class<?> getType() {
        return type;
    }

    @NotNull
    @Override
    public Optional<String> getName() {
        return name;
    }
}