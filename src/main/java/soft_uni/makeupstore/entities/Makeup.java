package soft_uni.makeupstore.entities;

import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "makeup")
public class Makeup{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;


    @Pattern(regexp = "^[A-Z][A-Za-z]{2,99}\\b")
    @Column(nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "makeup_type")
    private MakeupType makeupType;

    @URL(regexp = "https:\\/\\/www\\.youtube\\.com\\/watch\\?v=\\\\K.{11}\b")
    @Column(name = "usage_guide_video")
    private String usageGuideVideo;

    @Pattern(regexp = "((http://)|(https://)).*")
    @Column(name = "image_url", nullable = false)
    private String imageURL;

    @Min(1)
    @Column(nullable = false)
    private int quantity;

    @Min(1)
    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;


    public Makeup() {
    }

    public Makeup(String brand, MakeupType makeupType, String imageURL, int quantity, double price, String description) {
        this.brand = brand;
        this.makeupType = makeupType;
        this.imageURL = imageURL;
        this.quantity = quantity;
        this.price = BigDecimal.valueOf(price);
        this.description = description;

    }

    public MakeupType getMakeupType() {
        return makeupType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMakeupType(MakeupType makeupType) {
        this.makeupType = makeupType;
    }

    public String getBrand() {
        return brand;
    }

    public String getUsageGuideVideo() {
        return usageGuideVideo;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setUsageGuideVideo(String usageGuideVideo) {
        this.usageGuideVideo = usageGuideVideo;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Makeup makeup = (Makeup) o;
        return Objects.equals(brand, makeup.brand);
    }

    @Override
    public String toString() {
        return String.format("Brand - %s%nType - %s%nPrice - %.2f",brand, makeupType, price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand);
    }
}
