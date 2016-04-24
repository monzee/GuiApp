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
 * @param <Field> an enum of fields that the screen accepts input for
 */
public interface Form<Field> {
    void setError(Field field, String message);
    String getError(Field field);
    <T> void set(Field field, T value);
    <T> T get(Field field);
}
