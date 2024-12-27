package com.samsung.android.settings.notification.brief.policy;

import android.util.JsonWriter;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class PolicyJSONManager {
    public static void writePolicy(JsonWriter jsonWriter, long j, int i, SparseArray sparseArray) {
        jsonWriter.beginObject();
        jsonWriter.name("policy_version").value(j);
        jsonWriter.name("policy_type").value(i);
        jsonWriter.name("edge_lighting_policy");
        jsonWriter.beginArray();
        int size = sparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = sparseArray.keyAt(i2);
            HashMap hashMap = (HashMap) sparseArray.valueAt(i2);
            if (keyAt == 1 || keyAt == 2) {
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()) {
                    PolicyInfo policyInfo = (PolicyInfo) ((Map.Entry) it.next()).getValue();
                    jsonWriter.beginObject();
                    jsonWriter.name("item").value(policyInfo.item);
                    jsonWriter.name("category").value(policyInfo.category);
                    jsonWriter.name("range").value(policyInfo.range);
                    jsonWriter.name("versionCode").value(policyInfo.versionCode);
                    jsonWriter.name("color").value(policyInfo.color);
                    jsonWriter.endObject();
                }
            }
        }
        jsonWriter.endArray();
        HashMap hashMap2 = (HashMap) sparseArray.get(10);
        if (hashMap2 != null) {
            jsonWriter.name("edge_lighting_priority");
            jsonWriter.beginArray();
            Iterator it2 = hashMap2.entrySet().iterator();
            while (it2.hasNext()) {
                PolicyInfo policyInfo2 = (PolicyInfo) ((Map.Entry) it2.next()).getValue();
                jsonWriter.beginObject();
                jsonWriter.name("item").value(policyInfo2.item);
                jsonWriter.name("priority").value(policyInfo2.priority);
                jsonWriter.name("default_on").value(policyInfo2.defaultOn);
                jsonWriter.name("color").value(policyInfo2.color);
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
        }
        HashMap hashMap3 = (HashMap) sparseArray.get(11);
        if (hashMap3 != null) {
            jsonWriter.name("edge_lighting_whitelist");
            jsonWriter.beginArray();
            Iterator it3 = hashMap3.entrySet().iterator();
            while (it3.hasNext()) {
                PolicyInfo policyInfo3 = (PolicyInfo) ((Map.Entry) it3.next()).getValue();
                jsonWriter.beginObject();
                jsonWriter.name("item").value(policyInfo3.item);
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
        }
        jsonWriter.endObject();
    }
}
