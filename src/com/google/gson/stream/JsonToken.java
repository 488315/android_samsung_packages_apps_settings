package com.google.gson.stream;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class JsonToken {
    public static final /* synthetic */ JsonToken[] $VALUES;
    public static final JsonToken BEGIN_ARRAY;
    public static final JsonToken BEGIN_OBJECT;
    public static final JsonToken BOOLEAN;
    public static final JsonToken END_ARRAY;
    public static final JsonToken END_DOCUMENT;
    public static final JsonToken END_OBJECT;
    public static final JsonToken NAME;
    public static final JsonToken NULL;
    public static final JsonToken NUMBER;
    public static final JsonToken STRING;

    static {
        JsonToken jsonToken = new JsonToken("BEGIN_ARRAY", 0);
        BEGIN_ARRAY = jsonToken;
        JsonToken jsonToken2 = new JsonToken("END_ARRAY", 1);
        END_ARRAY = jsonToken2;
        JsonToken jsonToken3 = new JsonToken("BEGIN_OBJECT", 2);
        BEGIN_OBJECT = jsonToken3;
        JsonToken jsonToken4 = new JsonToken("END_OBJECT", 3);
        END_OBJECT = jsonToken4;
        JsonToken jsonToken5 = new JsonToken("NAME", 4);
        NAME = jsonToken5;
        JsonToken jsonToken6 = new JsonToken("STRING", 5);
        STRING = jsonToken6;
        JsonToken jsonToken7 = new JsonToken("NUMBER", 6);
        NUMBER = jsonToken7;
        JsonToken jsonToken8 = new JsonToken("BOOLEAN", 7);
        BOOLEAN = jsonToken8;
        JsonToken jsonToken9 = new JsonToken("NULL", 8);
        NULL = jsonToken9;
        JsonToken jsonToken10 = new JsonToken("END_DOCUMENT", 9);
        END_DOCUMENT = jsonToken10;
        $VALUES =
                new JsonToken[] {
                    jsonToken,
                    jsonToken2,
                    jsonToken3,
                    jsonToken4,
                    jsonToken5,
                    jsonToken6,
                    jsonToken7,
                    jsonToken8,
                    jsonToken9,
                    jsonToken10
                };
    }

    public static JsonToken valueOf(String str) {
        return (JsonToken) Enum.valueOf(JsonToken.class, str);
    }

    public static JsonToken[] values() {
        return (JsonToken[]) $VALUES.clone();
    }
}
