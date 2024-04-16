package soft_uni.makeupstore.services.dtos;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class CartDTO {
    private BigDecimal currentPrice;
    private Set<MakeupDTO> content;

    public CartDTO(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
        content = new HashSet<>();
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setContent(Set<MakeupDTO> content) {
        this.content = content;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public Set<MakeupDTO> getContent() {
        return content;
    }

    public CartDTO() {
    }
}
