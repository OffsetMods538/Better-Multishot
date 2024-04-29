package top.offsetmonkey538.bettermultishot;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

public class BetterMultishot implements ModInitializer {
	public static final String MOD_ID = "bettermultishot";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {

	}

	public static <T> T load(Class<T> clazz) {
		final T loaded = ServiceLoader.load(clazz)
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Failed to load service for " + clazz.getName()));
		LOGGER.debug("Loaded '{}' for service '{}'!", loaded, clazz);
		return loaded;
	}
}
