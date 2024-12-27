package com.samsung.android.settings.usefulfeature.blockedcallandmsg;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class BlockedCallAndMsgPreferenceController extends BasePreferenceController {
    private static final String FIREWALL_PACKAGE_NAME = "com.sec.android.app.firewall";
    private static final String KEY_CALL_MSG_SPAM_FILTER = "blocked_call_and_msg";
    private static final int SEP_13_5 = 130500;
    private static final String TAG = "BlockedCallAndMsgPreferenceController";
    private final PackageManager mPackageManager;
    private SecPreferenceScreen mPreference;

    public BlockedCallAndMsgPreferenceController(Context context) {
        this(context, KEY_CALL_MSG_SPAM_FILTER);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreferenceScreen secPreferenceScreen =
                (SecPreferenceScreen) preferenceScreen.findPreference(KEY_CALL_MSG_SPAM_FILTER);
        this.mPreference = secPreferenceScreen;
        secPreferenceScreen.getClass();
        SecPreferenceUtils.applySummaryColor(secPreferenceScreen, true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.isChinaModel()) {
            return 3;
        }
        try {
            if ((this.mPackageManager.getPackageInfo(FIREWALL_PACKAGE_NAME, 128)
                                    .applicationInfo
                                    .flags
                            & 1)
                    != 0) {
                return UserHandle.myUserId() != 0 ? 4 : 0;
            }
            return 3;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("Settings", "isPackagePreloaded: " + e);
            return 3;
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mContext.getString(R.string.identify_unknown_numbers_chn));
        arrayList.add(this.mContext.getString(R.string.smart_block));
        return Utils.buildSummaryString(
                Utils.getTopLevelSummarySeparator(this.mContext), arrayList, arrayList.size());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        SecPreferenceScreen secPreferenceScreen = this.mPreference;
        if (secPreferenceScreen != null && preference.equals(secPreferenceScreen)) {
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.setClassName(
                        FIREWALL_PACKAGE_NAME,
                        "com.sec.android.app.firewall.spamfilter.SpamFilterSettings");
                this.mContext.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                android.util.secutil.Log.secD(TAG, "ActivityNotFoundException in CalMsgSpamFiter");
                e.printStackTrace();
            }
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return isAvailable();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public BlockedCallAndMsgPreferenceController(Context context, String str) {
        super(context, str);
        this.mPackageManager = context.getPackageManager();
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
