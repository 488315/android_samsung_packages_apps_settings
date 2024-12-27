package com.samsung.android.settings.goodsettings.policy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.goodsettings.GoodSettingsTopLevelPreferenceParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PolicyManager {
    private static final String TAG = "[GS]PolicyManager";
    private static PolicyManager mInstance;
    private long mLastUpdated;
    private ArrayList<PolicyInfo> mPolicies;
    private String mPolicyLocale;
    private Version mVersion;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class Version {
        private static final /* synthetic */ Version[] $VALUES;
        public static final Version V1;
        public static final Version V2;

        static {
            Version version = new Version("V1", 0);
            V1 = version;
            Version version2 = new Version("V2", 1);
            V2 = version2;
            $VALUES = new Version[] {version, version2};
        }

        public static Version valueOf(String str) {
            return (Version) Enum.valueOf(Version.class, str);
        }

        public static Version[] values() {
            return (Version[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0109 A[Catch: all -> 0x0103, IOException -> 0x0105, TRY_LEAVE, TryCatch #9 {IOException -> 0x0105, blocks: (B:96:0x00ff, B:83:0x0109), top: B:95:0x00ff, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0128 A[Catch: IOException -> 0x0116, TRY_ENTER, TRY_LEAVE, TryCatch #3 {IOException -> 0x0116, blocks: (B:94:0x0128, B:89:0x0112), top: B:80:0x00fd }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x00ff A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.android.settings.goodsettings.policy.PolicyManager getInstance(
            android.content.Context r9) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.goodsettings.policy.PolicyManager.getInstance(android.content.Context):com.samsung.android.settings.goodsettings.policy.PolicyManager");
    }

    public final long getLastUpdated() {
        return this.mLastUpdated;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final PolicyInfo getPolicy(Context context, String str) {
        PolicyInfo policyInfo;
        char c;
        String jsonElement;
        String language = Locale.getDefault().getLanguage();
        if (!TextUtils.equals(this.mPolicyLocale, language)) {
            PolicyInfo policyInfo2 =
                    (PolicyInfo)
                            this.mPolicies.stream()
                                    .filter(new PolicyManager$$ExternalSyntheticLambda3())
                                    .findAny()
                                    .orElse(null);
            if (policyInfo2 == null || TextUtils.isEmpty(policyInfo2.mValue)) {
                Log.d(
                        TAG,
                        "updatePolicyLocale: policy or policy.value is empty. policy = "
                                + policyInfo2);
            } else {
                String str2 = policyInfo2.mValue;
                HashSet<String> hashSet =
                        GoodSettingsTopLevelPreferenceParser.SKIPPING_PREFERENCE_KEY_SET;
                JsonArray jsonArray = (JsonArray) new Gson().fromJson(str2, JsonArray.class);
                if (jsonArray == null) {
                    DialogFragment$$ExternalSyntheticOutline0.m(
                            "updateLocale: json array is empty. dump = ",
                            str2,
                            "GoodSettingsTopLevelPreferenceParser");
                    jsonElement = null;
                } else {
                    Map convertToMap =
                            GoodSettingsTopLevelPreferenceParser.convertToMap(
                                    GoodSettingsTopLevelPreferenceParser.getTopLevelPreferences(
                                            context, null, GoodSettingsContract.FLAGS_PHASE_1));
                    Iterator it = jsonArray.elements.iterator();
                    while (it.hasNext()) {
                        JsonObject asJsonObject = ((JsonElement) it.next()).getAsJsonObject();
                        if (asJsonObject.members.containsKey(
                                GoodSettingsContract.EXTRA_NAME.POLICY_KEY)) {
                            String asString =
                                    asJsonObject
                                            .get(GoodSettingsContract.EXTRA_NAME.POLICY_KEY)
                                            .getAsString();
                            HashMap hashMap = (HashMap) convertToMap;
                            if (hashMap.containsKey(asString)) {
                                asJsonObject.addProperty(
                                        UniversalCredentialUtil.AGENT_TITLE,
                                        ((JsonObject) hashMap.get(asString))
                                                .get(UniversalCredentialUtil.AGENT_TITLE)
                                                .getAsString());
                            }
                        }
                    }
                    jsonElement = jsonArray.toString();
                }
                if (!TextUtils.isEmpty(jsonElement)) {
                    this.mPolicyLocale = language;
                    setPolicy(
                            context,
                            new PolicyInfo(policyInfo2.mKey, policyInfo2.mType, jsonElement));
                }
            }
        }
        Optional findAny =
                this.mPolicies.stream()
                        .filter(new PolicyManager$$ExternalSyntheticLambda1(1, str))
                        .findAny();
        str.getClass();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -2000414648:
                if (str.equals(GoodSettingsContract.SAMSUNG_ACCOUNT_POLICY_KEY_HIDE_EMAIL)) {
                    c2 = 0;
                    break;
                }
                break;
            case -605247382:
                if (str.equals("home_configuration_top_level_preferences")) {
                    c2 = 1;
                    break;
                }
                break;
            case 525226638:
                if (str.equals(GoodSettingsContract.SAMSUNG_ACCOUNT_POLICY_KEY_SHOW_NICK_NAME)) {
                    c = 2;
                    c2 = c;
                    break;
                }
                break;
            case 540100469:
                if (str.equals(GoodSettingsContract.IS_ENABLED)) {
                    c = 3;
                    c2 = c;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                policyInfo =
                        new PolicyInfo(
                                GoodSettingsContract.SAMSUNG_ACCOUNT_POLICY_KEY_HIDE_EMAIL,
                                GoodSettingsContract.POLICY_TYPE.BOOLEAN,
                                String.valueOf(false));
                break;
            case 1:
                this.mPolicyLocale = Locale.getDefault().getLanguage();
                policyInfo =
                        new PolicyInfo(
                                "home_configuration_top_level_preferences",
                                GoodSettingsContract.POLICY_TYPE.STRING,
                                GoodSettingsTopLevelPreferenceParser.getTopLevelPreferences(
                                                context, null, GoodSettingsContract.FLAGS_PHASE_1)
                                        .toString());
                break;
            case 2:
                policyInfo =
                        new PolicyInfo(
                                GoodSettingsContract.SAMSUNG_ACCOUNT_POLICY_KEY_SHOW_NICK_NAME,
                                GoodSettingsContract.POLICY_TYPE.BOOLEAN,
                                String.valueOf(false));
                break;
            case 3:
                policyInfo =
                        new PolicyInfo(
                                GoodSettingsContract.IS_ENABLED,
                                GoodSettingsContract.POLICY_TYPE.BOOLEAN,
                                String.valueOf(true));
                break;
            default:
                policyInfo = null;
                break;
        }
        return (PolicyInfo) findAny.orElse(policyInfo);
    }

    public final void initializePolicy(Context context, String str) {
        this.mPolicies.removeIf(new PolicyManager$$ExternalSyntheticLambda1(0, str));
        PolicyJSONManager.writeJson(context, new PolicyData(this.mPolicyLocale, this.mPolicies));
        this.mLastUpdated = System.currentTimeMillis();
    }

    public final boolean isEnabled(Context context) {
        PolicyInfo policy = getPolicy(context, GoodSettingsContract.IS_ENABLED);
        return policy == null
                || !TextUtils.equals(policy.mType, GoodSettingsContract.POLICY_TYPE.BOOLEAN)
                || Boolean.parseBoolean(policy.mValue);
    }

    public final boolean isSupportGoodSettingsV1() {
        return this.mVersion == Version.V1;
    }

    public final void setPolicy(Context context, PolicyInfo policyInfo) {
        this.mPolicies.removeIf(new PolicyManager$$ExternalSyntheticLambda1(2, policyInfo));
        this.mPolicies.add(policyInfo);
        PolicyJSONManager.writeJson(context, new PolicyData(this.mPolicyLocale, this.mPolicies));
        this.mLastUpdated = System.currentTimeMillis();
    }

    public final void setSupportGoodSettingsVersion(Version version) {
        this.mVersion = version;
    }
}
