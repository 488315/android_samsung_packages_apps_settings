package com.android.settings.deviceinfo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Build;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.BidiFormatter;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.Toast;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settingslib.MinorModeUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.development.DevelopmentSettingsEnabler;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BuildNumberPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart {
    static final int REQUEST_CONFIRM_PASSWORD_FOR_DEV_PREF = 100;
    static final int TAPS_TO_BE_A_DEVELOPER = 7;
    private Activity mActivity;
    private RestrictedLockUtils.EnforcedAdmin mDebuggingFeaturesDisallowedAdmin;
    private boolean mDebuggingFeaturesDisallowedBySystem;
    private int mDevHitCountdown;
    private Toast mDevHitToast;
    private InstrumentedPreferenceFragment mFragment;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private boolean mProcessingLastDevHit;
    private final UserManager mUm;

    public BuildNumberPreferenceController(Context context, String str) {
        super(context, str);
        this.mUm = (UserManager) context.getSystemService("user");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableDevelopmentSettings() {
        this.mDevHitCountdown = 0;
        this.mProcessingLastDevHit = false;
        DevelopmentSettingsEnabler.setDevelopmentSettingsEnabled(this.mContext, true);
        Toast toast = this.mDevHitToast;
        if (toast != null) {
            toast.cancel();
        }
        if (MinorModeUtils.hasCHNMinorModePreventDeveloperOptionsFromBeingTurnedOn(this.mContext)) {
            this.mDevHitCountdown = 7;
            return;
        }
        Toast makeText = Toast.makeText(this.mContext, R.string.show_dev_on, 1);
        this.mDevHitToast = makeText;
        makeText.show();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getSearchFeatureProvider().getClass();
    }

    private void showDevelopmentSettingsConfirmDialog() {
        Toast toast = this.mDevHitToast;
        if (toast != null) {
            toast.cancel();
        }
        final int i = 1;
        AlertDialog.Builder positiveButton =
                new AlertDialog.Builder(this.mContext)
                        .setTitle(R.string.dev_settings_warning_title)
                        .setMessage(R.string.dev_settings_warning_message)
                        .setPositiveButton(
                                android.R.string.ok,
                                new DialogInterface.OnClickListener(
                                        this) { // from class:
                                                // com.android.settings.deviceinfo.BuildNumberPreferenceController.1
                                    public final /* synthetic */ BuildNumberPreferenceController
                                            this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i2) {
                                        switch (i) {
                                            case 0:
                                                this.this$0.mDevHitCountdown = 7;
                                                this.this$0.mDevHitToast = null;
                                                this.this$0.mProcessingLastDevHit = false;
                                                break;
                                            default:
                                                this.this$0.enableDevelopmentSettings();
                                                dialogInterface.dismiss();
                                                break;
                                        }
                                    }
                                });
        final int i2 = 0;
        AlertDialog create =
                positiveButton
                        .setNegativeButton(
                                android.R.string.cancel,
                                new DialogInterface.OnClickListener(
                                        this) { // from class:
                                                // com.android.settings.deviceinfo.BuildNumberPreferenceController.1
                                    public final /* synthetic */ BuildNumberPreferenceController
                                            this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i22) {
                                        switch (i2) {
                                            case 0:
                                                this.this$0.mDevHitCountdown = 7;
                                                this.this$0.mDevHitToast = null;
                                                this.this$0.mProcessingLastDevHit = false;
                                                break;
                                            default:
                                                this.this$0.enableDevelopmentSettings();
                                                dialogInterface.dismiss();
                                                break;
                                        }
                                    }
                                })
                        .create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        return BidiFormatter.getInstance().unicodeWrap(Build.DISPLAY);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        EnterpriseDeviceManager enterpriseDeviceManager;
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey()) || isUserAMonkey()) {
            return false;
        }
        if (!this.mUm.isAdminUser() && !this.mUm.isDemoUser()) {
            this.mMetricsFeatureProvider.action(this.mContext, 847, new Pair[0]);
            return false;
        }
        if (!WizardManagerHelper.isDeviceProvisioned(this.mContext)) {
            this.mMetricsFeatureProvider.action(this.mContext, 847, new Pair[0]);
            return false;
        }
        if (this.mUm.hasUserRestriction("no_debugging_features")) {
            if (this.mUm.isDemoUser()) {
                Context context = this.mContext;
                StringBuilder sb = Utils.sBuilder;
                ComponentName deviceOwnerComponentOnAnyUser =
                        ((DevicePolicyManager) context.getSystemService("device_policy"))
                                .getDeviceOwnerComponentOnAnyUser();
                if (deviceOwnerComponentOnAnyUser != null) {
                    Intent action =
                            new Intent()
                                    .setPackage(deviceOwnerComponentOnAnyUser.getPackageName())
                                    .setAction(
                                            "com.android.settings.action.REQUEST_DEBUG_FEATURES");
                    if (this.mContext.getPackageManager().resolveActivity(action, 0) != null) {
                        this.mContext.startActivity(action);
                        return false;
                    }
                }
            }
            RestrictedLockUtils.EnforcedAdmin enforcedAdmin =
                    this.mDebuggingFeaturesDisallowedAdmin;
            if (enforcedAdmin != null && !this.mDebuggingFeaturesDisallowedBySystem) {
                RestrictedLockUtils.sendShowAdminSupportDetailsIntent(this.mContext, enforcedAdmin);
            }
            this.mMetricsFeatureProvider.action(this.mContext, 847, new Pair[0]);
            return false;
        }
        if (!KnoxUtils.checkKnoxCustomSettingsHiddenItem(256)) {
            Context context2 = this.mContext;
            if (((context2 == null
                                    || (enterpriseDeviceManager =
                                                    EnterpriseDeviceManager.getInstance(
                                                            context2.getApplicationContext()))
                                            == null)
                            ? null
                            : enterpriseDeviceManager.getRestrictionPolicy())
                    .isDeveloperModeAllowed()) {
                int i = this.mDevHitCountdown;
                if (i > 0) {
                    int i2 = i - 1;
                    this.mDevHitCountdown = i2;
                    if (i2 == 0 && !this.mProcessingLastDevHit) {
                        this.mDevHitCountdown = i;
                        String string =
                                this.mContext.getString(
                                        R.string.sec_unlock_set_unlock_launch_picker_title);
                        ChooseLockSettingsHelper.Builder builder =
                                new ChooseLockSettingsHelper.Builder(
                                        this.mActivity, this.mFragment);
                        builder.mRequestCode = 100;
                        builder.mTitle = string;
                        boolean show = builder.show();
                        this.mProcessingLastDevHit = show;
                        if (!show) {
                            showDevelopmentSettingsConfirmDialog();
                        }
                        MetricsFeatureProvider metricsFeatureProvider =
                                this.mMetricsFeatureProvider;
                        Activity activity = this.mActivity;
                        metricsFeatureProvider.getClass();
                        metricsFeatureProvider.action(
                                MetricsFeatureProvider.getAttribution(activity),
                                848,
                                this.mFragment.getMetricsCategory(),
                                !this.mProcessingLastDevHit ? 1 : 0,
                                null);
                    } else if (i2 > 0 && i2 < 5) {
                        Toast toast = this.mDevHitToast;
                        if (toast != null) {
                            toast.cancel();
                        }
                        Context context3 = this.mContext;
                        Resources resources = context3.getResources();
                        int i3 = this.mDevHitCountdown;
                        Toast makeText =
                                Toast.makeText(
                                        context3,
                                        resources.getQuantityString(
                                                R.plurals.sec_show_dev_countdown,
                                                i3,
                                                Integer.valueOf(i3)),
                                        0);
                        this.mDevHitToast = makeText;
                        makeText.show();
                    }
                    MetricsFeatureProvider metricsFeatureProvider2 = this.mMetricsFeatureProvider;
                    Activity activity2 = this.mActivity;
                    metricsFeatureProvider2.getClass();
                    metricsFeatureProvider2.action(
                            MetricsFeatureProvider.getAttribution(activity2),
                            848,
                            this.mFragment.getMetricsCategory(),
                            0,
                            null);
                } else if (i < 0) {
                    Toast toast2 = this.mDevHitToast;
                    if (toast2 != null) {
                        toast2.cancel();
                    }
                    Toast makeText2 = Toast.makeText(this.mContext, R.string.show_dev_already, 1);
                    this.mDevHitToast = makeText2;
                    makeText2.show();
                    MetricsFeatureProvider metricsFeatureProvider3 = this.mMetricsFeatureProvider;
                    Activity activity3 = this.mActivity;
                    metricsFeatureProvider3.getClass();
                    metricsFeatureProvider3.action(
                            MetricsFeatureProvider.getAttribution(activity3),
                            848,
                            this.mFragment.getMetricsCategory(),
                            1,
                            null);
                }
                return true;
            }
        }
        this.mMetricsFeatureProvider.action(this.mContext, 847, new Pair[0]);
        return false;
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

    public boolean isUserAMonkey() {
        return ActivityManager.isUserAMonkey();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    public boolean onActivityResult(int i, int i2, Intent intent) {
        if (i != 100) {
            return false;
        }
        if (i2 == -1) {
            showDevelopmentSettingsConfirmDialog();
        }
        this.mProcessingLastDevHit = false;
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mDebuggingFeaturesDisallowedAdmin =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, UserHandle.myUserId(), "no_debugging_features");
        this.mDebuggingFeaturesDisallowedBySystem =
                RestrictedLockUtilsInternal.hasBaseUserRestriction(
                        this.mContext, UserHandle.myUserId(), "no_debugging_features");
        this.mDevHitCountdown =
                DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(this.mContext) ? -1 : 7;
        this.mDevHitToast = null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setHost(InstrumentedPreferenceFragment instrumentedPreferenceFragment) {
        this.mFragment = instrumentedPreferenceFragment;
        this.mActivity = instrumentedPreferenceFragment.getActivity();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public boolean useDynamicSliceSummary() {
        return true;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
