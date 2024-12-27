package com.samsung.android.settings.wifi.details;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SecWifiPreferenceControllerHelper {
    public final Map mValidators = new HashMap();
    public final Map mValidationUpdaters = new HashMap();
    public final AnonymousClass1 mValidatorCallback = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.details.SecWifiPreferenceControllerHelper$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ValidationUpdater {
        void update(boolean z);
    }

    public final void addValidator(
            WifiNetworkConfigPreferenceController wifiNetworkConfigPreferenceController,
            ValidationUpdater validationUpdater) {
        ((HashMap) this.mValidators)
                .put(
                        wifiNetworkConfigPreferenceController,
                        Boolean.valueOf(
                                wifiNetworkConfigPreferenceController.ipAndProxyFieldsAreValid()));
        wifiNetworkConfigPreferenceController.mCallback = this.mValidatorCallback;
        ((HashMap) this.mValidationUpdaters)
                .put(wifiNetworkConfigPreferenceController, validationUpdater);
    }
}
