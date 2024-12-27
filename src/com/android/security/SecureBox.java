package com.android.security;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;

import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import com.sec.ims.configuration.DATA;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECFieldFp;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class SecureBox {
    public static final BigInteger BIG_INT_02;
    public static final byte[] CONSTANT_01;
    public static final BigInteger EC_PARAM_A;
    public static final BigInteger EC_PARAM_B;
    public static final BigInteger EC_PARAM_P;

    @VisibleForTesting static final ECParameterSpec EC_PARAM_SPEC;
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final byte[] HKDF_INFO_WITHOUT_PUBLIC_KEY;
    public static final byte[] HKDF_INFO_WITH_PUBLIC_KEY;
    public static final byte[] HKDF_SALT;
    public static final byte[] VERSION;

    static {
        byte[] bArr = {2, 0};
        VERSION = bArr;
        Charset charset = StandardCharsets.UTF_8;
        HKDF_SALT = ArrayUtils.concat(new byte[][] {"SECUREBOX".getBytes(charset), bArr});
        HKDF_INFO_WITH_PUBLIC_KEY = "P256 HKDF-SHA-256 AES-128-GCM".getBytes(charset);
        HKDF_INFO_WITHOUT_PUBLIC_KEY = "SHARED HKDF-SHA-256 AES-128-GCM".getBytes(charset);
        CONSTANT_01 = new byte[] {1};
        EMPTY_BYTE_ARRAY = new byte[0];
        BIG_INT_02 = BigInteger.valueOf(2L);
        BigInteger bigInteger =
                new BigInteger(
                        "ffffffff00000001000000000000000000000000ffffffffffffffffffffffff", 16);
        EC_PARAM_P = bigInteger;
        BigInteger subtract =
                bigInteger.subtract(new BigInteger(DATA.DM_FIELD_INDEX.PUBLIC_USER_ID));
        EC_PARAM_A = subtract;
        BigInteger bigInteger2 =
                new BigInteger(
                        "5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b", 16);
        EC_PARAM_B = bigInteger2;
        EC_PARAM_SPEC =
                new ECParameterSpec(
                        new EllipticCurve(new ECFieldFp(bigInteger), subtract, bigInteger2),
                        new ECPoint(
                                new BigInteger(
                                        "6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296",
                                        16),
                                new BigInteger(
                                        "4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5",
                                        16)),
                        new BigInteger(
                                "ffffffff00000000ffffffffffffffffbce6faada7179e84f3b9cac2fc632551",
                                16),
                        1);
    }

    public static byte[] aesGcmInternal(
            SecretKey secretKey, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            try {
                cipher.init(1, secretKey, new GCMParameterSpec(128, bArr));
                try {
                    cipher.updateAAD(bArr3);
                    return cipher.doFinal(bArr2);
                } catch (AEADBadTagException e) {
                    throw e;
                } catch (BadPaddingException | IllegalBlockSizeException e2) {
                    throw new RuntimeException(e2);
                }
            } catch (InvalidAlgorithmParameterException e3) {
                throw new RuntimeException(e3);
            }
        } catch (NoSuchPaddingException e4) {
            throw new RuntimeException(e4);
        }
    }

    public static PublicKey decodePublicKey(byte[] bArr) {
        BigInteger bigInteger = new BigInteger(1, Arrays.copyOfRange(bArr, 1, 33));
        BigInteger bigInteger2 = new BigInteger(1, Arrays.copyOfRange(bArr, 33, 65));
        BigInteger bigInteger3 = EC_PARAM_P;
        if (bigInteger.compareTo(bigInteger3) >= 0
                || bigInteger2.compareTo(bigInteger3) >= 0
                || bigInteger.signum() == -1
                || bigInteger2.signum() == -1) {
            throw new InvalidKeyException("Point lies outside of the expected curve");
        }
        BigInteger bigInteger4 = BIG_INT_02;
        if (!bigInteger2
                .modPow(bigInteger4, bigInteger3)
                .equals(
                        bigInteger
                                .modPow(bigInteger4, bigInteger3)
                                .add(EC_PARAM_A)
                                .mod(bigInteger3)
                                .multiply(bigInteger)
                                .add(EC_PARAM_B)
                                .mod(bigInteger3))) {
            throw new InvalidKeyException("Point lies outside of the expected curve");
        }
        try {
            return KeyFactory.getInstance(CertProvisionProfile.KEY_TYPE_EC)
                    .generatePublic(
                            new ECPublicKeySpec(
                                    new ECPoint(bigInteger, bigInteger2), EC_PARAM_SPEC));
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encrypt(PublicKey publicKey, byte[] bArr, byte[] bArr2) {
        KeyPair generateKeyPair;
        byte[] generateSecret;
        byte[] bArr3;
        byte[] bArr4 = EMPTY_BYTE_ARRAY;
        if (publicKey == null && bArr4.length == 0) {
            throw new IllegalArgumentException("Both the public key and shared secret are empty");
        }
        if (bArr == null) {
            bArr = bArr4;
        }
        if (bArr2 == null) {
            bArr2 = bArr4;
        }
        if (publicKey == null) {
            bArr3 = HKDF_INFO_WITHOUT_PUBLIC_KEY;
            generateKeyPair = null;
            generateSecret = bArr4;
        } else {
            KeyPairGenerator keyPairGenerator =
                    KeyPairGenerator.getInstance(CertProvisionProfile.KEY_TYPE_EC);
            try {
                try {
                    keyPairGenerator.initialize(new ECGenParameterSpec("prime256v1"));
                    generateKeyPair = keyPairGenerator.generateKeyPair();
                } catch (InvalidAlgorithmParameterException e) {
                    throw new NoSuchAlgorithmException("Unable to find the NIST P-256 curve", e);
                }
            } catch (InvalidAlgorithmParameterException unused) {
                keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
                generateKeyPair = keyPairGenerator.generateKeyPair();
            }
            PrivateKey privateKey = generateKeyPair.getPrivate();
            KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH");
            try {
                keyAgreement.init(privateKey);
                keyAgreement.doPhase(publicKey, true);
                generateSecret = keyAgreement.generateSecret();
                bArr3 = HKDF_INFO_WITH_PUBLIC_KEY;
            } catch (RuntimeException e2) {
                throw new InvalidKeyException(e2);
            }
        }
        byte[] bArr5 = new byte[12];
        new SecureRandom().nextBytes(bArr5);
        byte[] concat = ArrayUtils.concat(new byte[][] {generateSecret, bArr4});
        byte[] bArr6 = HKDF_SALT;
        Mac mac = Mac.getInstance("HmacSHA256");
        try {
            mac.init(new SecretKeySpec(bArr6, "HmacSHA256"));
            try {
                mac.init(new SecretKeySpec(mac.doFinal(concat), "HmacSHA256"));
                mac.update(bArr3);
                try {
                    byte[] aesGcmInternal =
                            aesGcmInternal(
                                    new SecretKeySpec(
                                            Arrays.copyOf(mac.doFinal(CONSTANT_01), 16), "AES"),
                                    bArr5,
                                    bArr2,
                                    bArr);
                    byte[] bArr7 = VERSION;
                    if (generateKeyPair == null) {
                        return ArrayUtils.concat(new byte[][] {bArr7, bArr5, aesGcmInternal});
                    }
                    ECPoint w = ((ECPublicKey) generateKeyPair.getPublic()).getW();
                    byte[] byteArray = w.getAffineX().toByteArray();
                    byte[] byteArray2 = w.getAffineY().toByteArray();
                    byte[] bArr8 = new byte[65];
                    System.arraycopy(
                            byteArray2, 0, bArr8, 65 - byteArray2.length, byteArray2.length);
                    System.arraycopy(byteArray, 0, bArr8, 33 - byteArray.length, byteArray.length);
                    bArr8[0] = 4;
                    return ArrayUtils.concat(new byte[][] {bArr7, bArr8, bArr5, aesGcmInternal});
                } catch (AEADBadTagException e3) {
                    throw new RuntimeException(e3);
                }
            } catch (InvalidKeyException e4) {
                throw new RuntimeException(e4);
            }
        } catch (InvalidKeyException e5) {
            throw new RuntimeException(e5);
        }
    }
}
