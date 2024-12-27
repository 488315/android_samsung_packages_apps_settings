package com.google.gson;

import java.lang.reflect.Field;
import java.util.Locale;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class FieldNamingPolicy implements FieldNamingStrategy {
    public static final /* synthetic */ FieldNamingPolicy[] $VALUES;
    public static final FieldNamingPolicy IDENTITY;

    static {
        FieldNamingPolicy fieldNamingPolicy =
                new FieldNamingPolicy() { // from class: com.google.gson.FieldNamingPolicy.1
                    @Override // com.google.gson.FieldNamingStrategy
                    public final String translateName(Field field) {
                        return field.getName();
                    }
                };
        IDENTITY = fieldNamingPolicy;
        $VALUES =
                new FieldNamingPolicy[] {
                    fieldNamingPolicy,
                    new FieldNamingPolicy() { // from class: com.google.gson.FieldNamingPolicy.2
                        @Override // com.google.gson.FieldNamingStrategy
                        public final String translateName(Field field) {
                            return FieldNamingPolicy.upperCaseFirstLetter(field.getName());
                        }
                    },
                    new FieldNamingPolicy() { // from class: com.google.gson.FieldNamingPolicy.3
                        @Override // com.google.gson.FieldNamingStrategy
                        public final String translateName(Field field) {
                            return FieldNamingPolicy.upperCaseFirstLetter(
                                    FieldNamingPolicy.separateCamelCase(field.getName(), ' '));
                        }
                    },
                    new FieldNamingPolicy() { // from class: com.google.gson.FieldNamingPolicy.4
                        @Override // com.google.gson.FieldNamingStrategy
                        public final String translateName(Field field) {
                            return FieldNamingPolicy.separateCamelCase(field.getName(), '_')
                                    .toUpperCase(Locale.ENGLISH);
                        }
                    },
                    new FieldNamingPolicy() { // from class: com.google.gson.FieldNamingPolicy.5
                        @Override // com.google.gson.FieldNamingStrategy
                        public final String translateName(Field field) {
                            return FieldNamingPolicy.separateCamelCase(field.getName(), '_')
                                    .toLowerCase(Locale.ENGLISH);
                        }
                    },
                    new FieldNamingPolicy() { // from class: com.google.gson.FieldNamingPolicy.6
                        @Override // com.google.gson.FieldNamingStrategy
                        public final String translateName(Field field) {
                            return FieldNamingPolicy.separateCamelCase(field.getName(), '-')
                                    .toLowerCase(Locale.ENGLISH);
                        }
                    },
                    new FieldNamingPolicy() { // from class: com.google.gson.FieldNamingPolicy.7
                        @Override // com.google.gson.FieldNamingStrategy
                        public final String translateName(Field field) {
                            return FieldNamingPolicy.separateCamelCase(field.getName(), '.')
                                    .toLowerCase(Locale.ENGLISH);
                        }
                    }
                };
    }

    public static String separateCamelCase(String str, char c) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt) && sb.length() != 0) {
                sb.append(c);
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    public static String upperCaseFirstLetter(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (Character.isLetter(charAt)) {
                if (Character.isUpperCase(charAt)) {
                    return str;
                }
                char upperCase = Character.toUpperCase(charAt);
                if (i == 0) {
                    return upperCase + str.substring(1);
                }
                return str.substring(0, i) + upperCase + str.substring(i + 1);
            }
        }
        return str;
    }

    public static FieldNamingPolicy valueOf(String str) {
        return (FieldNamingPolicy) Enum.valueOf(FieldNamingPolicy.class, str);
    }

    public static FieldNamingPolicy[] values() {
        return (FieldNamingPolicy[]) $VALUES.clone();
    }
}
