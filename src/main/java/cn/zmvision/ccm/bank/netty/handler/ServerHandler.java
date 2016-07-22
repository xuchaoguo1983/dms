package cn.zmvision.ccm.bank.netty.handler;

import cn.zmvision.ccm.bank.netty.domain.req.Request;
import cn.zmvision.ccm.bank.netty.event.EventManager;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Autowired
	EventManager eventManager;

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if ((msg != null) && ((msg instanceof Request)))
			eventManager.handle(ctx, (Request) msg);
		else
			super.channelRead(ctx, msg);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		super.exceptionCaught(ctx, cause);
	}
}