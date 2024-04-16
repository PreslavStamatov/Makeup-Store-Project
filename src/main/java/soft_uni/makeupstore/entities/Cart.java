package soft_uni.makeupstore.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;


    @Column(name="current_price", columnDefinition="Decimal(10,2) default '0.00'")
    private BigDecimal currentPrice;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "carts_makeup",
            joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "makeup_id", referencedColumnName = "id"))
    private Set<Makeup> content;

    public Cart() {
        this.currentPrice = BigDecimal.valueOf(0);
        this.content = new HashSet<>();
    }



    public void setCurrentPrice(BigDecimal currenPrice) {
        this.currentPrice = currenPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public Set<Makeup> getContent() {
        return content;
    }


    public void setContent(Set<Makeup> content) {
        this.content = content;
    }

    public void addMakeup(Makeup makeup) {
        this.content.add(makeup);
        this.currentPrice = this.currentPrice.add(makeup.getPrice());
    }
}
