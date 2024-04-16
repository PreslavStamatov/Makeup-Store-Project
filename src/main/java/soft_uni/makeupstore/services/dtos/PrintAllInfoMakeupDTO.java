package soft_uni.makeupstore.services.dtos;

import soft_uni.makeupstore.entities.MakeupType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PrintAllInfoMakeupDTO {

    private String brand;
    private MakeupType makeupType;
    private String usageGuideVideo;
    private String imageURL;
    private int quantity;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public PrintAllInfoMakeupDTO() {
    }

    public PrintAllInfoMakeupDTO(String brand, MakeupType makeupType, String usageGuideVideo, String imageURL, int quantity, BigDecimal price, String description, LocalDate releaseDate) {
        this.brand = brand;
        this.makeupType = makeupType;
        this.usageGuideVideo = usageGuideVideo;
        this.imageURL = imageURL;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setMakeupType(MakeupType makeupType) {
        this.makeupType = makeupType;
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

    public String getBrand() {
        return brand;
    }

    public MakeupType getMakeupType() {
        return makeupType;
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

    @Override
    public String toString() {
        return String.format("%s%s%nBuy from - %s%nPrice - %.2f%nQuantity - %d%nDescription - %s",brand ,makeupType, imageURL, price, quantity, description);
    }
}
