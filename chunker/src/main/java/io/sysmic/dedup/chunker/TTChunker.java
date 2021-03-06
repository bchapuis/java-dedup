package io.sysmic.dedup.chunker;

import io.sysmic.dedup.rollinghash.RollingHash;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A two thresholds (TT) chunker splits files into chunks by using a basic sliding window algorithm,
 * a minimal threshold and a maximal threshold.
 */
public class TTChunker extends Chunker {

    private final RollingHash rh;

    private final int d;

    private final int tmin;

    private final int tmax;

    private final int windowSize;

    public TTChunker(RollingHash rh, int d, int tmin, int tmax, int windowSize) {
        this.rh = rh;
        this.d = computeBitmask(d);
        this.tmin = tmin;
        this.tmax = tmax;
        this.windowSize = windowSize;
    }

    public Iterator<ByteBuffer> chunk(final ReadableByteChannel channel) {
        return new ChunkIterator() {

            private ByteBuffer buffer = ByteBuffer.allocate(tmax);

            public boolean hasNext() {
                try {
                    channel.read(buffer);
                    buffer.flip();
                    return buffer.hasRemaining();
                } catch (IOException e) {
                    return false;
                } finally {
                    buffer.compact();
                }
            }

            public ByteBuffer next() {
                try {
                    // fill the buffer
                    channel.read(buffer);

                    // switch to read mode
                    buffer.flip();

                    if (buffer.hasRemaining()) {
                        // initialize output buffer
                        ByteBuffer output = ByteBuffer.allocate(tmax);

                        // reset the rolling hash function
                        rh.reset();

                        // initialize the breakpoint
                        int breakpoint = 0;

                        while (buffer.hasRemaining() && breakpoint == 0) {
                            // get the next byte
                            byte b = buffer.get();

                            // initialize the rolling hash at tmin
                            if (buffer.position() == tmin) {
                                buffer.position(buffer.position() - windowSize);
                                byte[] window = new byte[windowSize];
                                buffer.get(window);
                                rh.init(window);
                            }

                            // slide the window after tmin
                            if (buffer.position() > tmin) {
                                rh.roll(b);
                            }

                            // look for breakpoints from tmin
                            if (buffer.position() >= tmin) {
                                int hash = rh.getValue();
                                if ((hash & d) == 0) {
                                    breakpoint = buffer.position();
                                }
                            }

                            // fill the output buffer
                            output.put(b);
                        }

                        // compact the buffer for the next iteration
                        buffer.compact();

                        // switch the output to read mode
                        output.flip();
                        return output.asReadOnlyBuffer();
                    } else {
                        throw new NoSuchElementException();
                    }
                } catch (IOException e) {
                    throw new NoSuchElementException();
                }
            }
        };
    }

}
