package fuzs.easyshulkerboxes.api.capability;

import fuzs.puzzleslib.capability.data.CapabilityComponent;

public interface ContainerSlotCapability extends CapabilityComponent {

    int getCurrentSlot();

    void setCurrentSlot(int currentSlot);
}
