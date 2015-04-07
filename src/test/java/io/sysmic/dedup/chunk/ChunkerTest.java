package io.sysmic.dedup.chunk;

import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

public abstract class ChunkerTest {

    public Random random = new Random(0);

    public abstract Chunker newChunker();

    public byte[] chunkAndReassemble(byte[] input) {
        ReadableByteChannel channel = Channels.newChannel(new ByteArrayInputStream(input));
        int position = 0;
        byte[] output = new byte[input.length];
        Iterator<ByteBuffer> it = newChunker().chunk(channel);
        while (it.hasNext()) {
            ByteBuffer c = it.next();
            byte[] chunk = new byte[c.remaining()];
            c.get(chunk);
            System.arraycopy(chunk, 0, output, position, chunk.length);
            position += chunk.length;
        }
        return output;
    }

    @Test
    public void mustWorkWithEmptyByteSequence() {
        byte[] input = new byte[0];
        ReadableByteChannel data = Channels.newChannel(new ByteArrayInputStream(input));
        Iterator<ByteBuffer> it = newChunker().chunk(data);
        assertFalse(it.hasNext());
    }

    @Test
    public void mustWorkWithShortByteSequence() {
        byte[] input = new byte[1];
        random.nextBytes(input);
        assertArrayEquals(input, chunkAndReassemble(input));
    }

    @Test
    public void mustWorkWithLongByteSequence() {
        byte[] input = new byte[10 * 1024 * 1024];
        random.nextBytes(input);
        assertArrayEquals(input, chunkAndReassemble(input));
    }
}
