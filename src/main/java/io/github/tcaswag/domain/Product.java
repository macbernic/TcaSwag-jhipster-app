package io.github.tcaswag.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.tcaswag.domain.enumeration.SportType;

import io.github.tcaswag.domain.enumeration.ProductSize;

import io.github.tcaswag.domain.enumeration.GenderType;

/**
 * product model
 */
@ApiModel(description = "product model")
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
    private String desc;

    @NotNull
    @Size(min = 3)
    @Column(name = "brand", nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport")
    private SportType sport;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_size", nullable = false)
    private ProductSize size;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderType gender;

    @NotNull
    @Column(name = "retail_price", nullable = false)
    private Float retailPrice;

    @Column(name = "members_first_price")
    private Float membersFirstPrice;

    @Column(name = "members_price")
    private Float membersPrice;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductAsset> assets = new HashSet<>();
    @OneToMany(mappedBy = "sku")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MemberOrderItem> orderedItems = new HashSet<>();
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

    public String getDesc() {
        return desc;
    }

    public Product desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public ProductSize getSize() {
        return size;
    }

    public Product size(ProductSize size) {
        this.size = size;
        return this;
    }

    public void setSize(ProductSize size) {
        this.size = size;
    }

    public GenderType getGender() {
        return gender;
    }

    public Product gender(GenderType gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public Float getRetailPrice() {
        return retailPrice;
    }

    public Product retailPrice(Float retailPrice) {
        this.retailPrice = retailPrice;
        return this;
    }

    public void setRetailPrice(Float retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Float getMembersFirstPrice() {
        return membersFirstPrice;
    }

    public Product membersFirstPrice(Float membersFirstPrice) {
        this.membersFirstPrice = membersFirstPrice;
        return this;
    }

    public void setMembersFirstPrice(Float membersFirstPrice) {
        this.membersFirstPrice = membersFirstPrice;
    }

    public Float getMembersPrice() {
        return membersPrice;
    }

    public Product membersPrice(Float membersPrice) {
        this.membersPrice = membersPrice;
        return this;
    }

    public void setMembersPrice(Float membersPrice) {
        this.membersPrice = membersPrice;
    }

    public Set<ProductAsset> getAssets() {
        return assets;
    }

    public Product assets(Set<ProductAsset> productAssets) {
        this.assets = productAssets;
        return this;
    }

    public Product addAssets(ProductAsset productAsset) {
        this.assets.add(productAsset);
        productAsset.setProduct(this);
        return this;
    }

    public Product removeAssets(ProductAsset productAsset) {
        this.assets.remove(productAsset);
        productAsset.setProduct(null);
        return this;
    }

    public void setAssets(Set<ProductAsset> productAssets) {
        this.assets = productAssets;
    }

    public Set<MemberOrderItem> getOrderedItems() {
        return orderedItems;
    }

    public Product orderedItems(Set<MemberOrderItem> memberOrderItems) {
        this.orderedItems = memberOrderItems;
        return this;
    }

    public Product addOrderedItems(MemberOrderItem memberOrderItem) {
        this.orderedItems.add(memberOrderItem);
        memberOrderItem.setSku(this);
        return this;
    }

    public Product removeOrderedItems(MemberOrderItem memberOrderItem) {
        this.orderedItems.remove(memberOrderItem);
        memberOrderItem.setSku(null);
        return this;
    }

    public void setOrderedItems(Set<MemberOrderItem> memberOrderItems) {
        this.orderedItems = memberOrderItems;
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
            ", brand='" + getBrand() + "'" +
            ", sport='" + getSport() + "'" +
            ", size='" + getSize() + "'" +
            ", gender='" + getGender() + "'" +
            ", retailPrice=" + getRetailPrice() +
            ", membersFirstPrice=" + getMembersFirstPrice() +
            ", membersPrice=" + getMembersPrice() +
            "}";
    }
}
