package com.jommo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 订单中所包含的商品
 * @TableName order_item
 */
@TableName(value ="order_item")
@Data
public class OrderItem implements Serializable {
    /**
     * ID
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 订单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 商品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    /**
     * 商品图片
     */
    private String goodsPic;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品编号
     */
    private String goodsSn;

    /**
     * 销售价格
     */
    private BigDecimal goodsPrice;

    /**
     *  购买数量
     */
    private Integer goodsQuantity;

    /**
     * 商品sku编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsSkuId;

    /**
     * 商品sku条码
     */
    private String goodsSkuCode;

    /**
     * 商品分类id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsCategoryId;

    /**
     * 商品总价
     */
    private BigDecimal totalAmount;

    /**
     * 商品规格
     */
    private String goodsAttr;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OrderItem other = (OrderItem) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderSn() == null ? other.getOrderSn() == null : this.getOrderSn().equals(other.getOrderSn()))
            && (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
            && (this.getGoodsPic() == null ? other.getGoodsPic() == null : this.getGoodsPic().equals(other.getGoodsPic()))
            && (this.getGoodsName() == null ? other.getGoodsName() == null : this.getGoodsName().equals(other.getGoodsName()))
            && (this.getGoodsSn() == null ? other.getGoodsSn() == null : this.getGoodsSn().equals(other.getGoodsSn()))
            && (this.getGoodsPrice() == null ? other.getGoodsPrice() == null : this.getGoodsPrice().equals(other.getGoodsPrice()))
            && (this.getGoodsQuantity() == null ? other.getGoodsQuantity() == null : this.getGoodsQuantity().equals(other.getGoodsQuantity()))
            && (this.getGoodsSkuId() == null ? other.getGoodsSkuId() == null : this.getGoodsSkuId().equals(other.getGoodsSkuId()))
            && (this.getGoodsSkuCode() == null ? other.getGoodsSkuCode() == null : this.getGoodsSkuCode().equals(other.getGoodsSkuCode()))
            && (this.getGoodsCategoryId() == null ? other.getGoodsCategoryId() == null : this.getGoodsCategoryId().equals(other.getGoodsCategoryId()))
            && (this.getTotalAmount() == null ? other.getTotalAmount() == null : this.getTotalAmount().equals(other.getTotalAmount()))
            && (this.getGoodsAttr() == null ? other.getGoodsAttr() == null : this.getGoodsAttr().equals(other.getGoodsAttr()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderSn() == null) ? 0 : getOrderSn().hashCode());
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getGoodsPic() == null) ? 0 : getGoodsPic().hashCode());
        result = prime * result + ((getGoodsName() == null) ? 0 : getGoodsName().hashCode());
        result = prime * result + ((getGoodsSn() == null) ? 0 : getGoodsSn().hashCode());
        result = prime * result + ((getGoodsPrice() == null) ? 0 : getGoodsPrice().hashCode());
        result = prime * result + ((getGoodsQuantity() == null) ? 0 : getGoodsQuantity().hashCode());
        result = prime * result + ((getGoodsSkuId() == null) ? 0 : getGoodsSkuId().hashCode());
        result = prime * result + ((getGoodsSkuCode() == null) ? 0 : getGoodsSkuCode().hashCode());
        result = prime * result + ((getGoodsCategoryId() == null) ? 0 : getGoodsCategoryId().hashCode());
        result = prime * result + ((getTotalAmount() == null) ? 0 : getTotalAmount().hashCode());
        result = prime * result + ((getGoodsAttr() == null) ? 0 : getGoodsAttr().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsPic=").append(goodsPic);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsSn=").append(goodsSn);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", goodsQuantity=").append(goodsQuantity);
        sb.append(", goodsSkuId=").append(goodsSkuId);
        sb.append(", goodsSkuCode=").append(goodsSkuCode);
        sb.append(", goodsCategoryId=").append(goodsCategoryId);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", goodsAttr=").append(goodsAttr);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}