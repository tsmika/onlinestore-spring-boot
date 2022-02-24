package by.brest.karas.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartRecord {

    private Integer customerId;

    private Integer productId;

    @NotNull(message = "The field can not be empty")
    @Min(value = 1, message = "Quantity must be more than 0")
    private Integer quantity;

    public CartRecord() {
    }

    public CartRecord(Integer customerId, Integer productId, Integer quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "customerId=" + customerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
