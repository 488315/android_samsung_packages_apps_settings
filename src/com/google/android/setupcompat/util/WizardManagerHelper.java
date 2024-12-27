package com.google.android.setupcompat.util;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WizardManagerHelper {
    public static final String ACTION_NEXT = "com.android.wizard.NEXT";
    static final String EXTRA_WIZARD_BUNDLE = "wizardBundle";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum SuwLifeCycleEnum {
        UNKNOWN(0),
        /* JADX INFO: Fake field, exist only in values array */
        INITIALIZATION(1),
        /* JADX INFO: Fake field, exist only in values array */
        PREDEFERRED(2),
        /* JADX INFO: Fake field, exist only in values array */
        DEFERRED(3),
        /* JADX INFO: Fake field, exist only in values array */
        PORTAL(4),
        /* JADX INFO: Fake field, exist only in values array */
        RESTORE_ANYTIME(5);

        public final int value;

        SuwLifeCycleEnum(int i) {
            this.value = i;
        }
    }

    public static void copyWizardManagerExtras(Intent intent, Intent intent2) {
        intent2.putExtra(EXTRA_WIZARD_BUNDLE, intent.getBundleExtra(EXTRA_WIZARD_BUNDLE));
        for (String str :
                Arrays.asList(
                        "firstRun",
                        "deferredSetup",
                        "preDeferredSetup",
                        "portalSetup",
                        "isSetupFlow",
                        "isSuwSuggestedActionFlow")) {
            intent2.putExtra(str, intent.getBooleanExtra(str, false));
        }
        intent2.putExtra(
                "suw_lifecycle",
                intent.getIntExtra("suw_lifecycle", SuwLifeCycleEnum.UNKNOWN.value));
        intent2.putExtra("theme", intent.getStringExtra("theme"));
    }

    public static boolean isAnySetupWizard(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.getBooleanExtra("isSetupFlow", false);
    }

    public static boolean isDeviceProvisioned(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) == 1;
    }

    public static boolean isUserSetupComplete(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "user_setup_complete", 0) == 1;
    }
}
