package com.samsung.android.settings.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.datausage.DataUsageUtils;
import com.android.settingslib.Utils;
import com.android.settingslib.core.lifecycle.LifecycleObserver;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSimToolkitPreferenceController extends BasePreferenceController
        implements LifecycleObserver {
    private static final String KEY_SIM_TOOLKIT = "sec_sim_toolkit";
    private static final String TAG = "SecSimToolkitPreferenceController";
    private Context mContext;
    private Preference mPreference;
    private BroadcastReceiver mReceiver;
    private TelephonyManager mTelephonyManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UpdateStateReceiver extends BroadcastReceiver {
        public UpdateStateReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                    "action: ",
                    action,
                    SecSimToolkitPreferenceController.TAG,
                    "android.intent.action.SIM_STATE_CHANGED")) {
                SecSimToolkitPreferenceController secSimToolkitPreferenceController =
                        SecSimToolkitPreferenceController.this;
                secSimToolkitPreferenceController.updateState(
                        secSimToolkitPreferenceController.mPreference);
                return;
            }
            if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                Uri data = intent.getData();
                String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                DialogFragment$$ExternalSyntheticOutline0.m(
                        "packageName: ", schemeSpecificPart, SecSimToolkitPreferenceController.TAG);
                if (schemeSpecificPart != null) {
                    if (schemeSpecificPart.equals("com.android.stk")
                            || schemeSpecificPart.equals("com.android.stk2")) {
                        SecSimToolkitPreferenceController secSimToolkitPreferenceController2 =
                                SecSimToolkitPreferenceController.this;
                        secSimToolkitPreferenceController2.updateState(
                                secSimToolkitPreferenceController2.mPreference);
                    }
                }
            }
        }
    }

    public SecSimToolkitPreferenceController(Context context) {
        super(context, KEY_SIM_TOOLKIT);
        this.mContext = context;
        this.mTelephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x004d, code lost:

       if (r7 != 1) goto L14;
    */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0050, code lost:

       r3 = false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0079, code lost:

       if (r7 == 1) goto L15;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isStkComponentEnabled() {
        /*
            r7 = this;
            android.telephony.TelephonyManager r0 = r7.mTelephonyManager
            java.lang.String r1 = "com.android.stk.StkLauncherActivity_Chn"
            java.lang.String r2 = "com.android.stk"
            r3 = 1
            r4 = 0
            if (r0 == 0) goto L5b
            boolean r0 = r0.isMultiSimEnabled()
            if (r0 == 0) goto L5b
            android.content.Context r0 = r7.mContext
            boolean r0 = com.android.settings.Utils.hasPackage(r0, r2)
            if (r0 == 0) goto L53
            android.content.Context r0 = r7.mContext
            java.lang.String r5 = "com.android.stk2"
            boolean r0 = com.android.settings.Utils.hasPackage(r0, r5)
            if (r0 == 0) goto L53
            android.content.Context r0 = r7.mContext
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            android.content.ComponentName r6 = new android.content.ComponentName
            r6.<init>(r2, r1)
            int r0 = r0.getComponentEnabledSetting(r6)
            android.content.Context r7 = r7.mContext
            android.content.pm.PackageManager r7 = r7.getPackageManager()
            android.content.ComponentName r1 = new android.content.ComponentName
            java.lang.String r2 = "com.android.stk2.StkLauncherActivity_Chn"
            r1.<init>(r5, r2)
            int r7 = r7.getComponentEnabledSetting(r1)
            java.lang.String r1 = com.samsung.android.settings.connection.SecSimToolkitPreferenceController.TAG
            java.lang.String r2 = "DS isStkComponentEnabled: "
            java.lang.String r5 = ", "
            com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0.m(r2, r5, r0, r7, r1)
            if (r0 == r3) goto L51
            if (r7 != r3) goto L50
            goto L51
        L50:
            r3 = r4
        L51:
            r4 = r3
            goto L83
        L53:
            java.lang.String r7 = com.samsung.android.settings.connection.SecSimToolkitPreferenceController.TAG
            java.lang.String r0 = "DS model, but do not has Stk/Stk2"
            android.util.Log.d(r7, r0)
            goto L83
        L5b:
            android.content.Context r0 = r7.mContext
            boolean r0 = com.android.settings.Utils.hasPackage(r0, r2)
            if (r0 == 0) goto L7c
            android.content.Context r7 = r7.mContext
            android.content.pm.PackageManager r7 = r7.getPackageManager()
            android.content.ComponentName r0 = new android.content.ComponentName
            r0.<init>(r2, r1)
            int r7 = r7.getComponentEnabledSetting(r0)
            java.lang.String r0 = com.samsung.android.settings.connection.SecSimToolkitPreferenceController.TAG
            java.lang.String r1 = "SS isStkComponentEnabled: "
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m1m(r7, r1, r0)
            if (r7 != r3) goto L50
            goto L51
        L7c:
            java.lang.String r7 = com.samsung.android.settings.connection.SecSimToolkitPreferenceController.TAG
            java.lang.String r0 = "SS model, but do not has Stk"
            android.util.Log.d(r7, r0)
        L83:
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.connection.SecSimToolkitPreferenceController.isStkComponentEnabled():boolean");
    }

    private void registerReceivers() {
        if (this.mReceiver == null) {
            this.mReceiver = new UpdateStateReceiver();
            this.mContext.registerReceiver(
                    this.mReceiver,
                    AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                            "android.intent.action.SIM_STATE_CHANGED"));
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addDataScheme("package");
            this.mContext.registerReceiver(this.mReceiver, intentFilter);
        }
    }

    private void unRegisterReceivers() {
        BroadcastReceiver broadcastReceiver = this.mReceiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
            this.mReceiver = null;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Log.d(TAG, "getAvailabilityStatus: isChinaModel:" + Rune.isChinaModel());
        return (Utils.isWifiOnly(this.mContext) || !Rune.isChinaModel()) ? 3 : 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_SIM_TOOLKIT;
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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        if (Rune.isChinaModel()) {
            registerReceivers();
        } else {
            Log.d(TAG, "Not china model, skip onStart");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        unRegisterReceivers();
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
        Log.d(TAG, "updateState: " + preference);
        if (preference == null) {
            return;
        }
        Context context = this.mContext;
        int i = ConnectionsUtils.$r8$clinit;
        if (DataUsageUtils.hasActiveSim(context) && isStkComponentEnabled()) {
            preference.setEnabled(true);
        } else {
            preference.setEnabled(false);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
