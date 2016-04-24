/*
 * This file is a part of the GuiApp project.
 * Copyright 2016, CODEIA Inc. All rights reserved.
 * Distribution, reproduction and creation of derivative works are prohibited
 * without prior consent from the rights holder.
 */
package ph.codeia.guiapp;

import dagger.Component;
import javax.inject.Singleton;
import ph.codeia.guiapp.backend.login.LoginContract;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
@Singleton
@Component(modules = {
    GlobalModule.class, ServicesModule.class,
    LoginContract.Defaults.class,
})
public interface Application {
    CliContext cli();
}
