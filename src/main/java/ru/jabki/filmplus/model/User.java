package ru.jabki.filmplus.model;

import java.time.LocalDate;

public class User {

    private Long id;
    private String name;
    private String email;
    private String login;
    private LocalDate birthday;

    public User(final Long id, final String name, final String email, final String login, LocalDate birthday) {
        this.id  = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getLogin() {
        return this.login;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
