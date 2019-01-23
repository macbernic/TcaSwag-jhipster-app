package io.github.tcaswag.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.tcaswag.domain.enumeration.OrderStatus;

/**
 * order model
 */
@ApiModel(description = "order model")
@Entity
@Table(name = "member_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MemberOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 6)
    @Column(name = "order_id", nullable = false)
    private String orderId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @NotNull
    @Column(name = "first", nullable = false)
    private Boolean first;

    @Column(name = "amount")
    private Float amount;

    @OneToMany(mappedBy = "memberOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MemberOrderItem> items = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("orders")
    private Member tcaMember;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public MemberOrder orderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public MemberOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Boolean isFirst() {
        return first;
    }

    public MemberOrder first(Boolean first) {
        this.first = first;
        return this;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Float getAmount() {
        return amount;
    }

    public MemberOrder amount(Float amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Set<MemberOrderItem> getItems() {
        return items;
    }

    public MemberOrder items(Set<MemberOrderItem> memberOrderItems) {
        this.items = memberOrderItems;
        return this;
    }

    public MemberOrder addItems(MemberOrderItem memberOrderItem) {
        this.items.add(memberOrderItem);
        memberOrderItem.setMemberOrder(this);
        return this;
    }

    public MemberOrder removeItems(MemberOrderItem memberOrderItem) {
        this.items.remove(memberOrderItem);
        memberOrderItem.setMemberOrder(null);
        return this;
    }

    public void setItems(Set<MemberOrderItem> memberOrderItems) {
        this.items = memberOrderItems;
    }

    public Member getTcaMember() {
        return tcaMember;
    }

    public MemberOrder tcaMember(Member member) {
        this.tcaMember = member;
        return this;
    }

    public void setTcaMember(Member member) {
        this.tcaMember = member;
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
        MemberOrder memberOrder = (MemberOrder) o;
        if (memberOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), memberOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MemberOrder{" +
            "id=" + getId() +
            ", orderId='" + getOrderId() + "'" +
            ", status='" + getStatus() + "'" +
            ", first='" + isFirst() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
