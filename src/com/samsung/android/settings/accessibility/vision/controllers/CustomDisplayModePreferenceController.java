package com.samsung.android.settings.accessibility.vision.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.AbstractPreferenceController;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.base.widget.SingleChoicePreference;
import com.samsung.android.settings.accessibility.vision.displaymode.DefaultDisplayMode;
import com.samsung.android.settings.accessibility.vision.displaymode.DisplayModeInfo;
import com.samsung.android.settings.accessibility.vision.displaymode.HighContrastDisplayMode;
import com.samsung.android.settings.accessibility.vision.displaymode.LargeDisplayMode;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CustomDisplayModePreferenceController extends BasePreferenceController {
    private final String[] display_key;
    private String mDefaultKey;
    private SingleChoicePreference mSingleChoicePreference;

    public CustomDisplayModePreferenceController(Context context, String str) {
        super(context, str);
        this.display_key = new String[3];
        this.mDefaultKey = SignalSeverity.NONE;
        Init();
    }

    private void Init() {
        if (WizardManagerHelper.isUserSetupComplete(this.mContext)) {
            String[] strArr = this.display_key;
            strArr[0] = "default";
            strArr[1] = "high_contrast";
            strArr[2] = "large_display";
            return;
        }
        String[] strArr2 = this.display_key;
        strArr2[0] = "default_suw";
        strArr2[1] = "high_contrast_suw";
        strArr2[2] = "large_display_suw";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String checkDefaultKey() {
        return this.mDefaultKey;
    }

    /* JADX WARN: Type inference failed for: r8v0, types: [com.samsung.android.settings.accessibility.vision.controllers.CustomDisplayModePreferenceController$1] */
    private List<SingleChoicePreference.SingleChoiceCandidateInfo> getCandidates() {
        ArrayList arrayList = new ArrayList();
        ?? r8 = new SingleChoicePreference.OnItemSelectedListener() { // from class: com.samsung.android.settings.accessibility.vision.controllers.CustomDisplayModePreferenceController.1
            @Override // com.samsung.android.settings.accessibility.base.widget.SingleChoicePreference.OnItemSelectedListener
            public final void onItemSelected(final String str) {
                boolean equals = SignalSeverity.NONE.equals(str);
                CustomDisplayModePreferenceController customDisplayModePreferenceController = CustomDisplayModePreferenceController.this;
                if (equals) {
                    customDisplayModePreferenceController.mSingleChoicePreference.setTitle((CharSequence) null);
                    SingleChoicePreference singleChoicePreference = customDisplayModePreferenceController.mSingleChoicePreference;
                    singleChoicePreference.buttonTitle = null;
                    singleChoicePreference.buttonClickListener = null;
                    singleChoicePreference.notifyChanged();
                } else {
                    if ("high_contrast".equals(str)) {
                        if (AccessibilityRune.isAtLeastOneUI_6_1()) {
                            customDisplayModePreferenceController.mSingleChoicePreference.setTitle(R.string.high_contrast_mode_reduce_animations);
                        } else {
                            customDisplayModePreferenceController.mSingleChoicePreference.setTitle(R.string.high_contrast_mode);
                        }
                    } else if ("high_contrast_suw".equals(str)) {
                        if (AccessibilityRune.isAtLeastOneUI_6_1()) {
                            customDisplayModePreferenceController.mSingleChoicePreference.setTitle(R.string.high_contrast_mode_suw_reduce_animations);
                        } else {
                            customDisplayModePreferenceController.mSingleChoicePreference.setTitle(R.string.high_contrast_mode_suw);
                        }
                    } else if ("large_display".equals(str)) {
                        customDisplayModePreferenceController.mSingleChoicePreference.setTitle(R.string.large_display_mode);
                    } else if ("large_display_suw".equals(str)) {
                        customDisplayModePreferenceController.mSingleChoicePreference.setTitle(R.string.large_display_mode_suw);
                    } else if ("default".equals(str)) {
                        customDisplayModePreferenceController.mSingleChoicePreference.setTitle((CharSequence) null);
                    } else if ("default_suw".equals(str)) {
                        customDisplayModePreferenceController.mSingleChoicePreference.setTitle((CharSequence) null);
                    }
                    SingleChoicePreference singleChoicePreference2 = customDisplayModePreferenceController.mSingleChoicePreference;
                    String string = ((AbstractPreferenceController) customDisplayModePreferenceController).mContext.getString(R.string.display_mode_apply);
                    View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.samsung.android.settings.accessibility.vision.controllers.CustomDisplayModePreferenceController.1.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            String str2;
                            if ("high_contrast".equals(str)) {
                                str2 = "HighContrast";
                            } else if ("large_display".equals(str)) {
                                str2 = "LargeDisplay";
                            } else {
                                "default".equals(str);
                                str2 = "Default";
                            }
                            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                            if (featureFactoryImpl == null) {
                                throw new UnsupportedOperationException("No feature factory configured");
                            }
                            featureFactoryImpl.getA11ySettingsMetricsFeatureProvider().action(CustomDisplayModePreferenceController.this.getMetricsCategory(), "A11Y3004", Map.of("Button", str2, "From", WizardManagerHelper.isUserSetupComplete(((AbstractPreferenceController) CustomDisplayModePreferenceController.this).mContext) ? "Settings" : "SetupWizard"));
                            CustomDisplayModePreferenceController.this.changeMode(str);
                        }
                    };
                    singleChoicePreference2.buttonTitle = string;
                    singleChoicePreference2.buttonClickListener = onClickListener;
                    singleChoicePreference2.notifyChanged();
                }
                customDisplayModePreferenceController.mDefaultKey = str;
            }
        };
        Context context = this.mContext;
        arrayList.add(new DisplayModeInfo(context, this.display_key[0], context.getString(R.string.accessibility_timeout_default), this.mContext.getDrawable(R.drawable.customized_display_mode_default), r8, new CustomDisplayModePreferenceController$$ExternalSyntheticLambda0(this)));
        Context context2 = this.mContext;
        arrayList.add(new DisplayModeInfo(context2, this.display_key[1], context2.getString(R.string.high_contrast), this.mContext.getDrawable(R.drawable.customized_display_mode_high_contrast), r8, new CustomDisplayModePreferenceController$$ExternalSyntheticLambda0(this)));
        Context context3 = this.mContext;
        arrayList.add(new DisplayModeInfo(context3, this.display_key[2], context3.getString(R.string.large_display), this.mContext.getDrawable(R.drawable.customized_display_mode_large_display), r8, new CustomDisplayModePreferenceController$$ExternalSyntheticLambda0(this)));
        return arrayList;
    }

    private boolean isAvailableCustomDisplayMode() {
        return getAvailabilityStatus() == 0 || getAvailabilityStatus() == 1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    public boolean changeMode(String str) {
        Context context;
        LargeDisplayMode largeDisplayMode;
        char c = 65535;
        context = this.mContext;
        str.getClass();
        switch (str) {
            case "large_display":
            case "large_display_suw":
                LargeDisplayMode largeDisplayMode2 = new LargeDisplayMode();
                largeDisplayMode2.mContext = context;
                largeDisplayMode = largeDisplayMode2;
                break;
            case "default_suw":
            case "default":
                DefaultDisplayMode defaultDisplayMode = new DefaultDisplayMode();
                defaultDisplayMode.mContext = context;
                largeDisplayMode = defaultDisplayMode;
                break;
            case "high_contrast":
            case "high_contrast_suw":
                HighContrastDisplayMode highContrastDisplayMode = new HighContrastDisplayMode();
                highContrastDisplayMode.mContext = context;
                largeDisplayMode = highContrastDisplayMode;
                break;
            default:
                throw new IllegalArgumentException("Wrong Display Mode");
        }
        switch (str.hashCode()) {
            case -1875963970:
                if (str.equals("large_display")) {
                    c = 3;
                    break;
                }
                break;
            case -1095635372:
                if (str.equals("large_display_suw")) {
                    c = 0;
                    break;
                }
                break;
            case -436618409:
                if (str.equals("default_suw")) {
                    c = 2;
                    break;
                }
                break;
            case 378248447:
                if (str.equals("high_contrast")) {
                    c = 4;
                    break;
                }
                break;
            case 1544803905:
                if (str.equals("default")) {
                    c = 5;
                    break;
                }
                break;
            case 2106848021:
                if (str.equals("high_contrast_suw")) {
                    c = 1;
                    break;
                }
                break;
        }
        Toast.makeText(this.mContext, largeDisplayMode.changeDisplayModeToast(largeDisplayMode.doChangeDisplayMode(c == 0 || c == 1 || c == 2)), 0).show();
        this.mDefaultKey = SignalSeverity.NONE;
        this.mSingleChoicePreference.setTitle((CharSequence) null);
        SingleChoicePreference singleChoicePreference = this.mSingleChoicePreference;
        singleChoicePreference.buttonTitle = null;
        singleChoicePreference.buttonClickListener = null;
        singleChoicePreference.notifyChanged();
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SingleChoicePreference singleChoicePreference = (SingleChoicePreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mSingleChoicePreference = singleChoicePreference;
        if (singleChoicePreference != null) {
            List<SingleChoicePreference.SingleChoiceCandidateInfo> candidates = getCandidates();
            CustomDisplayModePreferenceController$$ExternalSyntheticLambda0 customDisplayModePreferenceController$$ExternalSyntheticLambda0 = new CustomDisplayModePreferenceController$$ExternalSyntheticLambda0(this);
            SingleChoicePreference.SingleChoiceAdapter singleChoiceAdapter = singleChoicePreference.gridAdapter;
            singleChoiceAdapter.list = candidates;
            singleChoiceAdapter.defaultKey = customDisplayModePreferenceController$$ExternalSyntheticLambda0;
            singleChoicePreference.notifyChanged();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean z = Settings.System.getInt(this.mContext.getContentResolver(), "easy_mode_switch", 1) != 1;
        if (WizardManagerHelper.isUserSetupComplete(this.mContext)) {
            return (z || Utils.isMinimalBatteryUseEnabled(this.mContext)) ? 2 : 1;
        }
        return 3;
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
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(Object obj) {
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        PreferenceCategory preferenceCategory;
        super.updateState(preference);
        if (preference == null || isAvailableCustomDisplayMode() || (preferenceCategory = (PreferenceCategory) preference.getParent()) == null) {
            return;
        }
        preferenceCategory.setVisible(false);
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
    }
}
