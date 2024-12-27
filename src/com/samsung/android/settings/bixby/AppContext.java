package com.samsung.android.settings.bixby;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppContext {
    public final JSONObject mAppContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public final JSONArray mLLMContext = new JSONArray();

        public final AppContext build() {
            Log.d("AppContext", "Builder.Build()");
            return new AppContext(this);
        }
    }

    public AppContext(Builder builder) {
        JSONObject jSONObject = new JSONObject();
        this.mAppContext = jSONObject;
        try {
            jSONObject.put("llmContext", builder.mLLMContext);
            jSONObject.put("llmCapsuleId", "samsung.settingsApp");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
