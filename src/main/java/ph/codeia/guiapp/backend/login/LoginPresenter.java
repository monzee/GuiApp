/*
 * This file is a part of the GuiApp project.
 * Copyright 2016, CODEIA Inc. All rights reserved.
 * Distribution, reproduction and creation of derivative works are prohibited
 * without prior consent from the rights holder.
 */
package ph.codeia.guiapp.backend.login;

import javax.inject.Inject;
import ph.codeia.guiapp.backend.login.LoginContract.Field;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
public class LoginPresenter implements LoginContract.Presenter, Callback<String> {
    private final LoginContract.Model model;
    private final LoginContract.Service service;
    private LoginContract.View view;
    private boolean pending = false;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.Service service) {
        this.model = model;
        this.service = service;
    }

    @Override
    public void tryLogin(String username, String password) {
        if (pending) {
            return;
        }
        if (isValid(username, password)) {
            model.set(Field.USERNAME, username);
            model.set(Field.PASSWORD, password);
            update("logging in...");
            service.login(username, password).enqueue(this);
            pending = true;
        } else {
            update(LoginContract.HAS_ERRORS);
        }
    }

    @Override
    public void bind(LoginContract.View view) {
        this.view = view;
        update();
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        pending = false;
        switch (response.code()) {
        case 200:
            didLogin(response.body());
            break;
        case 403:
            cantLogin(LoginContract.BAD_CREDENTIALS);
            break;
        case 404:
        case 410:
            cantLogin(LoginContract.GONE);
            break;
        default:
            cantLogin(LoginContract.UNKNOWN_RESPONSE);
            break;
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable error) {
        pending = false;
        cantLogin(error.getMessage());
    }

    private boolean isValid(String username, String password) {
        boolean valid = true;
        for (Field f : Field.values()) {
            model.setError(f, null);
        }
        if (username.isEmpty()) {
            model.setError(Field.USERNAME, LoginContract.IS_REQUIRED);
            valid = false;
        }
        if (password.isEmpty()) {
            model.setError(Field.PASSWORD, LoginContract.IS_REQUIRED);
            valid = false;
        }
        return valid;
    }

    private void update() {
        update(null);
    }

    private void update(String message) {
        if (view != null) {
            view.tell(message);
            view.show(model);
        }
    }

    private void didLogin(String token) {
        if (view != null) {
            view.tell("OK!");
            // store token
            // continue to gated screen
        }
    }

    private void cantLogin(String message) {
        if (view != null) {
            view.tell(message);
        }
    }

}
