package net.cordicus.raccoons.datagen;

import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(RaccoonsRabiesItems.RACCOON_SPAWN_EGG,
                new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.registerArmor((ArmorItem) RaccoonsRabiesItems.BANDIT_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) RaccoonsRabiesItems.BANDIT_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) RaccoonsRabiesItems.BANDIT_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) RaccoonsRabiesItems.BANDIT_BOOTS);
    }
}
