package com.samsung.android.settings.location;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.widget.SecClickableTextPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LocationDeclarationPrivacyController extends BasePreferenceController {
    private static final String LOCATION_DECLARATION_PRIVACY = "location_declaration_privacy";
    private SecClickableTextPreference mPreference;

    public LocationDeclarationPrivacyController(Context context) {
        super(context, LOCATION_DECLARATION_PRIVACY);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecClickableTextPreference) preferenceScreen.findPreference(getPreferenceKey());
        String string = this.mContext.getString(R.string.location_declaration_privacy_desc1);
        String string2 = this.mContext.getString(R.string.location_declaration_privacy_desc2);
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string, string2);
        int indexOf = m.indexOf(string2);
        SecClickableTextPreference secClickableTextPreference = this.mPreference;
        secClickableTextPreference.mBeginIndex = indexOf;
        secClickableTextPreference.mEndIndex = string2.length() + indexOf;
        SecClickableTextPreference secClickableTextPreference2 = this.mPreference;
        secClickableTextPreference2.mTextStyle = 1;
        secClickableTextPreference2.mForegroundColor =
                this.mContext.getColor(R.color.info_icon_color);
        ClickableSpan clickableSpan =
                new ClickableSpan() { // from class:
                                      // com.samsung.android.settings.location.LocationDeclarationPrivacyController.1
                    @Override // android.text.style.ClickableSpan
                    public final void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction("com.sec.clocationservice.action.show_location_consent");
                        intent.putExtra("settings", true);
                        if (intent.resolveActivity(
                                        ((AbstractPreferenceController)
                                                        LocationDeclarationPrivacyController.this)
                                                .mContext.getPackageManager())
                                != null) {
                            ((AbstractPreferenceController)
                                            LocationDeclarationPrivacyController.this)
                                    .mContext.startActivity(intent);
                        }
                    }
                };
        SecClickableTextPreference secClickableTextPreference3 = this.mPreference;
        secClickableTextPreference3.mClickableSpan = clickableSpan;
        secClickableTextPreference3.setTitle(m);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Rune.isChinaModel();
        return 3;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
