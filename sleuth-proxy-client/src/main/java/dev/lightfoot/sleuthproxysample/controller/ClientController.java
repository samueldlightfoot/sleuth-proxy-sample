package dev.lightfoot.sleuthproxysample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ClientController {

	private final WebClient webClient;
	private final CallClient callClient;

	public ClientController(WebClient webClient) {
		this.webClient = webClient;
		this.callClient = new ClientProxy<>(CallClient.class).newInstance(webClient);
	}

	@GetMapping("/callServerNonProxy")
	public Mono<Boolean> callServerNonProxy() {
		log.debug("Received request to call server (non-proxy)");
		return callViaWebClient()
			.then(callViaWebClient());
	}

	private Mono<Boolean> callViaWebClient() {
		return webClient.get()
			.uri("getBoolean")
			.retrieve()
			.bodyToMono(Boolean.class);
	}

	@GetMapping("/callServerProxy")
	public Mono<Boolean> callServerProxy() {
		log.debug("Received request to call server (proxy)");
		return callClient.call()
			.doOnNext(i -> {
				// Results show this will have a new trace id, but still not re-used
				callClient.call().subscribe();
			})
			.then(callClient.call());
	}

}
