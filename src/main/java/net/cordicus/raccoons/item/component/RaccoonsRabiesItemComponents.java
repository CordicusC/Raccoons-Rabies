package net.cordicus.raccoons.item.component;

import com.mojang.serialization.Codec;
import net.cordicus.raccoons.RaccoonsRabies;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class RaccoonsRabiesItemComponents {

    public static final ComponentType<Boolean> HIDE_BANDIT_HOOD = Registry.register(Registries.DATA_COMPONENT_TYPE, RaccoonsRabies.id("hide_bandit_hood"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build());
    public static final ComponentType<RaccoonHandheldDataComponent> RACCOON_ENTITY_DATA = registerComponent("raccoon_entity_data",
            (builder) -> builder.codec(RaccoonHandheldDataComponent.CODEC));



    public static void registerItemComponents() {

    }

    private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, RaccoonsRabies.id(id), (builderOperator.apply(ComponentType.builder())).build());
    }
}
