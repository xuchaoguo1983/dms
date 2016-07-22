package cn.zmvision.ccm.bank.netty.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.TYPE })
public @interface Event {
	// 事件协议版本
	public abstract int version() default 0;

	// 事件编号
	public abstract int[] value();
}
