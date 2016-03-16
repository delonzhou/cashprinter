/**
 * 
 */
package com.zhsh.cashprinter.common;

import com.zhsh.cashprinter.bo.PolicyPrice;
import com.zhsh.cashprinter.exception.CashPrinterRuntimeException;

/**
 * 计价方式接口
 * @author zhangsheng
 *
 */
public interface IPricePolicy extends CashPrinterContextAware{
	
	/**
	 * 
	 * @param productBarCode 条形码
	 * @param quantity 数量
	 * @return 计算后金额PolicyPrice
	 * @throws CashPrinterRuntimeException
	 */
	public PolicyPrice calculatePrice(String productBarCode, int quantity) throws CashPrinterRuntimeException;

}
