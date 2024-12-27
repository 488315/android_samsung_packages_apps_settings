package com.android.settings.biometrics2.data.repository;

import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import android.util.Log;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintRepository {
    public final FingerprintManager mFingerprintManager;
    public List mSensorPropertiesCache;

    public FingerprintRepository(FingerprintManager fingerprintManager) {
        this.mFingerprintManager = fingerprintManager;
        fingerprintManager.addAuthenticatorsRegisteredCallback(
                new IFingerprintAuthenticatorsRegisteredCallback
                        .Stub() { // from class:
                                  // com.android.settings.biometrics2.data.repository.FingerprintRepository.1
                    public final void onAllAuthenticatorsRegistered(List list) {
                        FingerprintRepository.this.mSensorPropertiesCache = list;
                    }
                });
    }

    public final boolean canAssumeSfps() {
        FingerprintSensorPropertiesInternal firstFingerprintSensorPropertiesInternal =
                getFirstFingerprintSensorPropertiesInternal();
        return firstFingerprintSensorPropertiesInternal != null
                && firstFingerprintSensorPropertiesInternal.isAnySidefpsType();
    }

    public final boolean canAssumeUdfps() {
        FingerprintSensorPropertiesInternal firstFingerprintSensorPropertiesInternal =
                getFirstFingerprintSensorPropertiesInternal();
        return firstFingerprintSensorPropertiesInternal != null
                && firstFingerprintSensorPropertiesInternal.isAnyUdfpsType();
    }

    public final FingerprintSensorPropertiesInternal getFirstFingerprintSensorPropertiesInternal() {
        List list = this.mSensorPropertiesCache;
        if (list == null) {
            Log.e("FingerprintRepository", "Sensor properties cache is null");
            return null;
        }
        if (list.size() > 0) {
            return (FingerprintSensorPropertiesInternal) list.get(0);
        }
        return null;
    }

    public final int getNumOfEnrolledFingerprintsSize(int i) {
        List enrolledFingerprints = this.mFingerprintManager.getEnrolledFingerprints(i);
        if (enrolledFingerprints != null) {
            return enrolledFingerprints.size();
        }
        return 0;
    }
}
