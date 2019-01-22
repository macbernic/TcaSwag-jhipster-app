package io.github.tcaswag.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TcaMember.
 */
@Entity
@Table(name = "tca_member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TcaMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @OneToOne    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "tcaMember")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TcaOrder> orders = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public TcaMember nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public User getUser() {
        return user;
    }

    public TcaMember user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<TcaOrder> getOrders() {
        return orders;
    }

    public TcaMember orders(Set<TcaOrder> tcaOrders) {
        this.orders = tcaOrders;
        return this;
    }

    public TcaMember addOrders(TcaOrder tcaOrder) {
        this.orders.add(tcaOrder);
        tcaOrder.setTcaMember(this);
        return this;
    }

    public TcaMember removeOrders(TcaOrder tcaOrder) {
        this.orders.remove(tcaOrder);
        tcaOrder.setTcaMember(null);
        return this;
    }

    public void setOrders(Set<TcaOrder> tcaOrders) {
        this.orders = tcaOrders;
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
        TcaMember tcaMember = (TcaMember) o;
        if (tcaMember.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tcaMember.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TcaMember{" +
            "id=" + getId() +
            ", nickname='" + getNickname() + "'" +
            "}";
    }
}
