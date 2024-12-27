package com.samsung.android.knox.ucm.core.jcajce;

import android.os.Bundle;
import android.os.Process;
import android.security.keystore.KeyProtection;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.crypto.SecretKey;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class UcmKeystore extends KeyStoreSpi {
    public static final String NAME = "KNOX";
    public static final List<String> SUPPORTED_ALGORITHMS =
            Arrays.asList(
                    "aes",
                    "hmacmd5",
                    "hmacsha1",
                    "hmacsha224",
                    "hmacsha256",
                    "hmacsha384",
                    "hmacsha512");
    public static final String TAG = "UcmKeystore";
    public String mSource;

    public UcmKeystore(String str) {
        this.mSource = str;
    }

    @Override // java.security.KeyStoreSpi
    public Enumeration<String> engineAliases() {
        Log.d("UcmKeystore", "engineAliases ");
        Bundle saw =
                UniversalCredentialUtil.getInstance()
                        .saw(
                                new UniversalCredentialUtil.UcmUriBuilder(this.mSource)
                                        .setResourceId(2)
                                        .setUid(Process.myUid())
                                        .build(),
                                1);
        String[] stringArray =
                saw != null
                        ? saw.getStringArray(UcmAgentService.PLUGIN_STRINGARRAY_RESPONSE)
                        : null;
        if (stringArray == null || stringArray.length == 0) {
            return null;
        }
        Vector vector = new Vector();
        for (String str : stringArray) {
            vector.add(str);
        }
        return vector.elements();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(String str) {
        Log.d("UcmKeystore", "engineContainsAlias " + str);
        Bundle saw =
                UniversalCredentialUtil.getInstance()
                        .saw(
                                new UniversalCredentialUtil.UcmUriBuilder(this.mSource)
                                        .setResourceId(2)
                                        .setUid(Process.myUid())
                                        .setAlias(str)
                                        .build(),
                                1);
        String[] stringArray =
                saw != null
                        ? saw.getStringArray(UcmAgentService.PLUGIN_STRINGARRAY_RESPONSE)
                        : null;
        if (stringArray == null) {
            return false;
        }
        for (String str2 : stringArray) {
            if (str2 != null && str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(String str) throws KeyStoreException {
        Log.d("UcmKeystore", "engineDeleteEntry " + str);
        Bundle delete =
                UniversalCredentialUtil.getInstance()
                        .delete(
                                new UniversalCredentialUtil.UcmUriBuilder(this.mSource)
                                        .setResourceId(2)
                                        .setUid(Process.myUid())
                                        .setAlias(str)
                                        .build());
        if (!(delete != null
                ? delete.getBoolean(UcmAgentService.PLUGIN_BOOLEAN_RESPONSE)
                : false)) {
            throw new KeyStoreException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "failed to delete entry ", str));
        }
    }

    @Override // java.security.KeyStoreSpi
    public Certificate engineGetCertificate(String str) {
        Log.d("UcmKeystore", "engineGetCertificate " + str);
        Certificate[] engineGetCertificateChain = engineGetCertificateChain(str);
        if (engineGetCertificateChain != null) {
            return engineGetCertificateChain[0];
        }
        Log.d("UcmKeystore", "engineGetCertificate empty");
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public String engineGetCertificateAlias(Certificate certificate) {
        Log.d("UcmKeystore", "engineGetCertificateAlias ");
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public Certificate[] engineGetCertificateChain(String str) {
        Log.d("UcmKeystore", "engineGetCertificateChain " + str);
        Bundle certificateChain =
                UniversalCredentialUtil.getInstance()
                        .getCertificateChain(
                                new UniversalCredentialUtil.UcmUriBuilder(this.mSource)
                                        .setResourceId(2)
                                        .setUid(Process.myUid())
                                        .setAlias(str)
                                        .build());
        byte[] byteArray =
                certificateChain != null
                        ? certificateChain.getByteArray(UcmAgentService.PLUGIN_BYTEARRAY_RESPONSE)
                        : null;
        if (byteArray == null || byteArray.length == 0) {
            Log.d("UcmKeystore", "getCertificateChain null");
            return null;
        }
        try {
            List list =
                    (List)
                            CertificateFactory.getInstance("X.509")
                                    .generateCertificates(new ByteArrayInputStream(byteArray));
            return (Certificate[]) list.toArray(new Certificate[list.size()]);
        } catch (CertificateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // java.security.KeyStoreSpi
    public Date engineGetCreationDate(String str) {
        DialogFragment$$ExternalSyntheticOutline0.m("engineGetCreationDate ", str, "UcmKeystore");
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public Key engineGetKey(String str, char[] cArr)
            throws NoSuchAlgorithmException, UnrecoverableKeyException {
        Log.d("UcmKeystore", "engineGetKey " + str);
        String build =
                new UniversalCredentialUtil.UcmUriBuilder(this.mSource)
                        .setResourceId(2)
                        .setUid(Process.myUid())
                        .setAlias(str)
                        .build();
        UniversalCredentialUtil universalCredentialUtil = UniversalCredentialUtil.getInstance();
        Bundle keyType = universalCredentialUtil.getKeyType(build);
        if (keyType == null) {
            Log.d("UcmKeystore", "engineGetKey response is null");
            return null;
        }
        boolean z = keyType.getBoolean(UcmAgentService.PLUGIN_BOOLEAN_RESPONSE);
        Log.d(
                "UcmKeystore",
                "getKeyType: boolean result = "
                        + z
                        + " error code = "
                        + keyType.getInt(UcmAgentService.PLUGIN_ERROR_CODE));
        if (!z) {
            throw new UnrecoverableKeyException("Key not found");
        }
        int i = keyType.getInt(UcmAgentService.PLUGIN_KEY_TYPE_RESPONSE);
        String string = keyType.getString(UcmAgentService.PLUGIN_STRING_RESPONSE);
        Log.d("UcmKeystore", "getKeyType = " + i + "; algorithm = " + string);
        if (i == 1) {
            return universalCredentialUtil.getSecretKey(build, string);
        }
        if (i == 2) {
            return universalCredentialUtil.getPrivateKey(build);
        }
        throw new UnrecoverableKeyException("Key type not supported");
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(String str) {
        DialogFragment$$ExternalSyntheticOutline0.m(
                "engineIsCertificateEntry ", str, "UcmKeystore");
        return false;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(String str) {
        Log.d("UcmKeystore", "engineIsKeyEntry " + str);
        return engineContainsAlias(str);
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(InputStream inputStream, char[] cArr)
            throws IOException, NoSuchAlgorithmException, CertificateException {
        Log.d("UcmKeystore", "engineLoad");
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(String str, Certificate certificate)
            throws KeyStoreException {
        DialogFragment$$ExternalSyntheticOutline0.m(
                "engineSetCertificateEntry ", str, "UcmKeystore");
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetEntry(
            String str, KeyStore.Entry entry, KeyStore.ProtectionParameter protectionParameter)
            throws KeyStoreException {
        Log.d(
                "UcmKeystore",
                "engineSetEntry(): alias="
                        + str
                        + "; entry="
                        + entry
                        + "; param="
                        + protectionParameter);
        if (entry instanceof KeyStore.PrivateKeyEntry) {
            super.engineSetEntry(str, entry, protectionParameter);
            return;
        }
        if (protectionParameter != null && !(protectionParameter instanceof KeyProtection)) {
            throw new KeyStoreException(
                    "Usupported protection parameter class, only KeyProtection parameter is"
                        + " supported for SecretKeyEntry");
        }
        SecretKey secretKey = ((KeyStore.SecretKeyEntry) entry).getSecretKey();
        if (secretKey == null) {
            throw new KeyStoreException("Key is null");
        }
        importKey(str, secretKey, (KeyProtection) protectionParameter);
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr)
            throws KeyStoreException {
        String str2;
        byte[] bArr;
        DialogFragment$$ExternalSyntheticOutline0.m("engineSetKeyEntry ", str, "UcmKeystore");
        if (cArr != null && cArr.length > 0) {
            throw new KeyStoreException("entries cannot be protected with passwords");
        }
        if (key == null || key.getEncoded() == null) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "key.getEncoded() == null ", str, "UcmKeystore");
            str2 = null;
            bArr = null;
        } else {
            Log.d("UcmKeystore", "key.getEncoded() is not null " + str);
            str2 = key.getAlgorithm();
            if (!isKeySupported(str2, key.getFormat())) {
                throw new KeyStoreException("Key format not supported");
            }
            bArr = key.getEncoded();
            if (bArr == null) {
                throw new KeyStoreException("PrivateKey has no encoding");
            }
        }
        if (key instanceof PrivateKey) {
            importKeyPair(str, bArr, certificateArr, str2);
        } else {
            if (!(key instanceof SecretKey)) {
                throw new KeyStoreException("Key not supported");
            }
            importKey(str, (SecretKey) key, null);
        }
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        Log.d("UcmKeystore", "engineSize ");
        Bundle saw =
                UniversalCredentialUtil.getInstance()
                        .saw(
                                new UniversalCredentialUtil.UcmUriBuilder(this.mSource)
                                        .setResourceId(2)
                                        .setUid(Process.myUid())
                                        .build(),
                                1);
        String[] stringArray =
                saw != null
                        ? saw.getStringArray(UcmAgentService.PLUGIN_STRINGARRAY_RESPONSE)
                        : null;
        if (stringArray != null) {
            return stringArray.length;
        }
        return 0;
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(OutputStream outputStream, char[] cArr)
            throws IOException, NoSuchAlgorithmException, CertificateException {
        throw new UnsupportedOperationException("Can not serialize to OutputStream");
    }

    public final void importKey(String str, SecretKey secretKey, KeyProtection keyProtection)
            throws KeyStoreException {
        String build =
                new UniversalCredentialUtil.UcmUriBuilder(this.mSource)
                        .setResourceId(2)
                        .setUid(Process.myUid())
                        .setAlias(str)
                        .build();
        UniversalCredentialUtil universalCredentialUtil = UniversalCredentialUtil.getInstance();
        if (engineContainsAlias(str)) {
            Bundle delete = universalCredentialUtil.delete(build);
            if (!(delete != null
                    ? delete.getBoolean(UcmAgentService.PLUGIN_BOOLEAN_RESPONSE)
                    : false)) {
                throw new KeyStoreException(
                        AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                "failed to replace key ", str));
            }
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_SECRET_KEY, secretKey);
        bundle.putString(
                UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_ALGORITHM, secretKey.getAlgorithm());
        if (keyProtection != null) {
            bundle.putInt(UcmAgentProviderImpl.KEY_EXTRA_PURPOSE, keyProtection.getPurposes());
            bundle.putBoolean(
                    UcmAgentProviderImpl.KEY_EXTRA_RANDOMIZED_ENCRYPTION,
                    keyProtection.isRandomizedEncryptionRequired());
            if (keyProtection.getBlockModes() != null && keyProtection.getBlockModes().length > 0) {
                bundle.putString(
                        UcmAgentProviderImpl.KEY_EXTRA_BLOCK_MODES,
                        keyProtection.getBlockModes()[0]);
            }
            if (keyProtection.getEncryptionPaddings() != null
                    && keyProtection.getEncryptionPaddings().length > 0) {
                bundle.putString(
                        UcmAgentProviderImpl.KEY_EXTRA_SIGNATURE_PADDINGS,
                        keyProtection.getEncryptionPaddings()[0]);
            }
        }
        Bundle importKey = universalCredentialUtil.importKey(build, bundle);
        if (!(importKey != null
                ? importKey.getBoolean(UcmAgentService.PLUGIN_BOOLEAN_RESPONSE)
                : false)) {
            throw new KeyStoreException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "failed to import keypair ", str));
        }
    }

    public final void importKeyPair(
            String str, byte[] bArr, Certificate[] certificateArr, String str2)
            throws KeyStoreException {
        if (certificateArr == null || certificateArr.length == 0) {
            throw new KeyStoreException("failed to import keypair");
        }
        if (!(certificateArr[0] instanceof X509Certificate)) {
            throw new KeyStoreException(
                    "failed to import keypair - certificate is not X509Certificate");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (Certificate certificate : certificateArr) {
            try {
                byteArrayOutputStream.write(certificate.getEncoded());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CertificateEncodingException e2) {
                e2.printStackTrace();
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        UniversalCredentialUtil universalCredentialUtil = UniversalCredentialUtil.getInstance();
        String build =
                new UniversalCredentialUtil.UcmUriBuilder(this.mSource)
                        .setResourceId(2)
                        .setUid(Process.myUid())
                        .setAlias(str)
                        .build();
        if (engineContainsAlias(str) && bArr != null) {
            Bundle delete = universalCredentialUtil.delete(build);
            if (!(delete != null
                    ? delete.getBoolean(UcmAgentService.PLUGIN_BOOLEAN_RESPONSE)
                    : false)) {
                throw new KeyStoreException(
                        AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                                "failed to replace keypair ", str));
            }
        }
        Bundle importKeyPair =
                universalCredentialUtil.importKeyPair(
                        build,
                        bArr,
                        byteArray,
                        AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_ALGORITHM, str2));
        if (!(importKeyPair != null
                ? importKeyPair.getBoolean(UcmAgentService.PLUGIN_BOOLEAN_RESPONSE)
                : false)) {
            throw new KeyStoreException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "failed to import keypair ", str));
        }
    }

    public final boolean isKeySupported(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return "PKCS#8".equals(str2) || SUPPORTED_ALGORITHMS.contains(str.toLowerCase());
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr)
            throws KeyStoreException {
        throw new UnsupportedOperationException("Can not determine the encoding");
    }
}
