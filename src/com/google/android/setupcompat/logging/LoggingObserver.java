package com.google.android.setupcompat.logging;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public interface LoggingObserver {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\f\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0010\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\u0003"
            },
            d2 = {
                "com/google/android/setupcompat/logging/LoggingObserver$ButtonType",
                ApnSettings.MVNO_NONE,
                "Lcom/google/android/setupcompat/logging/LoggingObserver$ButtonType;",
                "setupcompat_release"
            },
            k = 1,
            mv = {1, 9, 0})
    public final class ButtonType {
        public static final /* synthetic */ ButtonType[] $VALUES;

        static {
            ButtonType[] buttonTypeArr = {
                new ButtonType("UNKNOWN", 0),
                new ButtonType("PRIMARY", 1),
                new ButtonType("SECONDARY", 2)
            };
            $VALUES = buttonTypeArr;
            EnumEntriesKt.enumEntries(buttonTypeArr);
        }

        public static ButtonType valueOf(String str) {
            return (ButtonType) Enum.valueOf(ButtonType.class, str);
        }

        public static ButtonType[] values() {
            return (ButtonType[]) $VALUES.clone();
        }
    }

    void log();
}
