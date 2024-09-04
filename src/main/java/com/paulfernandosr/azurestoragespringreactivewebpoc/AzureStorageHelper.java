package com.paulfernandosr.azurestoragespringreactivewebpoc;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

import static java.util.UUID.randomUUID;

public class AzureStorageHelper {
    public static String buildRandomBlobName(String filename) {
        return String.format("%s.%s", randomUUID(), FilenameUtils.getExtension(filename));
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
