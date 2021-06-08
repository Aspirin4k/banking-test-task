package com.banking.integration;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClientControllerIntegrationTest extends DbUnitTestCase {
    @Autowired
    private MockMvc mvc;

    @Test
    @DataSet("fixtures/client.yml")
    public void testGetClient() throws Exception {
        this.mvc.perform(get("/client/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("Roman")))
                .andExpect(jsonPath("$.shortName", is("R")))
                .andExpect(jsonPath("$.address", is("Perm")))
                .andExpect(jsonPath("$.legalType", is("PARTNERSHIP")));
    }
}
