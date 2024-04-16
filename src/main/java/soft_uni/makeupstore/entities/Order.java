package soft_uni.makeupstore.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(name = "orders_makeup",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "makeup_id", referencedColumnName = "id"))
    private Set<Makeup> makeup;

    @Column
    private BigDecimal price;

    public Order() {
    }

    public Order(int id, User user, BigDecimal price) {
        this.id = id;
        this.user = user;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMakeup(Set<Makeup> makeup) {
        this.makeup = makeup;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Set<Makeup> getMakeup() {
        return makeup;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
