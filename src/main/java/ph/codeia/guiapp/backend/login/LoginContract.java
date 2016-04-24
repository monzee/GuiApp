/*
 * This file is a part of the GuiApp project.
 * Copyright 2016, CODEIA Inc. All rights reserved.
 * Distribution, reproduction and creation of derivative works are prohibited
 * without prior consent from the rights holder.
 */
package ph.codeia.guiapp.backend.login;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import ph.codeia.guiapp.backend.Form;
import ph.codeia.guiapp.backend.ViewBound;
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

    public interface Model extends Form<Field> {
    }

    public interface Service {
        @FormUrlEncoded
        @POST(value = "/login")
        Call<String> login(
                @retrofit2.http.Field(value = "username") String username,
                @retrofit2.http.Field(value = "password") String password);
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
