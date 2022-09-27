package com.al3xkras.web_notebook_api.user_service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public enum UserType {
    USER(Arrays.asList()),
    ADMIN(Arrays.asList());
    private final Collection<UserAuthority> authorities;
    UserType(Collection<UserAuthority> authorities){
        this.authorities=authorities;
    }
    public Collection<UserAuthority> authorities() {
        return Collections.unmodifiableCollection(authorities);
    }
}
