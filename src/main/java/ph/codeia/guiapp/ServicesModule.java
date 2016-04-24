/*
 * This file is a part of the GuiApp project.
 * Copyright 2016, CODEIA Inc. All rights reserved.
 * Distribution, reproduction and creation of derivative works are prohibited
 * without prior consent from the rights holder.
 */
package ph.codeia.guiapp;

import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
@Module
public class ServicesModule {
    @Provides
    @Singleton
    @Named("codeia")
    static Retrofit provideCodeiaEndpoint(OkHttpClient okhttp) {
        return new Retrofit.Builder()
                .baseUrl("https://codeia.ph/api/sandbox/")
                .client(okhttp)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    static OkHttpClient provideHttpClient(ExecutorService executor) {
        return new OkHttpClient.Builder()
                .dispatcher(new Dispatcher(executor))
                .build();
    }
}
