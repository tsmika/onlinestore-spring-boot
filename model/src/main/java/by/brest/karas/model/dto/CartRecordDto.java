package by.brest.karas.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * POJO CartRecordDto for model.
 *
 * @author Tsikhan Karas
 * @version 1.0
 */
public class CartRecordDto {
    /**
     * Customer id.
     */
    private Integer customerId;

    /**
     * Product id.
     */
    private Integer productId;

    /**
     * Short description for the product.
     */
    private String shortDescription;

    /**
     * Quantity of the product.
     */
    private Integer quantity;

    /**
     * Product price.
     */
    private BigDecimal price;

    /**
     * Summa for the product
     */
    private BigDecimal summa;

    /**
     * Constructor without arguments.
     */
    public CartRecordDto() {
    }

    /**
     * Constructor with arguments.
     *
     * @param customerId       customer id
     * @param productId        product id
     * @param shortDescription short description
     * @param quantity         quantity
     * @param price            price
     * @param summa            summa
     */
    public CartRecordDto(Integer customerId, Integer productId, String shortDescription, Integer quantity, BigDecimal price, BigDecimal summa) {
        this.customerId = customerId;
        this.productId = productId;
        this.shortDescription = shortDescription;
        this.quantity = quantity;
        this.price = price;
        this.summa = summa;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSumma() {
        return summa;
    }

    public void setSumma(BigDecimal summa) {
        this.summa = summa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartRecordDto)) return false;
        CartRecordDto that = (CartRecordDto) o;
        return Objects.equals(getCustomerId(), that.getCustomerId()) && Objects.equals(getProductId(), that.getProductId()) && Objects.equals(getShortDescription(), that.getShortDescription()) && Objects.equals(getQuantity(), that.getQuantity()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getSumma(), that.getSumma());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getProductId(), getShortDescription(), getQuantity(), getPrice(), getSumma());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{customerId=" + customerId +
                ", productId=" + productId +
                ", shortDescription='" + shortDescription + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", summa=" + summa +
                '}';
    }
}
