package com.android.settingslib.spa.framework;

import com.android.settings.spa.SpaActivity;
import com.android.settingslib.spa.framework.common.SettingsPage;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.common.SettingsPageProviderKt;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */ class BrowseActivity$onCreate$1$1$1$1 extends FunctionReferenceImpl
        implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        SettingsPage p0 = (SettingsPage) obj;
        Intrinsics.checkNotNullParameter(p0, "p0");
        SpaActivity spaActivity = (SpaActivity) ((BrowseActivity) this.receiver);
        spaActivity.getClass();
        String str = p0.sppName;
        SettingsPageProvider pageProvider = SettingsPageProviderKt.getPageProvider(str);
        boolean z = false;
        if ((pageProvider != null ? pageProvider.isEnabled() : false)
                && !SpaActivity.Companion.isSuwAndPageBlocked(spaActivity, str)) {
            z = true;
        }
        return Boolean.valueOf(z);
    }
}
