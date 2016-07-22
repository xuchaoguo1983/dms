package cn.zmvision.ccm.bank.netty.handler;

import cn.zmvision.ccm.bank.netty.domain.req.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestDecoder extends ByteToMessageDecoder {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final byte[] PACKET_HEAD_FLAG = "ZMKJ".getBytes();

	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		if (in.readableBytes() < 8) {
			return;
		}

		in.markReaderIndex();

		byte[] headFlag = new byte[4];
		in.readBytes(headFlag);

		if (!Arrays.equals(PACKET_HEAD_FLAG, headFlag)) {
			this.logger.error("invalid msg head flag:{}", headFlag);
			ctx.close();
			return;
		}

		int len = in.order(ByteOrder.LITTLE_ENDIAN).readInt();

		if (len > in.readableBytes()) {
			this.logger
					.debug("wait for more bytes to read, required length={}, actual length={}",
							Integer.valueOf(len),
							Integer.valueOf(in.readableBytes()));
			in.resetReaderIndex();
			return;
		}
		try {
			Request request = new Request(in);
			out.add(request);
		} catch (Exception e) {
			this.logger.error("parse request packet failed:{}", e);
			ctx.close();
		}
	}
}