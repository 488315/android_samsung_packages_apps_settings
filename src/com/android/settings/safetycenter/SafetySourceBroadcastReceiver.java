package com.android.settings.safetycenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.safetycenter.SafetyEvent;

import com.android.settings.privatespace.PrivateSpaceSafetySource;
import com.android.settings.security.ScreenLockPreferenceDetailsUtils;

import com.google.common.collect.ImmutableList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SafetySourceBroadcastReceiver extends BroadcastReceiver {
    public static final SafetyEvent EVENT_DEVICE_REBOOTED = new SafetyEvent.Builder(600).build();

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        SafetyCenterManagerWrapper.get().getClass();
        if (SafetyCenterManagerWrapper.isEnabled(context)) {
            if (!"android.safetycenter.action.REFRESH_SAFETY_SOURCES".equals(intent.getAction())) {
                if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                    SafetyEvent safetyEvent = EVENT_DEVICE_REBOOTED;
                    LockScreenSafetySource.setSafetySourceData(
                            context, new ScreenLockPreferenceDetailsUtils(context), safetyEvent);
                    BiometricsSafetySource.setSafetySourceData(context, safetyEvent);
                    PrivateSpaceSafetySource.setSafetySourceData(context, safetyEvent);
                    return;
                }
                return;
            }
            String[] stringArrayExtra =
                    intent.getStringArrayExtra(
                            "android.safetycenter.extra.REFRESH_SAFETY_SOURCE_IDS");
            String stringExtra =
                    intent.getStringExtra(
                            "android.safetycenter.extra.REFRESH_SAFETY_SOURCES_BROADCAST_ID");
            if (stringArrayExtra == null || stringArrayExtra.length <= 0 || stringExtra == null) {
                return;
            }
            SafetyEvent build =
                    new SafetyEvent.Builder(200).setRefreshBroadcastId(stringExtra).build();
            ImmutableList copyOf = ImmutableList.copyOf(stringArrayExtra);
            if (copyOf.contains("AndroidLockScreen")) {
                LockScreenSafetySource.setSafetySourceData(
                        context, new ScreenLockPreferenceDetailsUtils(context), build);
            }
            if (copyOf.contains("AndroidBiometrics")) {
                BiometricsSafetySource.setSafetySourceData(context, build);
            }
            if (copyOf.contains("AndroidPrivateSpace")) {
                PrivateSpaceSafetySource.setSafetySourceData(context, build);
            }
        }
    }
}
