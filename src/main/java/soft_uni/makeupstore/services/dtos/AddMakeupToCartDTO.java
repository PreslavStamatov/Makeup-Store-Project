package soft_uni.makeupstore.services.dtos;

public class AddMakeupToCartDTO {

    private String brand;

    public AddMakeupToCartDTO(String brand) {
        this.brand = brand;
    }

    public AddMakeupToCartDTO() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
