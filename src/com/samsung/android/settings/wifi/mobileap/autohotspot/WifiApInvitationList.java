package com.samsung.android.settings.wifi.mobileap.autohotspot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.LocaleList;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.widget.EmptyTextSettings;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApAutoHotspotInvitationConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApDateUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSmartTetheringApkUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreferenceWith2Buttons;
import com.samsung.android.wifi.SemWifiApContentProviderHelper;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApInvitationList extends EmptyTextSettings {
    public static final IntentFilter INTENT_FILTER;
    public FragmentActivity mActivity;
    public final AnonymousClass1 mBroadcastReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApInvitationList.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    IntentFilter intentFilter = WifiApInvitationList.INTENT_FILTER;
                    Log.i("WifiApInvitationList", "Received Broadcast: " + action);
                    if (!action.equals(
                            "com.samsung.android.server.wifi.softap.smarttethering.familyid")) {
                        if (action.equals(
                                "com.sec.mhs.smarttethering.MY_INVITATION_LIST_CHANGED")) {
                            WifiApInvitationList.this.updateInvitationPreferenceList();
                        }
                    } else {
                        String str =
                                SemWifiApContentProviderHelper.get(
                                        context, "hash_value_based_on_familyid");
                        if ((!str.isEmpty() ? Long.parseLong(str) : -1L) == -1) {
                            Log.e("WifiApInvitationList", "familyID is -1, so closing activity");
                            WifiApInvitationList.this.finish();
                        }
                    }
                }
            };
    public WifiApAutoHotspotInvitationConfig mClickedInvitationConfig;
    public FragmentActivity mContext;
    public PreferenceScreen mPreferenceScreen;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApInvitationList$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public final /* synthetic */ WifiApAutoHotspotInvitationConfig val$invitationConfig;

        public AnonymousClass2(
                WifiApAutoHotspotInvitationConfig wifiApAutoHotspotInvitationConfig) {
            this.val$invitationConfig = wifiApAutoHotspotInvitationConfig;
        }
    }

    static {
        IntentFilter intentFilter = new IntentFilter();
        INTENT_FILTER = intentFilter;
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("com.samsung.account.SAMSUNGACCOUNT_SIGNIN_COMPLETED");
        intentFilter.addAction("com.samsung.account.SAMSUNGACCOUNT_SIGNOUT_COMPLETED");
        intentFilter.addAction("com.samsung.android.server.wifi.softap.smarttethering.changed");
        intentFilter.addAction(
                "com.samsung.android.server.wifi.softap.smarttethering.familySharingDisabledIntent");
        intentFilter.addAction("com.sec.mhs.smarttethering.MY_INVITATION_LIST_CHANGED");
        intentFilter.addAction("com.samsung.android.server.wifi.softap.smarttethering.familyid");
        intentFilter.addAction("com.sec.mhs.smarttethering.GROUP_API_RESULT_AND_DB_READ_REQUEST");
        intentFilter.addAction(
                "com.samsung.android.server.wifi.softap.smarttethering.ACTION_WIFI_AP_FAMILY_LOCAL_GROUP_UPDATED");
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3400;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_invitation_list;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        Log.i("WifiApInvitationList", "onActivityCreated() - Triggered");
        super.onActivityCreated(bundle);
        FragmentActivity fragmentActivity = this.mContext;
        WifiApSmartTetheringApkUtils.startSmartTetheringApk(
                fragmentActivity,
                7,
                WifiApSmartTetheringApkUtils.getFamilyGroupId(fragmentActivity));
        setEmptyText(R.string.wifi_ap_no_invitation);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "onActivityResult() - requestCode: ",
                ", resultCode(-1 for RESULT_OK) : ",
                i,
                i2,
                "WifiApInvitationList");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.i("WifiApInvitationList", "onCreate() - Triggered");
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        this.mContext = activity;
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mPreferenceScreen = preferenceScreen;
        if (preferenceScreen != null) {
            preferenceScreen.removeAll();
        }
        addPreferencesFromResource(R.xml.sec_wifi_ap_invitation_list);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        final int i2 = 0;
        if (i != 31) {
            return null;
        }
        Log.i("WifiApInvitationList", "creating dialog  DIALOG_JOIN_FAMILY_GROUP");
        FragmentActivity activity = getActivity();
        boolean z = WifiApSettingsUtils.DBG;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, 0);
        builder.setTitle(R.string.wifi_ap_join_family_group);
        String str = this.mClickedInvitationConfig.mRequesterName;
        if (str == null) {
            Log.e("WifiApAutoHotspotInvitationConfig", "RequesterName is NULL");
            str = ApnSettings.MVNO_NONE;
        }
        String format =
                String.format(
                        getString(R.string.wifi_ap_x_invited_you_join_family_group_description),
                        str);
        String format2 =
                String.format(getString(R.string.wifi_ap_if_join_family_group_description), str);
        if (WifiApSmartTetheringApkUtils.isCurrentUserIsGroupOwner(this.mContext)
                && ((ArrayList) WifiApSmartTetheringApkUtils.getFamilyMemberList(this.mContext))
                                .size()
                        > 0) {
            format2 = getString(R.string.wifi_ap_accept_new_invitation_description);
        }
        builder.setMessage(format + "\n" + format2);
        builder.setNegativeButton(
                R.string.common_cancel,
                new DialogInterface.OnClickListener(this) { // from class:
                    // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApInvitationList.3
                    public final /* synthetic */ WifiApInvitationList this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        switch (i2) {
                            case 0:
                                IntentFilter intentFilter = WifiApInvitationList.INTENT_FILTER;
                                Log.i(
                                        "WifiApInvitationList",
                                        "Cancel button clicked for id "
                                                + this.this$0.mClickedInvitationConfig
                                                        .getGroupId());
                                break;
                            default:
                                IntentFilter intentFilter2 = WifiApInvitationList.INTENT_FILTER;
                                Log.i(
                                        "WifiApInvitationList",
                                        "OK clicked for id "
                                                + this.this$0.mClickedInvitationConfig
                                                        .getGroupId());
                                if (WifiApSmartTetheringApkUtils.isCurrentUserIsGroupOwner(
                                                this.this$0.mContext)
                                        && ((ArrayList)
                                                                WifiApSmartTetheringApkUtils
                                                                        .getFamilyMemberList(
                                                                                this.this$0
                                                                                        .mContext))
                                                        .size()
                                                > 0) {
                                    WifiApInvitationList wifiApInvitationList = this.this$0;
                                    FragmentActivity fragmentActivity =
                                            wifiApInvitationList.mActivity;
                                    String groupId =
                                            wifiApInvitationList.mClickedInvitationConfig
                                                    .getGroupId();
                                    Log.d(
                                            "WifiApSmartTetheringApkUtils",
                                            "Accepting Invitation for id " + groupId);
                                    Intent intent =
                                            new Intent(
                                                    "com.sec.mhs.smarttethering.WifiApGroupSemsActivityLauncher");
                                    intent.putExtra("launcher", 200);
                                    intent.putExtra("group_id", groupId);
                                    fragmentActivity.startActivityForResult(intent, 220);
                                    break;
                                } else {
                                    WifiApInvitationList wifiApInvitationList2 = this.this$0;
                                    FragmentActivity fragmentActivity2 =
                                            wifiApInvitationList2.mContext;
                                    String groupId2 =
                                            wifiApInvitationList2.mClickedInvitationConfig
                                                    .getGroupId();
                                    DialogFragment$$ExternalSyntheticOutline0.m(
                                            "Accepting Invitation for id ",
                                            groupId2,
                                            "WifiApSmartTetheringApkUtils");
                                    WifiApSmartTetheringApkUtils.startSmartTetheringApk(
                                            fragmentActivity2, 4, groupId2);
                                    break;
                                }
                        }
                    }
                });
        final int i3 = 1;
        builder.setPositiveButton(
                R.string.common_ok,
                new DialogInterface.OnClickListener(this) { // from class:
                    // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApInvitationList.3
                    public final /* synthetic */ WifiApInvitationList this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i32) {
                        switch (i3) {
                            case 0:
                                IntentFilter intentFilter = WifiApInvitationList.INTENT_FILTER;
                                Log.i(
                                        "WifiApInvitationList",
                                        "Cancel button clicked for id "
                                                + this.this$0.mClickedInvitationConfig
                                                        .getGroupId());
                                break;
                            default:
                                IntentFilter intentFilter2 = WifiApInvitationList.INTENT_FILTER;
                                Log.i(
                                        "WifiApInvitationList",
                                        "OK clicked for id "
                                                + this.this$0.mClickedInvitationConfig
                                                        .getGroupId());
                                if (WifiApSmartTetheringApkUtils.isCurrentUserIsGroupOwner(
                                                this.this$0.mContext)
                                        && ((ArrayList)
                                                                WifiApSmartTetheringApkUtils
                                                                        .getFamilyMemberList(
                                                                                this.this$0
                                                                                        .mContext))
                                                        .size()
                                                > 0) {
                                    WifiApInvitationList wifiApInvitationList = this.this$0;
                                    FragmentActivity fragmentActivity =
                                            wifiApInvitationList.mActivity;
                                    String groupId =
                                            wifiApInvitationList.mClickedInvitationConfig
                                                    .getGroupId();
                                    Log.d(
                                            "WifiApSmartTetheringApkUtils",
                                            "Accepting Invitation for id " + groupId);
                                    Intent intent =
                                            new Intent(
                                                    "com.sec.mhs.smarttethering.WifiApGroupSemsActivityLauncher");
                                    intent.putExtra("launcher", 200);
                                    intent.putExtra("group_id", groupId);
                                    fragmentActivity.startActivityForResult(intent, 220);
                                    break;
                                } else {
                                    WifiApInvitationList wifiApInvitationList2 = this.this$0;
                                    FragmentActivity fragmentActivity2 =
                                            wifiApInvitationList2.mContext;
                                    String groupId2 =
                                            wifiApInvitationList2.mClickedInvitationConfig
                                                    .getGroupId();
                                    DialogFragment$$ExternalSyntheticOutline0.m(
                                            "Accepting Invitation for id ",
                                            groupId2,
                                            "WifiApSmartTetheringApkUtils");
                                    WifiApSmartTetheringApkUtils.startSmartTetheringApk(
                                            fragmentActivity2, 4, groupId2);
                                    break;
                                }
                        }
                    }
                });
        return builder.create();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.i("WifiApInvitationList", "onCreateView() - Triggered");
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        Log.i("WifiApInvitationList", "onDestroyView() - Triggered");
        super.onDestroyView();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        Log.i("WifiApInvitationList", "onPause() - Triggered");
        getActivity().unregisterReceiver(this.mBroadcastReceiver);
        FragmentActivity fragmentActivity = this.mContext;
        boolean z = WifiApSmartTetheringApkUtils.DBG;
        Log.i("WifiApSmartTetheringApkUtils", "Resetting New Invitation Count DB.");
        SemWifiApContentProviderHelper.insert(
                fragmentActivity, "new_invitation_status", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        super.onPause();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        Log.i("WifiApInvitationList", "onResume() - Triggered");
        super.onResume();
        getActivity().registerReceiver(this.mBroadcastReceiver, INTENT_FILTER, 2);
        updateInvitationPreferenceList();
        FragmentActivity fragmentActivity = this.mContext;
        boolean z = WifiApSmartTetheringApkUtils.DBG;
        Log.i("WifiApSmartTetheringApkUtils", "Sending Clear Notification Broadcast.");
        Intent intent = new Intent();
        intent.setAction(
                "com.mhs.smarttethering.WifiApGroupBroadcastReceiver.ACTION_CLEAR_ALL_INVITATION_NOTIFICATION");
        intent.setClassName(
                "com.sec.mhs.smarttethering",
                "com.sec.mhs.smarttethering.WifiApGroupBroadcastReceiver");
        fragmentActivity.sendBroadcast(intent);
        WifiApSmartTetheringApkUtils.updateInvitationScreenStatus(this.mContext, true);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        Log.i("WifiApInvitationList", "onStart() - Triggered");
        super.onStart();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        Log.i("WifiApInvitationList", "onStop() - Triggered");
        WifiApSmartTetheringApkUtils.updateInvitationScreenStatus(this.mContext, false);
        super.onStop();
    }

    public final void updateInvitationPreferenceList() {
        Log.i("WifiApInvitationList", "updateInvitationPreferenceList() - Triggered");
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mPreferenceScreen = preferenceScreen;
        if (preferenceScreen != null) {
            preferenceScreen.removeAll();
        }
        addPreferencesFromResource(R.xml.sec_wifi_ap_invitation_list);
        FragmentActivity fragmentActivity = this.mContext;
        boolean z = WifiApSmartTetheringApkUtils.DBG;
        Log.i("WifiApSmartTetheringApkUtils", "Getting Invitation List");
        ArrayList arrayList = new ArrayList();
        String str = SemWifiApContentProviderHelper.get(fragmentActivity, "my_invitation_list");
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split("#");
            for (int i = 0; i < split.length; i++) {
                String[] split2 = split[i].split("\\*");
                StringBuilder m =
                        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "invitation No.", "1:");
                m.append(split2[0]);
                m.append(", ");
                m.append(split2[1]);
                m.append(", ");
                m.append(split2[2]);
                m.append(", ");
                Utils$$ExternalSyntheticOutline0.m(m, split2[3], "WifiApSmartTetheringApkUtils");
                String str2 = split2[0];
                String str3 = split2[3];
                long parseLong = Long.parseLong(split2[2]);
                long parseLong2 = Long.parseLong(split2[1]);
                WifiApAutoHotspotInvitationConfig wifiApAutoHotspotInvitationConfig =
                        new WifiApAutoHotspotInvitationConfig();
                wifiApAutoHotspotInvitationConfig.mGroupId = str2;
                wifiApAutoHotspotInvitationConfig.mRequesterName = str3;
                wifiApAutoHotspotInvitationConfig.mRequestedTime = parseLong;
                wifiApAutoHotspotInvitationConfig.mExpiryTimeToAdd = parseLong2;
                String str4 =
                        SemWifiApContentProviderHelper.get(
                                fragmentActivity,
                                "smart_tethering_Invitation_requester_icons_" + str2);
                Log.i("WifiApAutoHotspotInvitationConfig", "Set profile icon : " + str4.length());
                wifiApAutoHotspotInvitationConfig.mProfileIconString = str4;
                Log.i(
                        "WifiApAutoHotspotInvitationConfig",
                        "Invitation Config, " + wifiApAutoHotspotInvitationConfig.toString());
                arrayList.add(wifiApAutoHotspotInvitationConfig);
            }
        }
        Log.d("WifiApSmartTetheringApkUtils", "Invitation list size " + arrayList.size());
        if (arrayList.size() <= 0) {
            this.mEmpty.setVisibility(0);
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            WifiApAutoHotspotInvitationConfig wifiApAutoHotspotInvitationConfig2 =
                    (WifiApAutoHotspotInvitationConfig) it.next();
            Context prefContext = getPrefContext();
            AnonymousClass2 anonymousClass2 =
                    new AnonymousClass2(wifiApAutoHotspotInvitationConfig2);
            WifiApPreferenceWith2Buttons wifiApPreferenceWith2Buttons =
                    new WifiApPreferenceWith2Buttons(prefContext);
            Log.i("WifiApPreferenceWith2Buttons", "WifiApPreferenceWith2Buttons()");
            wifiApPreferenceWith2Buttons.mInvitationConfig = wifiApAutoHotspotInvitationConfig2;
            wifiApPreferenceWith2Buttons.mClickListener = anonymousClass2;
            wifiApPreferenceWith2Buttons.setLayoutResource(
                    R.layout.sec_wifi_ap_preference_with_2_buttons);
            wifiApPreferenceWith2Buttons.setKey(wifiApAutoHotspotInvitationConfig2.getGroupId());
            String string = prefContext.getString(R.string.wifi_ap_invitation_from_x);
            String str5 = wifiApAutoHotspotInvitationConfig2.mRequesterName;
            if (str5 == null) {
                Log.e("WifiApAutoHotspotInvitationConfig", "RequesterName is NULL");
                str5 = ApnSettings.MVNO_NONE;
            }
            wifiApPreferenceWith2Buttons.setTitle(String.format(string, str5));
            if (wifiApAutoHotspotInvitationConfig2.getPhoto(prefContext) != null) {
                wifiApPreferenceWith2Buttons.setIcon(
                        wifiApAutoHotspotInvitationConfig2.getPhoto(prefContext));
            } else {
                wifiApPreferenceWith2Buttons.setIcon(R.drawable.sec_wifi_ap_profile_default_image);
            }
            long j = wifiApAutoHotspotInvitationConfig2.mExpiryTimeToAdd;
            Integer num = WifiApDateUtils.NUMBER_OF_DAYS_IN_WEEK;
            LocaleList locales = prefContext.getResources().getConfiguration().getLocales();
            wifiApPreferenceWith2Buttons.setSummary(
                    String.format(
                            prefContext.getString(R.string.wifi_ap_invitation_expires),
                            DateFormat.format(
                                            DateFormat.getBestDateTimePattern(
                                                    (locales == null || locales.isEmpty())
                                                            ? Locale.getDefault()
                                                            : locales.get(0),
                                                    "MMM dd,yyyy HH:mm:ss"),
                                            j)
                                    .toString()));
            this.mPreferenceScreen.addPreference(wifiApPreferenceWith2Buttons);
        }
        this.mEmpty.setVisibility(8);
    }
}
