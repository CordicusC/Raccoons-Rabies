package net.cordicus.raccoons.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record RaccoonHandheldDataComponent(int type, String owner, boolean baby) {

    public static final Codec<RaccoonHandheldDataComponent> CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(
                Codec.INT.optionalFieldOf("type", 0).forGetter(RaccoonHandheldDataComponent::type),
                Codec.STRING.optionalFieldOf("owner", "").forGetter(RaccoonHandheldDataComponent::owner),
                Codec.BOOL.optionalFieldOf("baby", false).forGetter(RaccoonHandheldDataComponent::baby)
        ).apply(builder, RaccoonHandheldDataComponent::new);
    });
    //public static final PacketCodec<ByteBuf, RaccoonHandheldDataComponent> PACKET_CODEC = PacketCodecs.INTEGER.xmap(RaccoonHandheldDataComponent::new, RaccoonHandheldDataComponent::type);

    public RaccoonHandheldDataComponent withType(int newType) {
        return new RaccoonHandheldDataComponent(newType, this.owner, this.baby);
    }

    public RaccoonHandheldDataComponent withOwner(String newOwnerUUID) {
        return new RaccoonHandheldDataComponent(this.type, newOwnerUUID, this.baby);
    }

    public RaccoonHandheldDataComponent withBaby(boolean isBaby) {
        return new RaccoonHandheldDataComponent(this.type, this.owner, isBaby);
    }

}
