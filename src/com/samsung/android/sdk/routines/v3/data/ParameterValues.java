package com.samsung.android.sdk.routines.v3.data;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;
import com.samsung.android.sdk.routines.v3.internal.ExtraKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ParameterValues {
    public final HashMap a;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.sdk.routines.v3.data.ParameterValues$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ParameterValue.ValueType.values().length];
            a = iArr;
            try {
                iArr[1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[2] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[4] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[5] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[3] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[6] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ParameterValue {

        @SerializedName("VALUE")
        private Object a;

        @SerializedName("TYPE")
        private ValueType b;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        enum ValueType {
            UNKNOWN("UNKNOWN"),
            BOOLEAN("BOOLEAN"),
            NUMBER("NUMBER"),
            STRING("STRING"),
            /* JADX INFO: Fake field, exist only in values array */
            LIST_BOOLEAN("LIST{BOOLEAN}"),
            /* JADX INFO: Fake field, exist only in values array */
            LIST_NUMBER("LIST{NUMBER}"),
            /* JADX INFO: Fake field, exist only in values array */
            LIST_STRING("LIST{STRING}");

            public final String a;

            ValueType(String str) {
                this.a = str;
            }
        }

        public ParameterValue(Boolean bool) {
            this.a = bool;
            this.b = ValueType.BOOLEAN;
        }

        public final Object a() {
            return this.a;
        }

        public final String b() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("TYPE", this.b.a);
                int i = 0;
                switch (AnonymousClass1.a[this.b.ordinal()]) {
                    case 1:
                    case 2:
                        jSONObject.put("VALUE", this.a.toString());
                        break;
                    case 3:
                        JSONArray jSONArray = new JSONArray();
                        Boolean[] boolArr = (Boolean[]) this.a;
                        int length = boolArr.length;
                        while (i < length) {
                            jSONArray.put(boolArr[i].toString());
                            i++;
                        }
                        jSONObject.put("VALUE", jSONArray);
                        break;
                    case 4:
                        JSONArray jSONArray2 = new JSONArray();
                        Float[] fArr = (Float[]) this.a;
                        int length2 = fArr.length;
                        while (i < length2) {
                            jSONArray2.put(fArr[i].toString());
                            i++;
                        }
                        jSONObject.put("VALUE", jSONArray2);
                        break;
                    case 5:
                        jSONObject.put("VALUE", this.a);
                        break;
                    case 6:
                        JSONArray jSONArray3 = new JSONArray();
                        String[] strArr = (String[]) this.a;
                        int length3 = strArr.length;
                        while (i < length3) {
                            jSONArray3.put(strArr[i]);
                            i++;
                        }
                        jSONObject.put("VALUE", jSONArray3);
                        break;
                    default:
                        new Throwable().printStackTrace();
                        jSONObject.put("VALUE", this.a);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jSONObject.toString();
        }

        public static ParameterValue a(String str) {
            JSONObject jSONObject;
            int i;
            ValueType valueType;
            ParameterValue parameterValue = new ParameterValue();
            try {
                jSONObject = new JSONObject(str);
                String string = jSONObject.getString("TYPE");
                ValueType[] values = ValueType.values();
                int length = values.length;
                i = 0;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        valueType = ValueType.UNKNOWN;
                        break;
                    }
                    valueType = values[i2];
                    if (valueType.a.equalsIgnoreCase(string)) {
                        break;
                    }
                    i2++;
                }
                parameterValue.b = valueType;
            } catch (NumberFormatException | JSONException e) {
                e.printStackTrace();
            }
            switch (AnonymousClass1.a[valueType.ordinal()]) {
                case 1:
                    parameterValue.a = Boolean.valueOf(jSONObject.getString("VALUE"));
                    return parameterValue;
                case 2:
                    parameterValue.a = Float.valueOf(jSONObject.getString("VALUE"));
                    return parameterValue;
                case 3:
                    JSONArray jSONArray = jSONObject.getJSONArray("VALUE");
                    Boolean[] boolArr = new Boolean[jSONArray.length()];
                    while (i < jSONArray.length()) {
                        boolArr[i] = Boolean.valueOf(jSONArray.getString(i));
                        i++;
                    }
                    parameterValue.a = boolArr;
                    return parameterValue;
                case 4:
                    JSONArray jSONArray2 = jSONObject.getJSONArray("VALUE");
                    Float[] fArr = new Float[jSONArray2.length()];
                    while (i < jSONArray2.length()) {
                        fArr[i] = Float.valueOf(jSONArray2.getString(i));
                        i++;
                    }
                    parameterValue.a = fArr;
                    return parameterValue;
                case 5:
                    parameterValue.a = jSONObject.getString("VALUE");
                    return parameterValue;
                case 6:
                    JSONArray jSONArray3 = jSONObject.getJSONArray("VALUE");
                    String[] strArr = new String[jSONArray3.length()];
                    while (i < jSONArray3.length()) {
                        strArr[i] = jSONArray3.getString(i);
                        i++;
                    }
                    parameterValue.a = strArr;
                    return parameterValue;
                default:
                    new Throwable().printStackTrace();
                    parameterValue.a = jSONObject.get("VALUE");
                    return parameterValue;
            }
        }

        public ParameterValue(Float f) {
            this.a = f;
            this.b = ValueType.NUMBER;
        }

        public ParameterValue(String str) {
            this.a = str;
            this.b = ValueType.STRING;
        }
    }

    public ParameterValues() {
        this.a = new HashMap();
    }

    public static ParameterValues fromIntent(Intent intent) {
        String stringExtra = intent.getStringExtra(ExtraKey.PARAMETER_VALUES.a);
        return stringExtra != null ? fromJsonString(stringExtra) : new ParameterValues();
    }

    public static ParameterValues fromJsonString(String str) {
        HashMap hashMap = new HashMap();
        if (str == null || str.isEmpty()) {
            return new ParameterValues(hashMap);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                hashMap.put(next, ParameterValue.a(jSONObject.getString(next)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ParameterValues(hashMap);
    }

    public final Boolean getBoolean(String str, Boolean bool) {
        ParameterValue parameterValue = (ParameterValue) this.a.get(str);
        return (parameterValue == null || parameterValue.a() == null)
                ? bool
                : (Boolean) parameterValue.a();
    }

    public final Float getNumber(String str, Float f) {
        ParameterValue parameterValue = (ParameterValue) this.a.get(str);
        return (parameterValue == null || parameterValue.a() == null)
                ? f
                : (Float) parameterValue.a();
    }

    public final String getString(String str, String str2) {
        ParameterValue parameterValue = (ParameterValue) this.a.get(str);
        return (parameterValue == null || parameterValue.a() == null)
                ? str2
                : (String) parameterValue.a();
    }

    public final void put(String str, Boolean bool) {
        this.a.put(str, new ParameterValue(bool));
    }

    public final String toJsonString() {
        HashMap hashMap = new HashMap();
        this.a.entrySet().forEach(new ParameterValues$$ExternalSyntheticLambda0(hashMap));
        return new JSONObject(hashMap).toString();
    }

    public final void put(String str, Float f) {
        this.a.put(str, new ParameterValue(f));
    }

    public ParameterValues(HashMap hashMap) {
        HashMap hashMap2 = new HashMap();
        this.a = hashMap2;
        hashMap2.putAll(hashMap);
    }

    public final void put(String str, String str2) {
        this.a.put(str, new ParameterValue(str2));
    }
}
