package com.BKHOSTEL.BKHOSTEL.Dto;

import com.BKHOSTEL.BKHOSTEL.Entity.Post;
import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDto {
    @JsonProperty("total_page")
    private long totalPage;
    @JsonProperty("current_page")
    private long currentPage;

    private List<?> data;
}
