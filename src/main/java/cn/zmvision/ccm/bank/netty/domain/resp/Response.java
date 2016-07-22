package cn.zmvision.ccm.bank.netty.domain.resp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.Serializable;
import java.nio.ByteOrder;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;

	public ByteBuf toByteBuf() {
		ByteBuf msg = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
		toByteBuf0(msg);
		return msg;
	}

	protected void toByteBuf0(ByteBuf msg) {
		msg.writeByte(0);
	}
}
