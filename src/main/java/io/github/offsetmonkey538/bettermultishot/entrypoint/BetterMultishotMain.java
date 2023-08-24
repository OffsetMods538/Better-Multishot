package io.github.offsetmonkey538.bettermultishot.entrypoint;

import io.github.offsetmonkey538.bettermultishot.config.BetterMultishotConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.offsetmonkey538.monkeyconfig538.ConfigManager;

public class BetterMultishotMain implements ModInitializer {
	public static final String MOD_ID = "bettermultishot";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ConfigManager.init(new BetterMultishotConfig(), MOD_ID);
	}

	public static BetterMultishotConfig config() {
		return (BetterMultishotConfig) ConfigManager.get(MOD_ID);
	}
}
