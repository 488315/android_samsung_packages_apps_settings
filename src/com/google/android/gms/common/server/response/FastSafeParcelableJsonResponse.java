package com.google.android.gms.common.server.response;

import android.os.Parcelable;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.server.converter.StringToIntConverter;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class FastSafeParcelableJsonResponse implements Parcelable {
    public static final Object zaD(FastJsonResponse$Field fastJsonResponse$Field, Object obj) {
        FastJsonResponse$FieldConverter fastJsonResponse$FieldConverter =
                fastJsonResponse$Field.zak;
        if (fastJsonResponse$FieldConverter == null) {
            return obj;
        }
        StringToIntConverter stringToIntConverter =
                (StringToIntConverter) fastJsonResponse$FieldConverter;
        stringToIntConverter.getClass();
        String str = (String) stringToIntConverter.zac.get(((Integer) obj).intValue());
        return (str == null && stringToIntConverter.zab.containsKey("gms_unknown"))
                ? "gms_unknown"
                : str;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        Map map;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().isInstance(obj)) {
            return false;
        }
        FastSafeParcelableJsonResponse fastSafeParcelableJsonResponse =
                (FastSafeParcelableJsonResponse) obj;
        SafeParcelResponse safeParcelResponse = (SafeParcelResponse) this;
        zan zanVar = safeParcelResponse.zad;
        if (zanVar == null) {
            map = null;
        } else {
            String str = safeParcelResponse.zae;
            Preconditions.checkNotNull(str);
            map = (Map) zanVar.zab.get(str);
        }
        for (FastJsonResponse$Field fastJsonResponse$Field : map.values()) {
            if (isFieldSet(fastJsonResponse$Field)) {
                if (!fastSafeParcelableJsonResponse.isFieldSet(fastJsonResponse$Field)
                        || !Objects.equal(
                                getFieldValue(fastJsonResponse$Field),
                                fastSafeParcelableJsonResponse.getFieldValue(
                                        fastJsonResponse$Field))) {
                    return false;
                }
            } else if (fastSafeParcelableJsonResponse.isFieldSet(fastJsonResponse$Field)) {
                return false;
            }
        }
        return true;
    }

    public final Object getFieldValue(FastJsonResponse$Field fastJsonResponse$Field) {
        String str = fastJsonResponse$Field.zae;
        if (fastJsonResponse$Field.zag == null) {
            return getValueObject(str);
        }
        boolean z = getValueObject(str) == null;
        String str2 = fastJsonResponse$Field.zae;
        if (!z) {
            throw new IllegalStateException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "Concrete field shouldn't be value object: ", str2));
        }
        try {
            char upperCase = Character.toUpperCase(str.charAt(0));
            String substring = str.substring(1);
            StringBuilder sb = new StringBuilder(String.valueOf(substring).length() + 4);
            sb.append("get");
            sb.append(upperCase);
            sb.append(substring);
            Class[] clsArr = new Class[0];
            return getClass().getMethod(sb.toString(), null).invoke(this, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object getValueObject(String str) {
        return null;
    }

    public final int hashCode() {
        Map map;
        SafeParcelResponse safeParcelResponse = (SafeParcelResponse) this;
        zan zanVar = safeParcelResponse.zad;
        if (zanVar == null) {
            map = null;
        } else {
            String str = safeParcelResponse.zae;
            Preconditions.checkNotNull(str);
            map = (Map) zanVar.zab.get(str);
        }
        int i = 0;
        for (FastJsonResponse$Field fastJsonResponse$Field : map.values()) {
            if (isFieldSet(fastJsonResponse$Field)) {
                Object fieldValue = getFieldValue(fastJsonResponse$Field);
                Preconditions.checkNotNull(fieldValue);
                i = (i * 31) + fieldValue.hashCode();
            }
        }
        return i;
    }

    public final boolean isFieldSet(FastJsonResponse$Field fastJsonResponse$Field) {
        if (fastJsonResponse$Field.zac != 11) {
            return isPrimitiveFieldSet(fastJsonResponse$Field.zae);
        }
        if (fastJsonResponse$Field.zad) {
            throw new UnsupportedOperationException("Concrete type arrays not supported");
        }
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    public boolean isPrimitiveFieldSet(String str) {
        return false;
    }
}
