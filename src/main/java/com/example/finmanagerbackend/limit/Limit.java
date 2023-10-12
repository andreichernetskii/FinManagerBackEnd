package com.example.finmanagerbackend.limit;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

// todo: rozwiąć, żeby pokazywał o ile i jaki limit był przekrocony
@Entity
@Table( name = "Limits" )
public class Limit {
    @Id
    @Enumerated( EnumType.STRING )
    private LimitType limitType;
    @Column( nullable = false )
    private BigDecimal limitAmount;
    private String category;
    private LocalDate creationDate;

    // constructors

    public Limit() {
    }

    public Limit( LimitType limitType, BigDecimal limitAmount, String category, LocalDate creationDate ) {
        this.limitType = limitType;
        this.limitAmount = limitAmount;
        this.category = category;
        this.creationDate = creationDate;
    }

// getters

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public LimitType getLimitType() {
        return limitType;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    // setters

    public void setLimitAmount( BigDecimal amountLimit ) {
        this.limitAmount = amountLimit;
    }

    public void setLimitType( LimitType limitType ) {
        this.limitType = limitType;
    }

    public void setCategory( String category ) {
        this.category = category;
    }

    public void setCreationDate( LocalDate creationDate ) {
        this.creationDate = creationDate;
    }
}
