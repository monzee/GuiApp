/*
 * This file is a part of the GuiApp project.
 * Copyright 2016, CODEIA Inc. All rights reserved.
 * Distribution, reproduction and creation of derivative works are prohibited
 * without prior consent from the rights holder.
 */
package ph.codeia.guiapp;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
@Module
public class LanternaModule {
    @Provides
    @Singleton
    static Terminal provideTerminal(Logger logger) {
        try {
            return new DefaultTerminalFactory().createTerminal();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "failed to create terminal", ex);
            return null;
        }
    }

    @Provides
    @Singleton
    static Screen provideScreen(Terminal terminal, Logger logger) {
        try {
            return new TerminalScreen(terminal);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "failed to create screen", ex);
            return null;
        }
    }

    @Provides
    @Singleton
    static WindowBasedTextGUI provideGui(Screen screen) {
        return new MultiWindowTextGUI(screen, TextColor.ANSI.BLUE);
    }
}
