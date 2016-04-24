/*
 * This file is a part of the GuiApp project.
 * Copyright 2016, CODEIA Inc. All rights reserved.
 * Distribution, reproduction and creation of derivative works are prohibited
 * without prior consent from the rights holder.
 */
package ph.codeia.guiapp.tui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutData;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import dagger.Module;
import dagger.Provides;
import java.util.logging.Logger;
import ph.codeia.guiapp.backend.login.LoginContract;
import ph.codeia.guiapp.backend.login.LoginContract.Field;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
public class LoginView extends Panel implements LoginContract.View {
    private final TextBox username = new TextBox();
    private final TextBox password = new TextBox();
    private final Label usernameError = new Label("");
    private final Label passwordError = new Label("");
    private final Label status = new Label("");
    private final Logger logger;

    @Module
    public static class Provider {
        @Provides
        static LoginView provide(LoginContract.Presenter p, Logger l) {
            LoginView v = new LoginView(p, l);
            p.bind(v);
            return v;
        }
    }

    public LoginView(LoginContract.Presenter p, Logger log) {
        super();
        logger = log;
        setLayoutManager(new GridLayout(2));

        TerminalSize inputWidth = new TerminalSize(15, 1);
        TerminalSize labelWidth = new TerminalSize(10, 1);
        username.setPreferredSize(inputWidth);
        usernameError.setPreferredSize(inputWidth);
        LayoutData fill = GridLayout.createHorizontallyFilledLayoutData(1);
        LayoutData fat = GridLayout.createHorizontallyFilledLayoutData(2);

        addComponent(status, fat);

        addComponent(new Label("Username").setPreferredSize(labelWidth));
        addComponent(username, fill);
        addComponent(new EmptySpace());
        addComponent(usernameError, fill);

        addComponent(new Label("Password").setPreferredSize(labelWidth));
        addComponent(password, fill);
        addComponent(new EmptySpace());
        addComponent(passwordError, fill);

        Runnable login = () -> p.tryLogin(username.getText(), password.getText());
        addComponent(new Button("Login", login),
                GridLayout.createHorizontallyEndAlignedLayoutData(2));
    }

    @Override
    public void tell(String message) {
        status.setText(emptyStringOr(message));
    }

    @Override
    public void show(LoginContract.Model model) {
        username.setText(emptyStringOr(model.get(Field.USERNAME)));
        password.setText(emptyStringOr(model.get(Field.PASSWORD)));
        usernameError.setText(emptyStringOr(model.getError(Field.USERNAME)));
        passwordError.setText(emptyStringOr(model.getError(Field.PASSWORD)));
    }

    private static String emptyStringOr(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }
}