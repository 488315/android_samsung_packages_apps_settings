package com.samsung.android.settings.softwareupdate;

import android.annotation.NonNull;
import android.content.Context;
import android.content.Intent;
import android.os.SemSystemProperties;
import com.android.settings.Utils;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.util.SemLog;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
abstract class SoftwareUpdateVariant {
    public static final /* synthetic */ SoftwareUpdateVariant[] $VALUES = {new AnonymousClass1(0, "SAMSUNG", KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.samsung.settings.SOFTWARE_UPDATE_SETTINGS", "com.wssyncmldm"), new AnonymousClass2(1, "VZW", "com.samsung.sdm.sdmviewer", "com.android.settings.MANUFACTURER_APPLICATION_SETTING", "com.samsung.sdm.sdmviewer"), new AnonymousClass3(2, "VZW_NEW", "com.samsung.sdm", "com.android.settings.MANUFACTURER_APPLICATION_SETTING", "com.samsung.sdm"), new AnonymousClass4(3, "ATT", "com.ws.dm", "com.android.settings.MANUFACTURER_APPLICATION_SETTING", "com.ws.dm")};
    public static final AnonymousClass4 ATT = null;
    public static final AnonymousClass1 SAMSUNG = null;
    public static final AnonymousClass2 VZW = null;
    public static final AnonymousClass3 VZW_NEW = null;

    @NonNull
    private final String action;

    @NonNull
    private final String packageNameToCheck;

    @NonNull
    private final String packageNameToLaunch;

    /* JADX INFO: Fake field, exist only in values array */
    SoftwareUpdateVariant EF12;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant$1, reason: invalid class name */
    public enum AnonymousClass1 extends SoftwareUpdateVariant {
        @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant
        public final String className() {
            return null;
        }

        @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant
        public final String topLevelKey() {
            return "top_level_software_update";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant$2, reason: invalid class name */
    public enum AnonymousClass2 extends SoftwareUpdateVariant {
        @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant
        public final String className() {
            String salesCode = SemSystemProperties.getSalesCode();
            salesCode.getClass();
            switch (salesCode) {
                case "TFA":
                case "TFN":
                case "TFO":
                case "TFV":
                    return "com.samsung.sdm.sdmviewer.TFNSoftwareUpdateLauncher";
                default:
                    return "com.samsung.sdm.sdmviewer.VZWSystemUpdatesLauncher";
            }
        }

        @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant
        public final String topLevelKey() {
            return "dashboard_tile_pref_".concat(className());
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant$3, reason: invalid class name */
    public enum AnonymousClass3 extends SoftwareUpdateVariant {
        @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant
        public final String className() {
            return "com.samsung.sdm.VZWSystemUpdatesLauncher";
        }

        @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant
        public final boolean isFotaSupported(Context context) {
            return SoftwareUpdateUtils.isVZWFotaNewEnabled(context);
        }

        @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant
        public final String topLevelKey() {
            return "dashboard_tile_pref_com.samsung.sdm.VZWSystemUpdatesLauncher";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant$4, reason: invalid class name */
    public enum AnonymousClass4 extends SoftwareUpdateVariant {
        @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant
        public final String className() {
            return "com.att.fotaagent.enabler.ui.userinit.UserInitActivity";
        }

        @Override // com.samsung.android.settings.softwareupdate.SoftwareUpdateVariant
        public final String topLevelKey() {
            return "dashboard_tile_pref_com.att.fotaagent.enabler.ui.userinit.UserInitActivity";
        }
    }

    public SoftwareUpdateVariant(int i, String str, String str2, String str3, String str4) {
        this.packageNameToLaunch = str2;
        this.action = str3;
        this.packageNameToCheck = str4;
    }

    public static SoftwareUpdateVariant get(Context context) {
        for (SoftwareUpdateVariant softwareUpdateVariant : values()) {
            if (Utils.isPackageEnabled(context, softwareUpdateVariant.packageNameToCheck) && softwareUpdateVariant.isFotaSupported(context)) {
                return softwareUpdateVariant;
            }
        }
        throw new IllegalStateException("No packages enabled");
    }

    public static SoftwareUpdateVariant valueOf(String str) {
        return (SoftwareUpdateVariant) Enum.valueOf(SoftwareUpdateVariant.class, str);
    }

    public static SoftwareUpdateVariant[] values() {
        return (SoftwareUpdateVariant[]) $VALUES.clone();
    }

    public abstract String className();

    public final Intent intent() {
        Intent intent = new Intent(this.action).setPackage(this.packageNameToLaunch);
        String className = className();
        if (className == null) {
            return intent;
        }
        String str = intent.getPackage();
        if (str != null) {
            return intent.setClassName(str, className);
        }
        SemLog.w("SoftwareUpdateVariant", "packageName should not be null");
        return intent;
    }

    public boolean isFotaSupported(Context context) {
        return true;
    }

    public abstract String topLevelKey();
}
