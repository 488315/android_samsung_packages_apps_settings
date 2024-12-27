package com.samsung.android.settings.wifi;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class WifiListAdapter extends RecyclerView.Adapter {
    public final String TAG = getLogTag();
    public final Context mContext;
    public final boolean mHideLeftRightPadding;
    public final boolean mInSetupWizardActivity;
    public boolean mIsMaxSummaryLineOn;
    public final boolean mIsSupportWifiTips;
    public final RecyclerView mParentView;
    public final String mSAScreenId;
    public List mWifiEntries;
    public static final boolean DBG = Debug.semIsProductDev();
    public static final int mDefaultIconResId = R.drawable.ic_wifi_signal_0;
    public static final int[] STATE_NONE = new int[0];
    public static final int[] STATE_SECURED = {R.attr.state_encrypted};
    public static final int[] STATE_WIFI5_NONE = {R.attr.state_wifi_5};
    public static final int[] STATE_WIFI5_SECURED = {R.attr.state_wifi_5, R.attr.state_encrypted};
    public static final int[] STATE_WIFI6_NONE = {R.attr.state_wifi_6};
    public static final int[] STATE_WIFI6_SECURED = {R.attr.state_wifi_6, R.attr.state_encrypted};
    public static final int[] STATE_WIFI6E_NONE = {R.attr.state_wifi_6e};
    public static final int[] STATE_WIFI6E_SECURED = {R.attr.state_wifi_6e, R.attr.state_encrypted};
    public static final int[] STATE_WIFI7_NONE = {R.attr.state_wifi_7};
    public static final int[] STATE_WIFI7_SECURED = {R.attr.state_wifi_7, R.attr.state_encrypted};

    public WifiListAdapter(
            Context context,
            RecyclerView recyclerView,
            ArrayList arrayList,
            boolean z,
            String str,
            boolean z2) {
        this.mContext = context;
        this.mParentView = recyclerView;
        this.mWifiEntries = arrayList;
        this.mHideLeftRightPadding = z;
        this.mSAScreenId = str;
        this.mInSetupWizardActivity = z2;
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        boolean z3 = false;
        if (Settings.Global.getInt(
                                context.getContentResolver(),
                                "sem_wifi_developer_option_visible",
                                0)
                        == 1
                && Settings.Global.getInt(
                                context.getContentResolver(),
                                "sec_wifi_developer_max_summary_line",
                                0)
                        == 1) {
            z3 = true;
        }
        this.mIsMaxSummaryLineOn = z3;
        this.mIsSupportWifiTips = WifiTipsUtils.isSupportWifiTips(context);
    }

    public final void deleteItem(int i) {
        String str = this.TAG;
        if (i < 0 || i >= this.mWifiEntries.size()) {
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            i, "deleteItem with abnormal index ", " / ");
            m.append(getItemCount());
            Log.e(str, m.toString());
            return;
        }
        WifiEntry wifiEntry = (WifiEntry) this.mWifiEntries.remove(i);
        notifyItemRemoved(i);
        if (DBG) {
            StringBuilder m2 = ListPopupWindow$$ExternalSyntheticOutline0.m(i, "delete_", ": ");
            m2.append(wifiEntry.getKey());
            Log.d(str, m2.toString());
        }
    }

    public abstract String getLogTag();

    public final void launchFragment(String str, Bundle bundle, String str2) {
        if (DBG) {
            Log.d(this.TAG, "launch " + str + ". " + str2);
        }
        notifyOnLaunchActivityIfNeeded();
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = str;
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 103;
        launchRequest.mTitle = str2;
        subSettingLauncher.launch();
        notifyOnLaunchActivityFinishedIfNeeded();
    }

    public abstract void notifyOnLaunchActivityFinishedIfNeeded();

    public abstract void notifyOnLaunchActivityIfNeeded();

    public final void removeAll() {
        List list = this.mWifiEntries;
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:73:0x00eb, code lost:

       if (com.samsung.android.wifitrackerlib.SemWifiUtils.isSecured(r11.getSecurity$1()) == false) goto L66;
    */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00ed, code lost:

       r0 = com.samsung.android.settings.wifi.WifiListAdapter.STATE_WIFI5_SECURED;
    */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00f2, code lost:

       r10.setState(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00f0, code lost:

       r0 = com.samsung.android.settings.wifi.WifiListAdapter.STATE_WIFI5_NONE;
    */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0100  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateIcon(
            android.widget.ImageView r9,
            android.graphics.drawable.StateListDrawable r10,
            com.android.wifitrackerlib.WifiEntry r11) {
        /*
            Method dump skipped, instructions count: 300
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.WifiListAdapter.updateIcon(android.widget.ImageView,"
                    + " android.graphics.drawable.StateListDrawable,"
                    + " com.android.wifitrackerlib.WifiEntry):void");
    }
}
