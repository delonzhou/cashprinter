/**
 * 
 */
package com.zhsh.cashprinter.common;

import com.zhsh.cashprinter.exception.CashPrinterRuntimeException;

/**
 * 
 * 打印结算清单接口
 * @author zhangsheng
 *
 */
public interface ICashPrinter extends CashPrinterContextAware{
	
	public void printDetail(String shoppingListJson) throws CashPrinterRuntimeException;

}
