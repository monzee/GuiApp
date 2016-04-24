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
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.inject.Singleton;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
@Module
public class GlobalModule {
    @Provides
    @Singleton
    static Logger provideLogger() {
        return Logger.getLogger(GuiApp.class.getName());
    }

    @Provides
    @Singleton
    static ExecutorService provideExecutorService() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(
                1, 3,                      // waiting threads are killed
                10, TimeUnit.SECONDS,      // after 10 seconds of idling
                new SynchronousQueue<>());
        // because the cli locks up for 1 minute after exiting if we made an
        // http request during the session
        tpe.allowCoreThreadTimeOut(true);
        return tpe;
    }
}
