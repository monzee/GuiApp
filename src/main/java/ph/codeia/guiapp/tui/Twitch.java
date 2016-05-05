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

import com.googlecode.lanterna.gui2.AbstractWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutData;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Panels;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import ph.codeia.guiapp.logic.chrome.ChromeContract;
import ph.codeia.guiapp.logic.twitch.TwitchContract;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 */
public class Twitch extends AbstractWindow implements TwitchContract.View {

    @Inject
    Logger log;

    @Inject
    TwitchContract.Presenter presenter;

    @Inject
    ChromeContract.View chrome;

    private final Panel chat;

    public Twitch() {
        super("Twitch");
        setHints(Arrays.asList(Hint.EXPANDED));
        LayoutData fill = GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.FILL,
                false, true);
        setComponent(Panels.grid(2,
                Panels.vertical(
                        new Label("Game: Dota 2"),
                        new Label("Uptime: 2hrs 12min"),
                        new Label("View count: 23,314")
                ).withBorder(Borders.singleLine("Channel"))
                .setLayoutData(fill),

                (chat = Panels.vertical(
                        new Label("kasdf: Kappa")
                )).withBorder(Borders.singleLine("Chat"))
                .setLayoutData(GridLayout.createLayoutData(
                        GridLayout.Alignment.FILL,
                        GridLayout.Alignment.FILL,
                        true, true,
                        1, 2)),

                Panels.vertical(
                        new Label("low"),
                        new Label("medium"),
                        new Label("high"),
                        new Label("source")
                ).withBorder(Borders.singleLine("Playlist"))
                .setLayoutData(fill)
        ));
    }

    @Override
    public void show(TwitchContract.ChannelInfo info) {
    }

    @Override
    public void show(List<TwitchContract.Stream> playlist) {
    }

    @Override
    public void show(TwitchContract.ChatLine line) {
    }

}
