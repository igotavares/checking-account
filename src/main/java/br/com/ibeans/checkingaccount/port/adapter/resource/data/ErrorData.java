package br.com.ibeans.checkingaccount.port.adapter.resource.data;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ErrorData {

    private final Integer status;
    private final String timestamp;
    private final String title;
    private final String detail;
    private final List<Detail> details;

    @Getter
    @Builder
    public static class Detail {
        private final String name;
        private final String message;
    }

}
