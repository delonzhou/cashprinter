/**
 * 
 */
package com.zhsh.cashprinter.common;

/**
 * 优惠类型
 * @author zhangsheng
 *
 */
public enum PromotionType {
		// 无优惠
		NONE("NONE", "无优惠"),
		// 买二赠一
		TWO_SEND_ONE("TWO_SEND_ONE", "买二赠一"),
		// 95折
		OFF_5("OFF_5", "95折");

		private String code;
		private String desc;

		private PromotionType(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
		
		
}
