package com.android.settings.biometrics2.factory;

import android.app.Application;
import android.hardware.fingerprint.FingerprintManager;

import com.android.settings.Utils;
import com.android.settings.biometrics2.data.repository.FingerprintRepository;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BiometricsRepositoryProviderImpl {
    public static volatile FingerprintRepository sFingerprintRepository;

    public static FingerprintRepository getFingerprintRepository(Application application) {
        FingerprintManager fingerprintManagerOrNull =
                Utils.getFingerprintManagerOrNull(application);
        if (fingerprintManagerOrNull == null) {
            return null;
        }
        if (sFingerprintRepository == null) {
            synchronized (FingerprintRepository.class) {
                try {
                    if (sFingerprintRepository == null) {
                        sFingerprintRepository =
                                new FingerprintRepository(fingerprintManagerOrNull);
                    }
                } finally {
                }
            }
        }
        return sFingerprintRepository;
    }
}
