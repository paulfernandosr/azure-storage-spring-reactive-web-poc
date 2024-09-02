package com.paulfernandosr.azurestoragespringreactivewebpoc;

import org.springframework.http.codec.multipart.FilePart;

public record AzureStorageCommand(String containerName, FilePart file) {
}
