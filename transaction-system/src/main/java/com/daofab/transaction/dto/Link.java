package com.daofab.transaction.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Link {
    private String href;
    private String rel;
    private String method;
}
