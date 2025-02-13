package net.cordicus.raccoons.sounds;

import net.cordicus.raccoons.RaccoonsRabies;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class RaccoonsRabiesSounds {
    public static final SoundEvent ENTITY_RACCOON_AMBIENT = registerSoundEvent("entity_raccoon_ambient");
    public static final SoundEvent ENTITY_RACCOON_HURT = registerSoundEvent("entity_raccoon_hurt");
    public static final SoundEvent ENTITY_RACCOON_DEATH = registerSoundEvent("entity_raccoon_death");


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = RaccoonsRabies.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        RaccoonsRabies.LOGGER.info("Registering Sounds for "+RaccoonsRabies.MOD_ID);
    }
}
