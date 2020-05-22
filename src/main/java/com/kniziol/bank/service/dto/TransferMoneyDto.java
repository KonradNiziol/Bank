package com.kniziol.bank.service.dto;

import java.math.BigDecimal;

public record TransferMoneyDto(Long accountId, String money) {
}
