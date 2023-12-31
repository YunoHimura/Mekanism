package mekanism.common.capabilities.holder.chemical;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.chemical.infuse.IInfusionTank;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.IPigmentTank;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.ISlurryTank;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.common.capabilities.holder.ConfigHolder;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo.GasSlotInfo;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo.InfusionSlotInfo;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo.PigmentSlotInfo;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo.SlurrySlotInfo;
import mekanism.common.tile.component.config.slot.ISlotInfo;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ConfigChemicalTankHolder<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>, TANK extends IChemicalTank<CHEMICAL, STACK>>
      extends ConfigHolder<TANK> implements IChemicalTankHolder<CHEMICAL, STACK, TANK> {

    protected ConfigChemicalTankHolder(Supplier<Direction> facingSupplier, Supplier<TileComponentConfig> configSupplier) {
        super(facingSupplier, configSupplier);
    }

    void addTank(@NotNull TANK tank) {
        slots.add(tank);
    }

    @NotNull
    protected abstract List<TANK> getTanksFromSlot(ISlotInfo slotInfo);

    @NotNull
    @Override
    public List<TANK> getTanks(@Nullable Direction direction) {
        return getSlots(direction, this::getTanksFromSlot);
    }

    public static class ConfigGasTankHolder extends ConfigChemicalTankHolder<Gas, GasStack, IGasTank> {

        public ConfigGasTankHolder(Supplier<Direction> facingSupplier, Supplier<TileComponentConfig> configSupplier) {
            super(facingSupplier, configSupplier);
        }

        @Override
        protected TransmissionType getTransmissionType() {
            return TransmissionType.GAS;
        }

        @NotNull
        @Override
        protected List<IGasTank> getTanksFromSlot(ISlotInfo slotInfo) {
            return slotInfo instanceof GasSlotInfo info ? info.getTanks() : Collections.emptyList();
        }
    }

    public static class ConfigInfusionTankHolder extends ConfigChemicalTankHolder<InfuseType, InfusionStack, IInfusionTank> {

        public ConfigInfusionTankHolder(Supplier<Direction> facingSupplier, Supplier<TileComponentConfig> configSupplier) {
            super(facingSupplier, configSupplier);
        }

        @Override
        protected TransmissionType getTransmissionType() {
            return TransmissionType.INFUSION;
        }

        @NotNull
        @Override
        protected List<IInfusionTank> getTanksFromSlot(ISlotInfo slotInfo) {
            return slotInfo instanceof InfusionSlotInfo info ? info.getTanks() : Collections.emptyList();
        }
    }

    public static class ConfigPigmentTankHolder extends ConfigChemicalTankHolder<Pigment, PigmentStack, IPigmentTank> {

        public ConfigPigmentTankHolder(Supplier<Direction> facingSupplier, Supplier<TileComponentConfig> configSupplier) {
            super(facingSupplier, configSupplier);
        }

        @Override
        protected TransmissionType getTransmissionType() {
            return TransmissionType.PIGMENT;
        }

        @NotNull
        @Override
        protected List<IPigmentTank> getTanksFromSlot(ISlotInfo slotInfo) {
            return slotInfo instanceof PigmentSlotInfo info ? info.getTanks() : Collections.emptyList();
        }
    }

    public static class ConfigSlurryTankHolder extends ConfigChemicalTankHolder<Slurry, SlurryStack, ISlurryTank> {

        public ConfigSlurryTankHolder(Supplier<Direction> facingSupplier, Supplier<TileComponentConfig> configSupplier) {
            super(facingSupplier, configSupplier);
        }

        @Override
        protected TransmissionType getTransmissionType() {
            return TransmissionType.SLURRY;
        }

        @NotNull
        @Override
        protected List<ISlurryTank> getTanksFromSlot(ISlotInfo slotInfo) {
            return slotInfo instanceof SlurrySlotInfo info ? info.getTanks() : Collections.emptyList();
        }
    }
}