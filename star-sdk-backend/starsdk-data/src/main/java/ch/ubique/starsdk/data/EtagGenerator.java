package ch.ubique.starsdk.data;

import java.nio.ByteBuffer;

import org.springframework.util.DigestUtils;

public class EtagGenerator implements EtagGeneratorInterface {
    private byte secret[] = new byte[]{'s', 'e', 'c', 'r', 'e', 't'};
    @Override
    public String getEtag(int primaryKey) {
        String hash = DigestUtils.md5DigestAsHex(ByteBuffer.allocate(10).putInt(primaryKey).put(secret).array());
        return hash;
    }

}