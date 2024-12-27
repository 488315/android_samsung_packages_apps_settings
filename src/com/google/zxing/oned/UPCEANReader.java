package com.google.zxing.oned;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class UPCEANReader extends OneDReader {
    public static final int[][] L_AND_G_PATTERNS;
    public static final int[][] L_PATTERNS;
    public static final int[] START_END_PATTERN = {1, 1, 1};
    public static final int[] MIDDLE_PATTERN = {1, 1, 1, 1, 1};
    public static final int[] END_PATTERN = {1, 1, 1, 1, 1, 1};
    public final StringBuilder decodeRowStringBuffer = new StringBuilder(20);
    public final UPCEANExtensionSupport extensionReader = new UPCEANExtensionSupport();
    public final EANManufacturerOrgSupport eanManSupport = new EANManufacturerOrgSupport();

    static {
        int[][] iArr = {
            new int[] {3, 2, 1, 1},
            new int[] {2, 2, 2, 1},
            new int[] {2, 1, 2, 2},
            new int[] {1, 4, 1, 1},
            new int[] {1, 1, 3, 2},
            new int[] {1, 2, 3, 1},
            new int[] {1, 1, 1, 4},
            new int[] {1, 3, 1, 2},
            new int[] {1, 2, 1, 3},
            new int[] {3, 1, 1, 2}
        };
        L_PATTERNS = iArr;
        int[][] iArr2 = new int[20][];
        L_AND_G_PATTERNS = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, 10);
        for (int i = 10; i < 20; i++) {
            int[] iArr3 = L_PATTERNS[i - 10];
            int[] iArr4 = new int[iArr3.length];
            for (int i2 = 0; i2 < iArr3.length; i2++) {
                iArr4[i2] = iArr3[(iArr3.length - i2) - 1];
            }
            L_AND_G_PATTERNS[i] = iArr4;
        }
    }

    public static boolean checkStandardUPCEANChecksum(CharSequence charSequence) {
        int length = charSequence.length();
        if (length == 0) {
            return false;
        }
        int i = length - 1;
        return getStandardUPCEANChecksum(charSequence.subSequence(0, i))
                == Character.digit(charSequence.charAt(i), 10);
    }

    public static int decodeDigit(BitArray bitArray, int[] iArr, int i, int[][] iArr2) {
        OneDReader.recordPattern(i, bitArray, iArr);
        int length = iArr2.length;
        float f = 0.48f;
        int i2 = -1;
        for (int i3 = 0; i3 < length; i3++) {
            float patternMatchVariance = OneDReader.patternMatchVariance(iArr, iArr2[i3], 0.7f);
            if (patternMatchVariance < f) {
                i2 = i3;
                f = patternMatchVariance;
            }
        }
        if (i2 >= 0) {
            return i2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public static int[] findGuardPattern(
            BitArray bitArray, int i, boolean z, int[] iArr, int[] iArr2) {
        int i2 = bitArray.size;
        int nextUnset = z ? bitArray.getNextUnset(i) : bitArray.getNextSet(i);
        int length = iArr.length;
        boolean z2 = z;
        int i3 = 0;
        int i4 = nextUnset;
        while (nextUnset < i2) {
            if (bitArray.get(nextUnset) != z2) {
                iArr2[i3] = iArr2[i3] + 1;
            } else {
                if (i3 != length - 1) {
                    i3++;
                } else {
                    if (OneDReader.patternMatchVariance(iArr2, iArr, 0.7f) < 0.48f) {
                        return new int[] {i4, nextUnset};
                    }
                    i4 += iArr2[0] + iArr2[1];
                    int i5 = i3 - 1;
                    System.arraycopy(iArr2, 2, iArr2, 0, i5);
                    iArr2[i5] = 0;
                    iArr2[i3] = 0;
                    i3--;
                }
                iArr2[i3] = 1;
                z2 = !z2;
            }
            nextUnset++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public static int[] findStartGuardPattern(BitArray bitArray) {
        int[] iArr = new int[3];
        int[] iArr2 = null;
        boolean z = false;
        int i = 0;
        while (!z) {
            Arrays.fill(iArr, 0, 3, 0);
            iArr2 = findGuardPattern(bitArray, i, false, START_END_PATTERN, iArr);
            int i2 = iArr2[0];
            int i3 = iArr2[1];
            int i4 = i2 - (i3 - i2);
            if (i4 >= 0) {
                z = bitArray.isRange(i4, i2);
            }
            i = i3;
        }
        return iArr2;
    }

    public static int getStandardUPCEANChecksum(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        for (int i2 = length - 1; i2 >= 0; i2 -= 2) {
            int charAt = charSequence.charAt(i2) - '0';
            if (charAt < 0 || charAt > 9) {
                throw FormatException.getFormatInstance();
            }
            i += charAt;
        }
        int i3 = i * 3;
        for (int i4 = length - 2; i4 >= 0; i4 -= 2) {
            int charAt2 = charSequence.charAt(i4) - '0';
            if (charAt2 < 0 || charAt2 > 9) {
                throw FormatException.getFormatInstance();
            }
            i3 += charAt2;
        }
        return (1000 - i3) % 10;
    }

    public boolean checkChecksum(String str) {
        return checkStandardUPCEANChecksum(str);
    }

    public int[] decodeEnd(int i, BitArray bitArray) {
        return findGuardPattern(bitArray, i, false, START_END_PATTERN, new int[3]);
    }

    public abstract int decodeMiddle(BitArray bitArray, int[] iArr, StringBuilder sb);

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i, BitArray bitArray, Map map) {
        return decodeRow(i, bitArray, findStartGuardPattern(bitArray), map);
    }

    public abstract BarcodeFormat getBarcodeFormat();

    public Result decodeRow(int i, BitArray bitArray, int[] iArr, Map map) {
        int i2;
        if (map != null) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                    map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK));
        }
        StringBuilder sb = this.decodeRowStringBuffer;
        sb.setLength(0);
        int[] decodeEnd = decodeEnd(decodeMiddle(bitArray, iArr, sb), bitArray);
        int i3 = decodeEnd[1];
        int i4 = (i3 - decodeEnd[0]) + i3;
        if (i4 >= bitArray.size || !bitArray.isRange(i3, i4)) {
            throw NotFoundException.getNotFoundInstance();
        }
        String sb2 = sb.toString();
        if (sb2.length() < 8) {
            throw FormatException.getFormatInstance();
        }
        if (!checkChecksum(sb2)) {
            throw ChecksumException.getChecksumInstance();
        }
        BarcodeFormat barcodeFormat = getBarcodeFormat();
        float f = i;
        String str = null;
        Result result =
                new Result(
                        sb2,
                        null,
                        new ResultPoint[] {
                            new ResultPoint((iArr[1] + iArr[0]) / 2.0f, f),
                            new ResultPoint((decodeEnd[1] + decodeEnd[0]) / 2.0f, f)
                        },
                        barcodeFormat);
        try {
            Result decodeRow = this.extensionReader.decodeRow(i, decodeEnd[1], bitArray);
            result.putMetadata(ResultMetadataType.UPC_EAN_EXTENSION, decodeRow.text);
            result.putAllMetadata(decodeRow.resultMetadata);
            ResultPoint[] resultPointArr = decodeRow.resultPoints;
            ResultPoint[] resultPointArr2 = result.resultPoints;
            if (resultPointArr2 == null) {
                result.resultPoints = resultPointArr;
            } else if (resultPointArr != null && resultPointArr.length > 0) {
                ResultPoint[] resultPointArr3 =
                        new ResultPoint[resultPointArr2.length + resultPointArr.length];
                System.arraycopy(resultPointArr2, 0, resultPointArr3, 0, resultPointArr2.length);
                System.arraycopy(
                        resultPointArr,
                        0,
                        resultPointArr3,
                        resultPointArr2.length,
                        resultPointArr.length);
                result.resultPoints = resultPointArr3;
            }
            i2 = decodeRow.text.length();
        } catch (ReaderException unused) {
            i2 = 0;
        }
        int[] iArr2 = map == null ? null : (int[]) map.get(DecodeHintType.ALLOWED_EAN_EXTENSIONS);
        if (iArr2 != null) {
            for (int i5 : iArr2) {
                if (i2 != i5) {}
            }
            throw NotFoundException.getNotFoundInstance();
        }
        if (barcodeFormat == BarcodeFormat.EAN_13 || barcodeFormat == BarcodeFormat.UPC_A) {
            EANManufacturerOrgSupport eANManufacturerOrgSupport = this.eanManSupport;
            synchronized (eANManufacturerOrgSupport) {
                if (((ArrayList) eANManufacturerOrgSupport.ranges).isEmpty()) {
                    eANManufacturerOrgSupport.add("US/CA", new int[] {0, 19});
                    eANManufacturerOrgSupport.add("US", new int[] {30, 39});
                    eANManufacturerOrgSupport.add("US/CA", new int[] {60, 139});
                    eANManufacturerOrgSupport.add("FR", new int[] {300, 379});
                    eANManufacturerOrgSupport.add(
                            "BG", new int[] {VolteConstants.ErrorCode.ALTERNATIVE_SERVICES});
                    eANManufacturerOrgSupport.add("SI", new int[] {383});
                    eANManufacturerOrgSupport.add("HR", new int[] {385});
                    eANManufacturerOrgSupport.add("BA", new int[] {387});
                    eANManufacturerOrgSupport.add("DE", new int[] {400, 440});
                    eANManufacturerOrgSupport.add("JP", new int[] {450, 459});
                    eANManufacturerOrgSupport.add("RU", new int[] {460, 469});
                    eANManufacturerOrgSupport.add("TW", new int[] {471});
                    eANManufacturerOrgSupport.add("EE", new int[] {474});
                    eANManufacturerOrgSupport.add("LV", new int[] {475});
                    eANManufacturerOrgSupport.add("AZ", new int[] {476});
                    eANManufacturerOrgSupport.add("LT", new int[] {477});
                    eANManufacturerOrgSupport.add("UZ", new int[] {478});
                    eANManufacturerOrgSupport.add("LK", new int[] {479});
                    eANManufacturerOrgSupport.add(
                            "PH", new int[] {VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE});
                    eANManufacturerOrgSupport.add("BY", new int[] {481});
                    eANManufacturerOrgSupport.add("UA", new int[] {482});
                    eANManufacturerOrgSupport.add(
                            "MD", new int[] {VolteConstants.ErrorCode.ADDRESS_INCOMPLETE});
                    eANManufacturerOrgSupport.add("AM", new int[] {485});
                    eANManufacturerOrgSupport.add(
                            "GE", new int[] {VolteConstants.ErrorCode.BUSY_HERE});
                    eANManufacturerOrgSupport.add(
                            "KZ", new int[] {VolteConstants.ErrorCode.REQUEST_TERMINATED});
                    eANManufacturerOrgSupport.add("HK", new int[] {489});
                    eANManufacturerOrgSupport.add("JP", new int[] {490, 499});
                    eANManufacturerOrgSupport.add("GB", new int[] {500, FileType.XHTML});
                    eANManufacturerOrgSupport.add("GR", new int[] {FileType.LOC});
                    eANManufacturerOrgSupport.add("LB", new int[] {FileType.PNK});
                    eANManufacturerOrgSupport.add("CY", new int[] {FileType.JSON});
                    eANManufacturerOrgSupport.add("MK", new int[] {FileType.EMC});
                    eANManufacturerOrgSupport.add("MT", new int[] {535});
                    eANManufacturerOrgSupport.add("IE", new int[] {539});
                    eANManufacturerOrgSupport.add("BE/LU", new int[] {540, 549});
                    eANManufacturerOrgSupport.add("PT", new int[] {560});
                    eANManufacturerOrgSupport.add("IS", new int[] {569});
                    eANManufacturerOrgSupport.add("DK", new int[] {570, 579});
                    eANManufacturerOrgSupport.add("PL", new int[] {590});
                    eANManufacturerOrgSupport.add("RO", new int[] {594});
                    eANManufacturerOrgSupport.add("HU", new int[] {599});
                    eANManufacturerOrgSupport.add("ZA", new int[] {600, 601});
                    eANManufacturerOrgSupport.add(
                            "GH", new int[] {VolteConstants.ErrorCode.DECLINE});
                    eANManufacturerOrgSupport.add("BH", new int[] {608});
                    eANManufacturerOrgSupport.add("MU", new int[] {609});
                    eANManufacturerOrgSupport.add("MA", new int[] {611});
                    eANManufacturerOrgSupport.add("DZ", new int[] {613});
                    eANManufacturerOrgSupport.add("KE", new int[] {616});
                    eANManufacturerOrgSupport.add("CI", new int[] {618});
                    eANManufacturerOrgSupport.add("TN", new int[] {619});
                    eANManufacturerOrgSupport.add("SY", new int[] {621});
                    eANManufacturerOrgSupport.add("EG", new int[] {622});
                    eANManufacturerOrgSupport.add("LY", new int[] {624});
                    eANManufacturerOrgSupport.add("JO", new int[] {625});
                    eANManufacturerOrgSupport.add("IR", new int[] {626});
                    eANManufacturerOrgSupport.add("KW", new int[] {627});
                    eANManufacturerOrgSupport.add("SA", new int[] {628});
                    eANManufacturerOrgSupport.add("AE", new int[] {629});
                    eANManufacturerOrgSupport.add("FI", new int[] {640, 649});
                    eANManufacturerOrgSupport.add("CN", new int[] {690, 695});
                    eANManufacturerOrgSupport.add(
                            "NO",
                            new int[] {
                                KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, 709
                            });
                    eANManufacturerOrgSupport.add("IL", new int[] {729});
                    eANManufacturerOrgSupport.add("SE", new int[] {730, 739});
                    eANManufacturerOrgSupport.add("GT", new int[] {740});
                    eANManufacturerOrgSupport.add("SV", new int[] {741});
                    eANManufacturerOrgSupport.add("HN", new int[] {742});
                    eANManufacturerOrgSupport.add("NI", new int[] {743});
                    eANManufacturerOrgSupport.add("CR", new int[] {744});
                    eANManufacturerOrgSupport.add("PA", new int[] {745});
                    eANManufacturerOrgSupport.add("DO", new int[] {746});
                    eANManufacturerOrgSupport.add("MX", new int[] {750});
                    eANManufacturerOrgSupport.add("CA", new int[] {754, 755});
                    eANManufacturerOrgSupport.add("VE", new int[] {759});
                    eANManufacturerOrgSupport.add("CH", new int[] {760, 769});
                    eANManufacturerOrgSupport.add("CO", new int[] {770});
                    eANManufacturerOrgSupport.add("UY", new int[] {773});
                    eANManufacturerOrgSupport.add("PE", new int[] {775});
                    eANManufacturerOrgSupport.add("BO", new int[] {777});
                    eANManufacturerOrgSupport.add("AR", new int[] {779});
                    eANManufacturerOrgSupport.add("CL", new int[] {780});
                    eANManufacturerOrgSupport.add("PY", new int[] {784});
                    eANManufacturerOrgSupport.add("PE", new int[] {785});
                    eANManufacturerOrgSupport.add(
                            CertProvisionProfile.KEY_TYPE_EC, new int[] {786});
                    eANManufacturerOrgSupport.add("BR", new int[] {789, 790});
                    eANManufacturerOrgSupport.add("IT", new int[] {800, 839});
                    eANManufacturerOrgSupport.add("ES", new int[] {840, 849});
                    eANManufacturerOrgSupport.add("CU", new int[] {850});
                    eANManufacturerOrgSupport.add("SK", new int[] {858});
                    eANManufacturerOrgSupport.add("CZ", new int[] {859});
                    eANManufacturerOrgSupport.add("YU", new int[] {860});
                    eANManufacturerOrgSupport.add("MN", new int[] {865});
                    eANManufacturerOrgSupport.add("KP", new int[] {867});
                    eANManufacturerOrgSupport.add("TR", new int[] {868, 869});
                    eANManufacturerOrgSupport.add("NL", new int[] {870, 879});
                    eANManufacturerOrgSupport.add("KR", new int[] {880});
                    eANManufacturerOrgSupport.add("TH", new int[] {885});
                    eANManufacturerOrgSupport.add("SG", new int[] {888});
                    eANManufacturerOrgSupport.add("IN", new int[] {890});
                    eANManufacturerOrgSupport.add("VN", new int[] {893});
                    eANManufacturerOrgSupport.add("PK", new int[] {896});
                    eANManufacturerOrgSupport.add("ID", new int[] {899});
                    eANManufacturerOrgSupport.add("AT", new int[] {900, 919});
                    eANManufacturerOrgSupport.add("AU", new int[] {930, 939});
                    eANManufacturerOrgSupport.add("AZ", new int[] {940, 949});
                    eANManufacturerOrgSupport.add("MY", new int[] {955});
                    eANManufacturerOrgSupport.add("MO", new int[] {958});
                }
            }
            int parseInt = Integer.parseInt(sb2.substring(0, 3));
            int size = ((ArrayList) eANManufacturerOrgSupport.ranges).size();
            int i6 = 0;
            while (true) {
                if (i6 >= size) {
                    break;
                }
                int[] iArr3 = (int[]) ((ArrayList) eANManufacturerOrgSupport.ranges).get(i6);
                int i7 = iArr3[0];
                if (parseInt < i7) {
                    break;
                }
                if (iArr3.length != 1) {
                    i7 = iArr3[1];
                }
                if (parseInt <= i7) {
                    str =
                            (String)
                                    ((ArrayList) eANManufacturerOrgSupport.countryIdentifiers)
                                            .get(i6);
                    break;
                }
                i6++;
            }
            if (str != null) {
                result.putMetadata(ResultMetadataType.POSSIBLE_COUNTRY, str);
            }
        }
        result.putMetadata(
                ResultMetadataType.SYMBOLOGY_IDENTIFIER,
                "]E" + (barcodeFormat == BarcodeFormat.EAN_8 ? 4 : 0));
        return result;
    }
}
