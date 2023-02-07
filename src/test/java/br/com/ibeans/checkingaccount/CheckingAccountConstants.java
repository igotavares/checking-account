package br.com.ibeans.checkingaccount;

import java.math.BigDecimal;

public interface CheckingAccountConstants {

    interface CustomerConstants {
        String ID_IS_ONE = "6545a607-2efe-4492-bdcf-d2d1f458d613";
        String ID_IS_TWO = "484d2cf4-244a-4f27-8f49-2a2470d319b6";
        String NAME_IS_PEDRO_COSTA = "Pedro Costa";
    }

    interface DocumentConstants {
        String NUMBER_IS_13573406050 = "13573406050";
        String NUMBER_IS_06045672322 = "06045672322";
    }

    interface AgencyConstants {
        String ID_IS_ONE = "6eda679e-cab6-4b43-9f06-8e2bda6f1a2c";
        String ID_IS_TWO = "b4584b43-019f-444f-b200-d5f6088e7372";
        Long NUMBER_IS_4321 =  4321L;
        Long NUMBER_IS_5678 =  5678L;
    }

    interface FinancialMovementConstants {
        String ID_IS_ONE = "eb511f18-8d9c-4a5c-bf6b-26ceb24b1ade";
        BigDecimal AMOUNT_IS_10 =  BigDecimal.valueOf(10L);
    }

    interface AccountConstants {
        String ID_IS_ONE = "4fb6d970-5135-4168-ae7f-b4011d15e13f";
        String ID_IS_TWO = "e1fa20fa-c241-42fe-8401-61f0f44c7e18";
        Long NUMBER_IS_1234 =  1234L;
        Long NUMBER_IS_4321 =  4321L;
        Integer DIGIT_IS_1 =  1;
        Integer DIGIT_IS_2 =  2;
        BigDecimal AMOUNT_IS_10 =  BigDecimal.valueOf(10L);
        BigDecimal AMOUNT_IS_1000 =  BigDecimal.valueOf(1000L);
        BigDecimal AMOUNT_IS_20 =  BigDecimal.valueOf(20L);
    }

    interface  TransactionConstants {
        BigDecimal AMOUNT_IS_10 =  BigDecimal.valueOf(10L);
        BigDecimal AMOUNT_IS_1000 =  BigDecimal.valueOf(1000L);
        BigDecimal AMOUNT_IS_1001 =  BigDecimal.valueOf(1001L);
        BigDecimal AMOUNT_IS_20 =  BigDecimal.valueOf(20L);
    }

}
