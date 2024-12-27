package com.android.settings.wallpaper;

import android.R;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.internal.annotations.VisibleForTesting;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StyleSuggestionActivity extends StyleSuggestionActivityBase {
    @VisibleForTesting
    public static boolean isSuggestionComplete(Context context) {
        return (context.getResources().getBoolean(R.bool.config_enhanced_iwlan_handover_check)
                        && TextUtils.isEmpty(
                                Settings.Secure.getStringForUser(
                                        context.getContentResolver(),
                                        "theme_customization_overlay_packages",
                                        context.getUserId())))
                ? false
                : true;
    }
}
