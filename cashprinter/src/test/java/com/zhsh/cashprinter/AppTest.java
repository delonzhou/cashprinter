package com.zhsh.cashprinter;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.zhsh.cashprinter.bo.Product;
import com.zhsh.cashprinter.common.ICashPrinter;
import com.zhsh.cashprinter.common.PromotionType;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	private ICashPrinter cashPrinter = null;
	private CashPrinterContext ctx = null;
	@Before
	public void setUp() throws Exception {
		ctx = new CashPrinterContext();
		cashPrinter = new CashPrinterImpl();
		ctx.init();
		cashPrinter.setCashPrinterContext(ctx);
		initTestData();
	}
	
	/**
	 * 创建测试数据
	 */
	private void initTestData() {
		Product p1 = new Product();
		p1.setName("可口可乐");
		p1.setBarCode("ITEM000001");
		p1.setNumUnit("瓶");
		p1.setPrice(new BigDecimal("3.00"));
		ctx.addProduct(p1);
		//95折
		ctx.addPromotionProduct(p1.getBarCode(), PromotionType.OFF_5.getCode());
		
		
		Product p2 = new Product();
		p2.setName("羽毛球");
		p2.setBarCode("ITEM000002");
		p2.setNumUnit("个");
		p2.setPrice(new BigDecimal("1.00"));
		ctx.addProduct(p2);
		//买二赠一
		ctx.addPromotionProduct(p2.getBarCode(), PromotionType.TWO_SEND_ONE.getCode());
		
		Product p3 = new Product();
		p3.setName("苹果");
		p3.setBarCode("ITEM000003");
		p3.setNumUnit("斤");
		p3.setPrice(new BigDecimal("5.50"));
		ctx.addProduct(p3);
		//95折
		ctx.addPromotionProduct(p3.getBarCode(), PromotionType.OFF_5.getCode());
		
		Product p4 = new Product();
		p4.setName("篮球");
		p4.setBarCode("ITEM000004");
		p4.setNumUnit("个");
		p4.setPrice(new BigDecimal("102.00"));
		ctx.addProduct(p4);
		
	}

	@Test
	public void testPrintDetail() {
		String shoppingListJson = "['ITEM000001','ITEM000001','ITEM000001', 'ITEM000002-5', 'ITEM000003', 'ITEM000002', 'ITEM000003', 'ITEM000002', 'ITEM000004']";
        cashPrinter.printDetail(shoppingListJson);
	}
}
