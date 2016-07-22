package cn.zmvision.ccm.bank.netty;

import io.netty.channel.nio.NioEventLoopGroup;
import java.net.InetSocketAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TcpServerConfig {

	@Value("${netty.boss.thread.count}")
	private int bossCount;

	@Value("${netty.worker.thread.count}")
	private int workerCount;

	@Value("${netty.tcp.port}")
	private int tcpPort;

	@Bean(name = { "bossGroup" })
	public NioEventLoopGroup bossGroup() {
		return new NioEventLoopGroup(this.bossCount);
	}

	@Bean(name = { "workerGroup" })
	public NioEventLoopGroup workerGroup() {
		return new NioEventLoopGroup(this.workerCount);
	}

	@Bean(name = { "tcpSocketAddress" })
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(this.tcpPort);
	}
}