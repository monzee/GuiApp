/*
 * This file is a part of the GuiApp project.
 * Copyright 2016, CODEIA Inc. All rights reserved.
 * Distribution, reproduction and creation of derivative works are prohibited
 * without prior consent from the rights holder.
 */
package ph.codeia.guiapp.backend;

/**
 *
 * @author Mon Zafra &lt;mz@codeia.ph&gt;
 * @param <View> the view class this presenter can talk to
 */
public interface ViewBound<View> {
    void bind(View view);
}
