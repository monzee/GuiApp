/*
 * This file is a part of the GuiApp project.
 * Copyright 2016, CODEIA Inc. All rights reserved.
 * Distribution, reproduction and creation of derivative works are prohibited
 * without prior consent from the rights holder.
 */
package ph.codeia.guiapp;

import dagger.Subcomponent;
import javax.inject.Singleton;
import ph.codeia.guiapp.tui.LoginView;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
@Singleton
@Subcomponent(modules = {LanternaModule.class, LoginView.Provider.class})
public interface CliContext {
    GuiApp run();
}
