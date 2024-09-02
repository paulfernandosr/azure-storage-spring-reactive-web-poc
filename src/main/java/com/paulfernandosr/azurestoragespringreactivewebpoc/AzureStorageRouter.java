package com.paulfernandosr.azurestoragespringreactivewebpoc;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.paulfernandosr.azurestoragespringreactivewebpoc.AzureStorageConstants.READ_FILE_REQUEST_PATH;
import static com.paulfernandosr.azurestoragespringreactivewebpoc.AzureStorageConstants.UPLOAD_FILE_REQUEST_PATH;

@Configuration
@AllArgsConstructor
public class AzureStorageRouter {
    private AzureStorageHandler azureStorageHandler;

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(RequestPredicates.POST(UPLOAD_FILE_REQUEST_PATH), azureStorageHandler::uploadFile)
                .andRoute(RequestPredicates.GET(READ_FILE_REQUEST_PATH), azureStorageHandler::downloadFile);
    }
}
