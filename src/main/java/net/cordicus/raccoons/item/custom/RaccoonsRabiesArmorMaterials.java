package net.cordicus.raccoons.item.custom;

import net.cordicus.raccoons.RaccoonsRabies;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;

public class RaccoonsRabiesArmorMaterials
{
    static RegistryKey<? extends Registry<EquipmentAsset>> REGISTRY_KEY = RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset"));
    public static final RegistryKey<EquipmentAsset> BANDIT_KEY = RegistryKey.of(REGISTRY_KEY, RaccoonsRabies.id("bandit"));
    public static final int BASE_DURABILITY = 33;

    public static final ArmorMaterial BANDIT_ARMOUR_MATERIAL = new ArmorMaterial(BASE_DURABILITY, Util.make(new EnumMap<>(EquipmentType.class), map ->
    {
        map.put(EquipmentType.BOOTS, 2);
        map.put(EquipmentType.LEGGINGS, 4);
        map.put(EquipmentType.CHESTPLATE, 6);
        map.put(EquipmentType.HELMET, 2);
        map.put(EquipmentType.BODY, 4);
    }), 20, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0, 0, ItemTags.REPAIRS_DIAMOND_ARMOR, BANDIT_KEY);
}
