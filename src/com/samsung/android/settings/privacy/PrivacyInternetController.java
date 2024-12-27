package com.samsung.android.settings.privacy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PrivacyInternetController extends BasePreferenceController {
    private static final String INTERNET_AUTHORITY_URI = "content://com.sec.android.app.sbrowser";
    private static final String INTERNET_PACKAGE_NAME = "com.sec.android.app.sbrowser";
    private static final String INTERNET_SETTINGS_PRIVACY =
            "samsunginternet://settings_privacy/direct";
    private static final String METHOD_ANTI_TRACKING_STATS = "getAntiTrackingStats";
    private static final String PROVIDER_KEY_MODE = "mode";
    private static final String PROVIDER_KEY_TRACKER = "tracker";
    private static final String PROVIDER_KEY_TRACKING = "tracking";
    private static final String TAG = "PrivacyInternetController";
    private int mode;
    private int tracker;
    private int tracking;

    public PrivacyInternetController(Context context, String str) {
        super(context, str);
        this.mode = -1;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Bundle bundle = null;
        try {
            bundle =
                    this.mContext
                            .getContentResolver()
                            .call(
                                    Uri.parse(INTERNET_AUTHORITY_URI),
                                    METHOD_ANTI_TRACKING_STATS,
                                    (String) null,
                                    (Bundle) null);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "IllegalArgumentException:" + e.getMessage());
        }
        if (bundle != null) {
            this.mode = bundle.getInt(PROVIDER_KEY_MODE);
            this.tracker = bundle.getInt(PROVIDER_KEY_TRACKER);
            this.tracking = bundle.getInt(PROVIDER_KEY_TRACKING);
        }
        return (bundle == null || this.mode == -1) ? 3 : 0;
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
        return String.format(
                this.mContext.getString(R.string.privacy_protection_internet_summary),
                Integer.valueOf(this.tracker),
                Integer.valueOf(this.tracking));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        LoggingHelper.insertEventLogging(getMetricsCategory(), 56070);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(INTERNET_SETTINGS_PRIVACY));
        intent.setPackage(INTERNET_PACKAGE_NAME);
        this.mContext.startActivity(intent);
        return true;
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
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
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

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
