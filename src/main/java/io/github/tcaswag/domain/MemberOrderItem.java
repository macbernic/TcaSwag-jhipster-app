package io.github.tcaswag.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MemberOrderItem.
 */
@Entity
@Table(name = "member_order_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MemberOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "custom_text")
    private String customText;

    @NotNull
    @Column(name = "applied_price", nullable = false)
    private Float appliedPrice;

    @ManyToOne
    @JsonIgnoreProperties("items")
    private MemberOrder memberOrder;

    @ManyToOne
    @JsonIgnoreProperties("orderedItems")
    private Product sku;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomText() {
        return customText;
    }

    public MemberOrderItem customText(String customText) {
        this.customText = customText;
        return this;
    }

    public void setCustomText(String customText) {
        this.customText = customText;
    }

    public Float getAppliedPrice() {
        return appliedPrice;
    }

    public MemberOrderItem appliedPrice(Float appliedPrice) {
        this.appliedPrice = appliedPrice;
        return this;
    }

    public void setAppliedPrice(Float appliedPrice) {
        this.appliedPrice = appliedPrice;
    }

    public MemberOrder getMemberOrder() {
        return memberOrder;
    }

    public MemberOrderItem memberOrder(MemberOrder memberOrder) {
        this.memberOrder = memberOrder;
        return this;
    }

    public void setMemberOrder(MemberOrder memberOrder) {
        this.memberOrder = memberOrder;
    }

    public Product getSku() {
        return sku;
    }

    public MemberOrderItem sku(Product product) {
        this.sku = product;
        return this;
    }

    public void setSku(Product product) {
        this.sku = product;
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
        MemberOrderItem memberOrderItem = (MemberOrderItem) o;
        if (memberOrderItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), memberOrderItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MemberOrderItem{" +
            "id=" + getId() +
            ", customText='" + getCustomText() + "'" +
            ", appliedPrice=" + getAppliedPrice() +
            "}";
    }
}
