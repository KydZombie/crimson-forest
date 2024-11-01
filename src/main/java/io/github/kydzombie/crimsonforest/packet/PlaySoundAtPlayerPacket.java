package io.github.kydzombie.crimsonforest.packet;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.custom.SoundEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class PlaySoundAtPlayerPacket extends Packet implements IdentifiablePacket {
    private SoundEffect sound = null;

    public PlaySoundAtPlayerPacket() {}

    public PlaySoundAtPlayerPacket(@NotNull SoundEffect sound) {
        this.sound = sound;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            sound = SoundEffect.values()[stream.readInt()];
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Invalid sound effect index");
            sound = null;
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(sound.ordinal());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Random RANDOM = new Random();

    @Override
    public void apply(NetworkHandler networkHandler) {
        System.out.println("Attempting apply");
        if (sound == null) return;
        PlayerEntity player = PlayerHelper.getPlayerFromGame();
        if (player == null) return;

        System.out.println("Successfully Applying. Sound is " + sound.toString());
        player.world.playSound(player, sound.path, sound.volume, RANDOM.nextFloat(sound.minPitch, sound.maxPitch));
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public Identifier getId() {
        return TheCrimsonForest.NAMESPACE.id("play_sound_at_player");
    }
}
