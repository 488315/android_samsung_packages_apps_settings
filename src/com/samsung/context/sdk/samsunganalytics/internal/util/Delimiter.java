package com.samsung.context.sdk.samsunganalytics.internal.util;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Delimiter {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum Depth {
        ONE_DEPTH("\u0002", "\u0003"),
        TWO_DEPTH("\u0004", "\u0005"),
        THREE_DEPTH("\u0006", "\u0007");

        private String collDlm;
        private String keyvalueDlm;

        Depth(String str, String str2) {
            this.collDlm = str;
            this.keyvalueDlm = str2;
        }

        public final String getCollectionDLM() {
            return this.collDlm;
        }

        public final String getKeyValueDLM() {
            return this.keyvalueDlm;
        }
    }

    public static String makeDelimiterString(Map map, Depth depth) {
        String sb;
        String str = null;
        for (Map.Entry entry : map.entrySet()) {
            if (str == null) {
                sb = entry.getKey().toString();
            } else {
                StringBuilder m =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                str);
                m.append(depth.getCollectionDLM());
                StringBuilder m2 =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                m.toString());
                m2.append(entry.getKey());
                sb = m2.toString();
            }
            StringBuilder m3 =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(sb);
            m3.append(depth.getKeyValueDLM());
            StringBuilder m4 =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                            m3.toString());
            m4.append(entry.getValue());
            str = m4.toString();
        }
        return str;
    }
}
