package com.samsung.android.settings.connection;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CarrierBridgePreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final String CARRIER_BRIDGE_SUMMARY_DB_NAME = "carrier_bridge_summary";
    static final String KEY_CARRIER_BRIDGE = "carrier_bridge";
    static final String TAG = "CarrierBridgePreferenceController";
    private ContentObserver mCBObserver;
    private SecPreferenceScreen mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.connection.CarrierBridgePreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ CarrierBridgePreferenceController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ AnonymousClass1(
                CarrierBridgePreferenceController carrierBridgePreferenceController,
                Handler handler,
                int i) {
            super(handler);
            this.$r8$classId = i;
            this.this$0 = carrierBridgePreferenceController;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    if (this.this$0.mPreference != null) {
                        CarrierBridgePreferenceController carrierBridgePreferenceController =
                                this.this$0;
                        carrierBridgePreferenceController.updateState(
                                carrierBridgePreferenceController.mPreference);
                        break;
                    }
                    break;
                default:
                    if (this.this$0.mPreference != null) {
                        CarrierBridgePreferenceController carrierBridgePreferenceController2 =
                                this.this$0;
                        carrierBridgePreferenceController2.updateState(
                                carrierBridgePreferenceController2.mPreference);
                        break;
                    }
                    break;
            }
        }
    }

    public CarrierBridgePreferenceController(Context context, String str) {
        super(context, str);
        this.mCBObserver = new AnonymousClass1(this, new Handler(Looper.getMainLooper()), 0);
    }

    private ContentObserver getObserver() {
        if (this.mCBObserver == null) {
            this.mCBObserver = new AnonymousClass1(this, new Handler(Looper.getMainLooper()), 1);
        }
        return this.mCBObserver;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return TextUtils.isEmpty(
                        Settings.System.getString(
                                this.mContext.getContentResolver(), CARRIER_BRIDGE_SUMMARY_DB_NAME))
                ? 2
                : 0;
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
        return Settings.System.getString(
                this.mContext.getContentResolver(), CARRIER_BRIDGE_SUMMARY_DB_NAME);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        try {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor(CARRIER_BRIDGE_SUMMARY_DB_NAME),
                            false,
                            getObserver());
        } catch (Exception e) {
            SemLog.e(TAG, "can't Register Observer" + e);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        if (this.mCBObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mCBObserver);
            this.mCBObserver = null;
        }
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
        SecPreferenceScreen secPreferenceScreen = (SecPreferenceScreen) preference;
        secPreferenceScreen.getClass();
        SecPreferenceUtils.applySummaryColor(secPreferenceScreen, true);
        String string =
                Settings.System.getString(
                        this.mContext.getContentResolver(), CARRIER_BRIDGE_SUMMARY_DB_NAME);
        preference.setVisible(!TextUtils.isEmpty(string));
        preference.setSummary(string);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
