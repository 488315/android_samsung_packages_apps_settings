package com.samsung.android.settings.account.controller;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.preference.Preference;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SmartSwitchPreferenceController extends BasePreferenceController {
    private static final String KEY_SMART_SWITCH_PREFERENCE = "smartswitch_preference";
    private static final String SMARTSWITCH_PACKAGE_NAME = "com.sec.android.easyMover";
    private static final String TAG = "SmartSwitchPreferenceController";
    private static final String URL_MARKET_SERVICE = "market://details?id=";
    private static final String URL_SAMSUNG_APPS_SERVICE = "samsungapps://ProductDetail/";

    public SmartSwitchPreferenceController(Context context) {
        this(context, KEY_SMART_SWITCH_PREFERENCE);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Rune.supportSmartSwitch(this.mContext)) {
            return AccountUtils.checkIsDeviceOwner(this.mContext) ? 1 : 0;
        }
        return 3;
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

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_SMART_SWITCH_PREFERENCE;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v19, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v4, types: [android.content.Intent] */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r5v4, types: [android.content.pm.PackageManager] */
    /* JADX WARN: Type inference failed for: r5v5, types: [android.content.pm.ResolveInfo] */
    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Intent intent;
        Intent intent2;
        if (TextUtils.equals(KEY_SMART_SWITCH_PREFERENCE, preference.getKey())) {
            if (Utils.hasPackage(this.mContext, SMARTSWITCH_PACKAGE_NAME)) {
                try {
                    Intent launchIntentForPackage = this.mContext.getPackageManager().getLaunchIntentForPackage(SMARTSWITCH_PACKAGE_NAME);
                    if (launchIntentForPackage != null) {
                        this.mContext.startActivity(launchIntentForPackage);
                    } else {
                        this.mContext.startActivity(new Intent("com.sec.android.easyMover.LAUNCH_SMART_SWITCH"));
                    }
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Log.e(TAG, "not found activity");
                }
            } else {
                try {
                    ?? intent3 = new Intent();
                    try {
                        intent3.setComponent(new ComponentName("com.sec.android.easyMover.Agent", "com.sec.android.easyMover.Agent.ServiceActivity"));
                        intent3.setAction("com.sec.android.easyMover.Agent.action.AUTO_DOWNLOAD");
                        ?? resolveActivity = this.mContext.getPackageManager().resolveActivity(intent3, 0);
                        if (resolveActivity != 0) {
                            Log.d(TAG, "easyMover resolveActivity is not null, start easyMover Agent, uri : " + intent3.toString());
                            this.mContext.startActivity(intent3);
                            intent3 = intent3;
                            intent2 = resolveActivity;
                        } else {
                            intent2 = new Intent("android.intent.action.VIEW", Uri.parse("samsungapps://ProductDetail/com.sec.android.easyMover"));
                            try {
                                intent2.addFlags(268435456);
                                intent3 = 32768;
                                intent3 = 32768;
                                intent2.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                if (this.mContext.getPackageManager().resolveActivity(intent2, 0) != null) {
                                    Log.d(TAG, "startMarket resolveActivity is not null, start market service, uri : " + intent2.toString());
                                    this.mContext.startActivity(intent2);
                                    intent2 = intent2;
                                } else {
                                    Log.d(TAG, "null resolveActivity.try again via google play");
                                    Intent intent4 = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.sec.android.easyMover"));
                                    try {
                                        intent4.addFlags(268435456);
                                        intent4.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
                                        this.mContext.startActivity(intent4);
                                        intent2 = intent2;
                                    } catch (ActivityNotFoundException e2) {
                                        e = e2;
                                        intent = intent4;
                                        String str = TAG;
                                        StringBuilder sb = new StringBuilder("linkToMarket got an error, uri : ");
                                        sb.append(intent != null ? intent.toString() : null);
                                        Log.d(str, sb.toString());
                                        Log.e(str, "Can not link to market, Exception e: " + e.getMessage());
                                        LoggingHelper.insertEventLogging(7900, 7940);
                                        return super.handlePreferenceTreeClick(preference);
                                    }
                                }
                            } catch (ActivityNotFoundException e3) {
                                e = e3;
                                intent = intent2;
                            }
                        }
                    } catch (ActivityNotFoundException e4) {
                        e = e4;
                        intent = intent3;
                    }
                } catch (ActivityNotFoundException e5) {
                    e = e5;
                    intent = null;
                }
            }
            LoggingHelper.insertEventLogging(7900, 7940);
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
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(Object obj) {
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setEnabled(!(Rune.supportDesktopMode() && Utils.isDesktopModeEnabled(this.mContext)));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SmartSwitchPreferenceController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
    }
}
