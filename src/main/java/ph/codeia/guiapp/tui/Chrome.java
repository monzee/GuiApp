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
package ph.codeia.guiapp.tui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.AbstractWindow;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.TextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import ph.codeia.guiapp.backend.chrome.ChromeContract;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
public class Chrome extends AbstractWindow implements ChromeContract.View, TextGUI.Listener {

    @Module
    public static class Provider {
        private final Chrome instance;

        public Provider(Chrome c) {
            instance = c;
        }

        @Provides
        ChromeContract.View provideView() {
            return instance;
        }
    }

    @Inject
    Logger log;

    @Inject
    WindowBasedTextGUI gui;

    @Inject
    Lazy<LoginView> login;

    private final Label status = new Label("")
            .setBackgroundColor(TextColor.ANSI.BLUE)
            .setForegroundColor(new TextColor.RGB(255, 255, 0));

    public Chrome() {
        super();
        setHints(Arrays.asList(Hint.NO_DECORATIONS, Hint.NO_POST_RENDERING));
        setComponent(status);
    }

    @Override
    public void tell(String message) {
        status.setText(message == null ? "" : message);
    }

    @Override
    public void show(ChromeContract.Screen screen) {
        switch (screen) {
        case LOGIN:
            gui.addWindow(login.get());
            break;
        default:
            break;
        }
    }

    @Override
    public boolean onUnhandledKeyStroke(TextGUI tgui, KeyStroke ks) {
        if (ks.getKeyType() == KeyType.Escape) {
            close();
            return true;
        }
        return false;
    }


    public void run() {
        gui.addWindow(this);
        gui.addListener(this);
        Screen s = gui.getScreen();
        int row = s.getTerminalSize().getRows() - 1;
        setPosition(new TerminalPosition(0, row));
        show(ChromeContract.Screen.LOGIN);
        try {
            s.startScreen();
            gui.waitForWindowToClose(this);
            s.stopScreen();
        } catch (IOException e) {
            log.log(Level.SEVERE, "io error while starting/stopping screen", e);
        }
    }

}
