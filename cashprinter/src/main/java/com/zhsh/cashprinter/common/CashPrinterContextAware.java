/**
 * 
 */
package com.zhsh.cashprinter.common;

import com.zhsh.cashprinter.CashPrinterContext;
import com.zhsh.cashprinter.exception.CashPrinterRuntimeException;

/**
 * 用于注入ctx
 * @author zhangsheng
 *
 */
public interface CashPrinterContextAware {
	
	void setCashPrinterContext(CashPrinterContext ctx) throws CashPrinterRuntimeException;

}
