package top.offsetmonkey538.bettermultishot.item;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import static top.offsetmonkey538.bettermultishot.BetterMultishot.id;

public final class ModItemTags {
    private ModItemTags() {

    }

    public static final TagKey<Item> MULTISHOT_ENCHANTABLE = of("enchantable/multishot");

    private static TagKey<Item> of(String name) {
        return TagKey.of(RegistryKeys.ITEM, id(name));
    }
}
