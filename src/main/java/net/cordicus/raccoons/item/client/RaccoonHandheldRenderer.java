package net.cordicus.raccoons.item.client;

import net.cordicus.raccoons.item.custom.RaccoonHandheldItem;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.List;
import java.util.UUID;

public class RaccoonHandheldRenderer extends GeoArmorRenderer<RaccoonHandheldItem> {
    public RaccoonHandheldRenderer() {
        super(new RaccoonHandheldModel());
    }
}
