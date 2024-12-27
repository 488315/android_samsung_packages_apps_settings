package com.android.settings.spa;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.activityembedding.EmbeddedDeepLinkUtils;
import com.android.settingslib.spa.framework.util.SpaIntentKt;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0004B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"
        },
        d2 = {
            "Lcom/android/settings/spa/SpaBridgeActivity;",
            "Landroid/app/Activity;",
            "<init>",
            "()V",
            "Companion",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class SpaBridgeActivity extends Activity {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Companion {
        public static void startSpaActivityFromBridge(
                Activity activity, Function1 destinationFactory) {
            String str;
            Intrinsics.checkNotNullParameter(activity, "<this>");
            Intrinsics.checkNotNullParameter(destinationFactory, "destinationFactory");
            Bundle bundle =
                    activity.getPackageManager()
                            .getActivityInfo(
                                    activity.getComponentName(),
                                    PackageManager.ComponentInfoFlags.of(128L))
                            .metaData;
            String string = bundle.getString("com.android.settings.spa.DESTINATION");
            SpaDestination spaDestination = null;
            if (string != null
                    && !StringsKt__StringsJVMKt.isBlank(string)
                    && (str = (String) destinationFactory.invoke(string)) != null
                    && !StringsKt__StringsJVMKt.isBlank(str)) {
                spaDestination =
                        new SpaDestination(
                                str, bundle.getString("com.android.settings.HIGHLIGHT_MENU_KEY"));
            }
            if (spaDestination == null) {
                return;
            }
            Intent intent = new Intent(activity, (Class<?>) SpaActivity.class);
            SpaIntentKt.appendSpaParams$default(intent, spaDestination.destination, "external", 2);
            if (ActivityEmbeddingUtils.isEmbeddingActivityEnabled(activity)
                    && EmbeddedDeepLinkUtils.tryStartMultiPaneDeepLink(
                            activity, intent, spaDestination.highlightMenuKey)) {
                return;
            }
            activity.startActivity(intent);
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Companion.startSpaActivityFromBridge(
                this,
                new Function1() { // from class:
                                  // com.android.settings.spa.SpaBridgeActivity$Companion$startSpaActivityFromBridge$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        String it = (String) obj;
                        Intrinsics.checkNotNullParameter(it, "it");
                        return it;
                    }
                });
        finish();
    }
}
