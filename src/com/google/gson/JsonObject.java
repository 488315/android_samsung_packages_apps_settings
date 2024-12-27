package com.google.gson;

import com.google.gson.internal.LinkedTreeMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class JsonObject extends JsonElement {
    public final LinkedTreeMap members = new LinkedTreeMap(false);

    public final void add(String str, JsonElement jsonElement) {
        this.members.put(str, jsonElement);
    }

    public final void addProperty(String str, String str2) {
        this.members.put(str, str2 == null ? JsonNull.INSTANCE : new JsonPrimitive(str2));
    }

    public final boolean equals(Object obj) {
        return obj == this
                || ((obj instanceof JsonObject) && ((JsonObject) obj).members.equals(this.members));
    }

    public final JsonElement get(String str) {
        return (JsonElement) this.members.get(str);
    }

    public final int hashCode() {
        return this.members.hashCode();
    }

    public final void addProperty(String str, Boolean bool) {
        this.members.put(str, new JsonPrimitive(bool));
    }
}
