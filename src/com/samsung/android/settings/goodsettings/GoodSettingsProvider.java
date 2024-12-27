package com.samsung.android.settings.goodsettings;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.settings.goodsettings.policy.PolicyInfo;
import com.samsung.android.settings.goodsettings.policy.PolicyManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GoodSettingsProvider extends ContentProvider {
    public static final String METHOD_COMPARE_AND_MERGE = "compareAndMerge";
    public static final String METHOD_GET_POLICY = "getPolicy";
    public static final String METHOD_GET_TOP_LEVEL_PREFERENCES = "getTopLevelPreferences";
    public static final String METHOD_INITIALIZE = "initialize";
    public static final String METHOD_SET_POLICY = "setPolicy";
    private static final String TAG = "[GS]GoodSettingsProvider";
    private static final String[] mValidTypes = {
        GoodSettingsContract.POLICY_TYPE.BOOLEAN, GoodSettingsContract.POLICY_TYPE.STRING
    };

    public static Bundle setBundle(PolicyInfo policyInfo) {
        if (policyInfo == null
                || policyInfo.mKey == null
                || policyInfo.mType == null
                || policyInfo.mValue == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, policyInfo.mKey);
        bundle.putString("type", policyInfo.mType);
        String str = policyInfo.mType;
        str.getClass();
        if (str.equals(GoodSettingsContract.POLICY_TYPE.STRING)) {
            bundle.putString("value", policyInfo.mValue);
        } else if (str.equals(GoodSettingsContract.POLICY_TYPE.BOOLEAN)) {
            bundle.putBoolean("value", Boolean.parseBoolean(policyInfo.mValue));
        }
        return bundle;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        Bundle bundle2;
        boolean z = true;
        String str3 = null;
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "call() - method is empty");
            return null;
        }
        bundle2 = new Bundle();
        str.getClass();
        switch (str) {
            case "getTopLevelPreferences":
                if (bundle != null
                        && bundle.containsKey(GoodSettingsContract.EXTRA_NAME.PREFERENCE_KEYS)) {
                    ArrayList<String> stringArrayList =
                            bundle.getStringArrayList(
                                    GoodSettingsContract.EXTRA_NAME.PREFERENCE_KEYS);
                    ArrayList<String> stringArrayList2 = bundle.getStringArrayList("flags");
                    long flags =
                            (stringArrayList2 != null || stringArrayList2.isEmpty())
                                    ? 0L
                                    : GoodSettingsUtils.getFlags(stringArrayList2);
                    Bundle bundle3 = new Bundle();
                    bundle3.putString(
                            "home_configuration_top_level_preferences",
                            GoodSettingsTopLevelPreferenceParser.getTopLevelPreferences(
                                            getContext(), stringArrayList, flags)
                                    .toString());
                    return bundle3;
                }
                Log.e(TAG, "call() - Unsupported method : ".concat(str));
                return null;
            case "compareAndMerge":
                if (bundle != null
                        && bundle.containsKey(GoodSettingsContract.EXTRA_NAME.POLICY_KEY)) {
                    String string = bundle.getString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
                    if (!TextUtils.isEmpty(string)) {
                        PolicyManager policyManager = PolicyManager.getInstance(getContext());
                        Context context = getContext();
                        policyManager.getClass();
                        try {
                        } catch (Exception e) {
                            Log.e("[GS]PolicyManager", e.getMessage());
                        }
                        if ("home_configuration_top_level_preferences".equals(string)) {
                            String str4 = policyManager.getPolicy(context, string).mValue;
                            String merge =
                                    GoodSettingsTopLevelPreferenceParser.merge(context, str4);
                            if (!TextUtils.equals(str4, merge)) {
                                policyManager.setPolicy(
                                        context,
                                        new PolicyInfo(
                                                string,
                                                GoodSettingsContract.POLICY_TYPE.STRING,
                                                merge));
                                return setBundle(
                                        new PolicyInfo(
                                                string,
                                                GoodSettingsContract.POLICY_TYPE.BOOLEAN,
                                                Boolean.toString(z)));
                            }
                        }
                        z = false;
                        return setBundle(
                                new PolicyInfo(
                                        string,
                                        GoodSettingsContract.POLICY_TYPE.BOOLEAN,
                                        Boolean.toString(z)));
                    }
                }
                if (bundle != null) {
                    ArrayList<String> stringArrayList3 =
                            bundle.getStringArrayList(
                                    GoodSettingsContract.EXTRA_NAME.PREFERENCE_KEYS);
                    ArrayList<String> stringArrayList22 = bundle.getStringArrayList("flags");
                    if (stringArrayList22 != null) {
                        break;
                    }
                    Bundle bundle32 = new Bundle();
                    bundle32.putString(
                            "home_configuration_top_level_preferences",
                            GoodSettingsTopLevelPreferenceParser.getTopLevelPreferences(
                                            getContext(), stringArrayList3, flags)
                                    .toString());
                    return bundle32;
                }
                Log.e(TAG, "call() - Unsupported method : ".concat(str));
                return null;
            case "setPolicy":
                if (bundle != null
                        && bundle.containsKey(GoodSettingsContract.EXTRA_NAME.POLICY_KEY)
                        && bundle.containsKey("type")
                        && bundle.containsKey("value")) {
                    String string2 = bundle.getString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
                    final String string3 = bundle.getString("type");
                    if (!TextUtils.isEmpty(string2)
                            && Arrays.stream(mValidTypes)
                                    .anyMatch(
                                            new Predicate() { // from class:
                                                              // com.samsung.android.settings.goodsettings.GoodSettingsProvider$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Predicate
                                                public final boolean test(Object obj) {
                                                    String str5 = string3;
                                                    String str6 =
                                                            GoodSettingsProvider.METHOD_GET_POLICY;
                                                    return TextUtils.equals((String) obj, str5);
                                                }
                                            })) {
                        PolicyManager policyManager2 = PolicyManager.getInstance(getContext());
                        Context context2 = getContext();
                        String string4 = bundle.getString("type");
                        string4.getClass();
                        if (string4.equals(GoodSettingsContract.POLICY_TYPE.STRING)) {
                            str3 = bundle.getString("value");
                        } else if (string4.equals(GoodSettingsContract.POLICY_TYPE.BOOLEAN)) {
                            str3 = String.valueOf(bundle.getBoolean("value"));
                        }
                        policyManager2.setPolicy(context2, new PolicyInfo(string2, string3, str3));
                    }
                }
                return bundle2;
            case "getPolicy":
                if (bundle != null
                        && bundle.containsKey(GoodSettingsContract.EXTRA_NAME.POLICY_KEY)) {
                    String string5 = bundle.getString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
                    if (!TextUtils.isEmpty(string5)) {
                        return setBundle(
                                PolicyManager.getInstance(getContext())
                                        .getPolicy(getContext(), string5));
                    }
                }
                return bundle2;
            case "initialize":
                if (bundle != null) {
                    PolicyManager.getInstance(getContext())
                            .initializePolicy(
                                    getContext(),
                                    bundle.getString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY));
                }
                return bundle2;
            default:
                Log.e(TAG, "call() - Unsupported method : ".concat(str));
                return null;
        }
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("delete operation not supported currently.");
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        throw new UnsupportedOperationException("getType operation not supported currently.");
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("insert operation not supported currently.");
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        throw new UnsupportedOperationException("query operation not supported currently.");
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("update operation not supported currently.");
    }
}
