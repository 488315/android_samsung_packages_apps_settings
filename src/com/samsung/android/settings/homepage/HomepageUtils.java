package com.samsung.android.settings.homepage;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class HomepageUtils {
    public static final ArrayList SEPARATORS =
            new ArrayList(Arrays.asList(", ", "، ", "、", "，", "၊ "));
    public static final ArrayList SEPARATORS_REPLACEMENT_SKIP_LIST =
            new ArrayList(
                    Arrays.asList(
                            "top_level_knox_policy_notice",
                            "top_level_samsung_account",
                            "top_level_mde_suggestions"));

    public static void startActivity(
            Activity activity, Intent intent, int i, UserHandle userHandle) {
        if (activity == null) {
            Log.d("HomepageUtils", "activity is null.");
            return;
        }
        try {
            if (userHandle == null) {
                activity.startActivityForResult(null, intent, i, null);
            } else {
                activity.startActivityForResultAsUser(intent, i, null, userHandle);
            }
        } catch (ActivityNotFoundException unused) {
            Log.w("HomepageUtils", "No activity found for " + intent);
        }
    }
}
