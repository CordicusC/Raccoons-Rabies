package net.cordicus.raccoons.item;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.item.component.RaccoonHandheldDataComponent;
import net.cordicus.raccoons.item.component.RaccoonsRabiesItemComponents;
import net.cordicus.raccoons.item.custom.BanditSmithingTemplateItem;
import net.cordicus.raccoons.item.custom.RaccoonHandheldItem;
import net.cordicus.raccoons.item.custom.RaccoonsRabiesArmorItem;
import net.cordicus.raccoons.item.custom.RaccoonsRabiesArmorMaterials;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RaccoonsRabiesItems {

    // Raccoons and Drops
    public static Item RACCOON_FUR = register("raccoon_fur", (new Item.Settings()));
    public static Item ALBINO_RACCOON_FUR = register("albino_raccoon_fur", (new Item.Settings()));

    public static Item RACCOON = register("raccoon", (new RaccoonHandheldItem(new Item.Settings().maxCount(1).component(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA, new RaccoonHandheldDataComponent(0, "", false)))));


    // Bandit Armour Set
    public static final Item BANDIT_HOOD = register("bandit_hood", (new Item.Settings()).armor(ArmorMaterials.DIAMOND, EquipmentType.HELMET));
    public static final Item BANDIT_GAMBESON = register("bandit_gambeson", (new Item.Settings()).armor(ArmorMaterials.DIAMOND, EquipmentType.BODY));
    public static final Item BANDIT_GREAVES = register("bandit_greaves", (new Item.Settings()).armor(ArmorMaterials.DIAMOND, EquipmentType.LEGGINGS));
    public static final Item BANDIT_BOOTS = register("bandit_boots", (new Item.Settings()).armor(ArmorMaterials.DIAMOND, EquipmentType.BOOTS));
    public static final Item BANDIT_UPGRADE = register("bandit_upgrade", BanditSmithingTemplateItem.createBanditUpgrade());

    // Raccoon Spawn Egg
    public static final Item RACCOON_SPAWN_EGG = register("raccoon_spawn_egg", (new SpawnEggItem(RaccoonsRabiesEntities.RACCOON)));



    public static void registerModItems() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.addBefore(Items.NETHERITE_HELMET, BANDIT_HOOD);
            entries.addBefore(Items.NETHERITE_HELMET, BANDIT_GAMBESON);
            entries.addBefore(Items.NETHERITE_HELMET, BANDIT_GREAVES);
            entries.addBefore(Items.NETHERITE_HELMET, BANDIT_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, BANDIT_UPGRADE);
            entries.addAfter(Items.STRING, ALBINO_RACCOON_FUR);
            entries.addAfter(Items.STRING, RACCOON_FUR);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> entries.addAfter(Items.FOX_SPAWN_EGG, RACCOON_SPAWN_EGG));
    }

    public static Item register(String id, Item.Settings item) {
        return Registry.register(Registries.ITEM, Identifier.of(RaccoonsRabies.MOD_ID, id), item);
    }
}
