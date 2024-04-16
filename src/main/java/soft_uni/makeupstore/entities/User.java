package soft_uni.makeupstore.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Email(message = "Email must contain a '@' symbol")
    @Column(unique = true)
    private String email;

    @Length(min = 6)
    @Pattern(regexp = "(?=.*[A-Z])(?=.*\\d)(?=.*[a-z]).*", message = "Password not valid\nPassword must be at least 6 symbols long and must contain at least 1 uppercase, 1 lowercase letter and 1 digit")
    @Column
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "is_logged")
    private boolean isLogged;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_makeup",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "makeup_id", referencedColumnName = "id"))
    private Set<Makeup> makeupSet;

    @Column
    private boolean isAdministrator;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Makeup> getMakeupSet() {
        return makeupSet;
    }

    public String getFullName() {
        return fullName;
    }

    public Set<Makeup> getGames() {
        return makeupSet;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMakeupSet(Set<Makeup> makeupSet) {
        this.makeupSet = makeupSet;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

    public User() {
    }

    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.makeupSet = new HashSet<>();
        this.cart = new Cart();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s - loggedIn: %s", fullName, email, password, isLogged);
    }

    public void addToCollection(Makeup makeup) {
        this.makeupSet.add(makeup);
    }
}
