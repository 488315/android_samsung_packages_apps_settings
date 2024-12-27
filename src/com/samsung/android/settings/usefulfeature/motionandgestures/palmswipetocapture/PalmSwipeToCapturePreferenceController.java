package com.samsung.android.settings.usefulfeature.motionandgestures.palmswipetocapture;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.account.controller.SecAccountPreferenceController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.motionandgestures.MotionAndGestureSettings;
import com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class PalmSwipeToCapturePreferenceController extends SecMotionAndGestureBaseController {
    private AlertDialog mAllDisabledDialog;
    private SecSwitchPreference mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.motionandgestures.palmswipetocapture.PalmSwipeToCapturePreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 implements DialogInterface.OnClickListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PalmSwipeToCapturePreferenceController this$0;

        public /* synthetic */ AnonymousClass1(
                PalmSwipeToCapturePreferenceController palmSwipeToCapturePreferenceController,
                int i) {
            this.$r8$classId = i;
            this.this$0 = palmSwipeToCapturePreferenceController;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.dismissAllDialog();
                    break;
                case 1:
                    boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
                    if (!SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_ACCESSIBILITY_SUPPORT_MANAGE_EXCLUSIVE_TASK")) {
                        UsefulfeatureUtils.turnOffUniversalSwitch(
                                ((AbstractPreferenceController) this.this$0)
                                        .mContext.getApplicationContext());
                    }
                    this.this$0.setParmSwipeToCapture(true);
                    break;
                case 2:
                    this.this$0.dismissAllDialog();
                    break;
                default:
                    boolean z2 = UsefulfeatureUtils.mAccessibilityEnabled;
                    if (!SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_ACCESSIBILITY_SUPPORT_MANAGE_EXCLUSIVE_TASK")) {
                        ((AccessibilityManager)
                                        ((AbstractPreferenceController) this.this$0)
                                                .mContext.getSystemService("accessibility"))
                                .semSetScreenReaderEnabled(false);
                    }
                    this.this$0.setParmSwipeToCapture(true);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.motionandgestures.palmswipetocapture.PalmSwipeToCapturePreferenceController$3, reason: invalid class name */
    public final class AnonymousClass3 implements DialogInterface.OnDismissListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ PalmSwipeToCapturePreferenceController this$0;

        public /* synthetic */ AnonymousClass3(
                PalmSwipeToCapturePreferenceController palmSwipeToCapturePreferenceController,
                int i) {
            this.$r8$classId = i;
            this.this$0 = palmSwipeToCapturePreferenceController;
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public final void onDismiss(DialogInterface dialogInterface) {
            switch (this.$r8$classId) {
                case 0:
                    if (this.this$0.mPreference != null) {
                        this.this$0.mPreference.setChecked(
                                Settings.System.getInt(
                                                ((AbstractPreferenceController) this.this$0)
                                                        .mContext.getContentResolver(),
                                                "surface_palm_swipe",
                                                0)
                                        != 0);
                        break;
                    }
                    break;
                default:
                    if (this.this$0.mPreference != null) {
                        this.this$0.mPreference.setChecked(
                                Settings.System.getInt(
                                                ((AbstractPreferenceController) this.this$0)
                                                        .mContext.getContentResolver(),
                                                "surface_palm_swipe",
                                                0)
                                        != 0);
                        break;
                    }
                    break;
            }
        }
    }

    public PalmSwipeToCapturePreferenceController(Context context, String str) {
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

    private boolean isScreenCaptureEnabled() {
        Boolean bool = Boolean.FALSE;
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RestrictionPolicy3",
                        "isScreenCaptureEnabled",
                        new String[] {"false"});
        if (enterprisePolicyEnabled != -1 && enterprisePolicyEnabled != 1) {
            bool = Boolean.TRUE;
        }
        if (Settings.System.getInt(this.mContext.getContentResolver(), "access_control_enabled", 0)
                == 1) {
            bool = Boolean.TRUE;
        }
        if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_NFC_HW_KEYBOARD")
                && this.mContext.getResources().getConfiguration().semMobileKeyboardCovered == 1) {
            bool = Boolean.TRUE;
        }
        return bool.booleanValue();
    }

    private void makeTalkBackDisablePopup() {
        dismissAllDialog();
        String string = this.mContext.getString(R.string.palm_swipe_title);
        Context context = this.mContext;
        boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
        String semGetScreenReaderName =
                ((AccessibilityManager) context.getSystemService("accessibility"))
                        .semGetScreenReaderName();
        String format =
                String.format(
                        this.mContext.getString(R.string.turn_off_va_dialog_title),
                        semGetScreenReaderName);
        AlertDialog create =
                new AlertDialog.Builder(this.mContext)
                        .setTitle(format)
                        .setMessage(
                                String.format(
                                        this.mContext.getString(R.string.change_popup_msg),
                                        string,
                                        semGetScreenReaderName))
                        .setPositiveButton(R.string.turn_off_button, new AnonymousClass1(this, 3))
                        .setNegativeButton(R.string.cancel, new AnonymousClass1(this, 2))
                        .create();
        this.mAllDisabledDialog = create;
        create.setOnDismissListener(new AnonymousClass3(this, 1));
        this.mAllDisabledDialog.show();
    }

    private void maketurnOffUniversalPopup() {
        dismissAllDialog();
        String string = this.mContext.getString(R.string.palm_swipe_title);
        String format =
                String.format(
                        this.mContext.getString(R.string.turn_off_va_dialog_title),
                        this.mContext.getString(
                                R.string.accessibility_toggle_universal_input_preference_title));
        AlertDialog create =
                new AlertDialog.Builder(this.mContext)
                        .setTitle(format)
                        .setMessage(
                                String.format(
                                        this.mContext.getString(R.string.change_popup_msg),
                                        string,
                                        this.mContext.getString(
                                                R.string
                                                        .accessibility_toggle_universal_input_preference_title)))
                        .setPositiveButton(R.string.turn_off_button, new AnonymousClass1(this, 1))
                        .setNegativeButton(R.string.cancel, new AnonymousClass1(this, 0))
                        .create();
        this.mAllDisabledDialog = create;
        create.setOnDismissListener(new AnonymousClass3(this, 0));
        this.mAllDisabledDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setParmSwipeToCapture(boolean z) {
        Settings.System.putInt(this.mContext.getContentResolver(), "surface_palm_swipe", z ? 1 : 0);
        LoggingHelper.insertEventLogging(7604, 4370, z);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (UsefulfeatureUtils.isSupportMotionFeature(this.mContext, getPreferenceKey())
                && !SemFloatingFeature.getInstance()
                        .getBoolean(
                                "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05", false)) {
            return isScreenCaptureEnabled() ? 5 : 0;
        }
        return 3;
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
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", getPreferenceKey());
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = MotionAndGestureSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 7604;
        launchRequest.mArguments = bundle;
        return SecAccountPreferenceController$$ExternalSyntheticOutline0.m(
                subSettingLauncher, null, R.string.motion_and_gesture_title, 268468224);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_advanced_features;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController
    public ArrayList<Uri> getUriListRequiringObservation() {
        ArrayList<Uri> arrayList = new ArrayList<>();
        arrayList.add(Settings.System.getUriFor("access_control_enabled"));
        arrayList.add(Settings.System.getUriFor("surface_palm_swipe"));
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
        return (isScreenCaptureEnabled()
                        || Settings.System.getInt(
                                        this.mContext.getContentResolver(), "surface_palm_swipe", 0)
                                == 0)
                ? false
                : true;
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
            setParmSwipeToCapture(z);
            return true;
        }
        if (z
                && UsefulfeatureUtils.isUniversalSwitchSupportMultiUserKnoxMode(
                        this.mContext.getApplicationContext())
                && UsefulfeatureUtils.isUniversalSwitchEnabled(
                        this.mContext.getApplicationContext())) {
            maketurnOffUniversalPopup();
            return false;
        }
        if (z) {
            Context context = this.mContext;
            boolean z2 = UsefulfeatureUtils.mAccessibilityEnabled;
            if (((AccessibilityManager) context.getSystemService("accessibility"))
                            .semIsScreenReaderEnabled()
                    && Build.VERSION.SEM_PLATFORM_INT < 100500) {
                makeTalkBackDisablePopup();
                return false;
            }
        }
        setParmSwipeToCapture(z);
        return true;
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController
    public void updatePreference() {
        if (this.mPreference != null) {
            if (isScreenCaptureEnabled()) {
                this.mPreference.setEnabled(false);
            } else {
                this.mPreference.setEnabled(true);
            }
            this.mPreference.setChecked(getThreadEnabled());
        }
    }

    @Override // com.samsung.android.settings.usefulfeature.motionandgestures.SecMotionAndGestureBaseController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
