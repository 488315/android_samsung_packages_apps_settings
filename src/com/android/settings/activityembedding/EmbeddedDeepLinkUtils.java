package com.android.settings.activityembedding;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.util.Log;

import com.android.settings.homepage.DeepLinkHomepageActivityInternal;
import com.android.settings.password.PasswordUtils;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class EmbeddedDeepLinkUtils {
    public static final Intent getTrampolineIntent(String str, Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intent intent2 = new Intent(intent);
        if (intent2.getSelector() != null) {
            intent2.setSelector(null);
        }
        Intent intent3 = new Intent("android.settings.SETTINGS_EMBED_DEEP_LINK_ACTIVITY");
        intent3.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        intent3.replaceExtras(intent2);
        intent3.putExtra("settings_large_screen_deep_link_intent_data", intent2.getData());
        intent2.setData(null);
        intent3.putExtra(
                "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI", intent2.toUri(1));
        intent3.putExtra(
                "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY", str);
        intent3.addFlags(33554432);
        return intent3;
    }

    public static final boolean tryStartMultiPaneDeepLink(
            Activity activity, Intent intent, String str) {
        Intent trampolineIntent;
        Intrinsics.checkNotNullParameter(activity, "<this>");
        Intrinsics.checkNotNullParameter(intent, "intent");
        intent.putExtra(
                "initial_calling_package",
                PasswordUtils.getCallingAppPackageName(activity.getActivityToken()));
        if (intent.getBooleanExtra("is_from_slice", false)) {
            String stringExtra =
                    intent.getStringExtra(
                            "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY");
            if (stringExtra != null && stringExtra.length() != 0) {
                str = stringExtra;
            }
            trampolineIntent = getTrampolineIntent(str, intent);
            trampolineIntent.setClass(activity, DeepLinkHomepageActivityInternal.class);
        } else {
            trampolineIntent = getTrampolineIntent(str, intent);
        }
        try {
            UserInfo userInfo =
                    ContextsKt.getUserManager(activity)
                            .getUserInfo(activity.getUser().getIdentifier());
            Intrinsics.checkNotNull(userInfo);
            if (((!userInfo.isManagedProfile() && !userInfo.isPrivateProfile())
                            || userInfo.isSecureFolder())
                    && !userInfo.isCloneProfile()) {
                activity.startActivity(trampolineIntent);
                return true;
            }
            trampolineIntent
                    .setClass(activity, DeepLinkHomepageActivityInternal.class)
                    .putExtra("user_handle", activity.getUser());
            activity.startActivityAsUser(
                    trampolineIntent,
                    ContextsKt.getUserManager(activity)
                            .getProfileParent(userInfo.id)
                            .getUserHandle());
            return true;
        } catch (ActivityNotFoundException unused) {
            Log.e("EmbeddedDeepLinkUtils", "Deep link homepage is not available to show 2-pane UI");
            return false;
        }
    }
}
