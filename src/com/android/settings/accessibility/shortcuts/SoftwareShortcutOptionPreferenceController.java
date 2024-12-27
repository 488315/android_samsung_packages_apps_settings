package com.android.settings.accessibility.shortcuts;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityButtonFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.utils.AnnotationSpan;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SoftwareShortcutOptionPreferenceController
        extends ShortcutOptionPreferenceController {
    public SoftwareShortcutOptionPreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean isMagnificationInTargets() {
        return getShortcutTargets()
                .contains("com.android.server.accessibility.MagnificationController");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$getCustomizeAccessibilityButtonLink$0(View view) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = AccessibilityButtonFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public void enableShortcutForTargets(boolean z) {
        super.enableShortcutForTargets(z);
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    public CharSequence getCustomizeAccessibilityButtonLink() {
        return AnnotationSpan.linkify(
                this.mContext.getText(
                        R.string.accessibility_shortcut_edit_dialog_summary_software_floating),
                new AnnotationSpan.LinkInfo(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.accessibility.shortcuts.SoftwareShortcutOptionPreferenceController$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                SoftwareShortcutOptionPreferenceController.this
                                        .lambda$getCustomizeAccessibilityButtonLink$0(view);
                            }
                        }));
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public int getShortcutType() {
        return 1;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.accessibility.shortcuts.ShortcutOptionPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
