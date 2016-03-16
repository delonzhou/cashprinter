/**
 * 
 */
package com.zhsh.cashprinter;

import com.zhsh.cashprinter.common.IPricePolicy;
import com.zhsh.cashprinter.common.PromotionType;

/**
 * @author zhangsheng
 *
 */
public class PricePolicyFactory {
	public static IPricePolicy createPricePolicy(PromotionType promotionType) {
		if(promotionType == PromotionType.TWO_SEND_ONE) {
			return new SendProductPricePolicy();
		}else if(promotionType == PromotionType.OFF_5) {
			return new OFF5PricePolicy();
		}else {
			return new BasicPricePolicy();
		}
	}
}
