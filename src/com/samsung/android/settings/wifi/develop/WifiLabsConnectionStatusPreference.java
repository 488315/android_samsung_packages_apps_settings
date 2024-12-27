package com.samsung.android.settings.wifi.develop;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.wifitrackerlib.StandardWifiEntry;

import com.samsung.android.settings.wifi.develop.details.WifiNetworkDetailsFragmentForLabs;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiLabsConnectionStatusPreference extends Preference {
    public static final String[] MONTHS = {
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    };
    public int mCurrentState;
    public PreferenceViewHolder mHolder;
    public final SemWifiManager mSemWifiManager;
    public final WifiManager mWifiManager;

    public WifiLabsConnectionStatusPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCurrentState = -1;
        setLayoutResource(R.layout.sec_wifi_labs_connection_status_preference);
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    public final void launchNetworkDetailsFragment(
            String str, StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey) {
        Context context = getContext();
        Bundle bundle = new Bundle();
        bundle.putString("key_chosen_wifientry_key", standardWifiEntryKey.toString());
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mTitle = str;
        launchRequest.mDestinationName = WifiNetworkDetailsFragmentForLabs.class.getName();
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 0;
        subSettingLauncher.launch();
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Log.d("WifiLabsConnectionStatus.Preference", "onBindViewHolder");
        this.mHolder = preferenceViewHolder;
        updatePreference(false);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(36:5|(1:7)(1:154)|(1:9)(1:153)|10|(2:12|(36:14|(1:16)(2:148|(1:150)(1:151))|17|18|19|20|21|22|(3:24|(1:26)(2:28|(1:30)(1:31))|27)|32|(2:34|(26:36|37|38|(1:40)(2:137|(1:139)(1:140))|41|(4:44|(2:46|47)(2:49|(5:51|52|53|54|55)(1:134))|48|42)|135|136|59|(1:61)(2:131|(1:133))|62|63|64|(2:66|(12:68|70|71|(2:73|(8:75|76|(1:78)(1:123)|79|(1:81)(2:119|(1:121)(1:122))|82|(8:84|(2:86|(1:90))(1:106)|91|(1:93)(1:105)|94|(1:96)|97|(1:99)(1:104))(7:107|(1:109)(1:118)|110|(1:112)(1:117)|113|(1:115)|116)|(2:101|102)(1:103)))|125|76|(0)(0)|79|(0)(0)|82|(0)(0)|(0)(0)))|128|70|71|(0)|125|76|(0)(0)|79|(0)(0)|82|(0)(0)|(0)(0)))|144|38|(0)(0)|41|(1:42)|135|136|59|(0)(0)|62|63|64|(0)|128|70|71|(0)|125|76|(0)(0)|79|(0)(0)|82|(0)(0)|(0)(0)))|152|21|22|(0)|32|(0)|144|38|(0)(0)|41|(1:42)|135|136|59|(0)(0)|62|63|64|(0)|128|70|71|(0)|125|76|(0)(0)|79|(0)(0)|82|(0)(0)|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x034a, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x034b, code lost:

       android.util.Log.e("WifiLabsConnectionStatus.Preference", "NumberFormatException " + r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0323, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0324, code lost:

       r5 = 0;
    */
    /* JADX WARN: Removed duplicated region for block: B:101:0x052b  */
    /* JADX WARN: Removed duplicated region for block: B:103:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x04b8  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x036c  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0231  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0310 A[Catch: NumberFormatException -> 0x0323, TryCatch #2 {NumberFormatException -> 0x0323, blocks: (B:64:0x030a, B:66:0x0310, B:68:0x0318), top: B:63:0x030a }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0336 A[Catch: NumberFormatException -> 0x034a, TryCatch #3 {NumberFormatException -> 0x034a, blocks: (B:71:0x0329, B:73:0x0336, B:75:0x033e), top: B:70:0x0329 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0361  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0408  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePreference(boolean r39) {
        /*
            Method dump skipped, instructions count: 1327
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.develop.WifiLabsConnectionStatusPreference.updatePreference(boolean):void");
    }

    public WifiLabsConnectionStatusPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiLabsConnectionStatusPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiLabsConnectionStatusPreference(Context context) {
        this(context, null);
    }
}
