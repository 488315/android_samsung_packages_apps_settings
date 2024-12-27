package com.samsung.android.settings.accessibility.bixby.action.advanced;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.settings.Utils;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction;

import java.util.Map;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VoiceLabelAction extends BixbyControllerAction {
    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final void addCustomAction(Map map) {
        map.put(
                "viv.accessibilityApp.GetVoiceRecorderStatus",
                new BiFunction() { // from class:
                    // com.samsung.android.settings.accessibility.bixby.action.advanced.VoiceLabelAction$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        VoiceLabelAction.this.getClass();
                        Bundle bundle = new Bundle();
                        Uri uri = AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER;
                        bundle.putString(
                                "result",
                                Utils.hasPackage(
                                                (Context) obj,
                                                SemFloatingFeature.getInstance()
                                                        .getString(
                                                                "SEC_FLOATING_FEATURE_VOICERECORDER_CONFIG_PACKAGE_NAME"))
                                        ? "true"
                                        : "false");
                        return bundle;
                    }
                });
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyControllerAction
    public final String getControllerName(Context context) {
        return "com.samsung.android.settings.accessibility.vision.controllers.VoiceLabelPreferenceController";
    }

    @Override // com.samsung.android.settings.accessibility.bixby.action.BixbyActionTarget
    public final Intent getPunchoutIntent(Context context) {
        return new Intent("voicenote.intent.action.accessibility");
    }
}
