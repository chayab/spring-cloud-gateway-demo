package com.chaya.gateway.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class LoggingGatewayFilterFactory extends AbstractGatewayFilterFactory<LoggingGatewayFilterFactory.Config> {

	public LoggingGatewayFilterFactory() {
		super(Config.class);
	}
	@Override
	public Config newConfig() {
		return new Config();
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("logPrefix", "logEntry", "logExit");
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			// Pre-processing
			if (config.isLogEntry()) {
				log.info("Pre GatewayFilter logging: {} | {}", config.getLogPrefix(), exchange.getRequest().getPath());
			}
			return chain.filter(exchange)
					.then(Mono.fromRunnable(() -> {
						// Post-processing
						if (config.isLogExit()) {
							log.info("Post GatewayFilter logging: {} | {}", config.getLogPrefix(), exchange.getRequest().getPath());
						}
					}));
		};
	}

	@Setter
	@Getter
	@NoArgsConstructor
	public static class Config {
		private String logPrefix;
		private boolean logEntry;
		private boolean logExit;
	}
}
