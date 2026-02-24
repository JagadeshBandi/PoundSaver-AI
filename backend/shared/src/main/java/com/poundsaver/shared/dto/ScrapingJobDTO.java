package com.poundsaver.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poundsaver.shared.enums.JobStatus;
import com.poundsaver.shared.enums.Retailer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapingJobDTO {
    private String jobId;
    private Retailer retailer;
    private String searchQuery;
    private JobStatus status;
    private Integer productsScraped;
    private String errorMessage;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startedAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime completedAt;
    
    private Long durationMs;
}
