package com.samsung.android.scloud.oem.lib.utils;

import android.util.JsonReader;
import android.util.JsonToken;

import com.samsung.android.knox.net.apn.ApnSettings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SCloudParser {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.scloud.oem.lib.utils.SCloudParser$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$android$util$JsonToken;

        static {
            int[] iArr = new int[JsonToken.values().length];
            $SwitchMap$android$util$JsonToken = iArr;
            try {
                iArr[JsonToken.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$util$JsonToken[JsonToken.NUMBER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$util$JsonToken[JsonToken.BEGIN_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$util$JsonToken[JsonToken.END_ARRAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$util$JsonToken[JsonToken.NAME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$util$JsonToken[JsonToken.BOOLEAN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$android$util$JsonToken[JsonToken.NULL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$android$util$JsonToken[JsonToken.BEGIN_ARRAY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$android$util$JsonToken[JsonToken.END_OBJECT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public static JSONArray toJSONArray(JsonReader jsonReader) {
        JSONArray jSONArray = new JSONArray();
        try {
            jsonReader.beginArray();
            boolean z = false;
            while (jsonReader.hasNext() && !z) {
                int i =
                        AnonymousClass1.$SwitchMap$android$util$JsonToken[
                                jsonReader.peek().ordinal()];
                if (i == 1) {
                    jSONArray.put(jsonReader.nextString());
                } else if (i == 2) {
                    jSONArray.put(new BigDecimal(jsonReader.nextString()));
                } else if (i == 3) {
                    jSONArray.put(toJSONObject(jsonReader));
                } else if (i == 4) {
                    z = true;
                }
            }
            jsonReader.endArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONArray;
    }

    public static JSONObject toJSONObject(JsonReader jsonReader) {
        JSONObject jSONObject = new JSONObject();
        try {
            jsonReader.beginObject();
            String str = ApnSettings.MVNO_NONE;
            boolean z = false;
            while (jsonReader.hasNext() && !z) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
                switch (AnonymousClass1.$SwitchMap$android$util$JsonToken[
                        jsonReader.peek().ordinal()]) {
                    case 1:
                        jSONObject.put(str, jsonReader.nextString());
                        continue;
                    case 2:
                        jSONObject.put(str, new BigDecimal(jsonReader.nextString()));
                        continue;
                    case 3:
                        jSONObject.put(str, toJSONObject(jsonReader));
                        continue;
                    case 4:
                    default:
                        jsonReader.skipValue();
                        continue;
                    case 5:
                        str = jsonReader.nextName();
                        continue;
                    case 6:
                        jSONObject.put(str, jsonReader.nextBoolean());
                        continue;
                    case 7:
                        jsonReader.nextNull();
                        jSONObject.put(str, (Object) null);
                        continue;
                    case 8:
                        jSONObject.put(str, toJSONArray(jsonReader));
                        continue;
                    case 9:
                        z = true;
                        continue;
                }
                e.printStackTrace();
            }
            jsonReader.endObject();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }
}
