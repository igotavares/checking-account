package br.com.ibeans.checkingaccount.port.adapter.persistence;

import br.com.ibeans.checkingaccount.domain.model.account.FinancialMovement;
import br.com.ibeans.checkingaccount.domain.model.account.FinancialMovementId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface FinancialMovementRepository extends PagingAndSortingRepository<FinancialMovement, FinancialMovementId> {

    @Query(value = "SELECT IFNULL(SUM(AMOUNT),0) FROM financial_movement WHERE TYPE = 1 AND DATE(UPDATED_ON) = CURDATE() AND ACCOUNT_FROM = :accountId", nativeQuery = true)
    BigDecimal findTotalAmountDebitedBy(@Param("accountId") String accountId);

    Page<FinancialMovement> findAllByFromId(String id, Pageable pageable);
}
