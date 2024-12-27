package com.google.gson;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;

import java.math.BigDecimal;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ToNumberPolicy implements ToNumberStrategy {
    public static final /* synthetic */ ToNumberPolicy[] $VALUES;
    public static final ToNumberPolicy DOUBLE;
    public static final ToNumberPolicy LAZILY_PARSED_NUMBER;

    static {
        ToNumberPolicy toNumberPolicy =
                new ToNumberPolicy() { // from class: com.google.gson.ToNumberPolicy.1
                    @Override // com.google.gson.ToNumberStrategy
                    public final Number readNumber(JsonReader jsonReader) {
                        return Double.valueOf(jsonReader.nextDouble());
                    }
                };
        DOUBLE = toNumberPolicy;
        ToNumberPolicy toNumberPolicy2 =
                new ToNumberPolicy() { // from class: com.google.gson.ToNumberPolicy.2
                    @Override // com.google.gson.ToNumberStrategy
                    public final Number readNumber(JsonReader jsonReader) {
                        return new LazilyParsedNumber(jsonReader.nextString());
                    }
                };
        LAZILY_PARSED_NUMBER = toNumberPolicy2;
        $VALUES =
                new ToNumberPolicy[] {
                    toNumberPolicy,
                    toNumberPolicy2,
                    new ToNumberPolicy() { // from class: com.google.gson.ToNumberPolicy.3
                        @Override // com.google.gson.ToNumberStrategy
                        public final Number readNumber(JsonReader jsonReader) {
                            String nextString = jsonReader.nextString();
                            try {
                                return Long.valueOf(Long.parseLong(nextString));
                            } catch (NumberFormatException unused) {
                                try {
                                    Double valueOf = Double.valueOf(nextString);
                                    if (!valueOf.isInfinite()) {
                                        if (valueOf.isNaN()) {}
                                        return valueOf;
                                    }
                                    if (!jsonReader.lenient) {
                                        throw new MalformedJsonException(
                                                "JSON forbids NaN and infinities: "
                                                        + valueOf
                                                        + "; at path "
                                                        + jsonReader.getPath(true));
                                    }
                                    return valueOf;
                                } catch (NumberFormatException e) {
                                    StringBuilder m =
                                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                    "Cannot parse ", nextString, "; at path ");
                                    m.append(jsonReader.getPath(true));
                                    throw new JsonParseException(m.toString(), e);
                                }
                            }
                        }
                    },
                    new ToNumberPolicy() { // from class: com.google.gson.ToNumberPolicy.4
                        @Override // com.google.gson.ToNumberStrategy
                        public final Number readNumber(JsonReader jsonReader) {
                            String nextString = jsonReader.nextString();
                            try {
                                return new BigDecimal(nextString);
                            } catch (NumberFormatException e) {
                                StringBuilder m =
                                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                                "Cannot parse ", nextString, "; at path ");
                                m.append(jsonReader.getPath(true));
                                throw new JsonParseException(m.toString(), e);
                            }
                        }
                    }
                };
    }

    public static ToNumberPolicy valueOf(String str) {
        return (ToNumberPolicy) Enum.valueOf(ToNumberPolicy.class, str);
    }

    public static ToNumberPolicy[] values() {
        return (ToNumberPolicy[]) $VALUES.clone();
    }
}
