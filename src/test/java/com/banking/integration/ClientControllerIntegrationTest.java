package com.banking.integration;

import com.banking.dto.ClientDTO;
import com.banking.entity.LegalEntityType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClientControllerIntegrationTest extends DbUnitTestCase {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    @DataSet("fixtures/client.yml")
    public void testGetClientNotFound() throws Exception {
        this.mvc.perform(get("/client/100500"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Entity was not found")));
    }

    @Test
    @DataSet("fixtures/client.yml")
    public void testGetClientList() throws Exception {
        this.mvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.items", hasSize(3)))
                .andExpect(jsonPath("$.total", is(3)))
                .andExpect(jsonPath("$.has_more", is(false)))

                // Validate first item. Assuming next items has the same fields
                .andExpect(jsonPath("$.items[0].id", is("1")))
                .andExpect(jsonPath("$.items[0].name", is("Roman")))
                .andExpect(jsonPath("$.items[0].shortName", is("R")))
                .andExpect(jsonPath("$.items[0].address", is("Perm")))
                .andExpect(jsonPath("$.items[0].legalType", is("PARTNERSHIP")))

                .andExpect(jsonPath("$.items[1].id", is("2")))
                .andExpect(jsonPath("$.items[2].id", is("4")));
    }

    @Test
    @DataSet("fixtures/client.yml")
    public void testGetClientListPagination() throws Exception {
        this.mvc.perform(get("/client?size=1&page=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.total", is(3)))
                .andExpect(jsonPath("$.has_more", is(true)))

                .andExpect(jsonPath("$.items[0].id", is("2")));
    }

    @Test
    @DataSet("fixtures/client.yml")
    @ExpectedDataSet("assertions/client/client_create.yml")
    public void testCreateClient() throws Exception {
        ClientDTO client = new ClientDTO();
        client.setName("Richard");
        client.setShortName("R");
        client.setAddress("Ekat");
        client.setLegalType(LegalEntityType.PRIVATE_LIMITED_COMPANY);

        this.mvc.perform(
                post("/client")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(client))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is("5")));
    }

    @Test
    public void testCreateClientInvalidBody() throws Exception {
        this.mvc.perform(
                post("/client")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"Richard\",\"shortName\":\"R\"}")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Bad request")))
                .andExpect(jsonPath("$.details", hasSize(2)))
                .andExpect(
                        jsonPath(
                                "$.details",
                                containsInAnyOrder("legalType must not be null", "address must not be blank")
                        )
                );
    }

    @Test
    @DataSet("fixtures/client.yml")
    @ExpectedDataSet("assertions/client/client_update.yml")
    public void testUpdateClient() throws Exception {
        ClientDTO client = new ClientDTO();
        client.setName("Richard");
        client.setShortName("R");
        client.setAddress("Ekat");
        client.setLegalType(LegalEntityType.PRIVATE_LIMITED_COMPANY);

        this.mvc.perform(
                put("/client/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(client))
        )
                .andExpect(status().isNoContent());
    }

    @Test
    @DataSet("fixtures/client.yml")
    public void testUpdateClientInvalidBody() throws Exception {
        this.mvc.perform(
                put("/client/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Richard\",\"address\":\"R\"}")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Bad request")))
                .andExpect(jsonPath("$.details", hasSize(2)))
                .andExpect(
                        jsonPath(
                                "$.details",
                                containsInAnyOrder("legalType must not be null", "shortName must not be blank")
                        )
                );
    }

    @Test
    @DataSet("fixtures/client.yml")
    public void testUpdateClientNotFound() throws Exception {
        ClientDTO client = new ClientDTO();
        client.setName("Richard");
        client.setShortName("R");
        client.setAddress("Ekat");
        client.setLegalType(LegalEntityType.PRIVATE_LIMITED_COMPANY);

        this.mvc.perform(
                put("/client/100500")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(client))
        )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Entity was not found")));
    }

    @Test
    @DataSet({
            "fixtures/bank.yml",
            "fixtures/client.yml",
            "fixtures/deposit.yml",
    })
    @ExpectedDataSet({
            "assertions/client/client_delete.yml",
            "assertions/client/deposit_delete.yml",
    })
    public void testDeleteClient() throws Exception {
        this.mvc.perform(delete("/client/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DataSet("fixtures/client.yml")
    @ExpectedDataSet("fixtures/client.yml")
    public void testDeleteClientNotFound() throws Exception {
        this.mvc.perform(delete("/client/100500"))
                .andExpect(status().isNoContent());
    }
}
