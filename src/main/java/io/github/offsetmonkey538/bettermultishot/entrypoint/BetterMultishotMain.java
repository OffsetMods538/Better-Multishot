package io.github.offsetmonkey538.bettermultishot.entrypoint;

import io.github.offsetmonkey538.bettermultishot.config.BetterMultishotConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterMultishotMain implements ModInitializer {
	public static final String MOD_ID = "bettermultishot";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		new BetterMultishotConfig().init();
	}
}
