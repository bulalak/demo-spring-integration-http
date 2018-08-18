package com.example.demospringintegrationhttp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class DemoSpringIntegrationHttpApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors().withRequestDefaults(prettyPrint()).withResponseDefaults(prettyPrint())
                )
                .build();
    }

    @Test
    public void testMultipartRequest() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "contract.pdf", MediaType.APPLICATION_PDF_VALUE, "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile metadata = new MockMultipartFile("metadata", "", MediaType.APPLICATION_JSON_VALUE, "{\"version\": \"1.0\"}".getBytes(StandardCharsets.UTF_8));
        this.mockMvc.perform(multipart("/documents")
                .file(file)
                .file(metadata))
                .andExpect(status().isNoContent())
                .andDo(print())
        ;
    }

}
