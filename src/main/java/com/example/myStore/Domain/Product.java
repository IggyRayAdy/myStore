package com.example.myStore.Domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "prod")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Min(0)
    private int cost;
    @Digits(integer = 2, fraction = 0, message = "Enter discount, no more than 2 characters")
    @Min(0)
    private int discount;
    @NotBlank
    @Length(min = 3, max = 8, message = "Enter label, only 3-8 chars")
    private String label;
    @NotBlank
    @Length(min = 3, max = 14, message = "Enter description, only 3-14 chars")
    private String description;
    private String fileName;

    @Enumerated(EnumType.STRING)
    private TypeOfProduct typeOfProduct;

    public Product() {
    }

    public Product(int cost, int discount, String label, String description) {
        this.cost = cost;
        this.discount = discount;
        this.label = label;
        this.description = description;
    }

    public Product(int cost, int discount, String label, String description, String fileName, TypeOfProduct typeOfProduct) {
        this.cost = cost;
        this.discount = discount;
        this.label = label;
        this.description = description;
        this.fileName = fileName;
        this.typeOfProduct = typeOfProduct;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public TypeOfProduct getTypeOfProduct() {
        return Objects.requireNonNullElse(typeOfProduct, TypeOfProduct.NONE);
    }

    public void setTypeOfProduct(TypeOfProduct typeOfProduct) {

        this.typeOfProduct = typeOfProduct;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", cost=" + cost +
                ", discount=" + discount +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", fileName='" + fileName + '\'' +
                ", typeOfProduct=" + typeOfProduct +
                '}';
    }
}