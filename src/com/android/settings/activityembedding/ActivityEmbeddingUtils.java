package com.android.settings.activityembedding;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.FeatureFlagUtils;
import android.util.Log;

import androidx.window.embedding.ActivityEmbeddingController;
import androidx.window.embedding.SplitController;

import com.android.settings.Utils;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.homepage.SecTopLevelFeature;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ActivityEmbeddingUtils {
    public static final boolean SHOULD_ENABLE_LARGE_SCREEN_OPTIMIZATION =
            SystemProperties.getBoolean("persist.settings.large_screen_opt.enabled", false);

    public static boolean isAlreadyEmbedded(Activity activity) {
        return isEmbeddingActivityEnabled(activity)
                && ActivityEmbeddingController.getInstance(activity).isActivityEmbedded(activity);
    }

    public static boolean isEmbeddingActivityEnabled(Context context) {
        if (!isSettingsSplitEnabled(context)) {
            Log.d("ActivityEmbeddingUtils", "isSettingsSplitSupported = false");
            return false;
        }
        if (!SecTopLevelFeature.getInstance().getBoolean("flag_settings_support_large_screen")
                && !FeatureFlagUtils.isEnabled(context, "settings_support_large_screen")) {
            Log.d("ActivityEmbeddingUtils", "isFlagEnabled = false");
            return false;
        }
        if (!SecTopLevelFeature.getInstance().getBoolean("flag_user_setup_complete")
                && !WizardManagerHelper.isUserSetupComplete(context)) {
            Log.d("ActivityEmbeddingUtils", "isUserSetupComplete = false");
            return false;
        }
        if (SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) {
            Log.d("ActivityEmbeddingUtils", "isSecureFolderId = true");
            return false;
        }
        if (SecTopLevelFeature.getInstance().getBoolean("flag_force_single_view")) {
            Log.d("ActivityEmbeddingUtils", "getForceSingleView = true");
            return false;
        }
        StringBuilder sb = Utils.sBuilder;
        if (((ActivityManager) context.getSystemService(ActivityManager.class))
                        .getLockTaskModeState()
                == 1) {
            Log.d("ActivityEmbeddingUtils", "isLockTaskModeLocked = true");
            return false;
        }
        Log.d("ActivityEmbeddingUtils", "isEmbeddingActivityEnabled = true");
        return true;
    }

    public static boolean isSettingsSplitEnabled(Context context) {
        return SHOULD_ENABLE_LARGE_SCREEN_OPTIMIZATION
                && SplitController.getInstance(context).getSplitSupportStatus()
                        == SplitController.SplitSupportStatus.SPLIT_AVAILABLE;
    }
}
