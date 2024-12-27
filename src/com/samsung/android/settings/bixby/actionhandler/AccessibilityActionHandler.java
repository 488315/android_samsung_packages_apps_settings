package com.samsung.android.settings.bixby.actionhandler;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.sdk.bixby2.util.BixbyContextInfo;
import com.samsung.android.sdk.bixby2.util.BixbyUtils;
import com.samsung.android.settings.bixby.control.actionparam.BaseActionParam;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AccessibilityActionHandler extends BaseActionHandler {
    public String mAccessibilityMenu;
    public HashMap mActionHashMap;
    public String mActionName;
    public String mBixbyLocale;
    public String mFeatureKey;
    public String mFeatureValue;
    public Bundle mParamBundle;

    @Override // com.samsung.android.settings.bixby.actionhandler.BaseActionHandler
    public final String executeActionInternal(BaseActionParam baseActionParam) {
        Bundle bundle;
        this.mActionName = baseActionParam.mActionName;
        BixbyContextInfo bixbyContextInfo = BixbyUtils.getBixbyContextInfo(baseActionParam.mExtra);
        if (bixbyContextInfo != null) {
            this.mBixbyLocale = bixbyContextInfo.locale;
        }
        Log.d(
                "AccessibilityActionHandler",
                "actionName : "
                        + baseActionParam.mActionName
                        + ", mBixbyLocale : "
                        + this.mBixbyLocale
                        + ", bundle : "
                        + baseActionParam.mExtra);
        if (this.mBixbyLocale == null) {
            Log.d("AccessibilityActionHandler", "mBixbyLocale == null");
        }
        this.mParamBundle = new Bundle();
        Bundle bundle2 = baseActionParam.mExtra;
        String str = (String) this.mActionHashMap.get(this.mActionName);
        boolean isEmpty = TextUtils.isEmpty(str);
        String str2 = ApnSettings.MVNO_NONE;
        if (isEmpty) {
            str = ApnSettings.MVNO_NONE;
        } else {
            setActionParams(bundle2);
        }
        String str3 = "content://new.com.samsung.accessibility.bixby";
        if (TextUtils.isEmpty(str)) {
            bundle = baseActionParam.mExtra;
            String str4 = this.mActionName;
            str4.getClass();
            switch (str4) {
                case "viv.accessibilityApp.TurnOnSoundDetector":
                case "viv.accessibilityApp.GetDisclaimerPopup":
                case "viv.accessibilityApp.CheckSoundDetectorCondition":
                    str2 =
                            "viv.accessibilityApp.TurnOnSoundDetector".equals(this.mActionName)
                                    ? "turn_on"
                                    : "get";
                    setActionParams(bundle);
                    break;
                default:
                    str3 = ApnSettings.MVNO_NONE;
                    break;
            }
            Pair pair = new Pair(str2, str3);
            str = (String) pair.first;
            str3 = (String) pair.second;
        }
        String str5 = this.mAccessibilityMenu;
        if (str5 != null
                && !str5.contains("AssistantMenu")
                && !this.mAccessibilityMenu.contains("UniversalSwitch")
                && !this.mAccessibilityMenu.contains("AddUniversalSwitch")
                && !this.mAccessibilityMenu.contains("UniversalSwitchSettings")
                && !this.mAccessibilityMenu.contains("UniversalSwitchTutorial")
                && !this.mAccessibilityMenu.contains("InteractionControl")
                && !this.mAccessibilityMenu.contains("VoiceAccess")
                && !this.mAccessibilityMenu.contains("SingleTapMode")) {
            str3 = "content://com.samsung.android.settings.accessibility.bixby";
        }
        Bundle call =
                baseActionParam
                        .mContext
                        .getContentResolver()
                        .call(Uri.parse(str3), str, (String) null, this.mParamBundle);
        String str6 = (String) call.get("result");
        String str7 = (String) call.get("accessibilityMenu");
        String str8 = (String) call.get("menuValue");
        String str9 = (String) call.get("description");
        String str10 = (String) call.get("currentStatus");
        String str11 = (String) call.get("levelValue");
        String str12 = (String) call.get("menuName");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("result", str6);
            jSONObject.put("accessibilityMenu", str7);
            jSONObject.put("menuValue", str8);
            jSONObject.put("description", str9);
            jSONObject.put("currentStatus", str10);
            jSONObject.put("levelValue", str11);
            jSONObject.put("menuName", str12);
        } catch (JSONException e) {
            Log.e("AccessibilityActionHandler", "createResult() failed : " + e.toString());
        }
        StringBuilder sb = new StringBuilder("bundle : ");
        sb.append(call);
        sb.append(", result : ");
        sb.append(str6);
        sb.append(", accessibilityMenu : ");
        ConstraintWidget$$ExternalSyntheticOutline0.m(
                sb, str7, ", menuValue : ", str8, ", description : ");
        ConstraintWidget$$ExternalSyntheticOutline0.m(
                sb, str9, ", currentStatus : ", str10, ", levelValue : ");
        ConstraintWidget$$ExternalSyntheticOutline0.m(
                sb, str11, ", menuName : ", str12, ", jsonObject : ");
        sb.append(jSONObject.toString());
        Log.i("AccessibilityActionHandler", sb.toString());
        return jSONObject.toString();
    }

    @Override // com.samsung.android.settings.bixby.actionhandler.BaseActionHandler
    public final boolean isAffectedByKnoxPolicy() {
        return false;
    }

    public final void setActionParams(Bundle bundle) {
        HashMap hashMap = (HashMap) bundle.getSerializable("params");
        if (hashMap != null && !hashMap.isEmpty()) {
            for (Map.Entry entry : hashMap.entrySet()) {
                this.mFeatureKey = (String) entry.getKey();
                List list = (List) entry.getValue();
                if (list != null && list.size() > 0) {
                    this.mFeatureValue = (String) list.get(0);
                    if (!this.mFeatureKey.contains("menuValue")) {
                        this.mAccessibilityMenu = (String) list.get(0);
                    }
                }
                StringBuilder sb = new StringBuilder("for params  key : ");
                sb.append(this.mFeatureKey);
                sb.append(", value : ");
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        sb, this.mFeatureValue, "AccessibilityActionHandler");
                if (!TextUtils.isEmpty(this.mFeatureKey)
                        && !TextUtils.isEmpty(this.mFeatureValue)) {
                    StringBuilder sb2 = new StringBuilder("params  key : ");
                    sb2.append(this.mFeatureKey);
                    sb2.append(", value : ");
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            sb2, this.mFeatureValue, "AccessibilityActionHandler");
                    this.mParamBundle.putString(this.mFeatureKey, this.mFeatureValue);
                }
            }
        }
        String str = this.mActionName;
        if (str != null) {
            this.mParamBundle.putString("ActionName", str);
        }
        String str2 = this.mBixbyLocale;
        if (str2 != null) {
            this.mParamBundle.putString("BixbyLocale", str2);
        }
    }
}
