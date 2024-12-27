package com.samsung.android.settings.connection.tether;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.datausage.DataSaverBackend;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnDestroy;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.datausage.trafficmanager.ui.DataSaverSummaryCHN;
import com.samsung.android.settings.widget.SecClickableTextPreference;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecTetherDataSaverFooterPreferenceController extends BasePreferenceController
        implements SecTetherControllerInterface,
                DataSaverBackend.Listener,
                LifecycleObserver,
                OnCreate,
                OnDestroy {
    private static final String TAG = "TetherDataSaverFooter";
    private DataSaverBackend mDataSaverBackend;
    SecClickableTextPreference mPreference;

    public SecTetherDataSaverFooterPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecClickableTextPreference) preferenceScreen.findPreference(getPreferenceKey());
        onDataSaverChanged(this.mDataSaverBackend.mPolicyManager.getRestrictBackground());
        String string =
                this.mContext
                        .getResources()
                        .getString(
                                R.string.tether_settings_disabled_on_data_saver_subtext,
                                this.mContext.getString(R.string.sec_tether_settings_title_all));
        if (Rune.SUPPORT_SMARTMANAGER_CN) {
            String string2 = this.mContext.getString(R.string.data_saver_title_chn);
            string = string.replace(this.mContext.getString(R.string.data_saver_title), string2);
            int indexOf = string.indexOf(string2);
            SecClickableTextPreference secClickableTextPreference = this.mPreference;
            secClickableTextPreference.mBeginIndex = indexOf;
            secClickableTextPreference.mEndIndex = string2.length() + indexOf;
            this.mPreference.mClickableSpan =
                    new ClickableSpan() { // from class:
                                          // com.samsung.android.settings.connection.tether.SecTetherDataSaverFooterPreferenceController.1
                        @Override // android.text.style.ClickableSpan
                        public final void onClick(View view) {
                            SubSettingLauncher subSettingLauncher =
                                    new SubSettingLauncher(
                                            ((AbstractPreferenceController)
                                                            SecTetherDataSaverFooterPreferenceController
                                                                    .this)
                                                    .mContext);
                            int metricsCategory =
                                    SecTetherDataSaverFooterPreferenceController.this
                                            .getMetricsCategory();
                            SubSettingLauncher.LaunchRequest launchRequest =
                                    subSettingLauncher.mLaunchRequest;
                            launchRequest.mSourceMetricsCategory = metricsCategory;
                            launchRequest.mDestinationName = DataSaverSummaryCHN.class.getName();
                            subSettingLauncher.setTitleRes(R.string.data_saver_title_chn, null);
                            subSettingLauncher.launch();
                        }
                    };
        }
        this.mPreference.setTitle(string);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mDataSaverBackend.mPolicyManager.getRestrictBackground()) {
            return 0;
        }
        SemLog.i(TAG, "Data Saver on");
        return 2;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public void onCreate(Bundle bundle) {
        DataSaverBackend dataSaverBackend = new DataSaverBackend(this.mContext);
        this.mDataSaverBackend = dataSaverBackend;
        dataSaverBackend.addListener(this);
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface,
              // com.android.settings.datausage.DataSaverBackend.Listener
    public void onDataSaverChanged(boolean z) {
        SecClickableTextPreference secClickableTextPreference = this.mPreference;
        if (secClickableTextPreference != null) {
            secClickableTextPreference.setVisible(z);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        this.mDataSaverBackend.remListener(this);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface
    public /* bridge */ /* synthetic */ void startProvisioningIfNecessary(
            SecTetherSettings secTetherSettings, int i) {
        super.startProvisioningIfNecessary(secTetherSettings, i);
    }

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface
    public void updateController() {
        isAvailable();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.samsung.android.settings.connection.tether.SecTetherControllerInterface
    public void startTethering() {}

    public void onActivityCreated(Bundle bundle) {}

    public void onSaveInstanceState(Bundle bundle) {}

    public void onActivityResult(int i, int i2) {}

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public void onAllowlistStatusChanged(int i, boolean z) {}

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public void onDenylistStatusChanged(int i, boolean z) {}
}
