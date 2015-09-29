package io.sysmic.dedup.lucene;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.CharStreams;
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

// http://www.citrine.io/blog/2015/2/14/building-a-custom-analyzer-in-lucene

public class ChunkerTokenizer extends Tokenizer {

    ESLogger logger = ESLoggerFactory.getLogger("myscript");

    protected CharTermAttribute charTermAttribute = addAttribute(CharTermAttribute.class);

    private Iterator<ByteBuffer> iterator;

    public ChunkerTokenizer(Reader reader) {
        super(reader);
        try {
            Chunker chunker = new TTChunker(new RabinHash(), 540, 460, 2800, 48);
            InputStream stream = new ByteArrayInputStream(CharStreams.toString(reader).getBytes(Charsets.UTF_8));
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
