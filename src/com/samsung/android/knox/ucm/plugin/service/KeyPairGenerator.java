package com.samsung.android.knox.ucm.plugin.service;

import android.os.Bundle;

import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class KeyPairGenerator {
    public static final String SERVICE = "KeyPairGenerator";
    public final String algorithm;
    public final Provider provider;
    public UcmAgentProviderImpl.UcmAgentKeyPairGeneratorSpi spiImpl;

    public KeyPairGenerator(
            UcmAgentProviderImpl.UcmAgentKeyPairGeneratorSpi ucmAgentKeyPairGeneratorSpi,
            Provider provider,
            String str) {
        this.algorithm = str;
        this.spiImpl = ucmAgentKeyPairGeneratorSpi;
        this.provider = provider;
    }

    public static KeyPairGenerator getInstance(String str, Provider provider)
            throws NoSuchAlgorithmException {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null");
        }
        if (str == null) {
            throw new NullPointerException("algorithm == null");
        }
        UcmAgentProviderImpl.UcmAgentKeyPairGeneratorSpi ucmAgentKeyPairGeneratorSpi =
                (UcmAgentProviderImpl.UcmAgentKeyPairGeneratorSpi)
                        UcmSpiUtil.getSpi(
                                "KeyPairGenerator",
                                UcmAgentProviderImpl.UcmAgentKeyPairGeneratorSpi.class,
                                str,
                                provider);
        if (ucmAgentKeyPairGeneratorSpi != null) {
            return new KeyPairGenerator(ucmAgentKeyPairGeneratorSpi, provider, str);
        }
        throw new NullPointerException("spi == null");
    }

    public KeyPair generateKeyPair() {
        return this.spiImpl.generateKeyPair();
    }

    public int getErrorStatus() {
        return this.spiImpl.getErrorCode();
    }

    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom)
            throws InvalidAlgorithmParameterException {
        this.spiImpl.initialize(algorithmParameterSpec, (java.security.SecureRandom) null);
    }

    public void setProperty(Bundle bundle) {
        this.spiImpl.setProperty(bundle);
    }
}
