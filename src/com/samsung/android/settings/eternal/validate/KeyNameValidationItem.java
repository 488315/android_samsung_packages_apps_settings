package com.samsung.android.settings.eternal.validate;

import android.text.TextUtils;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class KeyNameValidationItem {
    public String mAttributeNames;
    public String mKey;
    public String mResultType;

    public KeyNameValidationItem(String str, String str2, String str3) {
        this.mKey = str;
        this.mResultType = str2;
        this.mAttributeNames = str3;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("key = ");
        sb.append(this.mKey);
        sb.append("\n        result = ");
        sb.append(this.mResultType);
        sb.append(", attr = ");
        String str = this.mAttributeNames;
        if (TextUtils.isEmpty(str)) {
            str = "value";
        }
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, str, "\n");
    }
}
