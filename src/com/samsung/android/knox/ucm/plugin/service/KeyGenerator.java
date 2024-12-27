package com.samsung.android.knox.ucm.plugin.service;

import android.os.Bundle;

import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.SecretKey;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class KeyGenerator {
    public static final String SERVICE = "KeyGenerator";
    public final String algorithm;
    public final Provider provider;
    public UcmAgentProviderImpl.UcmAgentKeyGeneratorSpi spiImpl;

    public KeyGenerator(
            UcmAgentProviderImpl.UcmAgentKeyGeneratorSpi ucmAgentKeyGeneratorSpi,
            Provider provider,
            String str) {
        this.algorithm = str;
        this.spiImpl = ucmAgentKeyGeneratorSpi;
        this.provider = provider;
    }

    public static KeyGenerator getInstance(String str, Provider provider)
            throws NoSuchAlgorithmException {
        if (provider == null) {
            throw new IllegalArgumentException("provider == null");
        }
        if (str == null) {
            throw new NullPointerException("algorithm == null");
        }
        UcmAgentProviderImpl.UcmAgentKeyGeneratorSpi ucmAgentKeyGeneratorSpi =
                (UcmAgentProviderImpl.UcmAgentKeyGeneratorSpi)
                        UcmSpiUtil.getSpi(
                                SERVICE,
                                UcmAgentProviderImpl.UcmAgentKeyGeneratorSpi.class,
                                str,
                                provider);
        if (ucmAgentKeyGeneratorSpi != null) {
            return new KeyGenerator(ucmAgentKeyGeneratorSpi, provider, str);
        }
        throw new NullPointerException("spi == null");
    }

    public SecretKey generateKey() {
        return this.spiImpl.engineGenerateKey();
    }

    public int getErrorStatus() {
        return this.spiImpl.getErrorCode();
    }

    public void init(
            AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom)
            throws InvalidAlgorithmParameterException {
        this.spiImpl.engineInit(algorithmParameterSpec, secureRandom);
    }

    public void setProperty(Bundle bundle) {
        this.spiImpl.setProperty(bundle);
    }
}
