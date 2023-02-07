package br.com.ibeans.checkingaccount.application.impl;

import br.com.ibeans.checkingaccount.application.TransactionService;
import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.domain.model.account.AccountNotFoundException;
import br.com.ibeans.checkingaccount.domain.model.account.FinancialMovement;
import br.com.ibeans.checkingaccount.domain.model.customer.Customer;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerNotFoundException;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerService;
import br.com.ibeans.checkingaccount.domain.model.shared.IdentityGenerator;
import br.com.ibeans.checkingaccount.domain.model.transaction.*;
import br.com.ibeans.checkingaccount.port.adapter.persistence.AccountRepositoy;
import br.com.ibeans.checkingaccount.port.adapter.persistence.FinancialMovementRepository;
import br.com.ibeans.checkingaccount.port.adapter.persistence.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private IdentityGenerator identity;
    private CustomerService customerService;
    private AccountRepositoy accountRepositoy;
    private FinancialMovementRepository financialMovementRepository;
    private TransactionRepository transactionRepository;
    private NotificationTransactionService notificationTransactionService;
    private DailyLimitSpec dailyLimit;

    @Override
    @Transactional
    public Transaction create(Transaction transaction) {
        Account accountFromFound = accountRepositoy.findById(transaction.accountIdFrom())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        Optional<AccountTransaction> accountTo = transaction.accountTo();

        Account accountToFound = accountRepositoy.findBy(
                accountTo.map(AccountTransaction::getAgency).get(),
                accountTo.map(AccountTransaction::getNumber).get(),
                accountTo.map(AccountTransaction::getDigit).get())
                .orElseThrow(() -> new AccountNotFoundException("Transfer account not found"));

        Optional<CustomerTransaction> customer = accountTo.map(AccountTransaction::getCustomer);

        Customer customerFound = customerService.customerFrom(customer
                .map(CustomerTransaction::getDocumentNumber)
                .orElse(null));

        accountToFound.validCustomer(customerFound.getId());

        dailyLimit.exceeded(transaction.accountIdFrom(), transaction.getAmount());

        accountFromFound.debit(transaction.getAmount());
        accountRepositoy.save(accountFromFound);

        FinancialMovement debitMovement = accountFromFound
                .createDebitMovement(identity.next(), transaction.getAmount(), accountToFound.getId());
        financialMovementRepository.save(debitMovement);

        accountToFound.credit(transaction.getAmount());
        accountRepositoy.save(accountToFound);

        FinancialMovement creditMovement = accountFromFound
                .createCreditMovement(identity.next(), transaction.getAmount(), accountToFound.getId());
        financialMovementRepository.save(creditMovement);

        transaction.setId(new TransactionId(identity.next()));
        transactionRepository.save(transaction);

        notificationTransactionService.notify(transaction);

        return new Transaction(transaction.getId());
    }

}
