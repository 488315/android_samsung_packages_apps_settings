package com.android.settings.accessibility.shortcuts;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.view.accessibility.AccessibilityManager;

import androidx.preference.Preference;

import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.internal.util.Preconditions;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ShortcutOptionPreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private boolean mIsInSetupWizard;
    private Set<String> mShortcutTargets;

    public ShortcutOptionPreferenceController(Context context, String str) {
        super(context, str);
        this.mShortcutTargets = Collections.emptySet();
    }

    public void enableShortcutForTargets(boolean z) {
        Set<String> shortcutTargets = getShortcutTargets();
        int shortcutType = getShortcutType();
        AccessibilityManager accessibilityManager =
                (AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class);
        if (accessibilityManager != null) {
            accessibilityManager.enableShortcutsForTargets(
                    z, shortcutType, shortcutTargets, UserHandle.myUserId());
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return isShortcutAvailable() ? 1 : 2;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public Set<String> getShortcutTargets() {
        return this.mShortcutTargets;
    }

    public int getShortcutType() {
        return 0;
    }

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

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public boolean isChecked() {
        Set shortcutTargetsFromSettings =
                ShortcutUtils.getShortcutTargetsFromSettings(
                        this.mContext, getShortcutType(), UserHandle.myUserId());
        return !shortcutTargetsFromSettings.isEmpty()
                && shortcutTargetsFromSettings.containsAll(getShortcutTargets());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public boolean isInSetupWizard() {
        return this.mIsInSetupWizard;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public abstract boolean isShortcutAvailable();

    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        enableShortcutForTargets(((Boolean) obj).booleanValue());
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setInSetupWizard(boolean z) {
        this.mIsInSetupWizard = z;
    }

    public void setShortcutTargets(Set<String> set) {
        Preconditions.checkCollectionNotEmpty(set, "a11y targets");
        this.mShortcutTargets = set;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (getPreferenceKey().equals(preference.getKey())
                && (preference instanceof ShortcutOptionPreference)) {
            ((ShortcutOptionPreference) preference).setChecked(isChecked());
        }
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
