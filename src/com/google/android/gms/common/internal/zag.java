package com.google.android.gms.common.internal;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class zag implements DialogInterface.OnClickListener {
    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        try {
            zaa();
        } catch (ActivityNotFoundException e) {
            Log.e(
                    "DialogRedirect",
                    true == Build.FINGERPRINT.contains("generic")
                            ? "Failed to start resolution intent. This may occur when resolving"
                                  + " Google Play services connection issues on emulators with"
                                  + " Google APIs but not Google Play Store."
                            : "Failed to start resolution intent.",
                    e);
        } finally {
            dialogInterface.dismiss();
        }
    }

    public abstract void zaa();
}
