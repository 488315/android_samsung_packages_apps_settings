package com.android.settings.privatespace;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.multiuser.Flags;
import android.os.UserManager;
import android.safetycenter.SafetyEvent;
import android.safetycenter.SafetySourceData;
import android.safetycenter.SafetySourceStatus;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.safetycenter.SafetyCenterManagerWrapper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PrivateSpaceSafetySource {
    public static void setSafetySourceData(Context context, SafetyEvent safetyEvent) {
        SafetyCenterManagerWrapper.get().getClass();
        if (!SafetyCenterManagerWrapper.isEnabled(context)) {
            Log.i("PrivateSpaceSafetySrc", "Safety Center disabled");
            return;
        }
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        PrivateSpaceMaintainer privateSpaceMaintainer = PrivateSpaceMaintainer.getInstance(context);
        if (Flags.enablePrivateSpaceFeatures()
                && Flags.blockPrivateSpaceCreation()
                && !privateSpaceMaintainer.mUserManager.canAddPrivateProfile()
                && !privateSpaceMaintainer.doesPrivateSpaceExist()) {
            Log.i(
                    "PrivateSpaceSafetySrc",
                    "Private Space not allowed for user " + context.getUser());
            return;
        }
        if (userManager != null && !userManager.isMainUser()) {
            Log.i("PrivateSpaceSafetySrc", "setSafetySourceData not main user");
            return;
        }
        if (!com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile()
                || !Flags.enablePrivateSpaceFeatures()) {
            SafetyCenterManagerWrapper.get().getClass();
            SafetyCenterManagerWrapper.setSafetySourceData(
                    context, "AndroidPrivateSpace", null, safetyEvent);
            return;
        }
        SafetySourceData build =
                new SafetySourceData.Builder()
                        .setStatus(
                                new SafetySourceStatus.Builder(
                                                context.getString(R.string.private_space_title),
                                                context.getString(R.string.private_space_summary),
                                                100)
                                        .setPendingIntent(
                                                PendingIntent.getActivity(
                                                        context,
                                                        0,
                                                        new Intent(
                                                                        context,
                                                                        (Class<?>)
                                                                                PrivateSpaceAuthenticationActivity
                                                                                        .class)
                                                                .setIdentifier(
                                                                        "AndroidPrivateSpace"),
                                                        67108864))
                                        .build())
                        .build();
        Log.d("PrivateSpaceSafetySrc", "Setting safety source data");
        SafetyCenterManagerWrapper.get().getClass();
        SafetyCenterManagerWrapper.setSafetySourceData(
                context, "AndroidPrivateSpace", build, safetyEvent);
    }
}
