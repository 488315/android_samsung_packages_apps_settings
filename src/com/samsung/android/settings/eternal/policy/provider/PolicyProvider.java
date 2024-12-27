package com.samsung.android.settings.eternal.policy.provider;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.eternal.log.EternalFileLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class PolicyProvider {
    public List mBackupSizeAllowedItems;
    public Context mContext;
    public List mDeferredRestorationItems;
    public List mRestoreRestrictedItems;
    public String mVersion;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.eternal.policy.provider.PolicyProvider$1, reason: invalid class name */
    class AnonymousClass1 extends TypeToken<List<PolicyItem>> {}

    public static List loadPolicy(String str, JSONObject jSONObject) {
        JSONArray jSONArray = jSONObject.getJSONArray(str);
        return (List) new Gson().fromJson(jSONArray.toString(), new AnonymousClass1().getType());
    }

    public abstract String getPolicyId();

    public abstract String getPolicyVersion();

    public String getRawPolicy() {
        return ApnSettings.MVNO_NONE;
    }

    public void initializePolicy() {
        try {
            this.mBackupSizeAllowedItems = new ArrayList();
            this.mRestoreRestrictedItems = new ArrayList();
            this.mDeferredRestorationItems = new ArrayList();
            if (this.mContext == null) {
                EternalFileLog.e(getPolicyId(), "initializePolicy() context is null");
                return;
            }
            String rawPolicy = getRawPolicy();
            if (TextUtils.isEmpty(rawPolicy)) {
                EternalFileLog.e(getPolicyId(), "initializeBackupPolicy() rawPolicy is null");
            } else {
                updatePolicyItems(rawPolicy);
            }
        } catch (JSONException e) {
            EternalFileLog.e(getPolicyId(), "initializePolicy() + " + e.toString());
        }
    }

    public boolean isValidProvider() {
        return (TextUtils.isEmpty(this.mVersion)
                        || ((ArrayList) this.mBackupSizeAllowedItems).isEmpty()
                        || ((ArrayList) this.mRestoreRestrictedItems).isEmpty())
                ? false
                : true;
    }

    public final void updatePolicyItems(String str) {
        JSONObject jSONObject = new JSONObject(str);
        this.mVersion = jSONObject.getString("policyVersion");
        ((ArrayList) this.mBackupSizeAllowedItems).addAll(loadPolicy("sizeLimitation", jSONObject));
        ((ArrayList) this.mRestoreRestrictedItems)
                .addAll(loadPolicy("restoreRestriction", jSONObject));
        ((ArrayList) this.mDeferredRestorationItems)
                .addAll(loadPolicy("deferredRestoration", jSONObject));
        EternalFileLog.i(
                getPolicyId(),
                "mBackupSizeAllowedItems : "
                        + ((ArrayList) this.mBackupSizeAllowedItems).size()
                        + " / mRestoreRestrictedItems : "
                        + ((ArrayList) this.mRestoreRestrictedItems).size()
                        + " / mDeferredRestorationItems : "
                        + ((ArrayList) this.mDeferredRestorationItems).size());
    }
}
