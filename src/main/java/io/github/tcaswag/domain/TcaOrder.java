package io.github.tcaswag.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.tcaswag.domain.enumeration.OrderStatus;

/**
 * A TcaOrder.
 */
@Entity
@Table(name = "tca_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TcaOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private Instant date;

    @NotNull
    @Column(name = "first", nullable = false)
    private Boolean first;

    @Column(name = "amount")
    private Float amount;

    @OneToMany(mappedBy = "tcaOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductSku> productSkuses = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("orders")
    private TcaMember tcaMember;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public TcaOrder orderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public TcaOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getDate() {
        return date;
    }

    public TcaOrder date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean isFirst() {
        return first;
    }

    public TcaOrder first(Boolean first) {
        this.first = first;
        return this;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Float getAmount() {
        return amount;
    }

    public TcaOrder amount(Float amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Set<ProductSku> getProductSkuses() {
        return productSkuses;
    }

    public TcaOrder productSkuses(Set<ProductSku> productSkus) {
        this.productSkuses = productSkus;
        return this;
    }

    public TcaOrder addProductSkus(ProductSku productSku) {
        this.productSkuses.add(productSku);
        productSku.setTcaOrder(this);
        return this;
    }

    public TcaOrder removeProductSkus(ProductSku productSku) {
        this.productSkuses.remove(productSku);
        productSku.setTcaOrder(null);
        return this;
    }

    public void setProductSkuses(Set<ProductSku> productSkus) {
        this.productSkuses = productSkus;
    }

    public TcaMember getTcaMember() {
        return tcaMember;
    }

    public TcaOrder tcaMember(TcaMember tcaMember) {
        this.tcaMember = tcaMember;
        return this;
    }

    public void setTcaMember(TcaMember tcaMember) {
        this.tcaMember = tcaMember;
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
        TcaOrder tcaOrder = (TcaOrder) o;
        if (tcaOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tcaOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TcaOrder{" +
            "id=" + getId() +
            ", orderId=" + getOrderId() +
            ", status='" + getStatus() + "'" +
            ", date='" + getDate() + "'" +
            ", first='" + isFirst() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
