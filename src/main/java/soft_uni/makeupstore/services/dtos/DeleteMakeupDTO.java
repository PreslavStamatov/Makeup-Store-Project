package soft_uni.makeupstore.services.dtos;

import jakarta.validation.constraints.Min;

public class DeleteMakeupDTO {

    @Min(value = 1, message = "Id should be a positive number")
    private int id;

    public DeleteMakeupDTO(int id) {
        this.id = id;
    }

    public DeleteMakeupDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
