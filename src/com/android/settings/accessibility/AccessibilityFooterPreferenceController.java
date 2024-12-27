package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.HelpUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilityFooterPreferenceController extends BasePreferenceController {
    private int mHelpResource;
    private String mIntroductionTitle;
    private String mLearnMoreText;

    public AccessibilityFooterPreferenceController(Context context, String str) {
        super(context, str);
    }

    private void updateFooterPreferences(
            AccessibilityFooterPreference accessibilityFooterPreference) {
        final Intent intent;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getIntroductionTitle());
        stringBuffer.append("\n\n");
        stringBuffer.append(accessibilityFooterPreference.getTitle());
        if (getHelpResource() != 0) {
            Context context = this.mContext;
            intent =
                    HelpUtils.getHelpIntent(
                            context,
                            context.getString(getHelpResource()),
                            this.mContext.getClass().getName());
        } else {
            intent = null;
        }
        if (intent != null) {
            accessibilityFooterPreference.setLearnMoreAction(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.accessibility.AccessibilityFooterPreferenceController$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            view.startActivityForResult(intent, 0);
                        }
                    });
            accessibilityFooterPreference.setLearnMoreText(getLearnMoreText());
            if (!accessibilityFooterPreference.mLinkEnabled) {
                accessibilityFooterPreference.mLinkEnabled = true;
                accessibilityFooterPreference.notifyChanged();
            }
        } else if (accessibilityFooterPreference.mLinkEnabled) {
            accessibilityFooterPreference.mLinkEnabled = false;
            accessibilityFooterPreference.notifyChanged();
        }
        accessibilityFooterPreference.setSelectable(false);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        updateFooterPreferences(
                (AccessibilityFooterPreference)
                        preferenceScreen.findPreference(getPreferenceKey()));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public int getHelpResource() {
        return this.mHelpResource;
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    public String getIntroductionTitle() {
        return this.mIntroductionTitle;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public String getLearnMoreText() {
        return this.mLearnMoreText;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setIntroductionTitle(String str) {
        this.mIntroductionTitle = str;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void setupHelpLink(int i, String str) {
        this.mHelpResource = i;
        this.mLearnMoreText = str;
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
