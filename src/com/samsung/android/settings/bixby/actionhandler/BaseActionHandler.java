package com.samsung.android.settings.bixby.actionhandler;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.sdk.bixby2.action.ResponseCallback;
import com.samsung.android.settings.bixby.control.actionparam.BaseActionParam;
import com.samsung.android.settings.bixby.converter.ResultConverter;
import com.samsung.android.settings.bixby.utils.BixbyUtils;
import com.samsung.android.settings.display.controller.EdgeScreenPreferenceController;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BaseActionHandler {
    public final Context mContext;
    public final ResultConverter mResultConverter;

    public BaseActionHandler(Context context) {
        this.mContext = context;
        ResultConverter resultConverter = new ResultConverter();
        HashMap hashMap = new HashMap();
        resultConverter.mMap = hashMap;
        hashMap.put("disabled_by_open_theme", "nighttheme_err_disabled_by_open_theme");
        hashMap.put(
                "disabled_by_accessibility_hight_contrasts_fonts",
                "nighttheme_err_disabled_by_accessibility_hight_contrasts_fonts");
        hashMap.put(
                "disabled_by_not_support_screen_mode",
                "whiteBalance_disabled_by_not_support_screen_mode");
        hashMap.put("disabled_by_natural", "whiteBalance_disabled_by_natural");
        hashMap.put(
                "disabled_by_eye_comfort_shield_on", "whiteBalance_disabled_by_eye_comfort_shield");
        hashMap.put(
                "disabled_by_accessibility_grey_scale",
                "disabled_by_ecs_toast_accessibility_grey_scale");
        hashMap.put(
                "disabled_by_accessibility_negative_color",
                "disabled_by_ecs_toast_accessibility_negative_color");
        hashMap.put(
                "disabled_by_accessibility_color_adjustment",
                "disabled_by_ecs_toast_accessibility_color_adjustment");
        hashMap.put(
                "disabled_by_accessibility_color_lens",
                "disabled_by_ecs_toast_accessibility_color_lens");
        hashMap.put("disabled_by_easy_mode_on", EdgeScreenPreferenceController.EASY_MODE);
        hashMap.put("disabled_by_talk_back_on", "edgepanel_err_talk_back_on");
        hashMap.put("disabled_by_universal_switch_on", "edgepanel_err_universal_switch_on");
        hashMap.put("disabled_by_psm_on", "aod_err_disabled_in_psm");
        hashMap.put("disabled_by_smart_view_connected", "aod_err_smart_view_connected");
        hashMap.put("disabled_by_color_reverse_on", "aod_err_color_reverse_on");
        hashMap.put("mute_state_off", "temporary_mute_state_off");
        hashMap.put("disabled_by_admin", "screentimeout_err_disabled_by_admin");
        hashMap.put("disabled_by_dex", "screentimeout_err_disabled_by_dex");
        hashMap.put("invalid_argument", "screentimeout_err_invalid_argument");
        this.mResultConverter = resultConverter;
    }

    public final void executeAction(
            Context context, String str, Bundle bundle, ResponseCallback responseCallback) {
        Log.i("BaseActionHandler", "executeAction() actionName : " + str);
        if (isAffectedByKnoxPolicy()
                && bundle != null
                && Build.VERSION.SEM_PLATFORM_INT >= 100500) {
            if ((!KnoxUtils.getApplicationRestrictions(context, 0).isEmpty()
                            ? Boolean.TRUE
                            : Boolean.FALSE)
                    .booleanValue()) {
                if (TextUtils.equals(bundle.getString("actionType"), "punchOut")) {
                    BixbyUtils.startSettingsMain(context);
                }
                try {
                    this.mResultConverter.getClass();
                    responseCallback.onComplete(ResultConverter.convert("restricted_by_knox"));
                    return;
                } catch (IllegalArgumentException e) {
                    Log.e("BaseActionHandler", e.getMessage());
                    return;
                }
            }
        }
        String executeActionInternal =
                executeActionInternal(new BaseActionParam(context, str, bundle));
        try {
            Log.i("BaseActionHandler", "callback response : " + executeActionInternal);
            responseCallback.onComplete(executeActionInternal);
        } catch (IllegalArgumentException e2) {
            Log.e("BaseActionHandler", e2.getMessage());
        }
    }

    public abstract String executeActionInternal(BaseActionParam baseActionParam);

    public boolean isAffectedByKnoxPolicy() {
        return !(this instanceof AODActionHandler);
    }
}
