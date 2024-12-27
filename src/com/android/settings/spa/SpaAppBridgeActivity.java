package com.android.settings.spa;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"
        },
        d2 = {
            "Lcom/android/settings/spa/SpaAppBridgeActivity;",
            "Landroid/app/Activity;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class SpaAppBridgeActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SpaBridgeActivity.Companion.startSpaActivityFromBridge(
                this,
                new Function1() { // from class:
                                  // com.android.settings.spa.SpaAppBridgeActivity$onCreate$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        String it = (String) obj;
                        Intrinsics.checkNotNullParameter(it, "it");
                        int i = SpaAppBridgeActivity.$r8$clinit;
                        Intent intent = SpaAppBridgeActivity.this.getIntent();
                        Intrinsics.checkNotNullExpressionValue(intent, "getIntent(...)");
                        Uri data = intent.getData();
                        String schemeSpecificPart =
                                data != null ? data.getSchemeSpecificPart() : null;
                        if (schemeSpecificPart == null) {
                            return null;
                        }
                        return it + "/" + schemeSpecificPart + "/" + UserHandle.myUserId();
                    }
                });
        finish();
    }
}
