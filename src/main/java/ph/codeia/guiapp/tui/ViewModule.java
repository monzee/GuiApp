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

import dagger.MembersInjector;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import ph.codeia.guiapp.screen.chrome.ChromeContract;
import ph.codeia.guiapp.screen.login.LoginContract;
import ph.codeia.guiapp.screen.twitch.TwitchContract;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
@Module
public class ViewModule {
    @Provides
    static ChromeContract.View chromeInterface(Chrome view) {
        return view;
    }

    @Provides
    @Singleton
    static Chrome chrome(MembersInjector<Chrome> injector) {
        Chrome view = new Chrome();
        injector.injectMembers(view);
        return view;
    }

    @Provides
    static TwitchContract.View twitchInterface(Twitch view) {
        return view;
    }

    @Provides
    static Twitch twitch(MembersInjector<Twitch> injector) {
        Twitch view = new Twitch();
        injector.injectMembers(view);
        view.presenter.bind(view);
        view.chrome.tell("ready.");
        return view;
    }

    @Provides
    static LoginContract.View loginInterface(Login view) {
        return view;
    }

    @Provides
    static Login login(LoginContract.Presenter p, ChromeContract.View c) {
        Login v = new Login(p, c);
        p.bind(v);
        return v;
    }
}