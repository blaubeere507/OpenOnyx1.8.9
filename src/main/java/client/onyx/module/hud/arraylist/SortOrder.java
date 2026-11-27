package client.onyx.module.hud.arraylist;

import client.onyx.setting.NamedEnum;

public enum SortOrder implements NamedEnum {
    WIDTH_DESC("Longest first"),
    WIDTH_ASC("Shortest first"),
    ALPHABETICAL("A-Z");

    private final String string;

    private SortOrder(String var3) {
        this.string = var3;
    }

    @Override
    public String getDisplayName() {
        return this.string;
    }
}
