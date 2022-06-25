package dev.lightfoot.sleuthproxysample.controller;

import reactor.core.publisher.Mono;

public interface CallClient {

	Mono<Boolean> call();

}
