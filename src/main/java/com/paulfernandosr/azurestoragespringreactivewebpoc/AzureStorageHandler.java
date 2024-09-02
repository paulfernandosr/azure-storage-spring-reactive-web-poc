package com.paulfernandosr.azurestoragespringreactivewebpoc;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.paulfernandosr.azurestoragespringreactivewebpoc.AzureStorageMapper.mapToAzureStorageQuery;

@Component
@RequiredArgsConstructor
public class AzureStorageHandler {
    private final AzureStorageService azureStorageService;

    public Mono<ServerResponse> uploadFile(ServerRequest request) {
        return request.multipartData()
                .map(MultiValueMap::toSingleValueMap)
                .map(AzureStorageMapper::mapToAzureStorageCommand)
                .flatMap(azureStorageService::uploadFile)
                .flatMap(blobUrl -> ServerResponse.created(URI.create(blobUrl)).build());
    }

    public Mono<ServerResponse> downloadFile(ServerRequest request) {
        AzureStorageQuery azureStorageQuery = mapToAzureStorageQuery(request);
        Flux<DataBuffer> dataBuffers = azureStorageService.downloadFile(azureStorageQuery);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(dataBuffers, DataBuffer.class);
    }
}