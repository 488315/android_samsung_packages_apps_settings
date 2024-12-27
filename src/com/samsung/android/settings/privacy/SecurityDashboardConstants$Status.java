package com.samsung.android.settings.privacy;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecurityDashboardConstants$Status {
    public static final /* synthetic */ SecurityDashboardConstants$Status[] $VALUES;
    public static final SecurityDashboardConstants$Status CRITICAL;
    public static final SecurityDashboardConstants$Status NONE;
    public static final SecurityDashboardConstants$Status OK;
    public static final SecurityDashboardConstants$Status SCANNING;
    public static final SecurityDashboardConstants$Status WARNING;

    static {
        SecurityDashboardConstants$Status securityDashboardConstants$Status =
                new SecurityDashboardConstants$Status("NONE", 0);
        NONE = securityDashboardConstants$Status;
        SecurityDashboardConstants$Status securityDashboardConstants$Status2 =
                new SecurityDashboardConstants$Status("SCANNING", 1);
        SCANNING = securityDashboardConstants$Status2;
        SecurityDashboardConstants$Status securityDashboardConstants$Status3 =
                new SecurityDashboardConstants$Status("OK", 2);
        OK = securityDashboardConstants$Status3;
        SecurityDashboardConstants$Status securityDashboardConstants$Status4 =
                new SecurityDashboardConstants$Status("WARNING", 3);
        WARNING = securityDashboardConstants$Status4;
        SecurityDashboardConstants$Status securityDashboardConstants$Status5 =
                new SecurityDashboardConstants$Status("CRITICAL", 4);
        CRITICAL = securityDashboardConstants$Status5;
        $VALUES =
                new SecurityDashboardConstants$Status[] {
                    securityDashboardConstants$Status,
                    securityDashboardConstants$Status2,
                    securityDashboardConstants$Status3,
                    securityDashboardConstants$Status4,
                    securityDashboardConstants$Status5
                };
    }

    public static SecurityDashboardConstants$Status valueOf(String str) {
        return (SecurityDashboardConstants$Status)
                Enum.valueOf(SecurityDashboardConstants$Status.class, str);
    }

    public static SecurityDashboardConstants$Status[] values() {
        return (SecurityDashboardConstants$Status[]) $VALUES.clone();
    }
}
