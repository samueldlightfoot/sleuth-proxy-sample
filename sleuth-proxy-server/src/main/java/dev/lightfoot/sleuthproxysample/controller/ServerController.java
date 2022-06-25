package dev.lightfoot.sleuthproxysample.controller;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ServerController {

	@GetMapping("/getBoolean")
	public Mono<Boolean> getBoolean() {
		log.debug("Received request to getBoolean");
		return Mono.just(true).delayElement(Duration.ofMillis(500));
	}

}
