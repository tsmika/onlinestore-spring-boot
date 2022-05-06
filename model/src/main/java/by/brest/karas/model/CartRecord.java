package by.brest.karas.model;

import java.util.Objects;

/**
 * POJO CartRecord for model.
 *
 * @author Tsikhan Karas
 * @version 1.0
 */
public class CartRecord {

    /**
     * Customer id.
     */
    private Integer customerId;

    /**
     * Product id.
     */
    private Integer productId;

    /**
     * Quantity of the product.
     */
    private Integer quantity;

    /**
     * Constructor without arguments.
     */
    public CartRecord() {
    }

    /**
     * Constructor with arguments.
     *
     * @param customerId customer id
     * @param productId  product id
     * @param quantity   quantity
     */
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartRecord)) return false;
        CartRecord that = (CartRecord) o;
        return Objects.equals(getCustomerId(), that.getCustomerId()) && Objects.equals(getProductId(), that.getProductId()) && Objects.equals(getQuantity(), that.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getProductId(), getQuantity());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "customerId=" + customerId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
