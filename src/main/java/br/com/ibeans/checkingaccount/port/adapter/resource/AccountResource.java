package br.com.ibeans.checkingaccount.port.adapter.resource;

import br.com.ibeans.checkingaccount.application.AccountService;
import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.domain.model.account.AccountNotFoundException;
import br.com.ibeans.checkingaccount.port.adapter.resource.converter.AccountConverter;
import br.com.ibeans.checkingaccount.port.adapter.resource.converter.Converter;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.AccountData;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.ErrorData;
import br.com.ibeans.checkingaccount.port.adapter.resource.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor
public class AccountResource {

    private AccountService accountService;
    private AccountConverter accountConverter;

    @GetMapping("/{id}")
    @Operation(summary = "Get a Account by Its Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Account",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AccountData.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorData.class))),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorData.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorData.class)))
    })
    public AccountData get(@PathVariable String id) {
        try {
            return Optional.of(id)
                    .map(accountService::findBy)
                    .map(accountConverter::toData)
                    .get();
        } catch (AccountNotFoundException cause) {
            throw ResourceNotFoundException.create(cause);
        }
    }

    @PostMapping()
    @Operation(summary = "Create a Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create a Account", content = @Content(schema = @Schema(implementation = AccountData.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorData.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = ErrorData.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorData.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    public AccountData post(@Valid @RequestBody AccountData accountData) {
        return Optional.of(accountData)
                        .map(accountConverter::toEntity)
                        .map(accountService::save)
                        .map(accountConverter::toData)
                        .get();
    }


}
