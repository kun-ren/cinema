package com.movie.api.constant;

import java.util.List;

/**
 * Role constants
 * Spring Security requires the "ROLE_" prefix
 */
public final class Roles {

    //Administrator
    public final static String ROLE_ADMIN = "ROLE_ADMIN";

    //Customer
    public final static String ROLE_USER = "ROLE_USER";

    //Worker
    public final static String ROLE_WORKER = "ROLE_WORKER";

    //Find user
    public final static String ROLE_FIND_USER = "ROLE_FIND_USER";

    //Add film
    public final static String ROLE_ADD_FILM = "ROLE_ADD_FILM";

    //Add screening
    public final static String ROLE_ADD_ARRANGEMENT = "ROLE_ADD_ARRANGEMENT";

    //Delete film
    public final static String ROLE_DELETE_FILM = "ROLE_DELETE_FILM";

    public final static String[] roles = {
            ROLE_FIND_USER,
            ROLE_ADD_FILM,
            ROLE_DELETE_FILM,
            ROLE_ADD_ARRANGEMENT,
    };

}

