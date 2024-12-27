package com.samsung.android.knox.ucm.core.jcajce;

import android.os.Bundle;
import android.os.Process;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;

import java.security.SecureRandomSpi;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class UcmSecureRandom extends SecureRandomSpi {
    private String mSource;

    public UcmSecureRandom(String str) {
        this.mSource = str;
    }

    @Override // java.security.SecureRandomSpi
    public final byte[] engineGenerateSeed(int i) {
        Bundle generateSecureRandom =
                UniversalCredentialUtil.getInstance()
                        .generateSecureRandom(
                                new UniversalCredentialUtil.UcmUriBuilder(this.mSource)
                                        .setResourceId(2)
                                        .setUid(Process.myUid())
                                        .build(),
                                i,
                                null);
        if (generateSecureRandom == null) {
            return null;
        }
        return generateSecureRandom.getByteArray(UcmAgentService.PLUGIN_BYTEARRAY_RESPONSE);
    }

    public final void engineMixSeed(byte[] bArr) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override // java.security.SecureRandomSpi
    public final void engineNextBytes(byte[] bArr) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override // java.security.SecureRandomSpi
    public final void engineSetSeed(byte[] bArr) {
        throw new UnsupportedOperationException("Not supported");
    }
}
