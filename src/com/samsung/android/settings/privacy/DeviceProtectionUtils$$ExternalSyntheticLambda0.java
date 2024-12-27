package com.samsung.android.settings.privacy;

import android.content.Context;
import android.os.Bundle;

import androidx.core.util.Consumer;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class DeviceProtectionUtils$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Context f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ DeviceProtectionUtils$$ExternalSyntheticLambda0(
            Context context,
            SecurityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda0
                    securityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda0) {
        this.f$0 = context;
        this.f$1 = securityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Context context = this.f$0;
                Consumer consumer = this.f$1;
                try {
                    Bundle call =
                            context.getContentResolver()
                                    .call(
                                            DeviceProtectionUtils.AUTHORITY_URI,
                                            "dc_security_last_scan_time",
                                            (String) null,
                                            (Bundle) null);
                    if (call == null || !call.getBoolean("result")) {
                        consumer.accept(ApnSettings.MVNO_NONE);
                    } else {
                        consumer.accept(String.valueOf(call.getLong("key_last_scan_time")));
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    SemLog.e(
                            "DeviceProtectionUtils",
                            " Unknown authority or Last Scan Method not found " + e.getMessage());
                    consumer.accept(ApnSettings.MVNO_NONE);
                    return;
                }
            default:
                this.f$1.accept(
                        Integer.valueOf(
                                DeviceProtectionUtils.getDeviceProtectionSecurityInfo(this.f$0)));
                break;
        }
    }

    public /* synthetic */ DeviceProtectionUtils$$ExternalSyntheticLambda0(
            SecurityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda1
                    securityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda1,
            Context context) {
        this.f$1 = securityDashboardAppSecurityPreferenceController$$ExternalSyntheticLambda1;
        this.f$0 = context;
    }
}
