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

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.AbstractWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutData;
import com.googlecode.lanterna.gui2.Panels;
import com.googlecode.lanterna.gui2.TextBox;
import dagger.Module;
import dagger.Provides;
import java.util.Arrays;
import ph.codeia.guiapp.logic.chrome.ChromeContract;
import ph.codeia.guiapp.logic.login.LoginContract;
import ph.codeia.guiapp.logic.login.LoginContract.Field;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
public class Login extends AbstractWindow implements LoginContract.View {

    @Module
    public static class Provider {
        @Provides
        static LoginContract.View provideInterface(Login view) {
            return view;
        }

        @Provides
        static Login provide(LoginContract.Presenter p, ChromeContract.View c) {
            Login v = new Login(p, c);
            p.bind(v);
            return v;
        }
    }

    private final TextBox username = new TextBox();
    private final TextBox password = new TextBox();
    private final Label usernameError = new Label("");
    private final Label passwordError = new Label("");
    private final ChromeContract.View chrome;

    public Login(LoginContract.Presenter p, ChromeContract.View c) {
        super("Login");
        chrome = c;

        TerminalSize inputWidth = new TerminalSize(15, 1);
        TerminalSize labelWidth = new TerminalSize(10, 1);
        LayoutData fill = GridLayout.createHorizontallyFilledLayoutData(1);
        LayoutData doubleSpanEnd = GridLayout.createHorizontallyEndAlignedLayoutData(2);

        username.setPreferredSize(inputWidth).setLayoutData(fill);
        usernameError.setLayoutData(fill);
        password.setPreferredSize(inputWidth).setLayoutData(fill);
        passwordError.setLayoutData(fill);

        Runnable login = () -> p.tryLogin(username.getText(), password.getText());

        setComponent(Panels.grid(2,
                new EmptySpace().setLayoutData(doubleSpanEnd),

                new Label("username").setPreferredSize(labelWidth),
                username,

                new EmptySpace(),
                usernameError,

                new Label("password").setPreferredSize(labelWidth),
                password,

                new EmptySpace(),
                passwordError,

                new Button("Login", login).setLayoutData(doubleSpanEnd)
        ));
        setHints(Arrays.asList(Hint.CENTERED));
    }

    @Override
    public void tell(String message) {
        chrome.tell(message);
    }

    @Override
    public void show(LoginContract.Model model) {
        username.setText(emptyStringOr(model.get(Field.USERNAME)));
        password.setText(emptyStringOr(model.get(Field.PASSWORD)));
        usernameError.setText(emptyStringOr(model.getError(Field.USERNAME)));
        passwordError.setText(emptyStringOr(model.getError(Field.PASSWORD)));
    }

    private static String emptyStringOr(String s) {
        return s == null ? "" : s;
    }
}