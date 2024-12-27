package com.samsung.android.settings;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;

import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DCMHomeSettings extends ListFragment {
    public static final String[] PRELOADED_HOME_PKGS = {
        "com.nttdocomo.android.paletteui",
        "com.nttdocomo.android.dhome",
        "com.nttdocomo.android.homezozo",
        PartnerConfigHelper.SUW_PACKAGE_NAME,
        "com.sec.android.app.launcher",
        "com.sec.android.app.easylauncher",
        "com.sec.android.app.kidslauncher",
        "com.sec.android.app.SecSetupWizard",
        "com.sec.android.app.longlifemodelauncher",
        "com.sec.android.app.kidshome",
        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG
    };
    public List mAppList;
    public FragmentActivity mContext;
    public List mHomeApps;
    public PackageManager mPm;
    public ContentResolver mResolver;
    public final HashSet mPreloadedHomeList = new HashSet(Arrays.asList(PRELOADED_HOME_PKGS));
    public boolean mHasEasyLauncher = true;
    public String mCurrentHomePkg = ApnSettings.MVNO_NONE;
    public String mSelectedHomePkg = ApnSettings.MVNO_NONE;
    public String mSamsungHomeLabel = ApnSettings.MVNO_NONE;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HomeAdapter extends ArrayAdapter {
        public HomeAdapter(FragmentActivity fragmentActivity, List list) {
            super(fragmentActivity, R.xml.sec_home_settings_list_item_third, list);
        }

        /* JADX WARN: Removed duplicated region for block: B:35:0x010d  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x01a1  */
        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.view.View getView(
                int r17, android.view.View r18, android.view.ViewGroup r19) {
            /*
                Method dump skipped, instructions count: 430
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.DCMHomeSettings.HomeAdapter.getView(int,"
                        + " android.view.View, android.view.ViewGroup):android.view.View");
        }
    }

    public final ResolveInfo getResolveInfo(String str) {
        List<ResolveInfo> list = this.mAppList;
        if (list == null) {
            return null;
        }
        for (ResolveInfo resolveInfo : list) {
            if (resolveInfo != null && resolveInfo.activityInfo.packageName.equals(str)) {
                return resolveInfo;
            }
        }
        return null;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setListAdapter(new HomeAdapter(this.mContext, this.mHomeApps));
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        this.mPm = activity.getPackageManager();
        this.mResolver = this.mContext.getContentResolver();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        this.mAppList = this.mPm.queryIntentActivities(intent, 65600);
        this.mHomeApps = new ArrayList();
        if (getResolveInfo("com.nttdocomo.android.dhome") != null) {
            ((ArrayList) this.mHomeApps).add(getResolveInfo("com.nttdocomo.android.dhome"));
            android.util.Log.d("DCMHomeSettings", "add home : com.nttdocomo.android.dhome");
        } else if (getResolveInfo("com.nttdocomo.android.homezozo") != null) {
            ((ArrayList) this.mHomeApps).add(getResolveInfo("com.nttdocomo.android.homezozo"));
            android.util.Log.d("DCMHomeSettings", "add home : com.nttdocomo.android.homezozo");
        } else if (getResolveInfo("com.nttdocomo.android.paletteui") != null) {
            ((ArrayList) this.mHomeApps).add(getResolveInfo("com.nttdocomo.android.paletteui"));
            android.util.Log.d("DCMHomeSettings", "add home : com.nttdocomo.android.paletteui");
        }
        if (getResolveInfo("com.sec.android.app.launcher") != null) {
            ResolveInfo resolveInfo = getResolveInfo("com.sec.android.app.launcher");
            ((ArrayList) this.mHomeApps).add(resolveInfo);
            if (resolveInfo != null) {
                this.mSamsungHomeLabel = resolveInfo.loadLabel(this.mPm).toString();
            }
            android.util.Log.d("DCMHomeSettings", "add home : com.sec.android.app.launcher");
        }
        if (getResolveInfo("com.sec.android.app.easylauncher") != null) {
            ((ArrayList) this.mHomeApps).add(getResolveInfo("com.sec.android.app.easylauncher"));
            android.util.Log.d("DCMHomeSettings", "add home : com.sec.android.app.easylauncher");
        } else {
            android.util.Log.d("DCMHomeSettings", "There is no easy launcher");
            this.mHasEasyLauncher = false;
        }
        if (Settings.Global.getInt(this.mResolver, "device_provisioned", 0) != 0) {
            for (int i = 0; i < this.mAppList.size(); i++) {
                ResolveInfo resolveInfo2 = (ResolveInfo) this.mAppList.get(i);
                if (!this.mPreloadedHomeList.contains(resolveInfo2.activityInfo.packageName)) {
                    ((ArrayList) this.mHomeApps).add(resolveInfo2);
                }
            }
        }
        for (int size = this.mAppList.size() - 1; size >= 0; size--) {
            String str = ((ResolveInfo) this.mAppList.get(size)).activityInfo.packageName;
            if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                            "pkg=", str, "DCMHomeSettings", "com.sec.android.app.SecSetupWizard")
                    || str.equals(PartnerConfigHelper.SUW_PACKAGE_NAME)) {
                android.util.Log.d("DCMHomeSettings", "[Remove] pkg=".concat(str));
                this.mAppList.remove(size);
            }
        }
        LoggingHelper.insertFlowLogging(55);
        LoggingHelper.insertEntranceLogging(55);
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.sec_home_settings_dcm, viewGroup, false);
    }

    @Override // androidx.fragment.app.ListFragment
    public final void onListItemClick(View view, final int i) {
        String str;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "onListItemClick : ", "DCMHomeSettings");
        ResolveInfo resolveInfo = (ResolveInfo) ((ArrayList) this.mHomeApps).get(i);
        if (resolveInfo == null
                || (str = this.mCurrentHomePkg) == null
                || str.equals(resolveInfo.activityInfo.packageName)) {
            android.util.Log.d("DCMHomeSettings", "Do not change to current home");
        } else {
            LoggingHelper.insertEventLogging(3897, 3897);
            new AlertDialog.Builder(this.mContext)
                    .setMessage(
                            getString(
                                    R.string.sec_home_settings_message,
                                    resolveInfo
                                            .activityInfo
                                            .applicationInfo
                                            .loadLabel(this.mPm)
                                            .toString()))
                    .setPositiveButton(
                            android.R.string.ok,
                            new DialogInterface.OnClickListener() { // from class:
                                // com.samsung.android.settings.DCMHomeSettings.1
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i2) {
                                    ResolveInfo resolveInfo2 =
                                            (ResolveInfo)
                                                    ((ArrayList) DCMHomeSettings.this.mHomeApps)
                                                            .get(i);
                                    DCMHomeSettings dCMHomeSettings = DCMHomeSettings.this;
                                    dCMHomeSettings.mSelectedHomePkg =
                                            resolveInfo2.activityInfo.packageName;
                                    int i3 = i;
                                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                            i3, "changeHome ", "DCMHomeSettings");
                                    ResolveInfo resolveInfo3 =
                                            (ResolveInfo)
                                                    ((ArrayList) dCMHomeSettings.mHomeApps).get(i3);
                                    if (resolveInfo3 == null) {
                                        android.util.Log.e(
                                                "DCMHomeSettings", "changeHome) info is null.");
                                    } else {
                                        IntentFilter intentFilter = new IntentFilter();
                                        intentFilter.addAction("android.intent.action.MAIN");
                                        intentFilter.addCategory("android.intent.category.HOME");
                                        intentFilter.addCategory("android.intent.category.DEFAULT");
                                        ComponentName[] componentNameArr =
                                                new ComponentName[dCMHomeSettings.mAppList.size()];
                                        ActivityInfo activityInfo = resolveInfo3.activityInfo;
                                        ComponentName componentName =
                                                new ComponentName(
                                                        activityInfo.packageName,
                                                        activityInfo.name);
                                        for (int i4 = 0;
                                                i4 < dCMHomeSettings.mAppList.size();
                                                i4++) {
                                            ResolveInfo resolveInfo4 =
                                                    (ResolveInfo) dCMHomeSettings.mAppList.get(i4);
                                            ActivityInfo activityInfo2 = resolveInfo4.activityInfo;
                                            componentNameArr[i4] =
                                                    new ComponentName(
                                                            activityInfo2.packageName,
                                                            activityInfo2.name);
                                            String str2 = resolveInfo4.activityInfo.packageName;
                                            ArrayList arrayList = new ArrayList();
                                            ArrayList arrayList2 = new ArrayList();
                                            dCMHomeSettings.mPm.getPreferredActivities(
                                                    arrayList, arrayList2, str2);
                                            if (arrayList2.size() > 0) {
                                                dCMHomeSettings.mPm.clearPackagePreferredActivities(
                                                        resolveInfo4.activityInfo.packageName);
                                            }
                                        }
                                        dCMHomeSettings.mPm.addPreferredActivity(
                                                intentFilter,
                                                resolveInfo3.match,
                                                componentNameArr,
                                                componentName);
                                        if (dCMHomeSettings.mHasEasyLauncher) {
                                            String str3 = resolveInfo3.activityInfo.packageName;
                                            if ("com.nttdocomo.android.dhome".equals(str3)
                                                    || "com.nttdocomo.android.homezozo".equals(str3)
                                                    || "com.nttdocomo.android.paletteui"
                                                            .equals(str3)) {
                                                dCMHomeSettings.setEasymode(false, true);
                                            } else if ("com.sec.android.app.launcher"
                                                    .equals(str3)) {
                                                dCMHomeSettings.setEasymode(false, false);
                                            } else if ("com.sec.android.app.easylauncher"
                                                    .equals(str3)) {
                                                dCMHomeSettings.setEasymode(true, false);
                                            } else {
                                                dCMHomeSettings.setEasymode(false, true);
                                            }
                                        }
                                    }
                                    DCMHomeSettings dCMHomeSettings2 = DCMHomeSettings.this;
                                    dCMHomeSettings2.getClass();
                                    android.util.Log.d("DCMHomeSettings", "startLauncher");
                                    Intent intent = new Intent("android.intent.action.MAIN");
                                    intent.addCategory("android.intent.category.HOME");
                                    intent.addFlags(268435456);
                                    intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                    intent.addFlags(8388608);
                                    dCMHomeSettings2.startActivity(intent);
                                    Intent intent2 =
                                            new Intent(
                                                    "com.sec.android.intent.action.LAUNCHER_CHANGED");
                                    intent2.addFlags(16777216);
                                    dCMHomeSettings2.mContext.sendBroadcast(intent2);
                                    dialogInterface.dismiss();
                                }
                            })
                    .setNegativeButton(
                            android.R.string.cancel, (DialogInterface.OnClickListener) null)
                    .create()
                    .show();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        int i = 0;
        while (true) {
            if (i >= ((ArrayList) this.mHomeApps).size()) {
                break;
            }
            ResolveInfo resolveInfo = (ResolveInfo) ((ArrayList) this.mHomeApps).get(i);
            if (resolveInfo != null) {
                String str = resolveInfo.activityInfo.packageName;
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                this.mPm.getPreferredActivities(arrayList, arrayList2, str);
                if (arrayList2.size() > 0) {
                    this.mCurrentHomePkg = resolveInfo.activityInfo.packageName;
                    break;
                }
            }
            i++;
        }
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                new StringBuilder("mCurrentHomePkg="), this.mCurrentHomePkg, "DCMHomeSettings");
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            ((HomeAdapter) listAdapter).notifyDataSetChanged();
        }
    }

    public final void setEasymode(boolean z, boolean z2) {
        Utils$$ExternalSyntheticOutline0.m653m(
                "setEasymode=", z, ", isDCMHome=", z2, "DCMHomeSettings");
        if (!Utils.isEasyModeEnabled(this.mContext)) {
            android.util.Log.d("DCMHomeSettings", "Easy mode feature is disabled.");
            return;
        }
        Settings.System.putInt(this.mResolver, "easy_mode_switch", !z ? 1 : 0);
        if (("com.sec.android.app.easylauncher".equals(this.mCurrentHomePkg)
                        || !"com.sec.android.app.easylauncher".equals(this.mSelectedHomePkg))
                && (!"com.sec.android.app.easylauncher".equals(this.mCurrentHomePkg)
                        || "com.sec.android.app.easylauncher".equals(this.mSelectedHomePkg))) {
            return;
        }
        Intent intent = new Intent("com.android.launcher.action.EASY_MODE_CHANGE");
        intent.addFlags(16777216);
        intent.putExtra("easymode", z);
        intent.putExtra("easymode_from", "settings");
        if (z2) {
            intent.putExtra("homemode_jpn", "docomo");
        } else {
            intent.putExtra("homemode_jpn", "samsung");
        }
        Intent intent2 = new Intent("com.samsung.launcher.action.EASY_MODE_CHANGE");
        intent2.addFlags(16777216);
        intent2.putExtra("easymode", z);
        intent2.putExtra("easymode_from", "settings");
        if (z2) {
            intent2.putExtra("homemode_jpn", "docomo");
        } else {
            intent2.putExtra("homemode_jpn", "samsung");
        }
        FragmentActivity fragmentActivity = this.mContext;
        UserHandle userHandle = UserHandle.ALL;
        fragmentActivity.sendBroadcastAsUser(intent, userHandle);
        this.mContext.sendBroadcastAsUser(intent2, userHandle);
    }
}
