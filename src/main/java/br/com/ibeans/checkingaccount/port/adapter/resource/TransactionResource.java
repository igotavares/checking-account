package br.com.ibeans.checkingaccount.port.adapter.resource;

import br.com.ibeans.checkingaccount.application.TransactionService;
import br.com.ibeans.checkingaccount.port.adapter.resource.converter.TransactionConverter;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.ErrorData;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.TransactionData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionResource {

    private TransactionService transactionService;
    private TransactionConverter transactionConverter;

    @PostMapping
    @Operation(summary = "Process a Transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process a Transaction Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorData.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorData.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorData.class)))
    })
    public TransactionData get(@Valid @RequestBody TransactionData transaction) {
        return Optional.of(transaction)
                        .map(transactionConverter::toEntity)
                        .map(transactionService::create)
                        .map(transactionConverter::toData)
                        .get();
    }

}
