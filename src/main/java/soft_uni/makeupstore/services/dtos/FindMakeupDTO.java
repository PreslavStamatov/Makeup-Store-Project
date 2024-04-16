package soft_uni.makeupstore.services.dtos;

import jakarta.validation.constraints.Pattern;

public class FindMakeupDTO {

    @Pattern(regexp = "^[A-Z][A-Za-z]{2,99}\\b", message = "Brand must start with uppercase letter")
    private String brand;

    public FindMakeupDTO(String brand) {
        this.brand = brand;
    }

    public FindMakeupDTO() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
