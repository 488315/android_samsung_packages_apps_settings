package com.google.gson;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class JsonArray extends JsonElement implements Iterable {
    public final ArrayList elements = new ArrayList();

    public final void add(JsonElement jsonElement) {
        this.elements.add(jsonElement);
    }

    public final boolean equals(Object obj) {
        return obj == this
                || ((obj instanceof JsonArray) && ((JsonArray) obj).elements.equals(this.elements));
    }

    @Override // com.google.gson.JsonElement
    public final boolean getAsBoolean() {
        return getAsSingleElement().getAsBoolean();
    }

    @Override // com.google.gson.JsonElement
    public final int getAsInt() {
        return getAsSingleElement().getAsInt();
    }

    public final JsonElement getAsSingleElement() {
        int size = this.elements.size();
        if (size == 1) {
            return (JsonElement) this.elements.get(0);
        }
        throw new IllegalStateException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        size, "Array must have size 1, but has size "));
    }

    @Override // com.google.gson.JsonElement
    public final String getAsString() {
        return getAsSingleElement().getAsString();
    }

    public final int hashCode() {
        return this.elements.hashCode();
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return this.elements.iterator();
    }
}
