package com.paulfernandosr.azurestoragespringreactivewebpoc;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.UUID;

import static org.apache.commons.io.FilenameUtils.getExtension;

public class AzureStorageUtils {
    public static String buildRandomBlobName(String filename) {
        return String.format("%s.%s", UUID.randomUUID(), getExtension(filename));
    }

    public static Flux<ByteBuffer> getByteBuffers(Flux<DataBuffer> dataBuffers) {
        return dataBuffers.flatMapSequential(dataBuffer -> Flux.fromIterable(dataBuffer::readableByteBuffers));
    }

    public static Mono<Long> getTotalByteCount(Flux<DataBuffer> dataBuffers) {
        return dataBuffers.map(DataBuffer::readableByteCount).reduce(0L, Long::sum);
    }

    public static DataBuffer getDataBuffer(ByteBuffer byteBuffer) {
        return new DefaultDataBufferFactory().wrap(byteBuffer);
    }
}
