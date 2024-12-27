package com.android.settings.spa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.spa.app.appinfo.AppInfoSettingsProvider;
import com.android.settingslib.core.lifecycle.HideNonSystemOverlayMixin;
import com.android.settingslib.spa.framework.BrowseActivity;
import com.android.settingslib.spa.framework.util.SpaIntentKt;

import com.google.android.setupcompat.util.WizardManagerHelper;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"
        },
        d2 = {
            "Lcom/android/settings/spa/SpaActivity;",
            "Lcom/android/settingslib/spa/framework/BrowseActivity;",
            "<init>",
            "()V",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class SpaActivity extends BrowseActivity {
    public static final Companion Companion = new Companion();
    public static final Set SuwBlockedPages;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {
        public static void startSpaActivity(Context context, String destination) {
            Intrinsics.checkNotNullParameter(context, "<this>");
            Intrinsics.checkNotNullParameter(destination, "destination");
            Intent intent = new Intent(context, (Class<?>) SpaActivity.class);
            SpaIntentKt.appendSpaParams$default(intent, destination, null, 6);
            SpaIntentKt.appendSpaParams$default(intent, null, "browse", 3);
            context.startActivity(intent);
        }

        public final boolean isSuwAndPageBlocked(Context context, String name) {
            Intrinsics.checkNotNullParameter(context, "<this>");
            Intrinsics.checkNotNullParameter(name, "name");
            if (!SpaActivity.SuwBlockedPages.contains(name)
                    || WizardManagerHelper.isDeviceProvisioned(context)) {
                return false;
            }
            Log.w("SpaActivity", name.concat(" blocked before SUW completed."));
            return true;
        }
    }

    static {
        AppInfoSettingsProvider appInfoSettingsProvider = AppInfoSettingsProvider.INSTANCE;
        SuwBlockedPages = SetsKt.setOf("AppInfoSettings");
    }

    @Override // com.android.settingslib.spa.framework.BrowseActivity,
              // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getLifecycle().addObserver(new HideNonSystemOverlayMixin(this));
    }
}
