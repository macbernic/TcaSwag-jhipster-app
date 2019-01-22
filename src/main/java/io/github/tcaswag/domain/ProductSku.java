package io.github.tcaswag.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import io.github.tcaswag.domain.enumeration.ProductSize;

import io.github.tcaswag.domain.enumeration.GenderType;

/**
 * A ProductSku.
 */
@Entity
@Table(name = "product_sku")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductSku implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_size", nullable = false)
    private ProductSize size;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderType gender;

    @Column(name = "custom_text")
    private String customText;

    @ManyToOne
    @JsonIgnoreProperties("productSkuses")
    private TcaOrder tcaOrder;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductSize getSize() {
        return size;
    }

    public ProductSku size(ProductSize size) {
        this.size = size;
        return this;
    }

    public void setSize(ProductSize size) {
        this.size = size;
    }

    public GenderType getGender() {
        return gender;
    }

    public ProductSku gender(GenderType gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getCustomText() {
        return customText;
    }

    public ProductSku customText(String customText) {
        this.customText = customText;
        return this;
    }

    public void setCustomText(String customText) {
        this.customText = customText;
    }

    public TcaOrder getTcaOrder() {
        return tcaOrder;
    }

    public ProductSku tcaOrder(TcaOrder tcaOrder) {
        this.tcaOrder = tcaOrder;
        return this;
    }

    public void setTcaOrder(TcaOrder tcaOrder) {
        this.tcaOrder = tcaOrder;
    }

    public Product getProduct() {
        return product;
    }

    public ProductSku product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        ProductSku productSku = (ProductSku) o;
        if (productSku.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productSku.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductSku{" +
            "id=" + getId() +
            ", size='" + getSize() + "'" +
            ", gender='" + getGender() + "'" +
            ", customText='" + getCustomText() + "'" +
            "}";
    }
}
