/**
 * 
 */
package com.zhsh.cashprinter;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhsh.cashprinter.bo.PolicyPrice;
import com.zhsh.cashprinter.bo.Product;
import com.zhsh.cashprinter.common.ICashPrinter;
import com.zhsh.cashprinter.common.IPricePolicy;
import com.zhsh.cashprinter.common.JsonUtil;
import com.zhsh.cashprinter.common.PromotionType;
import com.zhsh.cashprinter.exception.CashPrinterRuntimeException;

/**
 * @author zhangsheng
 *
 */
public class CashPrinterImpl implements ICashPrinter {
	
	private static final Logger logger = LoggerFactory.getLogger(CashPrinterImpl.class);
	private DecimalFormat df=new DecimalFormat("0.00"); //格式化，保留两位小数 
	
	private CashPrinterContext ctx;
	
	//条形码与商品数量分隔符
	private static String ITEM_DELIMITER = "-";

	/* (non-Javadoc)
	 * @see com.zhsh.cashprinter.common.ICashPrinter#printDetail(java.lang.String)
	 */
	public void printDetail(String shoppingListJson) throws CashPrinterRuntimeException {
		//将json转成List
		try {
			List<String> shoppingList = JsonUtil.convertJsonToList(shoppingListJson, String.class);
			Map<String, Integer> productQuantityMap = new HashMap<String, Integer>();
			for (String item : shoppingList) {
				String barCode = "";
				Integer quantity = 0;
				int addQuantity = 0;
				if(item.contains(ITEM_DELIMITER)) {
					String[] items = item.split(ITEM_DELIMITER);
					barCode = items[0];
					addQuantity = Integer.valueOf(items[1]);
				}else {
					barCode = item;
					addQuantity = 1;
				}
				Integer originQuantity = productQuantityMap.get(barCode);
				if(originQuantity == null) {
					originQuantity = 0;
				}else {
					
				}
				quantity = originQuantity + addQuantity;
				logger.debug("item:" + item + "; barCode:" + barCode + "; originQuantity:" + originQuantity + "; addQuantity:" + addQuantity + "; quantity:" + quantity);
				productQuantityMap.put(barCode, quantity);
			}
			BigDecimal totalPrice = new BigDecimal(0);
			BigDecimal totalSavePrice = new BigDecimal(0);
			
			//保存买二送一的商品，赠送的数量
			Map<String, PolicyPrice> sendPolicyPriceMap = new HashMap<String, PolicyPrice>();
			System.out.println("***<没钱赚商店>购物清单***");
			for(Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
				String barCode = entry.getKey();
				Integer quantity = entry.getValue();
				Product product = ctx.getProductByBarCode(barCode);
				String promotionCode = ctx.getProductPromotionCode(barCode);
				
				BigDecimal subtotalPrice = new BigDecimal(0);
				BigDecimal savePrice = new BigDecimal(0);
				IPricePolicy pricePolicy = ctx.getPricePolicy(promotionCode);
				if(pricePolicy == null) {
					throw new CashPrinterRuntimeException("pricePolicy is null.");// TODO
				}
				PolicyPrice policyPrice = pricePolicy.calculatePrice(barCode, quantity);
				if(policyPrice != null) {
					subtotalPrice = policyPrice.getPolicyPrice();
				}else {
					throw new CashPrinterRuntimeException("policyPrice is null.");// TODO
				}
				
				if(promotionCode.equals(PromotionType.NONE.getCode())) {
					//没有优惠
					printBasicPricePolicyList(product, quantity, subtotalPrice);
				}else if(promotionCode.equals(PromotionType.OFF_5.getCode())) {
					savePrice = policyPrice.getSavePrice();
					printBasicPricePolicyList(product, quantity, subtotalPrice, savePrice);
				}else if(promotionCode.equals(PromotionType.TWO_SEND_ONE.getCode())) {
					printBasicPricePolicyList(product, quantity, subtotalPrice);
					sendPolicyPriceMap.put(barCode, policyPrice);
					savePrice = policyPrice.getSavePrice();
				}else {
					logger.warn("no such promotion...");
					return;
				}
				totalPrice = totalPrice.add(subtotalPrice);
				totalSavePrice = totalSavePrice.add(savePrice);
			}
			System.out.println("----------------------");
			if(sendPolicyPriceMap.size() > 0) {
				//打印赠送商品信息
				System.out.println("买二赠一商品：");
				for(Map.Entry<String, PolicyPrice> entry : sendPolicyPriceMap.entrySet()) {
					String barCode = entry.getKey();
					PolicyPrice policyPrice = entry.getValue();
					Product pdt = ctx.getProductByBarCode(barCode);
					System.out.println("名称：" + pdt.getName() + "，数量：" + policyPrice.getSendQuantity() + pdt.getNumUnit());
				}
				System.out.println("----------------------");
			}
			System.out.println("总计：" + df.format(totalPrice) + "(元)");
			if(totalSavePrice.compareTo(new BigDecimal(0)) == 1) {
				System.out.println("节省：" + df.format(totalSavePrice) + "(元)");
			}
			System.out.println("**********************");
		} catch (Exception e) {
			logger.error("结算清单打印失败：", e);
		}
	}
	
	
	private void printBasicPricePolicyList(Product pdt, Integer quantity, BigDecimal subtotalPrice,
			BigDecimal... savePrice) {
		System.out.println("名称：" + pdt.getName() + "，数量：" + quantity + pdt.getNumUnit() + "，单价：" + df.format(pdt.getPrice()) + "(元)，小计："
				+ df.format(subtotalPrice) + "(元)" + (savePrice != null && savePrice.length == 1 && savePrice[0].compareTo(new BigDecimal(0)) == 1 ? "，节省" + df.format(savePrice[0]) + "(元)" : ""));
	}

	public void setCashPrinterContext(CashPrinterContext ctx) throws CashPrinterRuntimeException {
		this.ctx = ctx;
	}

}
