    package com.example.graduate.models;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;
    import jakarta.persistence.Table;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Entity
    @Table(name = "reviewers")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class Reviewer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "teacher_id")
        private Teacher teacher;

        @ManyToOne
        @JoinColumn(name = "project_id")
        private ProjectTopic project;

        private String note;
    }
