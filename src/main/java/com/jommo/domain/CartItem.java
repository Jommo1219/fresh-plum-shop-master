package com.jommo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @TableName cart_item
 */
@TableName(value = "cart_item")
@Data
public class CartItem implements Serializable {
    /**
     *
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsSkuId;

    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long memberId;

    /**
     * 用户昵称
     */
    private String memberNickname;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品主图
     */
    private String goodsPic;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品副标题
     */
    private String goodsSubTitle;

    /**
     * 商品sku条码
     */
    private String goodsSkuCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 商品分类
     */
    private Long goodsCategoryId;

    /**
     *
     */
    private String goodsSn;

    /**
     * 商品规格
     */
    private String goodsAttr;

    //扩展属性，保存商品库存
    @TableField(exist = false)
    private Integer realStock;

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
        CartItem other = (CartItem) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
                && (this.getGoodsSkuId() == null ? other.getGoodsSkuId() == null : this.getGoodsSkuId().equals(other.getGoodsSkuId()))
                && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
                && (this.getMemberNickname() == null ? other.getMemberNickname() == null : this.getMemberNickname().equals(other.getMemberNickname()))
                && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getGoodsPic() == null ? other.getGoodsPic() == null : this.getGoodsPic().equals(other.getGoodsPic()))
                && (this.getGoodsName() == null ? other.getGoodsName() == null : this.getGoodsName().equals(other.getGoodsName()))
                && (this.getGoodsSubTitle() == null ? other.getGoodsSubTitle() == null : this.getGoodsSubTitle().equals(other.getGoodsSubTitle()))
                && (this.getGoodsSkuCode() == null ? other.getGoodsSkuCode() == null : this.getGoodsSkuCode().equals(other.getGoodsSkuCode()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getGoodsCategoryId() == null ? other.getGoodsCategoryId() == null : this.getGoodsCategoryId().equals(other.getGoodsCategoryId()))
                && (this.getGoodsSn() == null ? other.getGoodsSn() == null : this.getGoodsSn().equals(other.getGoodsSn()))
                && (this.getGoodsAttr() == null ? other.getGoodsAttr() == null : this.getGoodsAttr().equals(other.getGoodsAttr()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getGoodsSkuId() == null) ? 0 : getGoodsSkuId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getMemberNickname() == null) ? 0 : getMemberNickname().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getGoodsPic() == null) ? 0 : getGoodsPic().hashCode());
        result = prime * result + ((getGoodsName() == null) ? 0 : getGoodsName().hashCode());
        result = prime * result + ((getGoodsSubTitle() == null) ? 0 : getGoodsSubTitle().hashCode());
        result = prime * result + ((getGoodsSkuCode() == null) ? 0 : getGoodsSkuCode().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getGoodsCategoryId() == null) ? 0 : getGoodsCategoryId().hashCode());
        result = prime * result + ((getGoodsSn() == null) ? 0 : getGoodsSn().hashCode());
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
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsSkuId=").append(goodsSkuId);
        sb.append(", memberId=").append(memberId);
        sb.append(", memberNickname=").append(memberNickname);
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append(", goodsPic=").append(goodsPic);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsSubTitle=").append(goodsSubTitle);
        sb.append(", goodsSkuCode=").append(goodsSkuCode);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", goodsCategoryId=").append(goodsCategoryId);
        sb.append(", goodsSn=").append(goodsSn);
        sb.append(", goodsAttr=").append(goodsAttr);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}