package com.kniziol.bank.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kniziol.bank.service.AccountService;
import com.kniziol.bank.service.dto.CreateAccountDto;
import com.kniziol.bank.service.dto.TransferMoneyDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @Test
    void getAccountBalance_whenValidAccountId_thenReturns200() throws Exception {
        mockMvc.perform(get("/account/balance")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("accountId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void createAccount_whenValidAccountCreatedObject_thenReturns201() throws Exception {
        mockMvc.perform(post("/account/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(
                        CreateAccountDto
                                .builder()
                                .firstName("Konrad")
                                .lastName("niziol")
                                .build())))
                .andExpect(status().isCreated());
    }

    @Test
    void increaseAccountBalance_whenValidATransferObject_thenReturns204() throws Exception {
        mockMvc.perform(put("/account/deposit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(
                        TransferMoneyDto
                                .builder()
                                .accountId(1L)
                                .money("200")
                                .build())))
                .andExpect(status().isNoContent());
    }

    @Test
    void withdrawAccountBalance_whenValidATransferObject_thenReturns204() throws Exception {
        mockMvc.perform(put("/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(
                        TransferMoneyDto
                                .builder()
                                .accountId(1L)
                                .money("200")
                                .build())))
                .andExpect(status().isNoContent());
    }

}