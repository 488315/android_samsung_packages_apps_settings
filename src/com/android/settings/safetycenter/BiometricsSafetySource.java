package com.android.settings.safetycenter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.safetycenter.SafetyEvent;
import android.safetycenter.SafetySourceData;
import android.safetycenter.SafetySourceStatus;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricNavigationUtils;
import com.android.settings.biometrics.ParentalControlsUtils;
import com.android.settings.biometrics.activeunlock.ActiveUnlockStatusUtils;
import com.android.settings.biometrics.combination.CombinedBiometricStatusUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.utils.StringUtil;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.biometrics.face.FaceEntry;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintEntry;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BiometricsSafetySource {
    public static void onBiometricsChanged(Context context) {
        setSafetySourceData(context, new SafetyEvent.Builder(100).build());
    }

    public static void setBiometricSafetySourceData(
            Context context,
            String str,
            String str2,
            PendingIntent pendingIntent,
            boolean z,
            boolean z2,
            SafetyEvent safetyEvent) {
        SafetySourceData build =
                new SafetySourceData.Builder()
                        .setStatus(
                                new SafetySourceStatus.Builder(str, str2, (z && z2) ? 200 : 100)
                                        .setPendingIntent(pendingIntent)
                                        .setEnabled(z)
                                        .build())
                        .build();
        SafetyCenterManagerWrapper.get().getClass();
        SafetyCenterManagerWrapper.setSafetySourceData(
                context, "AndroidBiometrics", build, safetyEvent);
    }

    public static void setSafetySourceData(Context context, SafetyEvent safetyEvent) {
        String string;
        String string2;
        String string3;
        String string4;
        FaceManager faceManager;
        FaceManager faceManager2;
        SafetyCenterManagerWrapper.get().getClass();
        if (SafetyCenterManagerWrapper.isEnabled(context)) {
            UserHandle myUserHandle = Process.myUserHandle();
            int identifier = myUserHandle.getIdentifier();
            UserManager userManager = UserManager.get(context);
            UserHandle profileParent = userManager.getProfileParent(myUserHandle);
            if (profileParent != null) {
                myUserHandle = profileParent;
            }
            Context createContextAsUser = context.createContextAsUser(myUserHandle, 0);
            if (Flags.allowPrivateProfile()
                    && android.multiuser.Flags.enablePrivateSpaceFeatures()
                    && userManager.isPrivateProfile()) {
                SafetyCenterManagerWrapper.get().getClass();
                SafetyCenterManagerWrapper.setSafetySourceData(
                        context, "AndroidBiometrics", null, safetyEvent);
                return;
            }
            BiometricNavigationUtils biometricNavigationUtils =
                    new BiometricNavigationUtils(identifier);
            CombinedBiometricStatusUtils combinedBiometricStatusUtils =
                    new CombinedBiometricStatusUtils(context, identifier);
            ActiveUnlockStatusUtils activeUnlockStatusUtils = new ActiveUnlockStatusUtils(context);
            boolean isProfile = userManager.isProfile();
            boolean z = true;
            int i = combinedBiometricStatusUtils.mUserId;
            if (!isProfile && activeUnlockStatusUtils.isAvailable()) {
                RestrictedLockUtils.EnforcedAdmin disablingAdmin =
                        combinedBiometricStatusUtils.getDisablingAdmin();
                String titleForActiveUnlock = activeUnlockStatusUtils.getTitleForActiveUnlock();
                String summary = combinedBiometricStatusUtils.getSummary();
                PendingIntent activity =
                        PendingIntent.getActivity(
                                context,
                                10,
                                biometricNavigationUtils.getBiometricSettingsIntent(
                                        context,
                                        Settings.CombinedBiometricSettingsActivity.class.getName(),
                                        disablingAdmin,
                                        Bundle.EMPTY),
                                67108864);
                boolean z2 = disablingAdmin == null;
                FingerprintManager fingerprintManager =
                        combinedBiometricStatusUtils.mFingerprintManager;
                if ((fingerprintManager == null || !fingerprintManager.hasEnrolledFingerprints(i))
                        && ((faceManager2 = combinedBiometricStatusUtils.mFaceManager) == null
                                || !faceManager2.hasEnrolledTemplates(i))) {
                    z = false;
                }
                setBiometricSafetySourceData(
                        context, titleForActiveUnlock, summary, activity, z2, z, safetyEvent);
                return;
            }
            if (Utils.hasFingerprintHardware(context) && Utils.hasFaceHardware(context)) {
                RestrictedLockUtils.EnforcedAdmin disablingAdmin2 =
                        combinedBiometricStatusUtils.getDisablingAdmin();
                UserManager userManager2 =
                        (UserManager) context.getSystemService(UserManager.class);
                if (userManager2 == null || !userManager2.isProfile()) {
                    string4 =
                            context.getString(
                                    R.string.security_settings_biometric_preference_title);
                } else {
                    string4 =
                            context.getString(
                                    Utils.isPrivateProfile(context, i)
                                            ? R.string.private_space_biometric_unlock_title
                                            : R.string
                                                    .security_settings_work_biometric_preference_title);
                }
                String summary2 = combinedBiometricStatusUtils.getSummary();
                UserManager userManager3 =
                        (UserManager) context.getSystemService(UserManager.class);
                PendingIntent activity2 =
                        PendingIntent.getActivity(
                                createContextAsUser,
                                10,
                                biometricNavigationUtils
                                        .getBiometricSettingsIntent(
                                                context,
                                                (userManager3 == null || !userManager3.isProfile())
                                                        ? Settings.CombinedBiometricSettingsActivity
                                                                .class
                                                                .getName()
                                                        : Settings
                                                                .CombinedBiometricProfileSettingsActivity
                                                                .class
                                                                .getName(),
                                                disablingAdmin2,
                                                Bundle.EMPTY)
                                        .setIdentifier(Integer.toString(identifier)),
                                67108864);
                boolean z3 = disablingAdmin2 == null;
                FingerprintManager fingerprintManager2 =
                        combinedBiometricStatusUtils.mFingerprintManager;
                if ((fingerprintManager2 == null || !fingerprintManager2.hasEnrolledFingerprints(i))
                        && ((faceManager = combinedBiometricStatusUtils.mFaceManager) == null
                                || !faceManager.hasEnrolledTemplates(i))) {
                    z = false;
                }
                setBiometricSafetySourceData(
                        context, string4, summary2, activity2, z3, z, safetyEvent);
                return;
            }
            FaceManager faceManagerOrNull = Utils.getFaceManagerOrNull(context);
            if (!Utils.hasFaceHardware(context)) {
                FingerprintManager fingerprintManagerOrNull =
                        Utils.getFingerprintManagerOrNull(context);
                if (!Utils.hasFingerprintHardware(context)) {
                    SafetyCenterManagerWrapper.get().getClass();
                    SafetyCenterManagerWrapper.setSafetySourceData(
                            context, "AndroidBiometrics", null, safetyEvent);
                    return;
                }
                RestrictedLockUtils.EnforcedAdmin parentConsentRequired =
                        ParentalControlsUtils.parentConsentRequired(context, 2);
                UserManager userManager4 =
                        (UserManager) context.getSystemService(UserManager.class);
                if (userManager4 == null || !userManager4.isProfile()) {
                    string =
                            context.getString(
                                    R.string.security_settings_fingerprint_preference_title);
                } else {
                    string =
                            context.getString(
                                    Utils.isPrivateProfile(context, identifier)
                                            ? R.string.private_space_fingerprint_unlock_title
                                            : R.string
                                                    .security_settings_work_fingerprint_preference_title);
                }
                setBiometricSafetySourceData(
                        context,
                        string,
                        RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                                                context, 32, identifier)
                                        != null
                                ? context.getString(R.string.disabled_by_admin)
                                : fingerprintManagerOrNull.hasEnrolledFingerprints(identifier)
                                        ? StringUtil.getIcuPluralsString(
                                                context,
                                                fingerprintManagerOrNull
                                                        .getEnrolledFingerprints(identifier)
                                                        .size(),
                                                R.string
                                                        .security_settings_fingerprint_preference_summary)
                                        : SemPersonaManager.isKnoxId(identifier)
                                                ? context.getString(
                                                        R.string
                                                                .security_settings_fingerprint_profile_preference_summary_none)
                                                : context.getString(
                                                        R.string
                                                                .security_settings_fingerprint_preference_summary_none),
                        PendingIntent.getActivity(
                                createContextAsUser,
                                30,
                                biometricNavigationUtils
                                        .getBiometricSettingsIntent(
                                                context,
                                                FingerprintEntry.class.getName(),
                                                parentConsentRequired,
                                                Bundle.EMPTY)
                                        .setIdentifier(Integer.toString(identifier)),
                                67108864),
                        parentConsentRequired == null,
                        fingerprintManagerOrNull.hasEnrolledFingerprints(identifier),
                        safetyEvent);
                return;
            }
            RestrictedLockUtils.EnforcedAdmin parentConsentRequired2 =
                    ParentalControlsUtils.parentConsentRequired(context, 8);
            UserManager userManager5 = (UserManager) context.getSystemService(UserManager.class);
            if (userManager5 == null || !userManager5.isProfile()) {
                string2 = context.getString(R.string.security_settings_face_preference_title);
            } else {
                string2 =
                        context.getString(
                                Utils.isPrivateProfile(context, identifier)
                                        ? R.string.private_space_face_unlock_title
                                        : R.string.security_settings_face_profile_preference_title);
            }
            int i2 =
                    SemPersonaManager.isKnoxId(identifier)
                            ? R.string.security_settings_face_profile_preference_summary_none
                            : R.string.security_settings_face_preference_summary_none;
            if (RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                            context, 128, identifier)
                    != null) {
                string3 = context.getString(R.string.disabled_by_admin);
            } else {
                Resources resources = context.getResources();
                if (faceManagerOrNull.hasEnrolledTemplates(identifier)) {
                    i2 = R.string.security_settings_face_preference_summary;
                }
                string3 = resources.getString(i2);
            }
            setBiometricSafetySourceData(
                    context,
                    string2,
                    string3,
                    PendingIntent.getActivity(
                            createContextAsUser,
                            20,
                            biometricNavigationUtils
                                    .getBiometricSettingsIntent(
                                            context,
                                            FaceEntry.class.getName(),
                                            parentConsentRequired2,
                                            Bundle.EMPTY)
                                    .setIdentifier(Integer.toString(identifier)),
                            67108864),
                    parentConsentRequired2 == null,
                    faceManagerOrNull.hasEnrolledTemplates(identifier),
                    safetyEvent);
        }
    }
}
