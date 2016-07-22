package cn.zmvision.ccm.bank.netty.event.handler.impl;

import cn.zmvision.ccm.bank.netty.domain.req.Request;
import cn.zmvision.ccm.bank.netty.domain.resp.Response;
import cn.zmvision.ccm.bank.netty.event.Event;
import cn.zmvision.ccm.bank.netty.event.handler.EventHandler;

@Event({0})
public class EchoEventHandler
  implements EventHandler
{
  public Response handle(Request msg)
  {
    return new Response();
  }
}