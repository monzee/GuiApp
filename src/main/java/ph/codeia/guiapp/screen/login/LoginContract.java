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

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import ph.codeia.guiapp.screen.Validates;
import ph.codeia.guiapp.screen.ViewBound;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
public abstract class LoginContract {
    public static final String IS_REQUIRED = "Field cannot be empty.";
    public static final String HAS_ERRORS = "One or more of the values you entered is invalid.";
    public static final String BAD_CREDENTIALS = "Wrong username/password combo.";
    public static final String GONE = "Cannot find service. The location might have changed.";
    public static final String UNKNOWN_RESPONSE = "Got a response I can't understand.";

    public enum Field { USERNAME, PASSWORD }

    public interface View {
        void tell(String message);
        void show(Model model);
    }

    public interface Presenter extends ViewBound<View>{
        void tryLogin(String username, String password);
    }

    public interface Model extends Validates<Field> {
        void set(Field f, String value);
        String get(Field f);
    }

    public interface Service {
        @FormUrlEncoded
        @POST("/login")
        Call<String> login(
                @retrofit2.http.Field("username") String username,
                @retrofit2.http.Field("password") String password);
    }

    @Module
    public static class Defaults {
        @Provides
        static Presenter providePresenter(LoginPresenter p) {
            return p;
        }

        @Provides
        @Singleton
        static Model provideModel(LoginState model) {
            return model;
        }

        @Provides
        @Singleton
        static Service provideService(@Named("codeia") Retrofit retrofit) {
            return retrofit.create(Service.class);
        }
    }
}
