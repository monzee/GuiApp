/*
 * This file is a part of the GuiApp project.
 * Copyright 2016, CODEIA Inc. All rights reserved.
 * Distribution, reproduction and creation of derivative works are prohibited
 * without prior consent from the rights holder.
 */
package ph.codeia.guiapp.backend.login;

import javax.inject.Inject;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
public class LoginState implements LoginContract.Model {
    private String username;
    private String usernameError;
    private String password;
    private String passwordError;

    @Inject
    public LoginState() {
    }

    @Override
    public void setError(LoginContract.Field field, String error) {
        switch (field) {
        case USERNAME:
            usernameError = error;
            break;
        case PASSWORD:
            passwordError = error;
            break;
        }
    }

    @Override
    public String getError(LoginContract.Field field) {
        switch (field) {
        case USERNAME:
            return usernameError;
        case PASSWORD:
            return passwordError;
        }
        return null;
    }

    @Override
    public String get(LoginContract.Field field) {
        switch (field) {
        case USERNAME:
            return username;
        case PASSWORD:
            return password;
        }
        return null;
    }

    @Override
    public <T> void set(LoginContract.Field field, T value) {
        switch (field) {
        case USERNAME:
            username = String.valueOf(value);
            break;
        case PASSWORD:
            password = String.valueOf(value);
            break;
        }
    }
}
