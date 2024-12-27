package com.samsung.android.knox.keystore;

import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ClientCertificateManager {
    public ClientCertificateManager(ContextInfo contextInfo) {}

    public boolean addPackageToExemptList(String str) {
        return false;
    }

    public boolean deleteCCMProfile() {
        return false;
    }

    public boolean deleteCSRProfile(String str) {
        return false;
    }

    public boolean deleteCertificate(String str) {
        return false;
    }

    public byte[] generateCSRUsingTemplate(String str, String str2, String str3) {
        return null;
    }

    public CCMProfile getCCMProfile() {
        return null;
    }

    public String getCCMVersion() {
        return null;
    }

    public String getDefaultCertificateAlias() {
        return null;
    }

    public boolean installCertificate(
            CertificateProfile certificateProfile, byte[] bArr, String str) {
        return false;
    }

    public boolean isCCMPolicyEnabledForPackage(String str) {
        return false;
    }

    public boolean removePackageFromExemptList(String str) {
        return false;
    }

    public boolean setCCMProfile(CCMProfile cCMProfile) {
        return false;
    }

    public boolean setCSRProfile(CSRProfile cSRProfile) {
        return false;
    }
}
