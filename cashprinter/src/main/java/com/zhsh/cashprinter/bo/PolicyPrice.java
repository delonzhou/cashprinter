/**
 * 
 */
package com.zhsh.cashprinter.bo;

import java.math.BigDecimal;

/**
 * 
 * 优惠价格信息封装类
 * @author zhangsheng
 *
 */
public class PolicyPrice {
	
	/**
	 * 商品条形码
	 */
	private String barCode;
	/**
	 * 优惠代码，如果为空则为没有优惠的情况
	 */
	private String promotionCode;
	/**
	 * 总数量
	 */
	private int totalQuantity;
	/**
	 * 实际购买的数量
	 */
	private int buyQuantity;
	/**
	 * 赠送的数量
	 */
	private int sendQuantity;
	/**
	 * 优惠价
	 */
	private BigDecimal policyPrice;
	/**
	 * 原价
	 */
	private BigDecimal basicPrice;
	
	/**
	 * 节省金额
	 */
	private BigDecimal savePrice;
	
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getPromotionCode() {
		return promotionCode;
	}
	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public int getBuyQuantity() {
		return buyQuantity;
	}
	public void setBuyQuantity(int buyQuantity) {
		this.buyQuantity = buyQuantity;
	}
	public int getSendQuantity() {
		return sendQuantity;
	}
	public void setSendQuantity(int sendQuantity) {
		this.sendQuantity = sendQuantity;
	}
	public BigDecimal getPolicyPrice() {
		return policyPrice;
	}
	public void setPolicyPrice(BigDecimal policyPrice) {
		this.policyPrice = policyPrice;
	}
	public BigDecimal getBasicPrice() {
		return basicPrice;
	}
	public void setBasicPrice(BigDecimal basicPrice) {
		this.basicPrice = basicPrice;
	}
	public BigDecimal getSavePrice() {
		return savePrice;
	}
	public void setSavePrice(BigDecimal savePrice) {
		this.savePrice = savePrice;
	}
}
