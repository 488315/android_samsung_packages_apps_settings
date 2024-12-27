package com.samsung.android.settings.wifi.mobileap.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.voiceinput.samsungaccount.contract.SaContract;
import com.samsung.android.settings.wifi.mobileap.AES;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApFamilyMember;
import com.samsung.android.wifi.SemWifiApContentProviderHelper;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiApSmartTetheringApkUtils {
    public static final boolean DBG = Utils.MHSDBG;

    public static String getFamilyGroupId(Context context) {
        String decrypt;
        String str = SemWifiApContentProviderHelper.get(context, "smart_tethering_familyid");
        boolean isEmpty = TextUtils.isEmpty(WifiApSettingsUtils.getSamsungAccount(context));
        String str2 = ApnSettings.MVNO_NONE;
        if (!isEmpty
                && (decrypt = AES.decrypt(str, WifiApSettingsUtils.getSamsungAccount(context)))
                        != null
                && !decrypt.equals(ApnSettings.MVNO_NONE)) {
            str2 = decrypt;
        }
        Log.i(
                "WifiApSmartTetheringApkUtils",
                "Getting family Group ID : " + WifiApSettingsUtils.hideSecondHalfOfString(str2));
        return str2;
    }

    public static List getFamilyMemberList(Context context) {
        boolean z = DBG;
        ArrayList arrayList = new ArrayList();
        String str =
                SemWifiApContentProviderHelper.get(context, "smart_tethering_family_user_names");
        String str2 = SemWifiApContentProviderHelper.get(context, "smart_tethering_family_guids");
        int i = 0;
        if (str == null || str.isEmpty() || str2 == null || str2.isEmpty()) {
            Log.d(
                    "WifiApSmartTetheringApkUtils",
                    "Getting Family Members To List - No family members.");
            while (i < 5) {
                SemWifiApContentProviderHelper.insert(
                        context, "smart_tethering_family_icons_" + i, (String) null);
                i++;
            }
            return arrayList;
        }
        String decrypt = AES.decrypt(str, WifiApSettingsUtils.getSamsungAccount(context));
        String decrypt2 = AES.decrypt(str2, WifiApSettingsUtils.getSamsungAccount(context));
        if (decrypt != null && decrypt2 != null) {
            String[] split = decrypt.split("\n");
            String[] split2 = decrypt2.split("\n");
            try {
                int length = split2.length;
                int i2 = 0;
                while (i < length) {
                    arrayList.add(
                            new WifiApFamilyMember(
                                    context,
                                    split2[i],
                                    split[i2],
                                    SemWifiApContentProviderHelper.get(
                                            context, "smart_tethering_family_icons_" + i2)));
                    i2++;
                    i++;
                }
            } catch (ArrayIndexOutOfBoundsException unused) {
                if (z) {
                    Log.d(
                            "WifiApSmartTetheringApkUtils",
                            "Getting Family Members To List - ArrayIndexOutOfBoundsException"
                                + " occurs");
                }
            } catch (NumberFormatException unused2) {
                if (z) {
                    Log.d(
                            "WifiApSmartTetheringApkUtils",
                            "Getting Family Members To List - NumberFormatException occurs");
                }
            }
        }
        return arrayList;
    }

    public static WifiApFamilyMember getFamilyOwner(Context context) {
        if (WifiApSettingsUtils.isSamsungAccountLoggedOut(context)) {
            return new WifiApFamilyMember(
                    context,
                    ApnSettings.MVNO_NONE,
                    WifiApUtils.getString(context, R.string.wifi_ap_not_signed_in),
                    null);
        }
        String str = SemWifiApContentProviderHelper.get(context, "smart_tethering_user_icon");
        String str2 = SemWifiApContentProviderHelper.get(context, "smart_tethering_user_name");
        String str3 = SemWifiApContentProviderHelper.get(context, "smart_tethering_guid");
        String decrypt = AES.decrypt(str2, WifiApSettingsUtils.getSamsungAccount(context));
        StringBuilder m =
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "getFamilyOwner: mDecryptedUserName: ",
                        decrypt,
                        ", mUserName: ",
                        str2,
                        ", currentUserId: ");
        m.append(str3);
        Log.i("WifiApSmartTetheringApkUtils", m.toString());
        return new WifiApFamilyMember(context, str3, decrypt, str);
    }

    public static boolean isCurrentUserIsGroupOwner(Context context) {
        String str = SemWifiApContentProviderHelper.get(context, "smart_tethering_ownerid");
        String str2 = SemWifiApContentProviderHelper.get(context, "smart_tethering_guid");
        boolean z = (str.isEmpty() || str2.isEmpty() || !str.equals(str2)) ? false : true;
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        "isOwner() - groupOwnerId: ",
                        str,
                        " currentUserId: ",
                        str2,
                        ", isOwnerOfGroup: "),
                z,
                "WifiApSmartTetheringApkUtils");
        return z;
    }

    public static boolean isFamilySharingServiceRegisteredOn(Context context) {
        boolean equals =
                SemWifiApContentProviderHelper.get(
                                context, "smart_tethering_family_sharing_service_registered")
                        .equals("1");
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Getting(DB) FamilySharing Service Registered: ",
                "WifiApSmartTetheringApkUtils",
                equals);
        return equals;
    }

    public static boolean isFamilySharingSwitchChangedAutomatically(Context context) {
        boolean equals =
                SemWifiApContentProviderHelper.get(
                                context,
                                "smart_tethering_family_sharing_switch_changed_automatically")
                        .equals("1");
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Getting(DB) Family Sharing Switch Changed Automatically: ",
                "WifiApSmartTetheringApkUtils",
                equals);
        return equals;
    }

    public static boolean isGroupSharingAppDisabled(Context context) {
        boolean z = WifiApSettingsUtils.DBG;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            try {
                PackageInfo packageInfo =
                        packageManager.getPackageInfo(SaContract.OLD_PACKAGE_NAME, 128);
                r3 = packageInfo != null ? packageInfo.applicationInfo.enabled : false;
                Log.i(
                        "WifiApSettingsUtils",
                        "isPkgEnabled(), com.samsung.android.mobileservice is "
                                .concat(r3 ? "enabled" : "disabled"));
            } catch (PackageManager.NameNotFoundException unused) {
                Log.i(
                        "WifiApSettingsUtils",
                        "isPkgEnabled(), com.samsung.android.mobileservice does not exist. ");
            }
        }
        boolean z2 = !r3;
        if (z2) {
            Log.e("WifiApSmartTetheringApkUtils", "Group Sharing App is Disabled");
        }
        return z2;
    }

    public static boolean isThereAnyNewInvitation(Context context) {
        String str = SemWifiApContentProviderHelper.get(context, "new_invitation_status");
        boolean z = (str == null || str.isEmpty() || Integer.parseInt(str) <= 0) ? false : true;
        Log.i(
                "WifiApSmartTetheringApkUtils",
                "isThereAnyNewInvitation() - newInvitationCount: "
                        + str
                        + " isThereAnyNewInvitationInDb: "
                        + z);
        return z;
    }

    public static void launchFamilyServiceRegisterActivityForResult(
            SettingsPreferenceFragment settingsPreferenceFragment, int i) {
        if (WifiApFeatureUtils.isSAFamilySupportedBasedOnCountry(
                settingsPreferenceFragment.getContext())) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i,
                    "launchFamilyServiceRegisterActivity() with requestCode: Not supported for SA"
                        + " family sharing",
                    "WifiApSmartTetheringApkUtils");
            return;
        }
        if (WifiApSettingsUtils.isSamsungAccountLoggedOut(
                settingsPreferenceFragment.getContext())) {
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i,
                    "For launchFamilyServiceRegisterActivity() Error. No SA Account. requestCode: ",
                    "WifiApSmartTetheringApkUtils");
            return;
        }
        Log.d(
                "WifiApSmartTetheringApkUtils",
                "launchFamilyServiceRegisterActivity() with requestCode: " + i);
        Intent intent = new Intent();
        intent.setAction("com.sec.mhs.smarttethering.WifiApGroupSemsActivityLauncher");
        intent.putExtra("launcher", 100);
        if (WifiApSettingsUtils.isSamsungAccountLoggedOut(
                settingsPreferenceFragment.getContext())) {
            return;
        }
        settingsPreferenceFragment.startActivityForResult(intent, i);
    }

    public static void launchSaFamilyServiceGroupActivity(
            SettingsPreferenceFragment settingsPreferenceFragment, int i) {
        Log.d(
                "WifiApSmartTetheringApkUtils",
                "Launching SA Family Group Activity. Supported in Samasung Account with"
                    + " requestCode: "
                        + i);
        Intent intent =
                new Intent("com.samsung.android.samsungaccount.action.OPEN_FAMILY_GROUP_MAIN");
        if (getFamilyGroupId(settingsPreferenceFragment.getContext()).isEmpty()) {
            Log.d(
                    "WifiApSmartTetheringApkUtils",
                    "Launching SA Family Group Activity for creating group");
            intent.putExtra("launch_mode", "request_create_group");
        }
        if (i < 0) {
            settingsPreferenceFragment.startActivity(intent);
        } else {
            settingsPreferenceFragment.startActivityForResult(intent, i);
        }
    }

    public static void launchSocialPickerForCreatingGroupActivity(
            SettingsPreferenceFragment settingsPreferenceFragment, int i) {
        if (settingsPreferenceFragment == null) {
            return;
        }
        Log.d("WifiApSmartTetheringApkUtils", "Launching SocialPicker Activity For Creating Group");
        Intent intent = new Intent();
        intent.setAction("com.sec.mhs.smarttethering.WifiApGroupSemsActivityLauncher");
        intent.putExtra("launcher", 110);
        settingsPreferenceFragment.startActivityForResult(intent, i);
    }

    public static void setFamilySharingServiceRegisteredDB(Context context, boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Setting(DB) FamilySharing Service Registered: ",
                "WifiApSmartTetheringApkUtils",
                z);
        SemWifiApContentProviderHelper.insert(
                context,
                "smart_tethering_family_sharing_service_registered",
                z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
    }

    public static void setFamilySharingSwitchChangedAutomatically(Context context, boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Setting(DB) family sharing switch changed automatically DB  ",
                "WifiApSmartTetheringApkUtils",
                z);
        SemWifiApContentProviderHelper.insert(
                context,
                "smart_tethering_family_sharing_switch_changed_automatically",
                z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
    }

    public static void startSmartTetheringApk(Context context, int i, String str) {
        if (!WifiApSettingsUtils.isActiveNetworkHasInternet(context)) {
            Log.d(
                    "WifiApSmartTetheringApkUtils",
                    "internet is not available, so not starting smarttethering apk");
            return;
        }
        Log.d("WifiApSmartTetheringApkUtils", "Starting SmartTethering apk for id: " + str);
        long currentTimeMillis = System.currentTimeMillis();
        String str2 =
                SemWifiApContentProviderHelper.get(
                        context, "smart_tethering_last_server_sync_time");
        if (i == 1 && str == null) {
            if (!TextUtils.isEmpty(str2)
                    && Math.abs(currentTimeMillis - Long.parseLong(str2)) <= 5000) {
                Log.d(
                        "WifiApSmartTetheringApkUtils",
                        "Starting SmartTethering apk is skipped because the last server sync was"
                            + " less than 5 seconds");
                return;
            } else {
                SemWifiApContentProviderHelper.insert(
                        context,
                        "smart_tethering_last_server_sync_time",
                        ApnSettings.MVNO_NONE + currentTimeMillis);
            }
        }
        if (i == 3 || !WifiApFeatureUtils.isSAFamilySupportedBasedOnCountry(context)) {
            Intent intent = new Intent();
            intent.setClassName(
                    "com.sec.mhs.smarttethering", "com.sec.mhs.smarttethering.WifiApGroupService");
            intent.putExtra("cmd_arg", i);
            if (str != null && str.startsWith("AHSP")) {
                intent.putExtra("group_id", str);
            }
            try {
                context.startService(intent);
            } catch (Exception unused) {
                Log.e(
                        "WifiApSmartTetheringApkUtils",
                        "can't start service com.sec.mhs.smarttethering");
            }
        }
    }

    public static void updateInvitationScreenStatus(FragmentActivity fragmentActivity, boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Updating Invitation screen status in DB: ", "WifiApSmartTetheringApkUtils", z);
        SemWifiApContentProviderHelper.insert(
                fragmentActivity,
                "is_my_invitation_screen_on",
                z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
    }
}
