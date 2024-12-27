package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.internal.view.RotationPolicy;
import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.SettingsMainSwitchPreferenceController;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AutoRotateSwitchBarController extends SettingsMainSwitchPreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private RotationPolicy.RotationPolicyListener mRotationPolicyListener;

    public AutoRotateSwitchBarController(Context context, String str) {
        super(context, str);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!RotationPolicy.isRotationLockToggleVisible(this.mContext)
                        || DeviceStateAutoRotationHelper.isDeviceStateRotationEnabled(
                                this.mContext))
                ? 3
                : 0;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_display;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return !RotationPolicy.isRotationLocked(this.mContext);
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        if (this.mRotationPolicyListener == null) {
            this.mRotationPolicyListener =
                    new RotationPolicy
                            .RotationPolicyListener() { // from class:
                                                        // com.android.settings.display.AutoRotateSwitchBarController.1
                        public final void onChange() {
                            if (((SettingsMainSwitchPreferenceController)
                                                    AutoRotateSwitchBarController.this)
                                            .mSwitchPreference
                                    != null) {
                                AutoRotateSwitchBarController autoRotateSwitchBarController =
                                        AutoRotateSwitchBarController.this;
                                autoRotateSwitchBarController.updateState(
                                        ((SettingsMainSwitchPreferenceController)
                                                        autoRotateSwitchBarController)
                                                .mSwitchPreference);
                            }
                        }
                    };
        }
        RotationPolicy.registerRotationPolicyListener(this.mContext, this.mRotationPolicyListener);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        RotationPolicy.RotationPolicyListener rotationPolicyListener = this.mRotationPolicyListener;
        if (rotationPolicyListener != null) {
            RotationPolicy.unregisterRotationPolicyListener(this.mContext, rotationPolicyListener);
        }
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        boolean z2 = !z;
        this.mMetricsFeatureProvider.action(this.mContext, 1753, z2);
        RotationPolicy.setRotationLock(
                this.mContext, z2, "AutoRotateSwitchBarController#setChecked");
        return true;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
