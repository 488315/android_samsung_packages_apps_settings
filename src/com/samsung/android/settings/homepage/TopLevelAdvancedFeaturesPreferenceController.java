package com.samsung.android.settings.homepage;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;

import com.android.settings.Utils;
import com.android.settings.widget.HomepagePreference;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000H\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\r\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\n"
                + "\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\n"
                + "\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0014J\b\u0010\u001d\u001a\u00020\u001cH\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\t\u0010\n"
                + "\"\u0004\b\u000b\u0010\fR\u001c\u0010\r"
                + "\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001e"
        },
        d2 = {
            "Lcom/samsung/android/settings/homepage/TopLevelAdvancedFeaturesPreferenceController;",
            "Lcom/samsung/android/settings/homepage/TopLevelPreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "mMaxSummaryCount",
            ApnSettings.MVNO_NONE,
            "getMMaxSummaryCount",
            "()I",
            "setMMaxSummaryCount",
            "(I)V",
            "mPreference",
            "Landroidx/preference/SecPreference;",
            "getMPreference",
            "()Landroidx/preference/SecPreference;",
            "setMPreference",
            "(Landroidx/preference/SecPreference;)V",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getLaunchIntent",
            "Landroid/content/Intent;",
            "getSummary",
            ApnSettings.MVNO_NONE,
            "getSummaryOnBackground",
            ApnSettings.MVNO_NONE,
            "isControllable",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes4.dex */
public final class TopLevelAdvancedFeaturesPreferenceController
        extends TopLevelPreferenceController {
    public static final int $stable = 8;
    private int mMaxSummaryCount;
    private SecPreference mPreference;

    public TopLevelAdvancedFeaturesPreferenceController(Context context, String str) {
        super(context, str);
        this.mMaxSummaryCount = 3;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        this.mPreference =
                (HomepagePreference) screen.findPreference("top_level_advanced_features");
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$UsefulFeatureMainActivity");
        intent.addFlags(268468224);
        return intent;
    }

    public final int getMMaxSummaryCount() {
        return this.mMaxSummaryCount;
    }

    public final SecPreference getMPreference() {
        return this.mPreference;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        ArrayList arrayList = new ArrayList();
        Context context = this.mContext;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if (context.getResources().getBoolean(R.bool.config_unfoldTransitionHapticsEnabled)) {
            String string =
                    this.mContext.getString(com.android.settings.R.string.sec_labs_settings_title);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            arrayList.add(string);
        }
        if (SemFloatingFeature.getInstance()
                        .getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION")
                > 0) {
            String string2 =
                    this.mContext.getString(com.android.settings.R.string.pen_settings_title);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            arrayList.add(string2);
        }
        if (Rune.supportFunctionKey() && !Rune.isMaintenanceMode()) {
            String string3 =
                    this.mContext.getString(
                            com.android.settings.R.string.sec_function_key_setting_title);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            arrayList.add(string3);
        }
        Context context2 = this.mContext;
        return Utils.buildSummaryString(
                Utils.getTopLevelSummarySeparator(context2), arrayList, this.mMaxSummaryCount);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean getSummaryOnBackground() {
        return true;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public final void setMMaxSummaryCount(int i) {
        this.mMaxSummaryCount = i;
    }

    public final void setMPreference(SecPreference secPreference) {
        this.mPreference = secPreference;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
