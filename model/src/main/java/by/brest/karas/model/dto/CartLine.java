package by.brest.karas.model.dto;

import java.math.BigDecimal;

/**
 * POJO CartRecord for model.
 */
public class CartLine {
    /**
     * Customer Id.
     */
    private Integer customerId;

    private Integer productId;

    private String shortDescription;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal summa;

    /**
     * Customer's cart records.
     */
//    private List<CartRecord> cartRecords;

    /**
     * Sum Total for the customer's cart.
     */
//    private BigDecimal cartSumTotal;

    /**
     * Constructor without arguments.
     */


    public CartLine() {
    }

    /**
     * Constructor with arguments.
     * @param customerId customer Id
     */

    public CartLine(Integer customerId, Integer productId, String shortDescription, Integer quantity, BigDecimal price, BigDecimal summa) {
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
}
