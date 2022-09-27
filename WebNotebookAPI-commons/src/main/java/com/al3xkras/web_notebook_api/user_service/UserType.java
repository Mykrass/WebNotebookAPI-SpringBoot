package com.al3xkras.web_notebook_api.user_service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.al3xkras.web_notebook_api.user_service.UserAuthority.*;

public enum UserType {
    USER(Arrays.asList()),
    ADMIN(Arrays.asList());
    private static List<UserAuthority> userAuthorities=Arrays.asList(
            LOGIN,
            READ_SELF_NOTES,
            WRITE_SELF_NOTES,
            READ_SHARED_NOTES,
            CREATE_NOTES,
            PIN_NOTES,
            ARCHIVE_NOTES,
            DELETE_NOTES,
            REFRESH_ACCESS_TOKENS,
            SHARE_NOTES,
            READ_SELF_SETTINGS,
            WRITE_SELF_SETTINGS,
            READ_SELF_PROFILE,
            WRITE_SELF_PROFILE
    );
    private static List<UserAuthority> adminAuthorities=Arrays.asList(
            ACCESS_ALL_USERS,
            BAN_USER,
            UNBAN_USER,
            SEND_WARNING_MESSAGES
    );
    private static List<UserAuthority> systemAuthorities=Arrays.asList(
            BAN_ADMIN,
            UNBAN_ADMIN,
            DELETE_ANY_USER,
            PERFORM_DATA_ROLLBACK
    );
    static {
        adminAuthorities.addAll(userAuthorities);
        systemAuthorities.addAll(adminAuthorities);
    }
    private final Collection<UserAuthority> authorities;
    UserType(Collection<UserAuthority> authorities){
        this.authorities=authorities;
    }
    public Collection<UserAuthority> authorities() {
        return Collections.unmodifiableCollection(authorities);
    }
}
