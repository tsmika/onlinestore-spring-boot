package by.brest.karas.model.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class ProductDto {

    private Integer productId;

    private String picture;

    private String shortDescription;

    private String detailDescription;

    private BigDecimal price;

    private Date creationDate;

    private Date updateDate;

    private Integer changedBy;

    public ProductDto() {
    }

    public ProductDto(Integer productId, String picture, String shortDescription, String detailDescription, BigDecimal price, Date creationDate, Date updateDate, Integer changedBy) {
        this.productId = productId;
        this.picture = picture;
        this.shortDescription = shortDescription;
        this.detailDescription = detailDescription;
        this.price = price;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.changedBy = changedBy;
    }
}
