/**
 * 
 */
package com.zhsh.cashprinter.bo;

/**
 * 商品促销信息
 * @author zhangsheng
 *
 */
public class PromotionProduct {
	private String pdtBarCode;//商品条形码
	private String pmtTypeCode;//优惠代码，对应PromotionType中的code
	
	public String getPdtBarCode() {
		return pdtBarCode;
	}
	public void setPdtBarCode(String pdtBarCode) {
		this.pdtBarCode = pdtBarCode;
	}
	public String getPmtTypeCode() {
		return pmtTypeCode;
	}
	public void setPmtTypeCode(String pmtTypeCode) {
		this.pmtTypeCode = pmtTypeCode;
	}
}
