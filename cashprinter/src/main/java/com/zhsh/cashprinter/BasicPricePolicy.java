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
 * 基本的计价方式：也就是没有优惠的计价方式
 * @author zhangsheng
 *
 */
public class BasicPricePolicy implements IPricePolicy{

	private CashPrinterContext ctx;
	
	public PolicyPrice calculatePrice(String productBarCode, int quantity) throws CashPrinterRuntimeException {
		PolicyPrice retPolicyPrice = new PolicyPrice();
		BigDecimal totalPrice = null;
		Product pdt = ctx.getProductByBarCode(productBarCode);
		if(pdt == null) {
			throw new CashPrinterRuntimeException("商品条形码不存在，barCode:" + productBarCode);
		}else {
			BigDecimal price = pdt.getPrice();
			totalPrice = price.multiply(new BigDecimal(String.valueOf(quantity)));
			
			retPolicyPrice.setBarCode(productBarCode);
			retPolicyPrice.setPromotionCode(PromotionType.NONE.getCode());
			retPolicyPrice.setTotalQuantity(quantity);
			retPolicyPrice.setBuyQuantity(quantity);
			retPolicyPrice.setSendQuantity(0);
			retPolicyPrice.setPolicyPrice(totalPrice);
			retPolicyPrice.setBasicPrice(totalPrice);
			retPolicyPrice.setSavePrice(new BigDecimal(0));
		}
		return retPolicyPrice;
	}

	public void setCashPrinterContext(CashPrinterContext ctx) {
		this.ctx = ctx;
	}

}
