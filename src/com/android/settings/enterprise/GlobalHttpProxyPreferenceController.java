package com.android.settings.enterprise;

import android.content.Context;
import android.net.ConnectivityManager;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GlobalHttpProxyPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {
    public final ConnectivityManager mCm;

    public GlobalHttpProxyPreferenceController(Context context) {
        super(context);
        this.mCm = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "global_http_proxy";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mCm.getGlobalProxy() != null;
    }
}
