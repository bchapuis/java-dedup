package io.sysmic.dedup.elasticsearch;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.CharStreams;
import io.sysmic.dedup.chunker.BSWChunker;
import io.sysmic.dedup.chunker.Chunker;
import io.sysmic.dedup.chunker.TTChunker;
import io.sysmic.dedup.rollinghash.RabinHash;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.util.Iterator;

public class ChunkerTokenizer extends Tokenizer {

    private ESLogger logger = ESLoggerFactory.getLogger(getClass().getName());

    private CharTermAttribute charTermAttribute = addAttribute(CharTermAttribute.class);

    private Iterator<ByteBuffer> iterator;

    private String content;

    public ChunkerTokenizer(Reader reader) {
        super(reader);
        try {
            Chunker chunker = new BSWChunker(new RabinHash(), 19, 19);
            content = CharStreams.toString(reader);

            // sanitize the content
            content = content.replaceAll("[^a-zA-Z ]", "");
            content = content.replaceAll("\\s+", " ");
            content = content.toLowerCase();

            InputStream stream = new ByteArrayInputStream(content.getBytes(Charsets.UTF_8));
            iterator = chunker.chunk(stream);
        } catch (IOException e) {
            throw new RuntimeException("initialization problem");
        }
    }

    @Override
    public boolean incrementToken() throws IOException {
        charTermAttribute.setEmpty();
        if (iterator.hasNext()) {
            ByteBuffer bb = iterator.next();
            HashFunction hf = Hashing.sha1();
            byte[] bytes = new byte[bb.limit()];
            bb.get(bytes);
            HashCode hc = hf.hashBytes(bytes);
            charTermAttribute.append(hc.toString());
            return true;
        }
        return false;
    }

}
