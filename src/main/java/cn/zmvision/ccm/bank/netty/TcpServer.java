package cn.zmvision.ccm.bank.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cn.zmvision.ccm.bank.netty.handler.RequestDecoder;
import cn.zmvision.ccm.bank.netty.handler.ResponseEncoder;
import cn.zmvision.ccm.bank.netty.handler.ServerHandler;

@Component
public class TcpServer {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("tcpSocketAddress")
	private InetSocketAddress tcpPort;

	@Autowired
	@Qualifier("bossGroup")
	private NioEventLoopGroup bossGroup;

	@Autowired
	@Qualifier("workerGroup")
	private NioEventLoopGroup workerGroup;

	@Autowired
	ServerHandler serverHandler;

	private Channel serverChannel;

	@PostConstruct
	public void start() throws InterruptedException {
		ServerBootstrap b = new ServerBootstrap();

		b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						ChannelPipeline p = ch.pipeline();
						p.addLast("decoder", new RequestDecoder());
						p.addLast("encoder", new ResponseEncoder());
						p.addLast(serverHandler);
					}

				}).option(ChannelOption.SO_BACKLOG, Integer.valueOf(128))
				.option(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(true));

		serverChannel = b.bind(tcpPort).sync().channel();

		logger.info("netty server started at:{}", tcpPort);
	}

	@PreDestroy
	public void stop() {
		logger.info("netty server stopped.");

		if (serverChannel != null) {
			serverChannel.close();
			serverChannel.parent().close();
			serverChannel = null;
		}
	}
}