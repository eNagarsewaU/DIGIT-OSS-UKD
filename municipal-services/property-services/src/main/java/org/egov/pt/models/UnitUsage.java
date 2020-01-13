package org.egov.pt.models;

import javax.validation.constraints.NotNull;

import org.egov.pt.models.enums.OccupancyType;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitUsage {
	
    @JsonProperty("id")
    @NotNull
    private String id;
    
    @JsonProperty("tenantId")
    @NotNull
    private String tenantId;

    @JsonProperty("usageCategory")
    private String usageCategory;

    @JsonProperty("occupancyType")
    @NotNull
    private OccupancyType occupancyType;

    @JsonProperty("occupancyDate")
    @NotNull
    private Long occupancyDate;

    @JsonProperty("constructionType")
    @NotNull
    private String constructionType;

}
