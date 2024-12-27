package com.samsung.android.settings.usefulfeature.functionkey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyItem;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\r"
                + "\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\n"
                + "\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0019\u001a\u00020\u000eH\u0002J\u0012\u0010\u001a\u001a\u00020\u000e2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\t\u0010\n"
                + "\"\u0004\b\u000b\u0010\f¨\u0006\u001d"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/functionkey/FunctionKeyDoublePressPreferenceController;",
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
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getFunctKeyDoublePress",
            "getSummary",
            "isChecked",
            ApnSettings.MVNO_NONE,
            "setChecked",
            "setFuncKeyDoublePress",
            "sideKeyDoublePressEnablePopup",
            "updateState",
            "preference",
            "Landroidx/preference/Preference;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public final class FunctionKeyDoublePressPreferenceController extends TogglePreferenceController {
    public static final int $stable = 8;
    private SecSwitchPreferenceScreen mPreference;

    public FunctionKeyDoublePressPreferenceController(Context context, String str) {
        super(context, str);
        if (TextUtils.isEmpty(
                Settings.Global.getString(
                        this.mContext.getContentResolver(),
                        "function_key_config_doublepress_selected_item"))) {
            UsefulfeatureUtils.migrationFunctionKeyDB(this.mContext, 2);
        }
    }

    private final int getFunctKeyDoublePress() {
        return Settings.Global.getInt(
                this.mContext.getContentResolver(), "function_key_config_doublepress", 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setFuncKeyDoublePress(boolean isChecked) {
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "function_key_config_doublepress",
                isChecked ? 1 : 0);
        UsefulfeatureUtils.setSideKeyCustomizationInfo(this.mContext, 2, isChecked);
        LoggingHelper.insertEventLogging(getMetricsCategory(), 7614, isChecked);
    }

    private final void sideKeyDoublePressEnablePopup() {
        final boolean z =
                Settings.System.getInt(this.mContext.getContentResolver(), "pwrkey_owner_status", 0)
                        != 0;
        String string = this.mContext.getString(R.string.turn_off_va_dialog_title);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String format =
                String.format(
                        string,
                        Arrays.copyOf(
                                new Object[] {
                                    this.mContext.getString(
                                            z
                                                    ? R.string.tvmode_quick_access
                                                    : R.string.dark_screen_with_side_key_title)
                                },
                                1));
        String string2 = this.mContext.getString(R.string.sec_function_key_enable_popup_msg);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        AlertDialog create =
                new AlertDialog.Builder(this.mContext)
                        .setTitle(format)
                        .setMessage(string2)
                        .setPositiveButton(
                                R.string.turn_off_button,
                                new DialogInterface.OnClickListener(
                                        this) { // from class:
                                                // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyDoublePressPreferenceController$sideKeyDoublePressEnablePopup$allDisabledDialog$1
                                    public final /* synthetic */
                                    FunctionKeyDoublePressPreferenceController this$0;

                                    {
                                        this.this$0 = this;
                                    }

                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        Context context;
                                        Context context2;
                                        if (z) {
                                            context2 =
                                                    ((AbstractPreferenceController) this.this$0)
                                                            .mContext;
                                            Settings.System.putInt(
                                                    context2.getContentResolver(),
                                                    "pwrkey_owner_status",
                                                    0);
                                        } else {
                                            context =
                                                    ((AbstractPreferenceController) this.this$0)
                                                            .mContext;
                                            Settings.System.putInt(
                                                    context.getContentResolver(), "lcd_curtain", 0);
                                        }
                                        this.this$0.setFuncKeyDoublePress(true);
                                    }
                                })
                        .setNegativeButton(
                                R.string.cancel,
                                FunctionKeyDoublePressPreferenceController$sideKeyDoublePressEnablePopup$allDisabledDialog$2
                                        .INSTANCE)
                        .create();
        if (create != null) {
            create.show();
        }
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
        return getFunctKeyDoublePress() == 1;
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
        if (isChecked) {
            boolean z =
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(), "pwrkey_owner_status", 0)
                            != 0;
            boolean z2 =
                    Settings.System.getInt(this.mContext.getContentResolver(), "lcd_curtain", 0)
                            != 0;
            if (z || z2) {
                sideKeyDoublePressEnablePopup();
            } else {
                setFuncKeyDoublePress(isChecked);
            }
        } else {
            setFuncKeyDoublePress(isChecked);
        }
        return true;
    }

    public final void setMPreference(SecSwitchPreferenceScreen secSwitchPreferenceScreen) {
        this.mPreference = secSwitchPreferenceScreen;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getSummary() {
        if (Settings.Global.getInt(
                        this.mContext.getContentResolver(), "function_key_config_doublepress", 1)
                == 1) {
            String string =
                    Settings.Global.getString(
                            this.mContext.getContentResolver(),
                            "function_key_config_doublepress_selected_item");
            if (!TextUtils.isEmpty(string)) {
                Iterator it =
                        ((ArrayList) FunctionKeyUtils.getFunctionKeyItems(this.mContext, 2, null))
                                .iterator();
                while (it.hasNext()) {
                    FunctionKeyItem functionKeyItem = (FunctionKeyItem) it.next();
                    if (Intrinsics.areEqual(functionKeyItem.key, string)) {
                        return functionKeyItem.title;
                    }
                }
            }
        }
        return ApnSettings.MVNO_NONE;
    }
}
