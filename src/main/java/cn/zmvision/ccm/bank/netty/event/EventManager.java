package cn.zmvision.ccm.bank.netty.event;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.zmvision.ccm.bank.netty.domain.req.Request;
import cn.zmvision.ccm.bank.netty.domain.resp.Response;
import cn.zmvision.ccm.bank.netty.event.handler.EventHandler;

@Component
public class EventManager {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Map<Integer, Map<Integer, EventHandler>> handlers = new HashMap<>();

	@PostConstruct
	public void init() throws InstantiationException, IllegalAccessException {
		Reflections reflections = new Reflections(getClass().getPackage()
				.getName());

		Set<Class<? extends EventHandler>> allClasses = reflections
				.getSubTypesOf(EventHandler.class);

		for (Class<? extends EventHandler> clazz : allClasses) {
			Annotation[] annotations = clazz.getAnnotations();

			for (Annotation annotation : annotations)
				if ((annotation instanceof Event)) {
					EventHandler handler = clazz.newInstance();
					Event event = (Event) annotation;
					int version = event.version();

					Map<Integer, EventHandler> map = handlers.get(version);
					if (map == null) {
						map = new HashMap<>();
						handlers.put(version, map);
					}

					int[] values = event.value();
					for (int code : values) {
						map.put(Integer.valueOf(code), handler);

						logger.info("bind packet handler = {} to event = {}",
								clazz.toString(), Integer.valueOf(code));
					}
				}
		}
	}

	public void handle(ChannelHandlerContext ctx, Request request) {
		logger.info("handle packet event = {}",
				Integer.valueOf(request.getEvent()));

		Map<Integer, EventHandler> map = handlers.get(Integer.valueOf(request
				.getVersion()));
		if (map == null) {
			throw new RuntimeException("no event handler found with version="
					+ request.getVersion());
		}

		EventHandler handler = map.get(Integer.valueOf(request.getEvent()));
		if (handler == null) {
			throw new RuntimeException("no event handler found with event="
					+ request.getEvent());
		}

		Response response = handler.handle(request);

		ChannelFuture f = ctx.writeAndFlush(response);
		f.addListener(ChannelFutureListener.CLOSE);
	}
}