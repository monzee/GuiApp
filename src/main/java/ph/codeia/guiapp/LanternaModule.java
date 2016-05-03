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
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, TextColor.ANSI.BLUE);
        gui.setEOFWhenNoWindows(true);
        gui.setBlockingIO(false);
        return gui;
    }
}
