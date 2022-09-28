package com.al3xkras.web_notebook_api.user_service.model;

import java.util.*;

import static com.al3xkras.web_notebook_api.user_service.model.UserAuthority.*;

public enum UserType {
    USER(Authorities.userAuthorities),
    ADMIN(Authorities.adminAuthorities),
    SYSTEM(Authorities.systemAuthorities);
    private final Collection<UserAuthority> authorities;
    UserType(Collection<UserAuthority> authorities){
        this.authorities=authorities;
    }
    public Collection<UserAuthority> authorities() {
        return Collections.unmodifiableCollection(authorities);
    }
    private static final class Authorities{
        private static final List<UserAuthority> userAuthorities;
        private static final List<UserAuthority> adminAuthorities;
        private static final List<UserAuthority> systemAuthorities;
        static {
            List<UserAuthority> userAuth=new ArrayList<>(Arrays.asList(
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
            ));
            List<UserAuthority> adminAuth=new ArrayList<>(Arrays.asList(
                    ACCESS_ALL_USERS,
                    BAN_USER,
                    UNBAN_USER,
                    SEND_WARNING_MESSAGES
            ));
            List<UserAuthority> systemAuth=new ArrayList<>(Arrays.asList(
                    BAN_ADMIN,
                    UNBAN_ADMIN,
                    DELETE_ANY_USER,
                    PERFORM_DATA_ROLLBACK
            ));
            adminAuth.addAll(userAuth);
            systemAuth.addAll(adminAuth);
            userAuthorities=Collections.unmodifiableList(userAuth);
            adminAuthorities=Collections.unmodifiableList(adminAuth);
            systemAuthorities=Collections.unmodifiableList(systemAuth);
        }
    }
}
