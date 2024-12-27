package com.android.settings.applications.specialaccess.deviceadmin;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DeviceAdminAdd$$ExternalSyntheticLambda5
        implements View.OnKeyListener {
    @Override // android.view.View.OnKeyListener
    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if ((keyEvent.getFlags() & 8) != 0) {
            return false;
        }
        Log.e("DeviceAdminAdd", "Can not activate device-admin with KeyEvent from non-system app.");
        return true;
    }
}
