package by.brest.karas.model.dto;

import by.brest.karas.model.CartRecord;

import java.math.BigDecimal;
import java.util.List;

/**
 * POJO CartRecord for model.
 */
public class Cart {
    /**
     * Customer Id.
     */
    private Integer customerId;

    /**
     * Customer's cart records.
     */
    private List<CartRecord> cartRecords;

    /**
     * Sum Total for the customer's cart.
     */
    private BigDecimal cartSumTotal;

    /**
     * Constructor without arguments.
     */
    public Cart() {
    }

    /**
     * Constructor with arguments.
     * @param customerId customer Id
     */

    public Cart(Integer customerId) {
        this.customerId = customerId;
    }

    public Cart(Integer customerId, List<CartRecord> cartRecords, BigDecimal cartSumTotal) {
        this.customerId = customerId;
        this.cartRecords = cartRecords;
        this.cartSumTotal = cartSumTotal;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getCartSumTotal() {
        return cartSumTotal;
    }

    public void setCartSumTotal(BigDecimal cartSumTotal) {
        this.cartSumTotal = cartSumTotal;
    }

    public List<CartRecord> getCartRecords() {
        return cartRecords;
    }

    public void setCartRecords(List<CartRecord> cartRecords) {
        this.cartRecords = cartRecords;
    }
}
