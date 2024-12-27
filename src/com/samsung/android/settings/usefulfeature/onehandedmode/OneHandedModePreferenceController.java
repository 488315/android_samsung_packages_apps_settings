package com.samsung.android.settings.usefulfeature.onehandedmode;

import android.R;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.account.controller.SecAccountPreferenceController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class OneHandedModePreferenceController extends SecMotionAndGestureBaseController {
    public static final int MAINTENANCE_MODE_USER_ID = 77;
    private static final int MODE_ON = 1;
    private AlertDialog mAllDisabledDialog;
    private SecSwitchPreferenceScreen mPreference;

    public OneHandedModePreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissAllDialog() {
        AlertDialog alertDialog = this.mAllDisabledDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mAllDisabledDialog = null;
        }
    }

    private void makeOneHandOperationDisablePopup() {
        dismissAllDialog();
        ViewGroup viewGroup =
                (ViewGroup)
                        ((LayoutInflater)
                                        new ContextThemeWrapper(
                                                        this.mContext,
                                                        R.style.Theme.DeviceDefault.Light.Dialog)
                                                .getSystemService("layout_inflater"))
                                .inflate(
                                        com.android.settings.R.layout
                                                .sec_accessibility_exclusive_popup,
                                        (ViewGroup) null);
        TextView textView =
                (TextView) viewGroup.findViewById(com.android.settings.R.id.dialog_desc_string);
        TextView textView2 =
                (TextView)
                        viewGroup.findViewById(com.android.settings.R.id.dialog_list_desc_string);
        textView.setText(
                this.mContext.getString(
                        com.android.settings.R.string.onehand_settings_dialog_text));
        textView2.setText(UsefulfeatureUtils.oneHandOperationDisablepopupMessage(this.mContext));
        final int i = 1;
        AlertDialog.Builder positiveButton =
                new AlertDialog.Builder(this.mContext)
                        .setTitle(
                                this.mContext.getString(
                                        com.android.settings.R.string
                                                .onehand_settings_dialog_title))
                        .setView(viewGroup)
                        .setPositiveButton(
                                com.android.settings.R.string.ok,
                                new DialogInterface.OnClickListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.usefulfeature.onehandedmode.OneHandedModePreferenceController.2
                                    public final /* synthetic */ OneHandedModePreferenceController
                                            this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i2) {
                                        switch (i) {
                                            case 0:
                                                this.this$0.dismissAllDialog();
                                                break;
                                            default:
                                                if (UsefulfeatureUtils.isEnabledOneHandOperation(
                                                        ((AbstractPreferenceController) this.this$0)
                                                                .mContext)) {
                                                    UsefulfeatureUtils
                                                            .turnOffFunctionsConflictWithOneHandedMode(
                                                                    ((AbstractPreferenceController)
                                                                                    this.this$0)
                                                                            .mContext);
                                                    this.this$0.setOneHandOperation(1);
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                });
        final int i2 = 0;
        AlertDialog create =
                positiveButton
                        .setNegativeButton(
                                com.android.settings.R.string.cancel,
                                new DialogInterface.OnClickListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.usefulfeature.onehandedmode.OneHandedModePreferenceController.2
                                    public final /* synthetic */ OneHandedModePreferenceController
                                            this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i22) {
                                        switch (i2) {
                                            case 0:
                                                this.this$0.dismissAllDialog();
                                                break;
                                            default:
                                                if (UsefulfeatureUtils.isEnabledOneHandOperation(
                                                        ((AbstractPreferenceController) this.this$0)
                                                                .mContext)) {
                                                    UsefulfeatureUtils
                                                            .turnOffFunctionsConflictWithOneHandedMode(
                                                                    ((AbstractPreferenceController)
                                                                                    this.this$0)
                                                                            .mContext);
                                                    this.this$0.setOneHandOperation(1);
                                                    break;
                                                }
                                                break;
                                        }
                                    }
                                })
                        .setOnDismissListener(
                                new DialogInterface
                                        .OnDismissListener() { // from class:
                                                               // com.samsung.android.settings.usefulfeature.onehandedmode.OneHandedModePreferenceController.1
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        OneHandedModePreferenceController.this.mPreference
                                                .setChecked(
                                                        OneHandedModePreferenceController.this
                                                                .getThreadEnabled());
                                    }
                                })
                        .create();
        this.mAllDisabledDialog = create;
        create.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOneHandOperation(int i) {
        boolean z = false;
        boolean z2 = i == 1;
        Settings.System.putInt(this.mContext.getContentResolver(), "any_screen_enabled", i);
        boolean z3 =
                Settings.System.getInt(
                                this.mContext.getContentResolver(), "one_handed_op_wakeup_type", 1)
                        != 0;
        boolean z4 =
                Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "navigation_bar_gesture_while_hidden",
                                0)
                        != 0;
        if (this.mContext.getResources().getBoolean(R.bool.config_sms_decode_gsm_8bit_data)
                && z2
                && z4
                && z3) {
            Settings.System.putInt(
                    this.mContext.getContentResolver(), "one_handed_op_wakeup_type", 0);
            Toast.makeText(
                            this.mContext,
                            com.android.settings.R.string
                                    .onehand_settings_change_gesture_by_navibar_toast,
                            1)
                    .show();
        }
        if (Settings.System.getInt(
                                this.mContext.getContentResolver(), "one_handed_op_wakeup_type", 0)
                        == 1
                && z2) {
            z = true;
        }
        UsefulfeatureUtils.setOneHandModeKeyCustomizationInfo(z);
    }

    private void updatePreferenceSummary() {
        if (this.mPreference != null) {
            if (!getThreadEnabled()) {
                this.mPreference.setSummary((CharSequence) null);
                return;
            }
            boolean z =
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "one_handed_op_wakeup_type",
                                    0)
                            == 1;
            SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
            boolean threadEnabled = getThreadEnabled();
            secSwitchPreferenceScreen.getClass();
            SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen, threadEnabled);
            this.mPreference.setSummary(
                    z
                            ? com.android.settings.R.string.onehand_settings_using_button
                            : com.android.settings.R.string.onehand_settings_using_gesture);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
        if (!(!Utils.isTablet())) {
            return 3;
        }
        return ActivityManager.getCurrentUser() == 77 ? 4 : 0;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = NewOneHandOperationSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 4352;
        return SecAccountPreferenceController$$ExternalSyntheticOutline0.m(
                subSettingLauncher,
                null,
                com.android.settings.R.string.onehand_settings_title,
                268468224);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return com.android.settings.R.string.menu_key_advanced_features;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController
    public ArrayList<Uri> getUriListRequiringObservation() {
        ArrayList<Uri> arrayList = new ArrayList<>(1);
        arrayList.add(Settings.System.getUriFor("any_screen_enabled"));
        return arrayList;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return UsefulfeatureUtils.isEnabledOneHandOperation(this.mContext)
                && Settings.System.getInt(
                                this.mContext.getContentResolver(), "any_screen_enabled", 0)
                        != 0;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return isAvailable();
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (this.mPreference == null) {
            setOneHandOperation(z ? 1 : 0);
        } else if (!z) {
            setOneHandOperation(z ? 1 : 0);
        } else {
            if (SemFloatingFeature.getInstance()
                            .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_NFC_HW_KEYBOARD")
                    && this.mContext.getResources().getConfiguration().semMobileKeyboardCovered
                            == 1) {
                this.mPreference.setChecked(getThreadEnabled());
                Context context = this.mContext;
                Toast.makeText(
                                context,
                                context.getResources()
                                        .getString(
                                                com.android.settings.R.string
                                                        .onehand_settings_cover_toast),
                                1)
                        .show();
                return false;
            }
            if (!UsefulfeatureUtils.isReadyOneHandedOperationStatusEnable(this.mContext)) {
                makeOneHandOperationDisablePopup();
                return false;
            }
            setOneHandOperation(z ? 1 : 0);
        }
        return true;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController
    public void updatePreference() {
        if (UsefulfeatureUtils.isEnabledOneHandOperation(this.mContext)) {
            this.mPreference.setEnabled(true);
        } else {
            this.mPreference.setEnabled(false);
        }
        this.mPreference.setChecked(getThreadEnabled());
        updatePreferenceSummary();
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
