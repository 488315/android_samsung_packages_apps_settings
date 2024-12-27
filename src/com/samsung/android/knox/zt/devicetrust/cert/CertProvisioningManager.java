package com.samsung.android.knox.zt.devicetrust.cert;

import android.content.Context;

import com.samsung.android.knox.zt.KnoxZtException;
import com.samsung.android.knox.zt.devicetrust.attestation.DeviceAttestationManager$$ExternalSyntheticOutline0;
import com.samsung.android.knox.zt.service.KnoxZtService;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CertProvisioningManager {
    public static volatile CertProvisioningManager sInstance;
    public final KnoxZtService mService;

    public CertProvisioningManager(Context context) throws KnoxZtException {
        try {
            this.mService = new KnoxZtService(context);
        } catch (Throwable th) {
            throw new KnoxZtException(
                    DeviceAttestationManager$$ExternalSyntheticOutline0.m(
                            "CertProvisioningManager failed : ", th));
        }
    }

    public static CertProvisioningManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (CertProvisioningManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new CertProvisioningManager(context);
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public int provisionCert(
            CertProvisionProfile certProvisionProfile,
            ICertProvisionListener iCertProvisionListener)
            throws KnoxZtException {
        try {
            return this.mService.provisionCert(certProvisionProfile, iCertProvisionListener);
        } catch (Throwable th) {
            throw new KnoxZtException(
                    DeviceAttestationManager$$ExternalSyntheticOutline0.m(
                            "provisionCert failed : ", th));
        }
    }
}
