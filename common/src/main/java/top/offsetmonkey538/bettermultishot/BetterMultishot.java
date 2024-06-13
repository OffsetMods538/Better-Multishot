package top.offsetmonkey538.bettermultishot;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.offsetmonkey538.bettermultishot.config.ModConfig;
import top.offsetmonkey538.monkeylib538.config.ConfigManager;
import top.offsetmonkey538.monkeylib538.utils.IdentifierUtils;

import java.util.ServiceLoader;

public class BetterMultishot implements PreLaunchEntrypoint {
	public static final String MOD_ID = "bettermultishot";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ModConfig config;


	@Override
	public void onPreLaunch() {
		config = ConfigManager.init(new ModConfig(), LOGGER::error);
	}

	public static Identifier id(String path) {
		return IdentifierUtils.INSTANCE.of(MOD_ID, path);
	}

	public static <T> T load(Class<T> clazz) {
		final T loaded = ServiceLoader.load(clazz)
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Failed to load service for " + clazz.getName()));
		LOGGER.debug("Loaded '{}' for service '{}'!", loaded, clazz);
		return loaded;
	}
}
