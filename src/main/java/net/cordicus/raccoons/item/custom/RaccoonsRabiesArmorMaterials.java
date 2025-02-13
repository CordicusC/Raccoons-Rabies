package net.cordicus.raccoons.item.custom;

import net.cordicus.raccoons.RaccoonsRabies;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class RaccoonsRabiesArmorMaterials {

    public static final RegistryEntry<ArmorMaterial> BANDIT = registerMaterial("bandit",
        () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, 3);
            map.put(ArmorItem.Type.LEGGINGS, 6);
            map.put(ArmorItem.Type.CHESTPLATE, 8);
            map.put(ArmorItem.Type.HELMET, 3);
            map.put(ArmorItem.Type.BODY, 11);
    }), 19, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, () -> Ingredient.ofItems(Items.DIAMOND),
                List.of(new ArmorMaterial.Layer(RaccoonsRabies.id("bandit"))), 0f, 0.1f));

    private static RegistryEntry<ArmorMaterial> registerMaterial(String id, Supplier<ArmorMaterial> armorMaterialSupplier) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, RaccoonsRabies.id(id), armorMaterialSupplier.get());
    }
}
