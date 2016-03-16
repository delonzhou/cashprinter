/**
 * 
 */
package com.zhsh.cashprinter.bo;

import java.math.BigDecimal;

/**
 * 商品信息
 * @author zhangsheng
 *
 */
public class Product {
	private String name;//名称
	private String numUnit;//数量单位
	/**
	 * 单价
	 */
	private BigDecimal price;
	private String category;//类别
	private String barCode;//条形码
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumUnit() {
		return numUnit;
	}
	public void setNumUnit(String numUnit) {
		this.numUnit = numUnit;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

}
