package com.samsung.android.settings.theftprotection.timer;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ProtectionTimerService$TimeDelayState {
    public static final /* synthetic */ ProtectionTimerService$TimeDelayState[] $VALUES;
    public static final ProtectionTimerService$TimeDelayState GRACE_PERIOD;
    public static final ProtectionTimerService$TimeDelayState NONE;
    public static final ProtectionTimerService$TimeDelayState SECURITY_DELAY;

    static {
        ProtectionTimerService$TimeDelayState protectionTimerService$TimeDelayState =
                new ProtectionTimerService$TimeDelayState("NONE", 0);
        NONE = protectionTimerService$TimeDelayState;
        ProtectionTimerService$TimeDelayState protectionTimerService$TimeDelayState2 =
                new ProtectionTimerService$TimeDelayState("SECURITY_DELAY", 1);
        SECURITY_DELAY = protectionTimerService$TimeDelayState2;
        ProtectionTimerService$TimeDelayState protectionTimerService$TimeDelayState3 =
                new ProtectionTimerService$TimeDelayState("GRACE_PERIOD", 2);
        GRACE_PERIOD = protectionTimerService$TimeDelayState3;
        $VALUES =
                new ProtectionTimerService$TimeDelayState[] {
                    protectionTimerService$TimeDelayState,
                    protectionTimerService$TimeDelayState2,
                    protectionTimerService$TimeDelayState3
                };
    }

    public static ProtectionTimerService$TimeDelayState valueOf(String str) {
        return (ProtectionTimerService$TimeDelayState)
                Enum.valueOf(ProtectionTimerService$TimeDelayState.class, str);
    }

    public static ProtectionTimerService$TimeDelayState[] values() {
        return (ProtectionTimerService$TimeDelayState[]) $VALUES.clone();
    }
}
