package com.android.settings.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.BidiFormatter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SeslSwitchBar;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.dialog.AccessibilityServiceWarning;
import com.android.settings.R;
import com.android.settings.accessibility.shortcuts.EditShortcutsPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.accessibility.AccessibilityUtils;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.exclusive.AccessibilityExclusiveUtil;
import com.samsung.android.settings.accessibility.recommend.RecommendedForYouUtils;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ToggleAccessibilityServicePreferenceFragment extends ToggleFeaturePreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AccessibilityDialogUtils.TTSLanguageDialogInfo languageDialogInfo;
    public AnonymousClass1 mPackageRemovedReceiver;
    public ComponentName mTileComponentName;
    public Dialog mWarningDialog;
    public final AtomicBoolean mIsDialogShown = new AtomicBoolean(false);
    public boolean mDisabledStateLogged = false;
    public long mStartTimeMillsForLogging = 0;

    public final boolean checkOneUIPopupDisplayed(boolean z) {
        if (this.languageDialogInfo.shouldShowDialog) {
            this.mIsDialogShown.set(false);
            showPopupDialog(z ? 9004 : 9005);
            Dialog dialog = this.mWarningDialog;
            if (dialog != null && dialog.isShowing()) {
                this.mWarningDialog.dismiss();
            }
            return true;
        }
        if (!AccessibilityExclusiveUtil.isExclusiveTaskEnabled(
                getPrefContext(), getExclusiveTaskName())) {
            return false;
        }
        this.mIsDialogShown.set(false);
        showPopupDialog(z ? 9002 : 9003);
        Dialog dialog2 = this.mWarningDialog;
        if (dialog2 != null && dialog2.isShowing()) {
            this.mWarningDialog.dismiss();
        }
        return true;
    }

    public final void enableShortcut(Context context) {
        int userPreferredShortcutTypes =
                this.mComponentName != null ? getUserPreferredShortcutTypes() : 0;
        if (userPreferredShortcutTypes == 0) {
            showPopupDialog(9000);
            return;
        }
        this.mShortcutPreference.setChecked(true);
        AccessibilityUtil.optInAllValuesToSettings(
                context, userPreferredShortcutTypes, this.mComponentName);
        this.mShortcutPreference.setSummary(getShortcutTypeSummary(context));
    }

    public final AccessibilityServiceInfo getAccessibilityServiceInfo() {
        if (this.mComponentName == null) {
            return null;
        }
        List<AccessibilityServiceInfo> installedAccessibilityServiceList =
                AccessibilityManager.getInstance(getPrefContext())
                        .getInstalledAccessibilityServiceList();
        int size = installedAccessibilityServiceList.size();
        for (int i = 0; i < size; i++) {
            AccessibilityServiceInfo accessibilityServiceInfo =
                    installedAccessibilityServiceList.get(i);
            ResolveInfo resolveInfo = accessibilityServiceInfo.getResolveInfo();
            if (this.mComponentName.getPackageName().equals(resolveInfo.serviceInfo.packageName)
                    && this.mComponentName.getClassName().equals(resolveInfo.serviceInfo.name)) {
                return accessibilityServiceInfo;
            }
        }
        return null;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final int getDefaultShortcutTypes() {
        AccessibilityServiceInfo accessibilityServiceInfo = getAccessibilityServiceInfo();
        return (accessibilityServiceInfo == null
                        || !accessibilityServiceInfo.isAccessibilityTool()
                        || this.mTileComponentName == null)
                ? 0
                : 16;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        if (i == 1008) {
            return 1810;
        }
        switch (i) {
            case 1002:
            case 1003:
            case VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI /* 1004 */:
                return 583;
            case 1005:
                return 584;
            default:
                return super.getDialogMetricsCategory(i);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ToggleAccessibilityServicePreferenceFragment";
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return getArguments().getInt("metrics_category");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return 0;
    }

    public final void handleConfirmServiceEnabled(boolean z) {
        getArguments().putBoolean("checked", z);
        onPreferenceToggled(this.mPreferenceKey, z);
    }

    public final void onAllowButtonFromEnableToggleClicked() {
        if (checkOneUIPopupDisplayed(true)) {
            return;
        }
        handleConfirmServiceEnabled(true);
        AccessibilityServiceInfo accessibilityServiceInfo = getAccessibilityServiceInfo();
        if (accessibilityServiceInfo != null && (accessibilityServiceInfo.flags & 256) != 0) {
            this.mIsDialogShown.set(false);
        }
        Dialog dialog = this.mWarningDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void onAllowButtonFromShortcutToggleClicked() {
        if (((SeslSwitchBar) this.mToggleServiceSwitchBar).mSwitch.isChecked()
                || !checkOneUIPopupDisplayed(false)) {
            this.mIsDialogShown.set(false);
            enableShortcut(getPrefContext());
            Dialog dialog = this.mWarningDialog;
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (z
                != (this.mComponentName == null
                        ? false
                        : AccessibilityUtils.getEnabledServicesFromSettings(getPrefContext())
                                .contains(this.mComponentName))) {
            if (!z) {
                this.mToggleServiceSwitchBar.setCheckedInternal(true);
                getArguments().putBoolean("checked", true);
                showDialog(1005);
                return;
            }
            this.mToggleServiceSwitchBar.setCheckedInternal(false);
            getArguments().putBoolean("checked", false);
            if (((AccessibilityManager)
                            getPrefContext().getSystemService(AccessibilityManager.class))
                    .isAccessibilityServiceWarningRequired(getAccessibilityServiceInfo())) {
                showPopupDialog(1002);
            } else {
                if (checkOneUIPopupDisplayed(true)) {
                    return;
                }
                onAllowButtonFromEnableToggleClicked();
            }
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null || !bundle.containsKey("has_logged")) {
            return;
        }
        this.mDisabledStateLogged = bundle.getBoolean("has_logged");
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        final int i2 = 1;
        final int i3 = 0;
        Context prefContext = getPrefContext();
        AccessibilityServiceInfo accessibilityServiceInfo = getAccessibilityServiceInfo();
        if (accessibilityServiceInfo != null) {
            int i4 = AccessibilityUtil.$r8$clinit;
            accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.isSystemApp();
        }
        switch (i) {
            case 1002:
                if (accessibilityServiceInfo == null) {
                    return null;
                }
                final int i5 = 3;
                final int i6 = 4;
                AlertDialog createAccessibilityServiceWarningDialog =
                        AccessibilityServiceWarning.createAccessibilityServiceWarningDialog(
                                getPrefContext(),
                                accessibilityServiceInfo,
                                new View.OnClickListener(this) { // from class:
                                    // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda1
                                    public final /* synthetic */
                                    ToggleAccessibilityServicePreferenceFragment f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i7 = i3;
                                        ToggleAccessibilityServicePreferenceFragment
                                                toggleAccessibilityServicePreferenceFragment =
                                                        this.f$0;
                                        switch (i7) {
                                            case 0:
                                                int i8 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromEnableToggleClicked();
                                                break;
                                            case 1:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutClicked");
                                                break;
                                            case 2:
                                                int i9 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 3:
                                                int i10 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .handleConfirmServiceEnabled(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                break;
                                            case 4:
                                                int i11 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 5:
                                                int i12 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromShortcutToggleClicked();
                                                break;
                                            case 6:
                                                int i13 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mShortcutPreference.setChecked(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutToggleClicked");
                                                break;
                                            case 7:
                                                int i14 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            default:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mIsDialogShown.set(false);
                                                Context context =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getContext();
                                                int metricsCategory =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getMetricsCategory();
                                                CharSequence shortcutTitle =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getShortcutTitle();
                                                ComponentName componentName =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mComponentName;
                                                Intent intent =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getIntent();
                                                Uri uri =
                                                        EditShortcutsPreferenceFragment
                                                                .VOLUME_KEYS_SHORTCUT_SETTING;
                                                Bundle bundle = new Bundle();
                                                if (AccessibilityShortcutController
                                                        .MAGNIFICATION_COMPONENT_NAME
                                                        .equals(componentName)) {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                "com.android.server.accessibility.MagnificationController"
                                                            });
                                                } else {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                componentName.flattenToString()
                                                            });
                                                }
                                                Intent intent2 = new Intent();
                                                if (intent != null) {
                                                    WizardManagerHelper.copyWizardManagerExtras(
                                                            intent, intent2);
                                                }
                                                SubSettingLauncher subSettingLauncher =
                                                        new SubSettingLauncher(context);
                                                String name =
                                                        EditShortcutsPreferenceFragment.class
                                                                .getName();
                                                SubSettingLauncher.LaunchRequest launchRequest =
                                                        subSettingLauncher.mLaunchRequest;
                                                launchRequest.mDestinationName = name;
                                                launchRequest.mExtras = intent2.getExtras();
                                                launchRequest.mArguments = bundle;
                                                launchRequest.mSourceMetricsCategory =
                                                        metricsCategory;
                                                launchRequest.mTitle = shortcutTitle;
                                                subSettingLauncher.launch();
                                                Dialog dialog =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mWarningDialog;
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                },
                                new View.OnClickListener(this) { // from class:
                                    // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda1
                                    public final /* synthetic */
                                    ToggleAccessibilityServicePreferenceFragment f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i7 = i5;
                                        ToggleAccessibilityServicePreferenceFragment
                                                toggleAccessibilityServicePreferenceFragment =
                                                        this.f$0;
                                        switch (i7) {
                                            case 0:
                                                int i8 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromEnableToggleClicked();
                                                break;
                                            case 1:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutClicked");
                                                break;
                                            case 2:
                                                int i9 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 3:
                                                int i10 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .handleConfirmServiceEnabled(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                break;
                                            case 4:
                                                int i11 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 5:
                                                int i12 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromShortcutToggleClicked();
                                                break;
                                            case 6:
                                                int i13 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mShortcutPreference.setChecked(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutToggleClicked");
                                                break;
                                            case 7:
                                                int i14 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            default:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mIsDialogShown.set(false);
                                                Context context =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getContext();
                                                int metricsCategory =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getMetricsCategory();
                                                CharSequence shortcutTitle =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getShortcutTitle();
                                                ComponentName componentName =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mComponentName;
                                                Intent intent =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getIntent();
                                                Uri uri =
                                                        EditShortcutsPreferenceFragment
                                                                .VOLUME_KEYS_SHORTCUT_SETTING;
                                                Bundle bundle = new Bundle();
                                                if (AccessibilityShortcutController
                                                        .MAGNIFICATION_COMPONENT_NAME
                                                        .equals(componentName)) {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                "com.android.server.accessibility.MagnificationController"
                                                            });
                                                } else {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                componentName.flattenToString()
                                                            });
                                                }
                                                Intent intent2 = new Intent();
                                                if (intent != null) {
                                                    WizardManagerHelper.copyWizardManagerExtras(
                                                            intent, intent2);
                                                }
                                                SubSettingLauncher subSettingLauncher =
                                                        new SubSettingLauncher(context);
                                                String name =
                                                        EditShortcutsPreferenceFragment.class
                                                                .getName();
                                                SubSettingLauncher.LaunchRequest launchRequest =
                                                        subSettingLauncher.mLaunchRequest;
                                                launchRequest.mDestinationName = name;
                                                launchRequest.mExtras = intent2.getExtras();
                                                launchRequest.mArguments = bundle;
                                                launchRequest.mSourceMetricsCategory =
                                                        metricsCategory;
                                                launchRequest.mTitle = shortcutTitle;
                                                subSettingLauncher.launch();
                                                Dialog dialog =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mWarningDialog;
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                },
                                new View.OnClickListener(this) { // from class:
                                    // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda1
                                    public final /* synthetic */
                                    ToggleAccessibilityServicePreferenceFragment f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i7 = i6;
                                        ToggleAccessibilityServicePreferenceFragment
                                                toggleAccessibilityServicePreferenceFragment =
                                                        this.f$0;
                                        switch (i7) {
                                            case 0:
                                                int i8 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromEnableToggleClicked();
                                                break;
                                            case 1:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutClicked");
                                                break;
                                            case 2:
                                                int i9 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 3:
                                                int i10 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .handleConfirmServiceEnabled(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                break;
                                            case 4:
                                                int i11 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 5:
                                                int i12 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromShortcutToggleClicked();
                                                break;
                                            case 6:
                                                int i13 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mShortcutPreference.setChecked(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutToggleClicked");
                                                break;
                                            case 7:
                                                int i14 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            default:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mIsDialogShown.set(false);
                                                Context context =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getContext();
                                                int metricsCategory =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getMetricsCategory();
                                                CharSequence shortcutTitle =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getShortcutTitle();
                                                ComponentName componentName =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mComponentName;
                                                Intent intent =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getIntent();
                                                Uri uri =
                                                        EditShortcutsPreferenceFragment
                                                                .VOLUME_KEYS_SHORTCUT_SETTING;
                                                Bundle bundle = new Bundle();
                                                if (AccessibilityShortcutController
                                                        .MAGNIFICATION_COMPONENT_NAME
                                                        .equals(componentName)) {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                "com.android.server.accessibility.MagnificationController"
                                                            });
                                                } else {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                componentName.flattenToString()
                                                            });
                                                }
                                                Intent intent2 = new Intent();
                                                if (intent != null) {
                                                    WizardManagerHelper.copyWizardManagerExtras(
                                                            intent, intent2);
                                                }
                                                SubSettingLauncher subSettingLauncher =
                                                        new SubSettingLauncher(context);
                                                String name =
                                                        EditShortcutsPreferenceFragment.class
                                                                .getName();
                                                SubSettingLauncher.LaunchRequest launchRequest =
                                                        subSettingLauncher.mLaunchRequest;
                                                launchRequest.mDestinationName = name;
                                                launchRequest.mExtras = intent2.getExtras();
                                                launchRequest.mArguments = bundle;
                                                launchRequest.mSourceMetricsCategory =
                                                        metricsCategory;
                                                launchRequest.mTitle = shortcutTitle;
                                                subSettingLauncher.launch();
                                                Dialog dialog =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mWarningDialog;
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                });
                this.mWarningDialog = createAccessibilityServiceWarningDialog;
                return createAccessibilityServiceWarningDialog;
            case 1003:
                if (accessibilityServiceInfo == null) {
                    return null;
                }
                final int i7 = 8;
                final int i8 = 2;
                AlertDialog createAccessibilityServiceWarningDialog2 =
                        AccessibilityServiceWarning.createAccessibilityServiceWarningDialog(
                                getPrefContext(),
                                accessibilityServiceInfo,
                                new View.OnClickListener(this) { // from class:
                                    // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda1
                                    public final /* synthetic */
                                    ToggleAccessibilityServicePreferenceFragment f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i72 = i7;
                                        ToggleAccessibilityServicePreferenceFragment
                                                toggleAccessibilityServicePreferenceFragment =
                                                        this.f$0;
                                        switch (i72) {
                                            case 0:
                                                int i82 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromEnableToggleClicked();
                                                break;
                                            case 1:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutClicked");
                                                break;
                                            case 2:
                                                int i9 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 3:
                                                int i10 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .handleConfirmServiceEnabled(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                break;
                                            case 4:
                                                int i11 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 5:
                                                int i12 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromShortcutToggleClicked();
                                                break;
                                            case 6:
                                                int i13 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mShortcutPreference.setChecked(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutToggleClicked");
                                                break;
                                            case 7:
                                                int i14 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            default:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mIsDialogShown.set(false);
                                                Context context =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getContext();
                                                int metricsCategory =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getMetricsCategory();
                                                CharSequence shortcutTitle =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getShortcutTitle();
                                                ComponentName componentName =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mComponentName;
                                                Intent intent =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getIntent();
                                                Uri uri =
                                                        EditShortcutsPreferenceFragment
                                                                .VOLUME_KEYS_SHORTCUT_SETTING;
                                                Bundle bundle = new Bundle();
                                                if (AccessibilityShortcutController
                                                        .MAGNIFICATION_COMPONENT_NAME
                                                        .equals(componentName)) {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                "com.android.server.accessibility.MagnificationController"
                                                            });
                                                } else {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                componentName.flattenToString()
                                                            });
                                                }
                                                Intent intent2 = new Intent();
                                                if (intent != null) {
                                                    WizardManagerHelper.copyWizardManagerExtras(
                                                            intent, intent2);
                                                }
                                                SubSettingLauncher subSettingLauncher =
                                                        new SubSettingLauncher(context);
                                                String name =
                                                        EditShortcutsPreferenceFragment.class
                                                                .getName();
                                                SubSettingLauncher.LaunchRequest launchRequest =
                                                        subSettingLauncher.mLaunchRequest;
                                                launchRequest.mDestinationName = name;
                                                launchRequest.mExtras = intent2.getExtras();
                                                launchRequest.mArguments = bundle;
                                                launchRequest.mSourceMetricsCategory =
                                                        metricsCategory;
                                                launchRequest.mTitle = shortcutTitle;
                                                subSettingLauncher.launch();
                                                Dialog dialog =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mWarningDialog;
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                },
                                new View.OnClickListener(this) { // from class:
                                    // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda1
                                    public final /* synthetic */
                                    ToggleAccessibilityServicePreferenceFragment f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i72 = i2;
                                        ToggleAccessibilityServicePreferenceFragment
                                                toggleAccessibilityServicePreferenceFragment =
                                                        this.f$0;
                                        switch (i72) {
                                            case 0:
                                                int i82 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromEnableToggleClicked();
                                                break;
                                            case 1:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutClicked");
                                                break;
                                            case 2:
                                                int i9 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 3:
                                                int i10 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .handleConfirmServiceEnabled(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                break;
                                            case 4:
                                                int i11 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 5:
                                                int i12 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromShortcutToggleClicked();
                                                break;
                                            case 6:
                                                int i13 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mShortcutPreference.setChecked(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutToggleClicked");
                                                break;
                                            case 7:
                                                int i14 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            default:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mIsDialogShown.set(false);
                                                Context context =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getContext();
                                                int metricsCategory =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getMetricsCategory();
                                                CharSequence shortcutTitle =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getShortcutTitle();
                                                ComponentName componentName =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mComponentName;
                                                Intent intent =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getIntent();
                                                Uri uri =
                                                        EditShortcutsPreferenceFragment
                                                                .VOLUME_KEYS_SHORTCUT_SETTING;
                                                Bundle bundle = new Bundle();
                                                if (AccessibilityShortcutController
                                                        .MAGNIFICATION_COMPONENT_NAME
                                                        .equals(componentName)) {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                "com.android.server.accessibility.MagnificationController"
                                                            });
                                                } else {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                componentName.flattenToString()
                                                            });
                                                }
                                                Intent intent2 = new Intent();
                                                if (intent != null) {
                                                    WizardManagerHelper.copyWizardManagerExtras(
                                                            intent, intent2);
                                                }
                                                SubSettingLauncher subSettingLauncher =
                                                        new SubSettingLauncher(context);
                                                String name =
                                                        EditShortcutsPreferenceFragment.class
                                                                .getName();
                                                SubSettingLauncher.LaunchRequest launchRequest =
                                                        subSettingLauncher.mLaunchRequest;
                                                launchRequest.mDestinationName = name;
                                                launchRequest.mExtras = intent2.getExtras();
                                                launchRequest.mArguments = bundle;
                                                launchRequest.mSourceMetricsCategory =
                                                        metricsCategory;
                                                launchRequest.mTitle = shortcutTitle;
                                                subSettingLauncher.launch();
                                                Dialog dialog =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mWarningDialog;
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                },
                                new View.OnClickListener(this) { // from class:
                                    // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda1
                                    public final /* synthetic */
                                    ToggleAccessibilityServicePreferenceFragment f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i72 = i8;
                                        ToggleAccessibilityServicePreferenceFragment
                                                toggleAccessibilityServicePreferenceFragment =
                                                        this.f$0;
                                        switch (i72) {
                                            case 0:
                                                int i82 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromEnableToggleClicked();
                                                break;
                                            case 1:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutClicked");
                                                break;
                                            case 2:
                                                int i9 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 3:
                                                int i10 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .handleConfirmServiceEnabled(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                break;
                                            case 4:
                                                int i11 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 5:
                                                int i12 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromShortcutToggleClicked();
                                                break;
                                            case 6:
                                                int i13 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mShortcutPreference.setChecked(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutToggleClicked");
                                                break;
                                            case 7:
                                                int i14 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            default:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mIsDialogShown.set(false);
                                                Context context =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getContext();
                                                int metricsCategory =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getMetricsCategory();
                                                CharSequence shortcutTitle =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getShortcutTitle();
                                                ComponentName componentName =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mComponentName;
                                                Intent intent =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getIntent();
                                                Uri uri =
                                                        EditShortcutsPreferenceFragment
                                                                .VOLUME_KEYS_SHORTCUT_SETTING;
                                                Bundle bundle = new Bundle();
                                                if (AccessibilityShortcutController
                                                        .MAGNIFICATION_COMPONENT_NAME
                                                        .equals(componentName)) {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                "com.android.server.accessibility.MagnificationController"
                                                            });
                                                } else {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                componentName.flattenToString()
                                                            });
                                                }
                                                Intent intent2 = new Intent();
                                                if (intent != null) {
                                                    WizardManagerHelper.copyWizardManagerExtras(
                                                            intent, intent2);
                                                }
                                                SubSettingLauncher subSettingLauncher =
                                                        new SubSettingLauncher(context);
                                                String name =
                                                        EditShortcutsPreferenceFragment.class
                                                                .getName();
                                                SubSettingLauncher.LaunchRequest launchRequest =
                                                        subSettingLauncher.mLaunchRequest;
                                                launchRequest.mDestinationName = name;
                                                launchRequest.mExtras = intent2.getExtras();
                                                launchRequest.mArguments = bundle;
                                                launchRequest.mSourceMetricsCategory =
                                                        metricsCategory;
                                                launchRequest.mTitle = shortcutTitle;
                                                subSettingLauncher.launch();
                                                Dialog dialog =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mWarningDialog;
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                });
                this.mWarningDialog = createAccessibilityServiceWarningDialog2;
                return createAccessibilityServiceWarningDialog2;
            case VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI /* 1004 */:
                if (accessibilityServiceInfo == null) {
                    return null;
                }
                final int i9 = 5;
                final int i10 = 6;
                final int i11 = 7;
                AlertDialog createAccessibilityServiceWarningDialog3 =
                        AccessibilityServiceWarning.createAccessibilityServiceWarningDialog(
                                getPrefContext(),
                                accessibilityServiceInfo,
                                new View.OnClickListener(this) { // from class:
                                    // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda1
                                    public final /* synthetic */
                                    ToggleAccessibilityServicePreferenceFragment f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i72 = i9;
                                        ToggleAccessibilityServicePreferenceFragment
                                                toggleAccessibilityServicePreferenceFragment =
                                                        this.f$0;
                                        switch (i72) {
                                            case 0:
                                                int i82 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromEnableToggleClicked();
                                                break;
                                            case 1:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutClicked");
                                                break;
                                            case 2:
                                                int i92 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 3:
                                                int i102 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .handleConfirmServiceEnabled(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                break;
                                            case 4:
                                                int i112 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 5:
                                                int i12 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromShortcutToggleClicked();
                                                break;
                                            case 6:
                                                int i13 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mShortcutPreference.setChecked(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutToggleClicked");
                                                break;
                                            case 7:
                                                int i14 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            default:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mIsDialogShown.set(false);
                                                Context context =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getContext();
                                                int metricsCategory =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getMetricsCategory();
                                                CharSequence shortcutTitle =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getShortcutTitle();
                                                ComponentName componentName =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mComponentName;
                                                Intent intent =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getIntent();
                                                Uri uri =
                                                        EditShortcutsPreferenceFragment
                                                                .VOLUME_KEYS_SHORTCUT_SETTING;
                                                Bundle bundle = new Bundle();
                                                if (AccessibilityShortcutController
                                                        .MAGNIFICATION_COMPONENT_NAME
                                                        .equals(componentName)) {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                "com.android.server.accessibility.MagnificationController"
                                                            });
                                                } else {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                componentName.flattenToString()
                                                            });
                                                }
                                                Intent intent2 = new Intent();
                                                if (intent != null) {
                                                    WizardManagerHelper.copyWizardManagerExtras(
                                                            intent, intent2);
                                                }
                                                SubSettingLauncher subSettingLauncher =
                                                        new SubSettingLauncher(context);
                                                String name =
                                                        EditShortcutsPreferenceFragment.class
                                                                .getName();
                                                SubSettingLauncher.LaunchRequest launchRequest =
                                                        subSettingLauncher.mLaunchRequest;
                                                launchRequest.mDestinationName = name;
                                                launchRequest.mExtras = intent2.getExtras();
                                                launchRequest.mArguments = bundle;
                                                launchRequest.mSourceMetricsCategory =
                                                        metricsCategory;
                                                launchRequest.mTitle = shortcutTitle;
                                                subSettingLauncher.launch();
                                                Dialog dialog =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mWarningDialog;
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                },
                                new View.OnClickListener(this) { // from class:
                                    // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda1
                                    public final /* synthetic */
                                    ToggleAccessibilityServicePreferenceFragment f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i72 = i10;
                                        ToggleAccessibilityServicePreferenceFragment
                                                toggleAccessibilityServicePreferenceFragment =
                                                        this.f$0;
                                        switch (i72) {
                                            case 0:
                                                int i82 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromEnableToggleClicked();
                                                break;
                                            case 1:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutClicked");
                                                break;
                                            case 2:
                                                int i92 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 3:
                                                int i102 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .handleConfirmServiceEnabled(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                break;
                                            case 4:
                                                int i112 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 5:
                                                int i12 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromShortcutToggleClicked();
                                                break;
                                            case 6:
                                                int i13 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mShortcutPreference.setChecked(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutToggleClicked");
                                                break;
                                            case 7:
                                                int i14 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            default:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mIsDialogShown.set(false);
                                                Context context =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getContext();
                                                int metricsCategory =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getMetricsCategory();
                                                CharSequence shortcutTitle =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getShortcutTitle();
                                                ComponentName componentName =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mComponentName;
                                                Intent intent =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getIntent();
                                                Uri uri =
                                                        EditShortcutsPreferenceFragment
                                                                .VOLUME_KEYS_SHORTCUT_SETTING;
                                                Bundle bundle = new Bundle();
                                                if (AccessibilityShortcutController
                                                        .MAGNIFICATION_COMPONENT_NAME
                                                        .equals(componentName)) {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                "com.android.server.accessibility.MagnificationController"
                                                            });
                                                } else {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                componentName.flattenToString()
                                                            });
                                                }
                                                Intent intent2 = new Intent();
                                                if (intent != null) {
                                                    WizardManagerHelper.copyWizardManagerExtras(
                                                            intent, intent2);
                                                }
                                                SubSettingLauncher subSettingLauncher =
                                                        new SubSettingLauncher(context);
                                                String name =
                                                        EditShortcutsPreferenceFragment.class
                                                                .getName();
                                                SubSettingLauncher.LaunchRequest launchRequest =
                                                        subSettingLauncher.mLaunchRequest;
                                                launchRequest.mDestinationName = name;
                                                launchRequest.mExtras = intent2.getExtras();
                                                launchRequest.mArguments = bundle;
                                                launchRequest.mSourceMetricsCategory =
                                                        metricsCategory;
                                                launchRequest.mTitle = shortcutTitle;
                                                subSettingLauncher.launch();
                                                Dialog dialog =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mWarningDialog;
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                },
                                new View.OnClickListener(this) { // from class:
                                    // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda1
                                    public final /* synthetic */
                                    ToggleAccessibilityServicePreferenceFragment f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        int i72 = i11;
                                        ToggleAccessibilityServicePreferenceFragment
                                                toggleAccessibilityServicePreferenceFragment =
                                                        this.f$0;
                                        switch (i72) {
                                            case 0:
                                                int i82 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromEnableToggleClicked();
                                                break;
                                            case 1:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutClicked");
                                                break;
                                            case 2:
                                                int i92 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 3:
                                                int i102 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .handleConfirmServiceEnabled(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                break;
                                            case 4:
                                                int i112 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            case 5:
                                                int i12 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onAllowButtonFromShortcutToggleClicked();
                                                break;
                                            case 6:
                                                int i13 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mShortcutPreference.setChecked(false);
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mWarningDialog.dismiss();
                                                Log.d(
                                                        "ToggleAccessibilityServicePreferenceFragment",
                                                        "onDenyButtonFromShortcutToggleClicked");
                                                break;
                                            case 7:
                                                int i14 =
                                                        ToggleAccessibilityServicePreferenceFragment
                                                                .$r8$clinit;
                                                toggleAccessibilityServicePreferenceFragment
                                                        .onDialogButtonFromUninstallClicked();
                                                break;
                                            default:
                                                toggleAccessibilityServicePreferenceFragment
                                                        .mIsDialogShown.set(false);
                                                Context context =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getContext();
                                                int metricsCategory =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getMetricsCategory();
                                                CharSequence shortcutTitle =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getShortcutTitle();
                                                ComponentName componentName =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mComponentName;
                                                Intent intent =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .getIntent();
                                                Uri uri =
                                                        EditShortcutsPreferenceFragment
                                                                .VOLUME_KEYS_SHORTCUT_SETTING;
                                                Bundle bundle = new Bundle();
                                                if (AccessibilityShortcutController
                                                        .MAGNIFICATION_COMPONENT_NAME
                                                        .equals(componentName)) {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                "com.android.server.accessibility.MagnificationController"
                                                            });
                                                } else {
                                                    bundle.putStringArray(
                                                            "targets",
                                                            new String[] {
                                                                componentName.flattenToString()
                                                            });
                                                }
                                                Intent intent2 = new Intent();
                                                if (intent != null) {
                                                    WizardManagerHelper.copyWizardManagerExtras(
                                                            intent, intent2);
                                                }
                                                SubSettingLauncher subSettingLauncher =
                                                        new SubSettingLauncher(context);
                                                String name =
                                                        EditShortcutsPreferenceFragment.class
                                                                .getName();
                                                SubSettingLauncher.LaunchRequest launchRequest =
                                                        subSettingLauncher.mLaunchRequest;
                                                launchRequest.mDestinationName = name;
                                                launchRequest.mExtras = intent2.getExtras();
                                                launchRequest.mArguments = bundle;
                                                launchRequest.mSourceMetricsCategory =
                                                        metricsCategory;
                                                launchRequest.mTitle = shortcutTitle;
                                                subSettingLauncher.launch();
                                                Dialog dialog =
                                                        toggleAccessibilityServicePreferenceFragment
                                                                .mWarningDialog;
                                                if (dialog != null) {
                                                    dialog.dismiss();
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                });
                this.mWarningDialog = createAccessibilityServiceWarningDialog3;
                return createAccessibilityServiceWarningDialog3;
            case 1005:
                if (accessibilityServiceInfo == null) {
                    return null;
                }
                Context prefContext2 = getPrefContext();
                DialogInterface.OnClickListener onClickListener =
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda12
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i12) {
                                ToggleAccessibilityServicePreferenceFragment
                                        toggleAccessibilityServicePreferenceFragment =
                                                ToggleAccessibilityServicePreferenceFragment.this;
                                int i13 = ToggleAccessibilityServicePreferenceFragment.$r8$clinit;
                                if (i12 == -2) {
                                    toggleAccessibilityServicePreferenceFragment
                                            .handleConfirmServiceEnabled(true);
                                } else if (i12 == -1) {
                                    toggleAccessibilityServicePreferenceFragment
                                            .handleConfirmServiceEnabled(false);
                                } else {
                                    toggleAccessibilityServicePreferenceFragment.getClass();
                                    throw new IllegalArgumentException(
                                            "Unexpected button identifier");
                                }
                            }
                        };
                Locale locale = prefContext2.getResources().getConfiguration().getLocales().get(0);
                AlertDialog create =
                        new AlertDialog.Builder(prefContext2)
                                .setMessage(
                                        prefContext2.getString(
                                                R.string.sec_disable_service_message,
                                                BidiFormatter.getInstance(locale)
                                                        .unicodeWrap(
                                                                accessibilityServiceInfo
                                                                        .getResolveInfo()
                                                                        .loadLabel(
                                                                                prefContext2
                                                                                        .getPackageManager()))))
                                .setCancelable(true)
                                .setPositiveButton(R.string.accessibility_turn_off, onClickListener)
                                .setNegativeButton(R.string.common_cancel, onClickListener)
                                .create();
                this.mWarningDialog = create;
                return create;
            default:
                switch (i) {
                    case 9002:
                    case 9003:
                        androidx.appcompat.app.AlertDialog createExclusiveDialog =
                                AccessibilityDialogUtils.createExclusiveDialog(
                                        prefContext,
                                        "talkback_se",
                                        new ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda2(
                                                this, i, prefContext),
                                        null);
                        this.mWarningDialog = createExclusiveDialog;
                        return createExclusiveDialog;
                    case 9004:
                    case 9005:
                        androidx.appcompat.app.AlertDialog createTTSLanguageCheckDialog =
                                AccessibilityDialogUtils.createTTSLanguageCheckDialog(
                                        prefContext,
                                        this.languageDialogInfo,
                                        new ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda2(
                                                this, prefContext, i));
                        this.mWarningDialog = createTTSLanguageCheckDialog;
                        return createTTSLanguageCheckDialog;
                    default:
                        return super.onCreateDialog(i);
                }
        }
    }

    public final void onDialogButtonFromUninstallClicked() {
        Intent intent;
        this.mWarningDialog.dismiss();
        AccessibilityServiceInfo accessibilityServiceInfo = getAccessibilityServiceInfo();
        if (accessibilityServiceInfo == null) {
            Log.w(
                    "ToggleAccessibilityServicePreferenceFragment",
                    "createUnInstallIntent -- invalid a11yServiceInfo");
            intent = null;
        } else {
            intent =
                    new Intent(
                            "android.intent.action.UNINSTALL_PACKAGE",
                            Uri.parse(
                                    "package:"
                                            + accessibilityServiceInfo.getResolveInfo()
                                                    .serviceInfo
                                                    .applicationInfo
                                                    .packageName));
        }
        if (intent == null) {
            return;
        }
        startActivity(intent);
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onPreferenceToggled(String str, boolean z) {
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString == null) {
            unflattenFromString = this.mComponentName;
        }
        AccessibilityStatsLogUtils.logAccessibilityServiceEnabled(unflattenFromString, z);
        if (!z) {
            String packageName = unflattenFromString.getPackageName();
            if (this.mStartTimeMillsForLogging > 0 && !this.mDisabledStateLogged) {
                com.android.internal.accessibility.util.AccessibilityStatsLogUtils
                        .logNonA11yToolServiceWarningReported(
                                packageName,
                                com.android.internal.accessibility.util.AccessibilityStatsLogUtils
                                        .ACCESSIBILITY_PRIVACY_WARNING_STATUS_SERVICE_DISABLED,
                                SystemClock.elapsedRealtime() - this.mStartTimeMillsForLogging);
                this.mDisabledStateLogged = true;
            }
        }
        AccessibilityUtils.setAccessibilityServiceState(getPrefContext(), unflattenFromString, z);
        if (AccessibilityConstant.COMPONENT_NAME_VOICE_ACCESS.equals(unflattenFromString)) {
            RecommendedForYouUtils.updateFeatureConditionForProfile(
                    getPrefContext(), "enable_time_voice_access", "use_count_voice_access", z);
            return;
        }
        if (AccessibilityConstant.COMPONENT_NAME_GOOGLE_TALKBACK.equals(unflattenFromString)
                || AccessibilityConstant.COMPONENT_NAME_SAMSUNG_TALKBACK.equals(
                        unflattenFromString)) {
            Context prefContext = getPrefContext();
            int i = RecommendedForYouUtils.$r8$clinit;
            if (SecAccessibilityUtils.getAccessibilitySharedPreferences(prefContext)
                                    .getLong("enable_time_screen_reader", 0L)
                            <= 0
                    || !z) {
                RecommendedForYouUtils.updateFeatureConditionForProfile(
                        prefContext, "enable_time_screen_reader", "use_count_screen_reader", z);
            }
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void onProcessArguments(Bundle bundle) {
        super.onProcessArguments(bundle);
        String string = bundle.getString("settings_title");
        String string2 = bundle.getString("settings_component_name");
        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
            Intent component =
                    new Intent("android.intent.action.MAIN")
                            .setComponent(ComponentName.unflattenFromString(string2.toString()));
            if (!getPackageManager().queryIntentActivities(component, 0).isEmpty()) {
                this.mSettingsTitle = string;
                this.mSettingsIntent = component;
                setHasOptionsMenu(true);
            }
        }
        ComponentName componentName = (ComponentName) bundle.getParcelable("component_name");
        this.mComponentName = componentName;
        if (componentName != null) {
            int i = bundle.getInt("animated_image_res");
            if (i > 0) {
                this.mImageUri =
                        new Uri.Builder()
                                .scheme("android.resource")
                                .authority(this.mComponentName.getPackageName())
                                .appendPath(String.valueOf(i))
                                .build();
            }
            if (getAccessibilityServiceInfo() != null) {
                this.mPackageName =
                        getAccessibilityServiceInfo()
                                .getResolveInfo()
                                .loadLabel(getPackageManager());
            }
        }
        if (bundle.containsKey("tile_service_component_name")) {
            this.mTileComponentName =
                    ComponentName.unflattenFromString(
                            bundle.getString("tile_service_component_name"));
        }
        this.mStartTimeMillsForLogging = bundle.getLong("start_time_to_log_a11y_tool");
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateSwitchBarToggleSwitch();
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        if (this.mStartTimeMillsForLogging > 0) {
            bundle.putBoolean("has_logged", this.mDisabledStateLogged);
        }
        super.onSaveInstanceState(bundle);
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$1] */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        AccessibilityServiceInfo accessibilityServiceInfo = getAccessibilityServiceInfo();
        if (accessibilityServiceInfo == null) {
            getActivity().finishAndRemoveTask();
        } else {
            int i = AccessibilityUtil.$r8$clinit;
            if (!accessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.isSystemApp()
                    && this.mPackageRemovedReceiver == null
                    && getContext() != null) {
                this.mPackageRemovedReceiver =
                        new BroadcastReceiver() { // from class:
                                                  // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment.1
                            @Override // android.content.BroadcastReceiver
                            public final void onReceive(Context context, Intent intent) {
                                if (TextUtils.equals(
                                        ToggleAccessibilityServicePreferenceFragment.this
                                                .mComponentName.getPackageName(),
                                        intent.getData().getSchemeSpecificPart())) {
                                    ToggleAccessibilityServicePreferenceFragment.this
                                            .getActivity()
                                            .finishAndRemoveTask();
                                }
                            }
                        };
                IntentFilter intentFilter =
                        new IntentFilter("android.intent.action.PACKAGE_REMOVED");
                intentFilter.addDataScheme("package");
                getContext().registerReceiver(this.mPackageRemovedReceiver, intentFilter);
            }
        }
        if (accessibilityServiceInfo != null) {
            this.languageDialogInfo =
                    new AccessibilityDialogUtils.TTSLanguageDialogInfo(
                            getPrefContext(), accessibilityServiceInfo);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        if (this.mPackageRemovedReceiver == null || getContext() == null) {
            return;
        }
        getContext().unregisterReceiver(this.mPackageRemovedReceiver);
        this.mPackageRemovedReceiver = null;
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment,
              // com.android.settings.accessibility.ShortcutPreference.OnClickCallback
    public void onToggleClicked(ShortcutPreference shortcutPreference) {
        int userPreferredShortcutTypes = getUserPreferredShortcutTypes();
        if (!shortcutPreference.mChecked) {
            AccessibilityUtil.optOutAllValuesFromSettings(
                    getPrefContext(), userPreferredShortcutTypes, this.mComponentName);
        } else if (((AccessibilityManager)
                        getPrefContext().getSystemService(AccessibilityManager.class))
                .isAccessibilityServiceWarningRequired(getAccessibilityServiceInfo())) {
            shortcutPreference.setChecked(false);
            showPopupDialog(VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI);
        } else {
            onAllowButtonFromShortcutToggleClicked();
        }
        this.mShortcutPreference.setSummary(getShortcutTypeSummary(getPrefContext()));
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void registerKeysToObserverCallback(
            AccessibilitySettingsContentObserver accessibilitySettingsContentObserver) {
        AccessibilitySettingsContentObserver.ContentObserverCallback contentObserverCallback =
                new AccessibilitySettingsContentObserver
                        .ContentObserverCallback() { // from class:
                                                     // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str) {
                        int i = ToggleAccessibilityServicePreferenceFragment.$r8$clinit;
                        ToggleAccessibilityServicePreferenceFragment.this
                                .updateSwitchBarToggleSwitch();
                    }
                };
        ((HashMap) accessibilitySettingsContentObserver.mUrisToCallback)
                .put(Collections.emptyList(), contentObserverCallback);
    }

    public final void showPopupDialog(int i) {
        if (this.mIsDialogShown.compareAndSet(false, true)) {
            showDialog(i);
            setOnDismissListener(
                    new DialogInterface
                            .OnDismissListener() { // from class:
                                                   // com.android.settings.accessibility.ToggleAccessibilityServicePreferenceFragment$$ExternalSyntheticLambda13
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            ToggleAccessibilityServicePreferenceFragment.this.mIsDialogShown
                                    .compareAndSet(true, false);
                        }
                    });
        }
    }

    @Override // com.android.settings.accessibility.ToggleFeaturePreferenceFragment
    public final void updateSwitchBarToggleSwitch() {
        boolean contains =
                this.mComponentName == null
                        ? false
                        : AccessibilityUtils.getEnabledServicesFromSettings(getPrefContext())
                                .contains(this.mComponentName);
        if (((SeslSwitchBar) this.mToggleServiceSwitchBar).mSwitch.isChecked() != contains) {
            this.mToggleServiceSwitchBar.setChecked(contains);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {}

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {}
}
