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
public final class GuiApp {
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
