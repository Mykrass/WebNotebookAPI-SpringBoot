package com.al3xkras.web_notebook_api.user_service.model;

import com.al3xkras.web_notebook_api.notebook_service.entity.Note;
import com.al3xkras.web_notebook_api.user_service.entity.User;

public final class TestEntities {
    //authorized with Google
    //has notes
    //has pinned notes
    //has archived notes
    public static User user1 = User.builder()
            .userId(1L)
            .userType(UserType.USER)
            .email("user1@gmail.com")
            .username("user1")
            .password(null)
            .detailsProvider(UserDetailsProvider.GOOGLE)
            .build();
    //authorized locally
    //has notes
    //has pinned notes
    //does not have archived notes
    public static User user2 = User.builder()
            .userId(2L)
            .userType(UserType.USER)
            .email("user2@gmail.com")
            .username("user2")
            .password("Password123.")
            .detailsProvider(UserDetailsProvider.LOCAL)
            .build();
    //authorized with Google
    //has notes
    //does not have pinned notes
    //has archived notes
    public static User user3 = User.builder()
            .userId(3L)
            .userType(UserType.USER)
            .email("user3@gmail.com")
            .username("user3")
            .password(null)
            .detailsProvider(UserDetailsProvider.GOOGLE)
            .build();
    //authorized locally
    //does not have notes
    public static User user4 = User.builder()
            .userId(4L)
            .userType(UserType.USER)
            .email("user4@gmail.com")
            .username("user4")
            .password("Password123.")
            .detailsProvider(UserDetailsProvider.LOCAL)
            .build();
    //authorized with Google
    //banned
    public static User user5 = User.builder()
            .userId(5L)
            .userType(UserType.USER)
            .email("user5@gmail.com")
            .username("user5")
            .password(null)
            .detailsProvider(UserDetailsProvider.GOOGLE)
            .build();
    //authorized locally
    //banned
    public static User user6 = User.builder()
            .userId(6L)
            .userType(UserType.USER)
            .email("user6@gmail.com")
            .username("user6")
            .password("Password123.")
            .detailsProvider(UserDetailsProvider.LOCAL)
            .build();

    //not banned
    public static User admin1  = User.builder()
            .userId(-1L)
            .userType(UserType.ADMIN)
            .email("admin1@gmail.com")
            .username("admin1")
            .password("P234w123.")
            .detailsProvider(UserDetailsProvider.LOCAL)
            .build();
    //banned
    public static User admin2 = User.builder()
            .userId(-2L)
            .userType(UserType.ADMIN)
            .email("admin2@gmail.com")
            .username("admin2")
            .password("P234w567.")
            .detailsProvider(UserDetailsProvider.LOCAL)
            .build();

    public static User userWithExistingUsername=User.builder()
            .userId(null)
            .userType(UserType.USER)
            .email("user142@gmail.com")
            .username("user1")
            .password(null)
            .detailsProvider(UserDetailsProvider.GOOGLE)
            .build();
    public static User userWithExistingEmail=User.builder()
            .userId(null)
            .userType(UserType.USER)
            .email("user1@gmail.com")
            .username("user1234")
            .password(null)
            .detailsProvider(UserDetailsProvider.GOOGLE)
            .build();

    public static Note note1_user1_archived;
    public static Note note2_user1;
    public static Note note3_user1_pinned;
    public static Note note4_user1;
    public static Note note1_user2_pinned;
    public static Note note2_user2;
    public static Note note1_user3;
    public static Note note2_user3_archived;
    public static Note note3_user3;
    public static Note note4_user3_archived;
    public static Note note5_user3_archived;
    public static Note note6_user3;

    public static Note note5_user3_unarchived;
    public static Note note1_user2_archived;
    public static Note note2_user1_archived;

    public static User user5_unbanned;
}
