package com.banking.integration;

import com.banking.dto.BankDTO;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class BankControllerIntegrationTest extends DbUnitTestCase {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DataSet("fixtures/bank.yml")
    public void testGetBank() throws Exception {
        this.mvc.perform(get("/bank/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is("3")))
                .andExpect(jsonPath("$.name", is("Alfabank")))
                .andExpect(jsonPath("$.bic", is("987654321")));
    }

    @Test
    @DataSet("fixtures/bank.yml")
    public void testGetBankNotFound() throws Exception {
        this.mvc.perform(get("/bank/100500"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Entity was not found")));
    }

    @Test
    @DataSet("fixtures/bank.yml")
    public void testGetBankList() throws Exception {
        this.mvc.perform(get("/bank"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.items", hasSize(4)))
                .andExpect(jsonPath("$.total", is(4)))
                .andExpect(jsonPath("$.has_more", is(false)))

                // Validate first item. Assuming next items has the same fields
                .andExpect(jsonPath("$.items[0].id", is("1")))
                .andExpect(jsonPath("$.items[0].name", is("Bank")))
                .andExpect(jsonPath("$.items[0].bic", is("123456789")))

                .andExpect(jsonPath("$.items[1].id", is("3")))
                .andExpect(jsonPath("$.items[2].id", is("4")))
                .andExpect(jsonPath("$.items[3].id", is("5")));
    }

    @Test
    @DataSet("fixtures/bank.yml")
    public void testGetBankListPagination() throws Exception {
        this.mvc.perform(get("/bank?size=2&page=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.total", is(4)))
                .andExpect(jsonPath("$.has_more", is(false)))

                .andExpect(jsonPath("$.items[0].id", is("4")))
                .andExpect(jsonPath("$.items[1].id", is("5")));
    }

    @Test
    @DataSet("fixtures/bank.yml")
    @ExpectedDataSet("assertions/bank/bank_create.yml")
    public void testCreateBank() throws Exception {
        BankDTO bank = new BankDTO();
        bank.setName("WTB");
        bank.setBIC("111222333");

        this.mvc.perform(
                post("/bank")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(bank))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is("6")));
    }

    @Test
    public void testCreateBankInvalidBody() throws Exception {
        this.mvc.perform(
                post("/bank")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Richard\",\"shortName\":\"R\"}")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Bad request")))
                .andExpect(jsonPath("$.details", hasSize(1)))
                .andExpect(
                        jsonPath(
                                "$.details",
                                containsInAnyOrder("BIC must not be blank")
                        )
                );
    }

    @Test
    @DataSet("fixtures/bank.yml")
    @ExpectedDataSet("assertions/bank/bank_update.yml")
    public void testUpdateBank() throws Exception {
        BankDTO bank = new BankDTO();
        bank.setName("WTB");
        bank.setBIC("111222333");

        this.mvc.perform(
                put("/bank/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(bank))
        )
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateBankInvalidBody() throws Exception {
        this.mvc.perform(
                put("/bank/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Richard\",\"bic\":\"R\"}")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Bad request")))
                .andExpect(jsonPath("$.details", hasSize(1)))
                .andExpect(
                        jsonPath(
                                "$.details",
                                containsInAnyOrder("BIC must match \"^\\d{9}$\"")
                        )
                );
    }

    @Test
    @DataSet("fixtures/bank.yml")
    public void testUpdateBankNotFound() throws Exception {
        BankDTO bank = new BankDTO();
        bank.setName("WTB");
        bank.setBIC("111222333");

        this.mvc.perform(
                put("/bank/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(bank))
        )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Entity was not found")));
    }
}
