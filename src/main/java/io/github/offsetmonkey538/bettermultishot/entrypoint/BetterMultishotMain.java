package io.github.offsetmonkey538.bettermultishot.entrypoint;

import io.github.offsetmonkey538.bettermultishot.config.BetterMultishotConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.offsetmonkey538.monkeylib538.config.ConfigManager;

public class BetterMultishotMain implements ModInitializer {
	public static final String MOD_ID = "bettermultishot";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static BetterMultishotConfig config;

	@Override
	public void onInitialize() {
		config = ConfigManager.init(new BetterMultishotConfig(), LOGGER::error);
	}
}
