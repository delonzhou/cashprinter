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
 * 买二送一计价方式
 * @author zhangsheng
 *
 */
public class SendProductPricePolicy implements IPricePolicy{
	
	private CashPrinterContext ctx;

	public void setCashPrinterContext(CashPrinterContext ctx) throws CashPrinterRuntimeException {
		this.ctx = ctx;
	}

	public PolicyPrice calculatePrice(String productBarCode, int quantity) throws CashPrinterRuntimeException {
		PolicyPrice retPolicyPrice = new PolicyPrice();
		BigDecimal totalPrice = null;
		Product pdt = ctx.getProductByBarCode(productBarCode);
		if(pdt == null) {
			throw new CashPrinterRuntimeException("商品条形码不存在，barCode:" + productBarCode);
		}else {
			BigDecimal price = pdt.getPrice();
			//默认按买二送一实现
			int buyQuantity = 0;//购买的数量
			int sendQuantity = 0;//赠送的数量
			buyQuantity = quantity/3 * 2 + quantity%3;
			sendQuantity = quantity - buyQuantity;
			totalPrice = price.multiply(new BigDecimal(buyQuantity));
			BigDecimal basicPrice = price.multiply(new BigDecimal(quantity));
			BigDecimal savePrice = basicPrice.subtract(totalPrice);
			
			retPolicyPrice.setBarCode(productBarCode);
			retPolicyPrice.setPromotionCode(PromotionType.TWO_SEND_ONE.getCode());
			retPolicyPrice.setTotalQuantity(quantity);
			retPolicyPrice.setBuyQuantity(buyQuantity);
			retPolicyPrice.setSendQuantity(sendQuantity);
			retPolicyPrice.setPolicyPrice(totalPrice);
			retPolicyPrice.setBasicPrice(basicPrice);
			retPolicyPrice.setSavePrice(savePrice);
		}
		return retPolicyPrice;
	}
}
