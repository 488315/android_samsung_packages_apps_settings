package com.google.zxing.oned.rss.expanded.decoders;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.google.zxing.NotFoundException;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.configuration.DATA;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class FieldParser {
    public static final Map FOUR_DIGIT_DATA_LENGTH;
    public static final Map THREE_DIGIT_DATA_LENGTH;
    public static final Map THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH;
    public static final Map TWO_DIGIT_DATA_LENGTH;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DataLength {
        public final int length;
        public final boolean variable;

        public DataLength(int i, boolean z) {
            this.variable = z;
            this.length = i;
        }

        public static DataLength fixed(int i) {
            return new DataLength(i, false);
        }

        public static DataLength variable(int i) {
            return new DataLength(i, true);
        }
    }

    static {
        HashMap hashMap = new HashMap();
        TWO_DIGIT_DATA_LENGTH = hashMap;
        hashMap.put("00", DataLength.fixed(18));
        hashMap.put("01", DataLength.fixed(14));
        hashMap.put("02", DataLength.fixed(14));
        hashMap.put(DATA.DM_FIELD_INDEX.SMS_OVER_IMS, DataLength.variable(20));
        hashMap.put(DATA.DM_FIELD_INDEX.SMS_WRITE_UICC, DataLength.fixed(6));
        hashMap.put(DATA.DM_FIELD_INDEX.SIP_T1_TIMER, DataLength.fixed(6));
        hashMap.put(DATA.DM_FIELD_INDEX.SIP_T2_TIMER, DataLength.fixed(6));
        hashMap.put(DATA.DM_FIELD_INDEX.SIP_TA_TIMER, DataLength.fixed(6));
        hashMap.put(DATA.DM_FIELD_INDEX.SIP_TC_TIMER, DataLength.fixed(6));
        hashMap.put(DATA.DM_FIELD_INDEX.SIP_TF_TIMER, DataLength.fixed(2));
        hashMap.put(DATA.DM_FIELD_INDEX.SIP_TG_TIMER, DataLength.variable(20));
        hashMap.put(DATA.DM_FIELD_INDEX.SIP_TH_TIMER, DataLength.variable(29));
        hashMap.put(DATA.DM_FIELD_INDEX.LVC_BETA_SETTING, DataLength.variable(8));
        hashMap.put(DATA.DM_FIELD_INDEX.PUBLISH_TIMER_EXTEND, DataLength.variable(8));
        for (int i = 90; i <= 99; i++) {
            TWO_DIGIT_DATA_LENGTH.put(String.valueOf(i), DataLength.variable(30));
        }
        HashMap hashMap2 = new HashMap();
        THREE_DIGIT_DATA_LENGTH = hashMap2;
        hashMap2.put("240", DataLength.variable(30));
        hashMap2.put("241", DataLength.variable(30));
        hashMap2.put("242", DataLength.variable(6));
        hashMap2.put("250", DataLength.variable(30));
        hashMap2.put("251", DataLength.variable(30));
        hashMap2.put("253", DataLength.variable(17));
        hashMap2.put("254", DataLength.variable(20));
        hashMap2.put("400", DataLength.variable(30));
        hashMap2.put("401", DataLength.variable(30));
        hashMap2.put("402", DataLength.fixed(17));
        hashMap2.put("403", DataLength.variable(30));
        hashMap2.put("410", DataLength.fixed(13));
        hashMap2.put("411", DataLength.fixed(13));
        hashMap2.put("412", DataLength.fixed(13));
        hashMap2.put("413", DataLength.fixed(13));
        hashMap2.put("414", DataLength.fixed(13));
        hashMap2.put("420", DataLength.variable(20));
        hashMap2.put("421", DataLength.variable(15));
        hashMap2.put("422", DataLength.fixed(3));
        hashMap2.put("423", DataLength.variable(15));
        hashMap2.put("424", DataLength.fixed(3));
        hashMap2.put("425", DataLength.fixed(3));
        hashMap2.put("426", DataLength.fixed(3));
        THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH = new HashMap();
        for (int i2 = FileType.PPS; i2 <= 316; i2++) {
            THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH.put(String.valueOf(i2), DataLength.fixed(6));
        }
        for (int i3 = FileType.XLSX; i3 <= 336; i3++) {
            THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH.put(String.valueOf(i3), DataLength.fixed(6));
        }
        for (int i4 = FileType.CELL; i4 <= 357; i4++) {
            THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH.put(String.valueOf(i4), DataLength.fixed(6));
        }
        for (int i5 = 360; i5 <= 369; i5++) {
            THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH.put(String.valueOf(i5), DataLength.fixed(6));
        }
        Map map = THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH;
        map.put("390", DataLength.variable(15));
        map.put("391", DataLength.variable(18));
        map.put("392", DataLength.variable(15));
        map.put("393", DataLength.variable(18));
        map.put("703", DataLength.variable(30));
        HashMap hashMap3 = new HashMap();
        FOUR_DIGIT_DATA_LENGTH = hashMap3;
        hashMap3.put("7001", DataLength.fixed(13));
        hashMap3.put("7002", DataLength.variable(30));
        hashMap3.put("7003", DataLength.fixed(10));
        hashMap3.put("8001", DataLength.fixed(14));
        hashMap3.put("8002", DataLength.variable(20));
        hashMap3.put("8003", DataLength.variable(30));
        hashMap3.put("8004", DataLength.variable(30));
        hashMap3.put("8005", DataLength.fixed(6));
        hashMap3.put("8006", DataLength.fixed(18));
        hashMap3.put("8007", DataLength.variable(30));
        hashMap3.put("8008", DataLength.variable(12));
        hashMap3.put("8018", DataLength.fixed(18));
        hashMap3.put("8020", DataLength.variable(25));
        hashMap3.put("8100", DataLength.fixed(6));
        hashMap3.put("8101", DataLength.fixed(10));
        hashMap3.put("8102", DataLength.fixed(2));
        hashMap3.put("8110", DataLength.variable(70));
        hashMap3.put("8200", DataLength.variable(70));
    }

    public static String parseFieldsInGeneralPurpose(String str) {
        if (str.isEmpty()) {
            return null;
        }
        if (str.length() < 2) {
            throw NotFoundException.getNotFoundInstance();
        }
        DataLength dataLength =
                (DataLength) ((HashMap) TWO_DIGIT_DATA_LENGTH).get(str.substring(0, 2));
        if (dataLength != null) {
            boolean z = dataLength.variable;
            int i = dataLength.length;
            return z ? processVariableAI(2, i, str) : processFixedAI(2, i, str);
        }
        if (str.length() < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        String substring = str.substring(0, 3);
        DataLength dataLength2 = (DataLength) ((HashMap) THREE_DIGIT_DATA_LENGTH).get(substring);
        if (dataLength2 != null) {
            boolean z2 = dataLength2.variable;
            int i2 = dataLength2.length;
            return z2 ? processVariableAI(3, i2, str) : processFixedAI(3, i2, str);
        }
        if (str.length() < 4) {
            throw NotFoundException.getNotFoundInstance();
        }
        DataLength dataLength3 =
                (DataLength) ((HashMap) THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH).get(substring);
        if (dataLength3 != null) {
            boolean z3 = dataLength3.variable;
            int i3 = dataLength3.length;
            return z3 ? processVariableAI(4, i3, str) : processFixedAI(4, i3, str);
        }
        DataLength dataLength4 =
                (DataLength) ((HashMap) FOUR_DIGIT_DATA_LENGTH).get(str.substring(0, 4));
        if (dataLength4 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        boolean z4 = dataLength4.variable;
        int i4 = dataLength4.length;
        return z4 ? processVariableAI(4, i4, str) : processFixedAI(4, i4, str);
    }

    public static String processFixedAI(int i, int i2, String str) {
        if (str.length() < i) {
            throw NotFoundException.getNotFoundInstance();
        }
        String substring = str.substring(0, i);
        int i3 = i2 + i;
        if (str.length() < i3) {
            throw NotFoundException.getNotFoundInstance();
        }
        String substring2 = str.substring(i, i3);
        String str2 = "(" + substring + ')' + substring2;
        String parseFieldsInGeneralPurpose = parseFieldsInGeneralPurpose(str.substring(i3));
        return parseFieldsInGeneralPurpose == null
                ? str2
                : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                        str2, parseFieldsInGeneralPurpose);
    }

    public static String processVariableAI(int i, int i2, String str) {
        String substring = str.substring(0, i);
        int min = Math.min(str.length(), i2 + i);
        String substring2 = str.substring(i, min);
        String str2 = "(" + substring + ')' + substring2;
        String parseFieldsInGeneralPurpose = parseFieldsInGeneralPurpose(str.substring(min));
        return parseFieldsInGeneralPurpose == null
                ? str2
                : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                        str2, parseFieldsInGeneralPurpose);
    }
}
