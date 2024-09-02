package com.paulfernandosr.azurestoragespringreactivewebpoc;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

import static com.paulfernandosr.azurestoragespringreactivewebpoc.AzureStorageConstants.*;

public class AzureStorageMapper {
    public static AzureStorageCommand mapToAzureStorageCommand(Map<String, Part> multipartData) {
        FormFieldPart formFieldPart = (FormFieldPart) multipartData.get(CONTAINER_KEY);
        FilePart filePart = (FilePart) multipartData.get(FILE_KEY);
        return new AzureStorageCommand(formFieldPart.value(), filePart);
    }

    public static AzureStorageQuery mapToAzureStorageQuery(ServerRequest request) {
        String containerName = request.pathVariable(CONTAINER_KEY);
        String blobName = request.pathVariable(BLOB_KEY);
        return new AzureStorageQuery(containerName, blobName);
    }
}
