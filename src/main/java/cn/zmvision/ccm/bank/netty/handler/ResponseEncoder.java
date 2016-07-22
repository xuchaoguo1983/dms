package cn.zmvision.ccm.bank.netty.handler;

import cn.zmvision.ccm.bank.netty.domain.resp.Response;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseEncoder extends MessageToByteEncoder<Response> {
	protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out)
			throws Exception {
		ByteBuf buf = msg.toByteBuf();
		out.writeInt(ByteBufUtil.swapInt(buf.readableBytes()));
		out.writeBytes(buf);
	}
}