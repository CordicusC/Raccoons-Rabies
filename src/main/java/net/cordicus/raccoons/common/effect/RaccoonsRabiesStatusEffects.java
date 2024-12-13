package net.cordicus.raccoons.common.effect;

import net.cordicus.raccoons.RaccoonsRabies;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RaccoonsRabiesStatusEffects {
    private static StatusEffect registerStatusEffect(String id, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(RaccoonsRabies.MOD_ID, id), entry);
    }
}
