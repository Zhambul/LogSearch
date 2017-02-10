package ru.zhambul.logsearch.converter;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by zhambyl on 26/01/2017.
 */
@Stateless
@LocalBean
public class InStrToStringConverter implements Converter<InputStream, String> {

    @Override
    public String convert(InputStream stdOut) {
        try {
            StringBuilder builder = new StringBuilder();
            ByteBuffer buf = ByteBuffer.allocateDirect(10048);
            ReadableByteChannel readableByteChannel = Channels.newChannel(stdOut);
            while (readableByteChannel.read(buf) != -1) {
                buf.flip();
                while (buf.hasRemaining()) {
                    builder.append((char) buf.get());
                }
                buf.clear();
            }
            readableByteChannel.close();
            return builder.toString();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
}
