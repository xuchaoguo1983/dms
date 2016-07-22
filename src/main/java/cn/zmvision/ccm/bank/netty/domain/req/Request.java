package cn.zmvision.ccm.bank.netty.domain.req;

import io.netty.buffer.ByteBuf;

import java.io.Serializable;
import java.nio.charset.Charset;

import lombok.Getter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

@Getter
public class Request implements Serializable {
	private static final long serialVersionUID = 1L;
	private int version;
	private int event;
	private String payload;

	public Request(ByteBuf msg) throws Exception {
		this.payload = msg.toString(Charset.forName("utf-8"));
		Document document = DocumentHelper.parseText(this.payload);

		Element root = document.getRootElement();
		this.version = Integer.parseInt(root.element("version").getTextTrim());
		this.event = Integer.parseInt(root.element("event").getTextTrim());

		init(root);
	}

	protected void init(Element root) {
	}

}
