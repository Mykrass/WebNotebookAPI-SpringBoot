package com.al3xkras.web_notebook_api.user_service.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
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
    WRITE_SELF_PROFILE,

    ACCESS_ALL_USERS,
    BAN_USER,
    UNBAN_USER,
    SEND_WARNING_MESSAGES,

    BAN_ADMIN,
    UNBAN_ADMIN,
    DELETE_ANY_USER,
    PERFORM_DATA_ROLLBACK;
    @Override
    public String getAuthority() {
        return this.name();
    }
}
