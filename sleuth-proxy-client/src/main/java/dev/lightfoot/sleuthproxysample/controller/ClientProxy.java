package dev.lightfoot.sleuthproxysample.controller;

import com.google.common.reflect.Reflection;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class ClientProxy<T> {

	private final Class<T> clientInterface;

	public T newInstance(WebClient webClient) {
		return Reflection.newProxy(clientInterface, new UrlInvocationHandler(webClient));
	}

	private static class UrlInvocationHandler implements InvocationHandler {

		private final WebClient webClient;

		public UrlInvocationHandler(WebClient webClient) {
			this.webClient = webClient;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			return webClient.get().uri("getBoolean")
				.retrieve()
				.bodyToMono(Boolean.class);
		}
	}


}
