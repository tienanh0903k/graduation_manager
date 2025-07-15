    package com.example.graduate.models;

    import java.time.LocalDate;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.Table;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Entity
    @Table(name = "project_terms")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ProjectTerm {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name; 

        private LocalDate startDate;

        private LocalDate endDate;

        private Boolean active; 
    }