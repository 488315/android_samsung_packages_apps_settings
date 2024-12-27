package com.google.android.gms.common;

import com.google.android.gms.common.annotation.KeepName;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@KeepName
/* loaded from: classes3.dex */
public final class GooglePlayServicesMissingManifestValueException
        extends GooglePlayServicesManifestException {
    public GooglePlayServicesMissingManifestValueException() {
        super(
                0,
                "A required meta-data tag in your app's AndroidManifest.xml does not exist.  You"
                    + " must have the following declaration within the <application> element:    "
                    + " <meta-data android:name=\"com.google.android.gms.version\""
                    + " android:value=\"@integer/google_play_services_version\" />");
    }
}
