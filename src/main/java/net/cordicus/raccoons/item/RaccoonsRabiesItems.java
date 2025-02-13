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
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class RaccoonsRabiesItems {

    // Raccoons and Drops
    public static Item RACCOON_FUR = register("raccoon_fur", new Item(new Item.Settings()));
    public static Item ALBINO_RACCOON_FUR = register("albino_raccoon_fur", new Item(new Item.Settings()));

    public static Item RACCOON = register("raccoon", new RaccoonHandheldItem(new Item.Settings().maxCount(1).component(RaccoonsRabiesItemComponents.RACCOON_ENTITY_DATA, new RaccoonHandheldDataComponent(0, "", false))));


    // Bandit Armour Set
    public static final Item BANDIT_HOOD = registerItem("bandit_hood",
            new RaccoonsRabiesArmorItem(RaccoonsRabiesArmorMaterials.BANDIT, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(19))));
    public static final Item BANDIT_GAMBESON = registerItem("bandit_gambeson",
            new RaccoonsRabiesArmorItem(RaccoonsRabiesArmorMaterials.BANDIT, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(19))));
    public static final Item BANDIT_GREAVES = registerItem("bandit_greaves",
            new RaccoonsRabiesArmorItem(RaccoonsRabiesArmorMaterials.BANDIT, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(19))));
    public static final Item BANDIT_BOOTS = registerItem("bandit_boots",
            new RaccoonsRabiesArmorItem(RaccoonsRabiesArmorMaterials.BANDIT, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(19))));
    public static final Item BANDIT_UPGRADE = register("bandit_upgrade", BanditSmithingTemplateItem.createBanditUpgrade());

    // Raccoon Spawn Egg
    public static final Item RACCOON_SPAWN_EGG = registerItem("raccoon_spawn_egg",
            new SpawnEggItem(RaccoonsRabiesEntities.RACCOON, 0x191311, 0x877970, new Item.Settings()));



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

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, RaccoonsRabies.id(name), item);
    }

    public static Item register(String id, Item item) {
        return register(RaccoonsRabies.id(id), item);
    }

    public static Item register(Identifier id, Item item) {
        return register(RegistryKey.of(Registries.ITEM.getKey(), id), item);
    }

    public static Item register(RegistryKey<Item> key, Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }
        return Registry.register(Registries.ITEM, key, item);
    }
}
