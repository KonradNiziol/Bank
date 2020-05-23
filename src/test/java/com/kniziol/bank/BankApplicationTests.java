package com.kniziol.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kniziol.bank.infrastructure.data.ResponseData;
import com.kniziol.bank.service.dto.CreateAccountDto;
import com.kniziol.bank.service.dto.TransferMoneyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BankApplication.class)
@AutoConfigureMockMvc
class BankApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void baseTestAccountOperation_createBalanceDepositWithdraw() throws Exception {
        Long accountId = createAccount();
        checkAccountBalance(accountId.toString(), "0.00");
        increaseAccountBalance(accountId, "125");
        withdrawAccountBalance(accountId, "25");
        checkAccountBalance(accountId.toString(), "100.00");
        increaseAccountBalance(accountId, "100");
        withdrawAccountBalance(accountId, "200");
        checkAccountBalance(accountId.toString(), "0.00");

    }

    private Long createAccount() throws Exception {
        Long expectedId = 1L;
        String actualResponseBody = mockMvc.perform(post("/account/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(
                        CreateAccountDto
                                .builder()
                                .firstName("Konrad")
                                .lastName("niziol")
                                .build())))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ResponseData<Long> expectedResponseBody =
                ResponseData
                        .<Long>builder()
                        .data(expectedId)
                        .build();
        
        assertThat(objectMapper.writeValueAsString(expectedResponseBody))
                .isEqualToIgnoringWhitespace(actualResponseBody);

        return expectedId;
    }

    private void checkAccountBalance(String accountId, String expectedValue) throws Exception {
        String balance = mockMvc.perform(get("/account/balance")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("accountId", accountId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ResponseData<String> expectedResponseBody =
                ResponseData
                        .<String>builder()
                        .data(expectedValue)
                        .build();

        assertThat(objectMapper.writeValueAsString(expectedResponseBody))
                .isEqualToIgnoringWhitespace(balance);
    }

    private void increaseAccountBalance(Long accountId, String value) throws Exception {
        mockMvc.perform(put("/account/deposit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(
                        TransferMoneyDto
                                .builder()
                                .accountId(accountId)
                                .money(value)
                                .build())))
                .andExpect(status().isNoContent());
    }

    private void withdrawAccountBalance(Long accountId, String value) throws Exception {
        mockMvc.perform(put("/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(
                        TransferMoneyDto
                                .builder()
                                .accountId(accountId)
                                .money(value)
                                .build())))
                .andExpect(status().isNoContent());
    }

}
