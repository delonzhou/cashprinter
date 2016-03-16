/**
 * 
 */
package com.zhsh.cashprinter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhsh.cashprinter.bo.Product;
import com.zhsh.cashprinter.bo.PromotionProduct;
import com.zhsh.cashprinter.common.IPricePolicy;
import com.zhsh.cashprinter.common.PromotionType;

/**
 * 存储商品相关信息
 * @author zhangsheng
 *
 */
public class CashPrinterContext {
	
	private static final Logger logger = LoggerFactory.getLogger(CashPrinterContext.class);
	
	private Map<String, Product> productMap = new ConcurrentHashMap<String, Product>();
	private Map<String, PromotionProduct> promotionProductMap = new ConcurrentHashMap<String, PromotionProduct>();
	//计价方式map
	private Map<String, IPricePolicy> pricePolicyMap = new HashMap<String, IPricePolicy>();
	
	public void init() {
		//计价方式初始化
		initPrivePolicy();
	}
	
	private void initPrivePolicy() {
		IPricePolicy off5PricePolicy = PricePolicyFactory.createPricePolicy(PromotionType.OFF_5);
		IPricePolicy sendPricePolicy = PricePolicyFactory.createPricePolicy(PromotionType.TWO_SEND_ONE);
		IPricePolicy basicPricePolicy = PricePolicyFactory.createPricePolicy(PromotionType.NONE);
		pricePolicyMap.put(PromotionType.NONE.getCode(), basicPricePolicy);
		pricePolicyMap.put(PromotionType.TWO_SEND_ONE.getCode(), sendPricePolicy);
		pricePolicyMap.put(PromotionType.OFF_5.getCode(), off5PricePolicy);
		off5PricePolicy.setCashPrinterContext(this);
		sendPricePolicy.setCashPrinterContext(this);
		basicPricePolicy.setCashPrinterContext(this);
	}
	
	/**
	 * 通过条形码获取商品
	 * @param barCode
	 * @return
	 */
	public Product getProductByBarCode(String barCode) {
		return productMap.get(barCode);
	}
	
	/**
	 * 根据条形码获取优惠信息
	 * @param barCode
	 * @return
	 */
	public String getProductPromotionCode(String barCode) {
		if(promotionProductMap.get(barCode) == null) {
			return PromotionType.NONE.getCode();//没有优惠信息
		}else {
			return promotionProductMap.get(barCode).getPmtTypeCode();
		}
	}
	
	/**
	 * 增加商品
	 * @param pdt
	 */
	public void addProduct(Product pdt) {
		if(pdt != null) {
			productMap.put(pdt.getBarCode(), pdt);
		}
	}
	
	/**
	 * 增加商品的优惠信息
	 * @param pdtBarCode 商品条形码
	 * @param promotionCode 优惠码
	 */
	public void addPromotionProduct(String pdtBarCode, String promotionCode) {
		if(StringUtils.isNotEmpty(pdtBarCode)) {
			if(productMap.get(pdtBarCode) != null) {
				if(StringUtils.isNotEmpty(promotionCode)) {
					PromotionProduct pp = new PromotionProduct();
					pp.setPdtBarCode(pdtBarCode);
					pp.setPmtTypeCode(promotionCode);//TODO 检查是否是存在的优惠码
					promotionProductMap.put(pdtBarCode, pp);
				}else {
					logger.error("promotionCode is empty.");
				}
			}else {
				logger.error("商品条形码不存在.");
			}
		}else {
			logger.info("pdtBarCode is empty.");
		}
	}
	
	/**
	 * 根据优惠码获取对应的计价方式
	 * @param promotionCode
	 * @return
	 */
	public IPricePolicy getPricePolicy(String promotionCode) {
		if(pricePolicyMap.get(promotionCode) == null) {
			return pricePolicyMap.get(PromotionType.NONE.getCode());
		}else {
			return pricePolicyMap.get(promotionCode);
		}
	}

}
