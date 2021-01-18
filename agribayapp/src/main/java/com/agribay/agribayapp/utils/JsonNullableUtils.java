package com.agribay.agribayapp.utils;

import java.util.function.Consumer;

import org.openapitools.jackson.nullable.JsonNullable;

public final class JsonNullableUtils {
	private JsonNullableUtils() {
	}

	public static <T> void changeIfPresent(JsonNullable<T> nullable, Consumer<T> consumer) {
		if (nullable.isPresent()) {
			consumer.accept(nullable.get());
		}
	}
}
