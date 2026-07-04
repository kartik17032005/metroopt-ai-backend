package com.microservice.kochimetro.auth.user.entity.enums;

public enum Role {
    ADMIN, //full access to the system
    OPERATOR, //view trains, view schedules, etc
    ENGINEER // maintenance staff( view trains, create/update job cards etc, cannot delete anything)
}
