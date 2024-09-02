package com.paulfernandosr.azurestoragespringreactivewebpoc;

import com.azure.storage.blob.BlobServiceAsyncClient;
import com.azure.storage.blob.specialized.BlockBlobAsyncClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

import static com.paulfernandosr.azurestoragespringreactivewebpoc.AzureStorageUtils.getByteBuffers;
import static com.paulfernandosr.azurestoragespringreactivewebpoc.AzureStorageUtils.getTotalByteCount;

@Repository
@RequiredArgsConstructor
public class AzureStorageRepository {
    private final BlobServiceAsyncClient blobServiceAsyncClient;

    private BlockBlobAsyncClient getBlockBlobAsyncClient(String containerName, String blobName) {
        return blobServiceAsyncClient
                .getBlobContainerAsyncClient(containerName)
                .getBlobAsyncClient(blobName)
                .getBlockBlobAsyncClient();
    }

    public Mono<String> uploadFile(String containerName, String blobName, Flux<DataBuffer> dataBuffers) {
        BlockBlobAsyncClient blockBlobAsyncClient = getBlockBlobAsyncClient(containerName, blobName);
        Flux<ByteBuffer> byteBuffers = getByteBuffers(dataBuffers);
        return getTotalByteCount(dataBuffers)
                .flatMap(totalSize -> blockBlobAsyncClient.upload(byteBuffers, totalSize))
                .thenReturn(blockBlobAsyncClient.getBlobUrl());
    }

    public Flux<DataBuffer> downloadFile(String containerName, String blobName) {
        BlockBlobAsyncClient blockBlobAsyncClient = getBlockBlobAsyncClient(containerName, blobName);
        return blockBlobAsyncClient.downloadStream().map(AzureStorageUtils::getDataBuffer);
    }
}
