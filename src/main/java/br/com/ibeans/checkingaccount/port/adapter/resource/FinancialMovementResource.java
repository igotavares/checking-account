package br.com.ibeans.checkingaccount.port.adapter.resource;

import br.com.ibeans.checkingaccount.port.adapter.persistence.FinancialMovementRepository;
import br.com.ibeans.checkingaccount.port.adapter.resource.converter.FinancialMovementConverter;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.ErrorData;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.FinancialMovementData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts/{id}/financialMovements")
@AllArgsConstructor
public class FinancialMovementResource {

    private FinancialMovementConverter financialMovementConverter;
    private FinancialMovementRepository financialMovementRepository;

    @GetMapping
    @Operation(summary = "Get Financial Movements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Financial Movements"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorData.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorData.class)))
    })
    public Page<FinancialMovementData> getAll(@PathVariable String id, Pageable pageable) {
        return financialMovementRepository.findAllByFromId(id, pageable)
                .map(financialMovementConverter::toData);
    }

}
