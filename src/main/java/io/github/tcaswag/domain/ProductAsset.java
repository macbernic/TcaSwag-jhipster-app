package io.github.tcaswag.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductAsset.
 */
@Entity
@Table(name = "product_asset")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductAsset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "asset")
    private byte[] asset;

    @Column(name = "asset_content_type")
    private String assetContentType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("assets")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public ProductAsset title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getAsset() {
        return asset;
    }

    public ProductAsset asset(byte[] asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(byte[] asset) {
        this.asset = asset;
    }

    public String getAssetContentType() {
        return assetContentType;
    }

    public ProductAsset assetContentType(String assetContentType) {
        this.assetContentType = assetContentType;
        return this;
    }

    public void setAssetContentType(String assetContentType) {
        this.assetContentType = assetContentType;
    }

    public Product getProduct() {
        return product;
    }

    public ProductAsset product(Product product) {
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
        ProductAsset productAsset = (ProductAsset) o;
        if (productAsset.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productAsset.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductAsset{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", asset='" + getAsset() + "'" +
            ", assetContentType='" + getAssetContentType() + "'" +
            "}";
    }
}
