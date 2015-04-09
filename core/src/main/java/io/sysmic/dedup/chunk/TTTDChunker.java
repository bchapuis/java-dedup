package io.sysmic.dedup.chunk;

import io.sysmic.dedup.hash.RollingHash;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

/**
 * A two thresholds, two divisors (TTTD) chunker splits files into chunks by using a basic sliding window algorithm,
 * a minimal threshold, a maximal threshold, and a backup breakpoint mechanism that use two divisor.
 */
public class TTTDChunker extends BitmaskChunker {

    private final RollingHash rh;

    private final int d;

    private final int ddash;

    private final int tmin;

    private final int tmax;

    private final int windowSize;

    public TTTDChunker(RollingHash rh, int d, int ddash, int tmin, int tmax, int windowSize) {
        this.rh = rh;
        this.d = computeBitmask(d);
        this.ddash = computeBitmask(ddash);
        this.tmin = tmin;
        this.tmax = tmax;
        this.windowSize = windowSize;
    }

    public Iterator<ByteBuffer> chunk(final ReadableByteChannel channel) {
        return new ChunkIterator(channel) {

            private ByteBuffer buffer = ByteBuffer.allocate(tmax);

            public ByteBuffer computeNext() {
                ByteBuffer output = null;
                try {

                    // fill the buffer
                    channel.read(buffer);

                    // switch to read mode
                    buffer.flip();

                    if (buffer.hasRemaining()) {

                        // initialize output buffer
                        output = ByteBuffer.allocate(tmax);

                        // reset the rolling hash function
                        rh.reset();

                        // initialize the breakpoints
                        int breakpoint = 0;
                        int backupBreakpoint = 0;

                        while (buffer.hasRemaining() && breakpoint == 0) {
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

                                if ((hash & ddash) == 0) {
                                    backupBreakpoint = buffer.position();
                                }

                                if ((hash & d) == 0) {
                                    breakpoint = buffer.position();
                                }

                                if (!buffer.hasRemaining()) {
                                    breakpoint = backupBreakpoint;
                                }
                            }

                            // fill the output buffer
                            output.put(b);

                        }

                        // If no breakpoint have been found,
                        // set a hard threshold
                        buffer.position(0);
                        if (breakpoint == 0) {
                            breakpoint = buffer.remaining();
                        }

                        // Reset the position for the next round
                        buffer.position(breakpoint);

                        // Prepare the output buffer
                        output.position(0);
                        output.limit(breakpoint);

                        // compact the buffer for the next iteration
                        buffer.compact();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return output;
            }
        };
    }

}
