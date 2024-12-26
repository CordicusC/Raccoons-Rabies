package net.cordicus.raccoons.item;

import net.cordicus.raccoons.RaccoonsRabies;
import net.cordicus.raccoons.entity.RaccoonsRabiesEntities;
import net.cordicus.raccoons.item.custom.BanditSmithingTemplateItem;
import net.cordicus.raccoons.item.custom.RaccoonHandheldItem;
import net.cordicus.raccoons.item.custom.RaccoonsRabiesArmorItem;
import net.cordicus.raccoons.item.custom.RaccoonsRabiesArmorMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class RaccoonsRabiesItems {

    public static Item RACCOON_FUR = register("raccoon_fur", new Item(new FabricItemSettings()));
    public static Item ALBINO_RACCOON_FUR = register("albino_raccoon_fur", new Item(new FabricItemSettings()));

    public static Item RACCOON = register("raccoon", new RaccoonHandheldItem(new FabricItemSettings().maxCount(1)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(RaccoonsRabies.MOD_ID, name), item);
    }
    public static Item register(String id, Item item) {
        return register(new Identifier(RaccoonsRabies.MOD_ID, id), item);
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

    public static void registerModItems() {
        RaccoonsRabies.LOGGER.info("Registering Mod Items for" + RaccoonsRabies.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.addBefore(Items.NETHERITE_HELMET, BANDIT_HELMET);
            entries.addBefore(Items.NETHERITE_HELMET, BANDIT_CHESTPLATE);
            entries.addBefore(Items.NETHERITE_HELMET, BANDIT_LEGGINGS);
            entries.addBefore(Items.NETHERITE_HELMET, BANDIT_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, BANDIT_UPGRADE);
            entries.addAfter(Items.STRING, ALBINO_RACCOON_FUR);
            entries.addAfter(Items.STRING, RACCOON_FUR);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.addAfter(Items.FOX_SPAWN_EGG, RACCOON_SPAWN_EGG);
        });
    }

    //Bandit Armour Set
    public static final Item BANDIT_HELMET = registerItem("bandit_hood",
            new RaccoonsRabiesArmorItem(RaccoonsRabiesArmorMaterials.BANDIT, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item BANDIT_CHESTPLATE = registerItem("bandit_gambeson",
            new RaccoonsRabiesArmorItem(RaccoonsRabiesArmorMaterials.BANDIT, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item BANDIT_LEGGINGS = registerItem("bandit_greaves",
            new RaccoonsRabiesArmorItem(RaccoonsRabiesArmorMaterials.BANDIT, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item BANDIT_BOOTS = registerItem("bandit_boots",
            new RaccoonsRabiesArmorItem(RaccoonsRabiesArmorMaterials.BANDIT, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    public static final Item BANDIT_UPGRADE = register("bandit_upgrade", BanditSmithingTemplateItem.createBanditUpgrade());
    //Raccoon Spawn Egg
    public static final Item RACCOON_SPAWN_EGG = registerItem("raccoon_spawn_egg",
            new SpawnEggItem(RaccoonsRabiesEntities.RACCOON, 0x191311, 0x877970, new FabricItemSettings()));
}
