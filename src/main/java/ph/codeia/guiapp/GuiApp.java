/*
 * This file is a part of the GuiApp project.
 * Copyright 2016, CODEIA Inc. All rights reserved.
 * Distribution, reproduction and creation of derivative works are prohibited
 * without prior consent from the rights holder.
 */
package ph.codeia.guiapp;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import ph.codeia.guiapp.backend.login.LoginContract;
import ph.codeia.guiapp.backend.login.LoginContract.Field;
import ph.codeia.guiapp.tui.LoginView;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
public class GuiApp {
    @Inject
    public GuiApp(LoginContract.Model login, LoginView view, WindowBasedTextGUI gui, Logger log) {
        login.set(Field.USERNAME, "mon");
        login.set(Field.PASSWORD, "password");
        try {
            attach(gui, view);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "io error when starting/stopping screen", ex);
        }
    }

    private static void attach(WindowBasedTextGUI gui, LoginView view) throws IOException {
        BasicWindow win = new BasicWindow(" Login ");
        win.setHints(Arrays.asList(Window.Hint.CENTERED));
        win.setCloseWindowWithEscape(true);
        win.setComponent(view);
        Screen s = gui.getScreen();
        s.startScreen();
        gui.addWindowAndWait(win);
        s.stopScreen();
    }

    public static void main(String[] args) {
        DaggerApplication.create().cli().run();
    }

}
