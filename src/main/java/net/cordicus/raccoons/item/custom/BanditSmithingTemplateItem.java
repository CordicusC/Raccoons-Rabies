package net.cordicus.raccoons.item.custom;

import net.cordicus.raccoons.RaccoonsRabies;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.List;

public class BanditSmithingTemplateItem extends SmithingTemplateItem {
    private static final String MOD_ID = "raccoons-rabies";
    private static final Formatting TITLE_FORMAT = Formatting.GRAY;
    private static final Formatting DESCRIPTION_FORMAT = Formatting.BLUE;
    private static final String TRANSLATION_KEYS = Util.createTranslationKey("item", RaccoonsRabies.id("bandit_template"));
    private static final Text INGREDIENT_TEXT = Text.translatable(Util.createTranslationKey("item", RaccoonsRabies.id("smithing_template.ingredient")))
            .formatted(TITLE_FORMAT);
    private static final Text BANDIT_UPGRADE_APPLIES_TO_TEXT = Text.translatable(Util.createTranslationKey("item", RaccoonsRabies.id("smithing_template.bandit_upgrade.apply_to")))
            .formatted(DESCRIPTION_FORMAT);
    private static final Text BANDIT_UPGRADE_INGREDIENTS_TEXT = Text.translatable(Util.createTranslationKey("item", RaccoonsRabies.id("smithing_template.bandit_upgrade.ingredients")))
            .formatted(DESCRIPTION_FORMAT);
    private static final Text BANDIT_UPGRADE_TEXT = Text.translatable(Util.createTranslationKey("upgrade", RaccoonsRabies.id("bandit_upgrade")))
            .formatted(TITLE_FORMAT);
    private static final Text BANDIT_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item", RaccoonsRabies.id("smithing_template.base_slot_description")));
    private static final Text BANDIT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item", RaccoonsRabies.id("smithing_template.additions_slot_description")));

    private static final Identifier EMPTY_ARMOR_SLOT_HELMET_TEXTURE = Identifier.ofVanilla("item/empty_armor_slot_helmet");
    private static final Identifier EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE = Identifier.ofVanilla("item/empty_armor_slot_chestplate");
    private static final Identifier EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE = Identifier.ofVanilla("item/empty_armor_slot_leggings");
    private static final Identifier EMPTY_ARMOR_SLOT_BOOTS_TEXTURE = Identifier.ofVanilla("item/empty_armor_slot_boots");
    private static final Identifier EMPTY_SLOT_HOE_TEXTURE = Identifier.ofVanilla("item/empty_slot_hoe");
    private static final Identifier EMPTY_SLOT_AXE_TEXTURE = Identifier.ofVanilla("item/empty_slot_axe");
    private static final Identifier EMPTY_SLOT_SWORD_TEXTURE = Identifier.ofVanilla("item/empty_slot_sword");
    private static final Identifier EMPTY_SLOT_SHOVEL_TEXTURE = Identifier.ofVanilla("item/empty_slot_shovel");
    private static final Identifier EMPTY_SLOT_PICKAXE_TEXTURE = Identifier.ofVanilla("item/empty_slot_pickaxe");
    private static final Identifier EMPTY_SLOT_INGOT_TEXTURE = Identifier.ofVanilla("item/empty_slot_ingot");

    public BanditSmithingTemplateItem(
            Text appliesToText,
            Text ingredientsText,
            Text titleText,
            Text baseSlotDescriptionText,
            Text additionsSlotDescriptionText,
            List<Identifier> emptyBaseSlotTextures,
            List<Identifier> emptyAdditionsSlotTextures) {
        super(appliesToText, ingredientsText, titleText, baseSlotDescriptionText, (List<Identifier>) additionsSlotDescriptionText, emptyBaseSlotTextures, (Settings) emptyAdditionsSlotTextures);
    }

    public static Settings createBanditUpgrade() {
        return new SmithingTemplateItem(
                BANDIT_UPGRADE_APPLIES_TO_TEXT,
                BANDIT_UPGRADE_INGREDIENTS_TEXT,
                BANDIT_UPGRADE_TEXT,
                BANDIT_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT,
                (List<Identifier>) BANDIT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT,
                getBanditUpgradeEmptyBaseSlotTextures(),
                (Settings) getBanditUpgradeEmptyAdditionsSlotTextures()
        );
    }

    private static List<Identifier> getBanditUpgradeEmptyBaseSlotTextures() {
        return List.of(
                EMPTY_ARMOR_SLOT_HELMET_TEXTURE,
                EMPTY_SLOT_SWORD_TEXTURE,
                EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE,
                EMPTY_SLOT_PICKAXE_TEXTURE,
                EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE,
                EMPTY_SLOT_AXE_TEXTURE,
                EMPTY_ARMOR_SLOT_BOOTS_TEXTURE,
                EMPTY_SLOT_HOE_TEXTURE,
                EMPTY_SLOT_SHOVEL_TEXTURE
        );
    }
    private static List<Identifier> getBanditUpgradeEmptyAdditionsSlotTextures() {
        return List.of(EMPTY_SLOT_INGOT_TEXTURE);
    }

}
