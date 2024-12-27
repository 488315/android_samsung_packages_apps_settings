package com.samsung.android.settings.accessibility.dexterity.autoaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;

import com.android.settings.accessibility.AccessibilityDialogUtils;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutoClickPreferenceController extends TogglePreferenceController
        implements AccessibilityObservableController {
    private Fragment mParentFragment;
    private Preference mPreference;

    public AutoClickPreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showExclusiveDialog$0(
            DialogInterface dialogInterface, int i) {
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "accessibility_auto_action_type", 1);
        int i2 =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "accessibility_pause_auto_click_with",
                        0);
        AutoActionUtils.updateAction(this.mContext, i2, i2);
        updateState(this.mPreference);
    }

    private boolean showExclusiveDialog() {
        AlertDialog createExclusiveDialog =
                AccessibilityDialogUtils.createExclusiveDialog(
                        this.mContext,
                        "action_after_pointer_stops",
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.accessibility.dexterity.autoaction.AutoClickPreferenceController$$ExternalSyntheticLambda0
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                AutoClickPreferenceController.this.lambda$showExclusiveDialog$0(
                                        dialogInterface, i);
                            }
                        },
                        null);
        if (createExclusiveDialog == null) {
            return false;
        }
        createExclusiveDialog.show();
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        return List.of(Settings.Secure.getUriFor("accessibility_auto_action_type"));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey()) || this.mParentFragment == null) {
            return super.handlePreferenceTreeClick(preference);
        }
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "accessibility_auto_action_type",
                getThreadEnabled() ? 1 : 0);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "accessibility_auto_action_type", 0)
                != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (z && showExclusiveDialog()) {
            return false;
        }
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "accessibility_auto_action_type", z ? 1 : 0);
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "accessibility_pause_auto_click_with",
                        0);
        if (z) {
            AutoActionUtils.updateAction(this.mContext, -1, i);
            return true;
        }
        AutoActionUtils.updateAction(this.mContext, i, -1);
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "accessibility_auto_click_paused_state", 0);
        return true;
    }

    public void setParentFragment(Fragment fragment) {
        this.mParentFragment = fragment;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
