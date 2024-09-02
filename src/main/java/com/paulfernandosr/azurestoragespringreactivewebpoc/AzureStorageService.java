package com.paulfernandosr.azurestoragespringreactivewebpoc;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.paulfernandosr.azurestoragespringreactivewebpoc.AzureStorageUtils.buildRandomBlobName;

@Service
@RequiredArgsConstructor
public class AzureStorageService {
    private final AzureStorageRepository azureStorageRepository;

    public Mono<String> uploadFile(AzureStorageCommand azureStorageCommand) {
        FilePart filePart = azureStorageCommand.file();
        String randomBlobName = buildRandomBlobName(filePart.filename());
        return azureStorageRepository.uploadFile(azureStorageCommand.containerName(), randomBlobName, filePart.content());
    }

    public Flux<DataBuffer> downloadFile(AzureStorageQuery azureStorageQuery) {
        return azureStorageRepository.downloadFile(azureStorageQuery.containerName(), azureStorageQuery.blobName());
    }
}
