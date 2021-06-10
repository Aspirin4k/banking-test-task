package com.banking.integration;

import com.banking.dto.DepositDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DepositControllerIntegrationTest extends DbUnitTestCase {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DataSet({
            "fixtures/bank.yml",
            "fixtures/client.yml",
            "fixtures/deposit.yml"
    })
    public void testGetDeposit() throws Exception {
        this.mvc.perform(get("/deposit/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.dateOpened", is("1995-06-18")))
                .andExpect(jsonPath("$.percent", is(15.4)))
                .andExpect(jsonPath("$.months", is(12)))

                // client
                .andExpect(jsonPath("$.client.id", is("1")))
                .andExpect(jsonPath("$.client.name", is("Roman")))
                .andExpect(jsonPath("$.client.shortName", is("R")))
                .andExpect(jsonPath("$.client.address", is("Perm")))
                .andExpect(jsonPath("$.client.legalType", is("PARTNERSHIP")))

                // bank
                .andExpect(jsonPath("$.bank.id", is("1")))
                .andExpect(jsonPath("$.bank.name", is("Bank")))
                .andExpect(jsonPath("$.bank.bic", is("123456789")));
    }

    @Test
    @DataSet({
            "fixtures/bank.yml",
            "fixtures/client.yml",
            "fixtures/deposit.yml"
    })
    public void testGetDepositNotFound() throws Exception {
        this.mvc.perform(get("/deposit/4"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Entity was not found")));
    }

    @Test
    @DataSet({
            "fixtures/bank.yml",
            "fixtures/client.yml",
            "fixtures/deposit.yml"
    })
    public void testGetDepositList() throws Exception {
        this.mvc.perform(get("/deposit"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.items", hasSize(3)))
                .andExpect(jsonPath("$.total", is(3)))
                .andExpect(jsonPath("$.has_more", is(false)))

                .andExpect(jsonPath("$.items[0].id", is("1")))
                .andExpect(jsonPath("$.items[0].dateOpened", is("1995-06-18")))
                .andExpect(jsonPath("$.items[0].percent", is(15.4)))
                .andExpect(jsonPath("$.items[0].months", is(12)))

                // client
                .andExpect(jsonPath("$.items[0].client.id", is("1")))
                .andExpect(jsonPath("$.items[0].client.name", is("Roman")))
                .andExpect(jsonPath("$.items[0].client.shortName", is("R")))
                .andExpect(jsonPath("$.items[0].client.address", is("Perm")))
                .andExpect(jsonPath("$.items[0].client.legalType", is("PARTNERSHIP")))

                // bank
                .andExpect(jsonPath("$.items[0].bank.id", is("1")))
                .andExpect(jsonPath("$.items[0].bank.name", is("Bank")))
                .andExpect(jsonPath("$.items[0].bank.bic", is("123456789")))

                // Validate first item. Assuming next items has the same fields
                .andExpect(jsonPath("$.items[1].id", is("2")))
                .andExpect(jsonPath("$.items[2].id", is("3")));
    }

    @Test
    @DataSet({
            "fixtures/bank.yml",
            "fixtures/client.yml",
            "fixtures/deposit.yml"
    })
    public void testGetDepositListPagination() throws Exception {
        this.mvc.perform(get("/deposit?size=1&page=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.total", is(3)))
                .andExpect(jsonPath("$.has_more", is(true)))

                .andExpect(jsonPath("$.items[0].id", is("2")));
    }

    @Test
    @DataSet({
            "fixtures/bank.yml",
            "fixtures/client.yml",
            "fixtures/deposit.yml"
    })
    @ExpectedDataSet("assertions/deposit/deposit_create.yml")
    public void testCreateDeposit() throws Exception {
        DepositDTO deposit = new DepositDTO();
        deposit.setPercent(11.1);
        deposit.setMonths(11);
        deposit.setDateOpened(LocalDate.of(2001, 1, 1));
        deposit.setBankId("1");
        deposit.setClientId("4");

        this.mvc.perform(
                post("/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(deposit))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is("5")));
    }

    @Test
    @DataSet({
            "fixtures/bank.yml",
            "fixtures/client.yml",
            "fixtures/deposit.yml"
    })
    public void testCreateDepositBankNotFound() throws Exception {
        DepositDTO deposit = new DepositDTO();
        deposit.setPercent(11.1);
        deposit.setMonths(11);
        deposit.setDateOpened(LocalDate.of(2001, 1, 1));
        deposit.setBankId("100500");
        deposit.setClientId("4");

        this.mvc.perform(
                post("/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(deposit))
        )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Entity was not found")));
    }

    @Test
    @DataSet({
            "fixtures/bank.yml",
            "fixtures/client.yml",
            "fixtures/deposit.yml"
    })
    public void testCreateDepositClientNotFound() throws Exception {
        DepositDTO deposit = new DepositDTO();
        deposit.setPercent(11.1);
        deposit.setMonths(11);
        deposit.setDateOpened(LocalDate.of(2001, 1, 1));
        deposit.setBankId("1");
        deposit.setClientId("100500");

        this.mvc.perform(
                post("/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(deposit))
        )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Entity was not found")));
    }

    @Test
    public void testCreateDepositInvalidBody() throws Exception {
        this.mvc.perform(
                post("/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dateOpened\":\"2030-03-03\",\"percent\":\"12.3\", \"months\":-34}")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Bad request")))
                .andExpect(jsonPath("$.details", hasSize(4)))
                .andExpect(
                        jsonPath(
                                "$.details",
                                containsInAnyOrder(
                                        "dateOpened must be a date in the past or in the present",
                                        "months must be greater than or equal to 1",
                                        "bankId must not be blank",
                                        "clientId must not be blank"
                                )
                        )
                );
    }

    @Test
    @DataSet({
            "fixtures/bank.yml",
            "fixtures/client.yml",
            "fixtures/deposit.yml"
    })
    @ExpectedDataSet("assertions/deposit/deposit_update.yml")
    public void testUpdateDeposit() throws Exception {
        DepositDTO deposit = new DepositDTO();
        deposit.setPercent(11.1);
        deposit.setMonths(11);
        deposit.setDateOpened(LocalDate.of(2001, 1, 1));
        deposit.setBankId("1");
        deposit.setClientId("4");

        this.mvc.perform(
                put("/deposit/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(deposit))
        )
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateDepositInvalidBody() throws Exception {
        this.mvc.perform(
                put("/deposit/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dateOpened\":\"2010-03-03\",\"percent\":150,\"months\":34,\"clientId\":\"1\"}")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Bad request")))
                .andExpect(jsonPath("$.details", hasSize(2)))
                .andExpect(
                        jsonPath(
                                "$.details",
                                containsInAnyOrder(
                                        "bankId must not be blank",
                                        "percent must be less than or equal to 100"
                                )
                        )
                );
    }

    @Test
    public void testUpdateDepositInvalidPercentField() throws Exception {
        this.mvc.perform(
                put("/deposit/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dateOpened\":\"2010-03-03\",\"percent\":\"sss\",\"months\":34,\"clientId\":\"1\",\"bankId\":\"4\"}")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DataSet({
            "fixtures/bank.yml",
            "fixtures/client.yml",
            "fixtures/deposit.yml"
    })
    public void testUpdateDepositNotFound() throws Exception {
        DepositDTO deposit = new DepositDTO();
        deposit.setPercent(11.1);
        deposit.setMonths(11);
        deposit.setDateOpened(LocalDate.of(2001, 1, 1));
        deposit.setBankId("1");
        deposit.setClientId("4");

        this.mvc.perform(
                put("/deposit/100500")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(deposit))
        )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message", is("Entity was not found")));
    }

    @Test
    @DataSet({
            "fixtures/bank.yml",
            "fixtures/client.yml",
            "fixtures/deposit.yml"
    })
    @ExpectedDataSet("assertions/deposit/deposit_delete.yml")
    public void testDeleteDeposit() throws Exception {
        this.mvc.perform(delete("/deposit/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DataSet({
            "fixtures/bank.yml",
            "fixtures/client.yml",
            "fixtures/deposit.yml"
    })
    @ExpectedDataSet("assertions/deposit/deposit_delete_not_found.yml")
    public void testDeleteDepositNotFound() throws Exception {
        this.mvc.perform(delete("/deposit/100500"))
                .andExpect(status().isNoContent());
    }
}
