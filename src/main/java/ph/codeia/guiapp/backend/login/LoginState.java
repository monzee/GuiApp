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
