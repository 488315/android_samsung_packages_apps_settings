package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.activekey.DedicatedAppInfo;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import kotlin.Metadata;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000B\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\r"
                + "\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0012\u0010\u0017\u001a\u00020\u000e2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\t\u0010\n"
                + "\"\u0004\b\u000b\u0010\f¨\u0006\u001a"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/functionkey/FunctionKeyShortPressDedicatedAppPreferenceController;",
            "Lcom/android/settings/core/TogglePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "mPreference",
            "Landroidx/preference/SecSwitchPreferenceScreen;",
            "getMPreference",
            "()Landroidx/preference/SecSwitchPreferenceScreen;",
            "setMPreference",
            "(Landroidx/preference/SecSwitchPreferenceScreen;)V",
            "dedicatedAppUpdate",
            ApnSettings.MVNO_NONE,
            "displayPreference",
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "isChecked",
            ApnSettings.MVNO_NONE,
            "setChecked",
            "updateState",
            "preference",
            "Landroidx/preference/Preference;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public final class FunctionKeyShortPressDedicatedAppPreferenceController
        extends TogglePreferenceController {
    public static final int $stable = 8;
    private SecSwitchPreferenceScreen mPreference;

    public FunctionKeyShortPressDedicatedAppPreferenceController(Context context, String str) {
        super(context, str);
    }

    private final void dedicatedAppUpdate() {
        if (!getThreadEnabled()) {
            SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
            if (secSwitchPreferenceScreen != null) {
                secSwitchPreferenceScreen.setSummary(R.string.switch_off_text);
                return;
            }
            return;
        }
        String string =
                Settings.System.getString(
                        this.mContext.getContentResolver(), "dedicated_app_label_side");
        String dedicatedApp = DedicatedAppInfo.getDedicatedApp(this.mContext, 3);
        if (!TextUtils.isEmpty(dedicatedApp) && Utils.hasPackage(this.mContext, dedicatedApp)) {
            SecSwitchPreferenceScreen secSwitchPreferenceScreen2 = this.mPreference;
            if (secSwitchPreferenceScreen2 == null) {
                return;
            }
            secSwitchPreferenceScreen2.setSummary(string);
            return;
        }
        String str = (String) DedicatedAppInfo.loadAppList(this.mContext, 3).get(0);
        String valueOf = String.valueOf(Utils.getApplicationLabel(this.mContext, str));
        SecSwitchPreferenceScreen secSwitchPreferenceScreen3 = this.mPreference;
        if (secSwitchPreferenceScreen3 != null) {
            secSwitchPreferenceScreen3.setSummary(valueOf);
        }
        DedicatedAppInfo.saveDedicatedApp(this.mContext, 3, str);
        DedicatedAppInfo.saveDedicatedAppLabel(this.mContext, 3, valueOf);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                screen != null
                        ? (SecSwitchPreferenceScreen) screen.findPreference(getPreferenceKey())
                        : null;
        this.mPreference = secSwitchPreferenceScreen;
        if (secSwitchPreferenceScreen != null) {
            SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen, true);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.supportFunctionKey() || Rune.isMaintenanceMode()) {
            return 3;
        }
        if (!Utils.isGuestUser(this.mContext) && !Utils.isGuestMode(this.mContext)) {
            if (KnoxUtils.isApplicationRestricted(this.mContext, getPreferenceKey(), "hide")) {
                return 3;
            }
            if (!KnoxUtils.isApplicationRestricted(this.mContext, getPreferenceKey(), "grayout")) {
                return 0;
            }
        }
        return 5;
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

    public final SecSwitchPreferenceScreen getMPreference() {
        return this.mPreference;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
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
        return Settings.System.getInt(
                        this.mContext.getContentResolver(), "dedicated_app_side_switch", 0)
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
    public boolean setChecked(boolean isChecked) {
        Settings.System.putInt(
                this.mContext.getContentResolver(), "dedicated_app_side_switch", isChecked ? 1 : 0);
        LoggingHelper.insertEventLogging(getMetricsCategory(), 67221, isChecked);
        if (!DedicatedAppInfo.getDedicatedAppState(this.mContext, 3)) {
            return true;
        }
        DedicatedAppInfo.setB2BDeltaKeyCustomizationInfo(
                this.mContext, 3, DedicatedAppInfo.getDedicatedApp(this.mContext, 3), isChecked);
        return true;
    }

    public final void setMPreference(SecSwitchPreferenceScreen secSwitchPreferenceScreen) {
        this.mPreference = secSwitchPreferenceScreen;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (UsefulfeatureUtils.hasSideKeyDedicatedAppEnable(this.mContext)) {
            SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mPreference;
            if (secSwitchPreferenceScreen != null) {
                secSwitchPreferenceScreen.setVisible(true);
            }
            dedicatedAppUpdate();
            return;
        }
        SecSwitchPreferenceScreen secSwitchPreferenceScreen2 = this.mPreference;
        if (secSwitchPreferenceScreen2 != null) {
            secSwitchPreferenceScreen2.setVisible(false);
        }
        if (DedicatedAppInfo.getDedicatedAppSwitch(this.mContext, 3)
                || DedicatedAppInfo.getDedicatedAppState(this.mContext, 3)) {
            DedicatedAppInfo.setDedicatedAppSwitch(this.mContext, 3, false);
            DedicatedAppInfo.saveDedicatedApp(this.mContext, 3, ApnSettings.MVNO_NONE);
            DedicatedAppInfo.saveDedicatedAppLabel(this.mContext, 3, ApnSettings.MVNO_NONE);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
