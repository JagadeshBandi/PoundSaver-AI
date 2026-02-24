package com.poundsaver.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poundsaver.shared.enums.JobStatus;
import com.poundsaver.shared.enums.Retailer;

import java.time.LocalDateTime;
import java.util.Objects;

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

    // Default constructor
    public ScrapingJobDTO() {}

    // All-args constructor
    public ScrapingJobDTO(String jobId, Retailer retailer, String searchQuery, JobStatus status, 
                         Integer productsScraped, String errorMessage, LocalDateTime startedAt, 
                         LocalDateTime completedAt, Long durationMs) {
        this.jobId = jobId;
        this.retailer = retailer;
        this.searchQuery = searchQuery;
        this.status = status;
        this.productsScraped = productsScraped;
        this.errorMessage = errorMessage;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.durationMs = durationMs;
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }

    // Getters and Setters
    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }

    public Retailer getRetailer() { return retailer; }
    public void setRetailer(Retailer retailer) { this.retailer = retailer; }

    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }

    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }

    public Integer getProductsScraped() { return productsScraped; }
    public void setProductsScraped(Integer productsScraped) { this.productsScraped = productsScraped; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public Long getDurationMs() { return durationMs; }
    public void setDurationMs(Long durationMs) { this.durationMs = durationMs; }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScrapingJobDTO that = (ScrapingJobDTO) o;
        return Objects.equals(jobId, that.jobId) && Objects.equals(retailer, that.retailer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, retailer);
    }

    // toString
    @Override
    public String toString() {
        return "ScrapingJobDTO{" +
                "jobId='" + jobId + '\'' +
                ", retailer=" + retailer +
                ", status=" + status +
                ", productsScraped=" + productsScraped +
                '}';
    }

    // Builder pattern implementation
    public static class Builder {
        private String jobId;
        private Retailer retailer;
        private String searchQuery;
        private JobStatus status;
        private Integer productsScraped;
        private String errorMessage;
        private LocalDateTime startedAt;
        private LocalDateTime completedAt;
        private Long durationMs;

        public Builder jobId(String jobId) { this.jobId = jobId; return this; }
        public Builder retailer(Retailer retailer) { this.retailer = retailer; return this; }
        public Builder searchQuery(String searchQuery) { this.searchQuery = searchQuery; return this; }
        public Builder status(JobStatus status) { this.status = status; return this; }
        public Builder productsScraped(Integer productsScraped) { this.productsScraped = productsScraped; return this; }
        public Builder errorMessage(String errorMessage) { this.errorMessage = errorMessage; return this; }
        public Builder startedAt(LocalDateTime startedAt) { this.startedAt = startedAt; return this; }
        public Builder completedAt(LocalDateTime completedAt) { this.completedAt = completedAt; return this; }
        public Builder durationMs(Long durationMs) { this.durationMs = durationMs; return this; }

        public ScrapingJobDTO build() {
            return new ScrapingJobDTO(jobId, retailer, searchQuery, status, 
                productsScraped, errorMessage, startedAt, completedAt, durationMs);
        }
    }
}
