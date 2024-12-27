package com.android.settings.development;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Build;
import android.os.FileUtils;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.oemlock.OemLockManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.knoxguard.KnoxGuardManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class OemUnlockPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener,
                PreferenceControllerMixin,
                OnActivityResultListener {
    public static final IvParameterSpec ivParamSpec =
            new IvParameterSpec("i_love_office_tg".getBytes());
    public final Activity mActivity;
    public final Context mContext;
    public final DevelopmentSettingsDashboardFragment mFragment;
    public final OemLockManager mOemLockManager;
    public RestrictedSwitchPreference mPreference;
    public final TelephonyManager mTelephonyManager;
    public final UserManager mUserManager;

    public OemUnlockPreferenceController(
            Context context,
            FragmentActivity fragmentActivity,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        super(context);
        if (!("eng".equals(Build.TYPE) && Rune.isLDUModel())
                && TextUtils.equals(
                        SystemProperties.get("ro.oem_unlock_supported", "-9999"), "1")) {
            this.mOemLockManager = (OemLockManager) context.getSystemService("oem_lock");
        } else {
            this.mOemLockManager = null;
            Log.w("OemUnlockPreferenceController", "oem_unlock not supported.");
        }
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mActivity = fragmentActivity;
        this.mFragment = developmentSettingsDashboardFragment;
    }

    public static String decryptSKT(String str) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, getKey(), ivParamSpec);
            return new String(cipher.doFinal(Base64.decode(str, 0)));
        } catch (InvalidAlgorithmParameterException e) {
            Log.i(
                    "OemUnlockPreferenceController",
                    "sec_encrypt.decrypt() InvalidAlgorithmParameterException = " + e.toString());
            return null;
        } catch (InvalidKeyException e2) {
            Log.i(
                    "OemUnlockPreferenceController",
                    "sec_encrypt.decrypt() InvalidKeyException = " + e2.toString());
            return null;
        } catch (NoSuchAlgorithmException e3) {
            Log.i(
                    "OemUnlockPreferenceController",
                    "sec_encrypt.decrypt() NoSuchAlgorithmException = " + e3.toString());
            return null;
        } catch (NoSuchPaddingException e4) {
            Log.i(
                    "OemUnlockPreferenceController",
                    "sec_encrypt.decrypt() NoSuchPaddingException = " + e4.toString());
            return null;
        } catch (Exception e5) {
            Log.i(
                    "OemUnlockPreferenceController",
                    "sec_encrypt.decrypt() Exception = " + e5.toString());
            return null;
        }
    }

    public static SecretKeySpec getKey() {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update("SKT : Find lost phone plus !!!".getBytes());
        return new SecretKeySpec(messageDigest.digest(), "AES");
    }

    public void confirmEnableOemUnlock() {
        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment = this.mFragment;
        FragmentManagerImpl supportFragmentManager =
                developmentSettingsDashboardFragment.getActivity().getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag("EnableOemUnlockDlg") == null) {
            EnableOemUnlockSettingWarningDialog enableOemUnlockSettingWarningDialog =
                    new EnableOemUnlockSettingWarningDialog();
            enableOemUnlockSettingWarningDialog.setTargetFragment(
                    developmentSettingsDashboardFragment, 0);
            enableOemUnlockSettingWarningDialog.show(supportFragmentManager, "EnableOemUnlockDlg");
        }
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (RestrictedSwitchPreference) preferenceScreen.findPreference("oem_unlock_enable");
    }

    public final boolean enableOemUnlockPreference() {
        String str;
        if (Rune.isDomesticSKTModel()) {
            try {
                str =
                        decryptSKT(
                                FileUtils.readTextFile(
                                        new File("/efs/sec_efs/sktdm_mem/encmembership.txt"),
                                        128,
                                        null));
            } catch (IOException unused) {
                str = SignalSeverity.NONE;
            }
            Log.e("OemUnlockPreferenceController", "member = " + str);
            if ("member".equals(str) && this.mOemLockManager != null) {
                return false;
            }
        }
        String[] strArr = {"false"};
        if (!isAllowedByEDM(
                        "content://com.sec.knox.provider/RestrictionPolicy2",
                        "isFirmwareRecoveryAllowed",
                        strArr)
                || !isAllowedByEDM(
                        "content://com.sec.knox.provider/RestrictionPolicy1",
                        "isFactoryResetAllowed",
                        strArr)) {
            return false;
        }
        if (SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, -1) < 28) {
            return true;
        }
        return !isBootloaderUnlocked() && isOemUnlockAllowedByUserAndCarrier();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "oem_unlock_enable";
    }

    public final boolean isAllowedByEDM(String str, String str2, String[] strArr) {
        Cursor query =
                this.mContext.getContentResolver().query(Uri.parse(str), null, str2, strArr, null);
        try {
            if (query == null) {
                return true;
            }
            try {
                query.moveToFirst();
                if (!query.getString(query.getColumnIndex(str2)).equalsIgnoreCase("true")) {
                    if ("isFirmwareRecoveryAllowed".equals(str2)) {
                        Log.e("OemUnlockPreferenceController", "isOTAUpgradeAllowedByEDM is false");
                    } else {
                        Log.e(
                                "OemUnlockPreferenceController",
                                "isFactoryResetAllowedByEDM is false");
                    }
                    query.close();
                    return false;
                }
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            query.close();
            return true;
        } catch (Throwable th) {
            query.close();
            throw th;
        }
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        Log.d(
                "OemUnlockPreferenceController",
                "enableUnlock  : 1 = "
                        + SystemProperties.get("ro.frp.pst")
                        + " / 2 = "
                        + SystemProperties.get("ro.boot.other.locked"));
        return (SystemProperties.get("ro.frp.pst").equals(ApnSettings.MVNO_NONE)
                        || SystemProperties.get("ro.boot.other.locked").equals("1")
                        || KnoxGuardManager.getInstance().shouldBlockCustomRom()
                        || this.mOemLockManager == null)
                ? false
                : true;
    }

    public boolean isBootloaderUnlocked() {
        return this.mOemLockManager.isDeviceOemUnlocked();
    }

    public boolean isOemUnlockAllowedByUserAndCarrier() {
        return this.mOemLockManager.isOemUnlockAllowedByCarrier()
                && !this.mUserManager.hasBaseUserRestriction(
                        "no_factory_reset", UserHandle.of(UserHandle.myUserId()));
    }

    public boolean isOemUnlockedAllowed() {
        return this.mOemLockManager.isOemUnlockAllowed();
    }

    public final boolean isRampartEnabled() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "rampart_main_switch_enabled", 0)
                == 1;
    }

    @Override // com.android.settings.development.OnActivityResultListener
    public final boolean onActivityResult(int i, int i2, Intent intent) {
        if (i != 0) {
            return false;
        }
        if (i2 != -1) {
            return true;
        }
        if (this.mPreference.mChecked) {
            confirmEnableOemUnlock();
            return true;
        }
        this.mOemLockManager.setOemUnlockAllowedByUser(false);
        sendBroadcastToKMX(false);
        return true;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        this.mPreference.setEnabled(enableOemUnlockPreference());
        if (isRampartEnabled()) {
            this.mPreference.setEnabled(false);
        }
        updateOemUnlockSettingDescription();
        if (this.mPreference.isEnabled()) {
            this.mPreference.mHelper.checkRestrictionAndSetDisabled(
                    UserHandle.myUserId(), "no_factory_reset");
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (((Boolean) obj).booleanValue()) {
            if (showKeyguardConfirmation(this.mContext.getResources(), 0)) {
                return true;
            }
            confirmEnableOemUnlock();
            return true;
        }
        this.mOemLockManager.setOemUnlockAllowedByUser(false);
        sendBroadcastToKMX(false);
        FragmentManager childFragmentManager = this.mFragment.getChildFragmentManager();
        if (childFragmentManager.findFragmentByTag("OemLockInfoDialog") != null) {
            return true;
        }
        new OemLockInfoDialog().show(childFragmentManager, "OemLockInfoDialog");
        return true;
    }

    public final void sendBroadcastToKMX(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "send event to kmx, ", "OemUnlockPreferenceController", z);
        try {
            this.mContext
                    .getPackageManager()
                    .getPackageInfo(
                            "com.samsung.android.kmxservice",
                            NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
            Intent intent =
                    new Intent(
                            "com.samsung.android.kmxservice.trustchain.CHANGE_OEM_UNLOCK_ALLOWED");
            intent.setPackage("com.samsung.android.kmxservice");
            intent.putExtra(
                    "com.samsung.android.kmxservice.trustchain.VALUE_MENU_OEM_UNLOCKING", z);
            this.mContext.sendBroadcast(intent);
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m(
                    "KMX is not installed - ", e, "OemUnlockPreferenceController");
        }
    }

    public boolean showKeyguardConfirmation(Resources resources, int i) {
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(this.mActivity, this.mFragment);
        builder.mRequestCode = i;
        builder.mTitle = resources.getString(R.string.oem_unlock_enable);
        return builder.show();
    }

    @Override // com.android.settings.core.PreferenceControllerMixin
    public final void updateNonIndexableKeys(List list) {
        if (!isAvailable()) {
            if (TextUtils.isEmpty("oem_unlock_enable")) {
                Log.w(
                        "OemUnlockPreferenceController",
                        "Skipping updateNonIndexableKeys due to empty key " + toString());
            } else if (!list.contains("oem_unlock_enable")) {
                Log.i(
                        "OemUnlockPreferenceController",
                        "oem_unlock_enable , Key added to nonindexable keys");
                list.add("oem_unlock_enable");
            } else {
                Log.w(
                        "OemUnlockPreferenceController",
                        "Skipping updateNonIndexableKeys, key already in list. " + toString());
            }
        }
    }

    public final void updateOemUnlockSettingDescription() {
        int i;
        if (isBootloaderUnlocked()) {
            i = R.string.oem_unlock_enable_disabled_summary_bootloader_unlocked;
        } else {
            if (this.mContext
                    .getPackageManager()
                    .hasSystemFeature("android.hardware.telephony.carrierlock")) {
                int phoneCount = this.mTelephonyManager.getPhoneCount();
                for (int i2 = 0; i2 < phoneCount; i2++) {
                    if (this.mTelephonyManager.getAllowedCarriers(i2).size() > 0) {
                        i = R.string.oem_unlock_enable_disabled_summary_sim_locked_device;
                        break;
                    }
                }
            } else {
                Log.w(
                        "OemUnlockPreferenceController",
                        "getAllowedCarriers is unsupported without"
                            + " PackageManager#FEATURE_TELEPHONY_CARRIERLOCK");
            }
            i =
                    !isOemUnlockAllowedByUserAndCarrier()
                            ? R.string.oem_unlock_enable_disabled_summary_connectivity_or_locked
                            : R.string.oem_unlock_enable_summary;
        }
        if (isRampartEnabled()) {
            i = R.string.restricted_by_auto_blocker;
        }
        this.mPreference.setSummary(this.mContext.getResources().getString(i));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        this.mPreference.setChecked(isOemUnlockedAllowed());
        updateOemUnlockSettingDescription();
        this.mPreference.setDisabledByAdmin(null);
        if (KnoxGuardManager.getInstance().shouldBlockCustomRom()) {
            this.mPreference.setEnabled(false);
        } else {
            this.mPreference.setEnabled(enableOemUnlockPreference());
        }
        if (isRampartEnabled()) {
            this.mPreference.setEnabled(false);
        }
        if (this.mPreference.isEnabled()) {
            this.mPreference.mHelper.checkRestrictionAndSetDisabled(
                    UserHandle.myUserId(), "no_factory_reset");
        }
    }
}
