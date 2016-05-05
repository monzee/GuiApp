/*
 * The MIT License
 *
 * Copyright 2016 Mon Zafra &lt;mz@codeia.ph&gt;.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ph.codeia.guiapp.screen.login;

import javax.inject.Inject;
import ph.codeia.guiapp.screen.login.LoginContract.Field;
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
        model.set(Field.USERNAME, username);
        model.set(Field.PASSWORD, password);
        if (isValid(username, password)) {
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
