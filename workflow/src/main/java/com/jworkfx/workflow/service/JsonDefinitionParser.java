package com.jworkfx.workflow.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jworkfx.workflow.domain.Workflow;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Component
@Slf4j
public class JsonDefinitionParser {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<Workflow> parseJsonDefinition(
            @NonNull final String fileName
    ) {
        try {
            final InputStream inputStream = getClass().getResourceAsStream("/" + fileName);
            if (inputStream == null) {
                throw new IOException("File not found: " + fileName);
            }

            final Workflow workflow = objectMapper.readValue(inputStream, Workflow.class);
            log.info("Success parsing workflow '{}'", workflow.getName());
            return Optional.of(workflow);
        } catch (Exception ex) {
            log.error("parseJsonDefinition failed: {}", ex.getMessage());
        }
        return Optional.empty();
    }

}
