package cn.zmvision.ccm.bank.netty.event.handler;

import cn.zmvision.ccm.bank.netty.domain.req.Request;
import cn.zmvision.ccm.bank.netty.domain.resp.Response;

public interface EventHandler {
	public Response handle(Request paramRequest);
}
