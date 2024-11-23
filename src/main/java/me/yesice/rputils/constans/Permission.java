package me.yesice.rputils.constans;

public enum Permission {
    SPAWN_HORSE_BYPASS("horse.spawn.bypass");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String permission() {
        return permission;
    }
}
