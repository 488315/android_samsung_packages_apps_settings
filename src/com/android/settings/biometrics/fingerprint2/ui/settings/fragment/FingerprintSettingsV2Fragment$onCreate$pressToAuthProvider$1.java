package com.android.settings.biometrics.fingerprint2.ui.settings.fragment;

import android.R;
import android.content.Context;
import android.provider.Settings;
import com.samsung.android.knox.net.apn.ApnSettings;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "invoke", "()Ljava/lang/Boolean;"}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
final class FingerprintSettingsV2Fragment$onCreate$pressToAuthProvider$1 extends Lambda implements Function0 {
    final /* synthetic */ Context $context;
    final /* synthetic */ int $userHandle;

    /* JADX WARN: Type inference failed for: r0v6, types: [boolean, int] */
    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke */
    public final Object mo1068invoke() {
        int intForUser = Settings.Secure.getIntForUser(this.$context.getContentResolver(), "sfps_performant_auth_enabled_v2", -1, this.$userHandle);
        int i = intForUser;
        if (intForUser == -1) {
            ?? r0 = this.$context.getResources().getBoolean(R.bool.config_reduceBrightColorsAvailable);
            Settings.Secure.putIntForUser(this.$context.getContentResolver(), "sfps_performant_auth_enabled_v2", r0, this.$userHandle);
            i = r0;
        }
        return Boolean.valueOf(i == 1);
    }
}
