package com.makeup.portfolio.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="works")
@Data
public class Works {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String imgageUrl;

    // Relazione ManyToOne: Molti lavori appartengono a una categoria
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categories;
    
    private LocalDateTime createdAt;

    @PrePersist                              // PrePersist esegue questo metodo prima della insert cosi da salvare la data
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
