package io.github.tcaswag.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.github.tcaswag.domain.enumeration.SportType;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "product_id", nullable = false)
    private String productId;

    @NotNull
    @Size(min = 3)
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "jhi_desc")
    private byte[] desc;

    @Column(name = "jhi_desc_content_type")
    private String descContentType;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Size(min = 3)
    @Column(name = "brand", nullable = false)
    private String brand;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "subvention")
    private Float subvention;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport")
    private SportType sport;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public Product productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public Product title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getDesc() {
        return desc;
    }

    public Product desc(byte[] desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(byte[] desc) {
        this.desc = desc;
    }

    public String getDescContentType() {
        return descContentType;
    }

    public Product descContentType(String descContentType) {
        this.descContentType = descContentType;
        return this;
    }

    public void setDescContentType(String descContentType) {
        this.descContentType = descContentType;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Product creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getBrand() {
        return brand;
    }

    public Product brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Float getPrice() {
        return price;
    }

    public Product price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getSubvention() {
        return subvention;
    }

    public Product subvention(Float subvention) {
        this.subvention = subvention;
        return this;
    }

    public void setSubvention(Float subvention) {
        this.subvention = subvention;
    }

    public SportType getSport() {
        return sport;
    }

    public Product sport(SportType sport) {
        this.sport = sport;
        return this;
    }

    public void setSport(SportType sport) {
        this.sport = sport;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productId='" + getProductId() + "'" +
            ", title='" + getTitle() + "'" +
            ", desc='" + getDesc() + "'" +
            ", descContentType='" + getDescContentType() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", brand='" + getBrand() + "'" +
            ", price=" + getPrice() +
            ", subvention=" + getSubvention() +
            ", sport='" + getSport() + "'" +
            "}";
    }
}
