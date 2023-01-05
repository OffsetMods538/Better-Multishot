package io.github.offsetmonkey538.bettermultishot.config;

import com.kyanite.paragon.api.ConfigOption;
import com.kyanite.paragon.api.interfaces.Config;
import com.kyanite.paragon.api.interfaces.serializers.ConfigSerializer;
import com.kyanite.paragon.api.interfaces.serializers.JSON5Serializer;

public class BetterMultishotConfig implements Config {
    public static final ConfigOption<Boolean> NERF_BOW_MULTISHOT = new ConfigOption<>("nerf bow multishot", true);

    @Override
    public ConfigSerializer getSerializer() {
        return JSON5Serializer.builder(this).useDefaultJsonSuffix().build();
    }
}
