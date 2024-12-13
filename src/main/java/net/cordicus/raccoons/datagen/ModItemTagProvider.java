package net.cordicus.raccoons.datagen;

import net.cordicus.raccoons.item.RaccoonsRabiesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(RaccoonsRabiesItems.BANDIT_HELMET, RaccoonsRabiesItems.BANDIT_CHESTPLATE, RaccoonsRabiesItems.BANDIT_LEGGINGS, RaccoonsRabiesItems.BANDIT_BOOTS);
        getOrCreateTagBuilder(ItemTags.TRIM_TEMPLATES)
                .add(RaccoonsRabiesItems.BANDIT_UPGRADE);
    }
}
