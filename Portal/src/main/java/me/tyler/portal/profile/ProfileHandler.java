package me.tyler.portal.profile;

import me.tyler.portal.Portal;

import java.util.HashSet;
import java.util.Set;

public class ProfileHandler {

    private final Portal portal;
    private final Set<PortalProfile> profiles;

    public ProfileHandler(Portal portal) {
        this.portal = portal;
        this.profiles = new HashSet<PortalProfile>();
    }
}
