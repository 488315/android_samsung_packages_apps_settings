package com.samsung.android.settings.bixby.actionhandler;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

import com.samsung.android.settings.bixby.control.actionparam.BaseActionParam;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.goodsettings.GoodSettingsTopLevelPreferenceParser;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DeviceQnAActionHandler extends BaseActionHandler {
    public static Bundle call(Context context, String str, Bundle bundle) {
        ContentProviderClient acquireUnstableContentProviderClient;
        Bundle bundle2 = new Bundle();
        try {
            acquireUnstableContentProviderClient =
                    context.getContentResolver()
                            .acquireUnstableContentProviderClient(
                                    "com.samsung.android.settings.intelligence.search.provider.SettingSearchProvider");
            try {
            } catch (Throwable th) {
                if (acquireUnstableContentProviderClient != null) {
                    try {
                        acquireUnstableContentProviderClient.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (SQLiteException | RemoteException e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("exception when start settings : "),
                    "DeviceQnAActionHandler");
            bundle2.putString(
                    GoodSettingsTopLevelPreferenceParser.PREFERENCE_INFO_IS_AVAILABLE,
                    "notIndexed");
        }
        if (acquireUnstableContentProviderClient != null) {
            bundle2 = acquireUnstableContentProviderClient.call(str, "null", bundle);
            acquireUnstableContentProviderClient.close();
            return bundle2;
        }
        Log.w("DeviceQnAActionHandler", "fail to acquire provider client");
        bundle2.putString(
                GoodSettingsTopLevelPreferenceParser.PREFERENCE_INFO_IS_AVAILABLE, "notIndexed");
        if (acquireUnstableContentProviderClient != null) {
            acquireUnstableContentProviderClient.close();
        }
        return bundle2;
    }

    @Override // com.samsung.android.settings.bixby.actionhandler.BaseActionHandler
    public final String executeActionInternal(BaseActionParam baseActionParam) {
        String parameterValue = baseActionParam.getParameterValue("authority");
        String parameterValue2 =
                baseActionParam.getParameterValue(GoodSettingsContract.EXTRA_NAME.POLICY_KEY);
        Bundle bundle = new Bundle();
        if (baseActionParam.mActionName.equals("CheckAvailableDeeplink")) {
            Context context = this.mContext;
            Bundle bundle2 = new Bundle();
            bundle2.putString("authority", parameterValue);
            bundle2.putString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, parameterValue2);
            Bundle call = call(context, "requestValidation", bundle2);
            if (call == null) {
                bundle =
                        AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                GoodSettingsTopLevelPreferenceParser.PREFERENCE_INFO_IS_AVAILABLE,
                                "notIndexed");
            } else {
                Bundle bundle3 = new Bundle();
                String string = call.getString("result");
                if (TextUtils.isEmpty(string)) {
                    bundle3.putString(
                            GoodSettingsTopLevelPreferenceParser.PREFERENCE_INFO_IS_AVAILABLE,
                            "notIndexed");
                } else {
                    bundle3.putString(
                            GoodSettingsTopLevelPreferenceParser.PREFERENCE_INFO_IS_AVAILABLE,
                            string);
                    bundle3.putString(
                            "extraDeeplink",
                            "bixby://com.android.settings/LaunchQnADeeplink/punchOut?authority="
                                    + parameterValue
                                    + "&key="
                                    + parameterValue2);
                }
                AbsAdapter$$ExternalSyntheticOutline0.m(
                        "isValidSettings() ", string, "DeviceQnAActionHandler");
                bundle = bundle3;
            }
        } else if (baseActionParam.mActionName.equals("LaunchQnADeeplink")) {
            Context context2 = this.mContext;
            Bundle bundle4 = new Bundle();
            bundle4.putString("authority", parameterValue);
            bundle4.putString(GoodSettingsContract.EXTRA_NAME.POLICY_KEY, parameterValue2);
            bundle = call(context2, "requestStart", bundle4);
        }
        return this.mResultConverter.convert(bundle, baseActionParam.mActionName);
    }

    @Override // com.samsung.android.settings.bixby.actionhandler.BaseActionHandler
    public final boolean isAffectedByKnoxPolicy() {
        return false;
    }
}
