package com.jworkfx.workflow;

import com.jworkfx.workflow.domain.Workflow;
import com.jworkfx.workflow.service.JsonDefinitionParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest("workflow.enable=true")
public class JsonDefinitionParserTest {

    @Autowired
    private JsonDefinitionParser jsonDefinitionParser;

    @Test
    public void testParseJsonDefinition() {
        final String givenSampleJson = "definitions/test-workflow.json";
        Optional<Workflow> workflowOptional = jsonDefinitionParser
                .parseJsonDefinition(givenSampleJson);

        assertThat(workflowOptional).isPresent();
        Workflow workflow = workflowOptional.get();
        assertThat(workflow.getId()).isEqualTo("Workflow1");
    }
}
