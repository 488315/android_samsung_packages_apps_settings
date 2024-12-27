package com.google.gson;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class JsonNull extends JsonElement {
    public static final JsonNull INSTANCE = new JsonNull();

    public final boolean equals(Object obj) {
        return obj instanceof JsonNull;
    }

    public final int hashCode() {
        return JsonNull.class.hashCode();
    }
}
