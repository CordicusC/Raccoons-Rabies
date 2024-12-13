package net.cordicus.raccoons.item;

import net.cordicus.raccoons.RaccoonsRabies;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RaccoonsRabiesItemGroups {
    public static final ItemGroup RACCOON_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(RaccoonsRabies.MOD_ID, "raccoons-rabies"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.raccoon"))
                    .icon(() -> new ItemStack(RaccoonsRabiesItems.RACCOON_SPAWN_EGG)).entries((displayContext, entries) -> {
                        entries.add(RaccoonsRabiesItems.RACCOON_SPAWN_EGG);
                        entries.add(RaccoonsRabiesItems.BANDIT_UPGRADE);
                        entries.add(RaccoonsRabiesItems.BANDIT_BOOTS);
                        entries.add(RaccoonsRabiesItems.BANDIT_LEGGINGS);
                        entries.add(RaccoonsRabiesItems.BANDIT_CHESTPLATE);
                        entries.add(RaccoonsRabiesItems.BANDIT_HELMET);
                    }).build());


    public static void registerItemGroups() {
        RaccoonsRabies.LOGGER.info("Registering Item Groups for "+ RaccoonsRabies.MOD_ID);
    }
}
