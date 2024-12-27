package com.samsung.android.settings.bixby.converter;

import android.util.Log;

import com.samsung.android.settings.bixby.control.actionparam.Parameter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ValueConverter {
    public static JSONArray convertParameterToJSONArray(List list) {
        if (list == null || list.isEmpty()) {
            Log.w("ValueConverter", "There is no parameter");
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Parameter parameter = (Parameter) it.next();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(parameter.key, parameter.value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }
}
