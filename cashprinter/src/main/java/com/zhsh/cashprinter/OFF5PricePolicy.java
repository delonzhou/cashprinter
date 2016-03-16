/**
 * 
 */
package com.zhsh.cashprinter;

import java.math.BigDecimal;

import com.zhsh.cashprinter.bo.PolicyPrice;
import com.zhsh.cashprinter.bo.Product;
import com.zhsh.cashprinter.common.IPricePolicy;
import com.zhsh.cashprinter.common.PromotionType;
import com.zhsh.cashprinter.exception.CashPrinterRuntimeException;

/**
 * 95折优惠计价方式  TODO 折扣可以传入
 * @author zhangsheng
 *
 */
public class OFF5PricePolicy implements IPricePolicy {

	private CashPrinterContext ctx;
	
	/* (non-Javadoc)
	 * @see com.zhsh.cashprinter.common.CashPrinterContextAware#setCashPrinterContext(com.zhsh.cashprinter.CashPrinterContext)
	 */
	public void setCashPrinterContext(CashPrinterContext ctx) throws CashPrinterRuntimeException {
		this.ctx = ctx;
	}

	/* (non-Javadoc)
	 * @see com.zhsh.cashprinter.common.IPricePolicy#calculatePrice(java.lang.String, int)
	 */
	public PolicyPrice calculatePrice(String productBarCode, int quantity) throws CashPrinterRuntimeException {
		PolicyPrice retPolicyPrice = new PolicyPrice();
		BigDecimal totalPrice = null;   
		Product pdt = ctx.getProductByBarCode(productBarCode);
		if(pdt == null) {
			throw new CashPrinterRuntimeException("商品条形码不存在，barCode:" + productBarCode);
		}else {
			BigDecimal price = pdt.getPrice();
			totalPrice = price.multiply(new BigDecimal(String.valueOf(quantity))).multiply(new BigDecimal("0.95"));// TODO 折扣是可以传参的
			BigDecimal basicPrice = price.multiply(new BigDecimal(quantity));
			BigDecimal savePrice = basicPrice.subtract(totalPrice);
			
			retPolicyPrice.setBarCode(productBarCode);
			retPolicyPrice.setPromotionCode(PromotionType.OFF_5.getCode());
			retPolicyPrice.setTotalQuantity(quantity);
			retPolicyPrice.setBuyQuantity(quantity);
			retPolicyPrice.setSendQuantity(0);
			retPolicyPrice.setPolicyPrice(totalPrice);
			retPolicyPrice.setBasicPrice(basicPrice);
			retPolicyPrice.setSavePrice(savePrice);
		}
		return retPolicyPrice;
	}
}
