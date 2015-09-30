package io.sysmic.dedup.examples;

import io.sysmic.dedup.chunker.BSWChunker;
import io.sysmic.dedup.chunker.ChunkIterable;
import io.sysmic.dedup.chunker.TTChunker;
import io.sysmic.dedup.rollinghash.RabinHash;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.Iterator;

public class ChunkText {

    public static void main(String[] args) throws ParseException, FileNotFoundException {

        Options options = new Options();
        options.addOption(Option.builder("f").desc("text file").type(String.class).hasArg().build());
        options.addOption(Option.builder("d").desc("divisor").type(Number.class).hasArg().build());
        options.addOption(Option.builder("tmin").desc("min threshold").type(Number.class).hasArg().build());
        options.addOption(Option.builder("tmax").desc("max threshold").type(Number.class).hasArg().build());
        options.addOption(Option.builder("ws").desc("window size").type(Number.class).hasArg().build());

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String f = (String) cmd.getOptionValue("f");
        Number d = (Number) cmd.getParsedOptionValue("d");
        Number tmin = (Number) cmd.getParsedOptionValue("tmin");
        Number tmax = (Number) cmd.getParsedOptionValue("tmax");
        Number ws = (Number) cmd.getParsedOptionValue("ws");

        System.out.printf("f:     %s\n", f);
        System.out.printf("d:     %d\n", d);
        System.out.printf("tmin:  %d\n", tmin);
        System.out.printf("tmax:  %d\n", tmax);
        System.out.printf("ws:    %d\n", ws);

        BSWChunker chunker = new BSWChunker(new RabinHash(), d.intValue(), ws.intValue());

        File file = new File(f);
        FileInputStream input = new FileInputStream(file);

        int c = 0   ;
        for(ByteBuffer bb : new ChunkIterable(chunker, input)) {
            byte[] bytes = new byte[bb.limit()];
            bb.get(bytes);
            System.out.printf("-- chunk (size: %d) \n", bytes.length);
            System.out.println(new String(bytes));
            c++;
        }
        System.out.printf("-- count (%d) \n", c);

    }

}
