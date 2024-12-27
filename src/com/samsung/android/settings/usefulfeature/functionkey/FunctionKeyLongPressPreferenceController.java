package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyItem;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000:\n"
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
                + "\u0002\u0018\u0002\n"
                + "\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\r"
                + "\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\n"
                + "\u0010\u0013\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u0010\u0014\u001a\u00020\u000e2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\t\u0010\n"
                + "\"\u0004\b\u000b\u0010\f¨\u0006\u0017"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/functionkey/FunctionKeyLongPressPreferenceController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
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
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getSummary",
            "updateState",
            "preference",
            "Landroidx/preference/Preference;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public final class FunctionKeyLongPressPreferenceController extends BasePreferenceController {
    public static final int $stable = 8;
    private SecPreference mPreference;

    public FunctionKeyLongPressPreferenceController(Context context, String str) {
        super(context, str);
        if (TextUtils.isEmpty(
                Settings.Global.getString(
                        this.mContext.getContentResolver(),
                        "function_key_config_longpress_selected_item"))) {
            UsefulfeatureUtils.migrationFunctionKeyDB(this.mContext, 1);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        SecPreference secPreference =
                screen != null ? (SecPreference) screen.findPreference(getPreferenceKey()) : null;
        this.mPreference = secPreference;
        if (secPreference != null) {
            SecPreferenceUtils.applySummaryColor(secPreference, true);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.supportFunctionKey() || Rune.isMaintenanceMode()) {
            return 3;
        }
        if (!Rune.supportBixbyClient() && !Rune.isSupportAiAgent(this.mContext)) {
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

    public final SecPreference getMPreference() {
        return this.mPreference;
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

    public final void setMPreference(SecPreference secPreference) {
        this.mPreference = secPreference;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (Rune.supportBixbyClient()) {
            boolean z =
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "dedicated_app_side_switch",
                                    0)
                            != 0;
            SecPreference secPreference = this.mPreference;
            if (secPreference == null) {
                return;
            }
            secPreference.setVisible(!z);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getSummary() {
        String string =
                Settings.Global.getString(
                        this.mContext.getContentResolver(),
                        "function_key_config_longpress_selected_item");
        if (!TextUtils.isEmpty(string)) {
            Iterator it =
                    ((ArrayList) FunctionKeyUtils.getFunctionKeyItems(this.mContext, 1, null))
                            .iterator();
            while (it.hasNext()) {
                FunctionKeyItem functionKeyItem = (FunctionKeyItem) it.next();
                if (Intrinsics.areEqual(functionKeyItem.key, string)) {
                    return functionKeyItem.title;
                }
            }
        }
        return ApnSettings.MVNO_NONE;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
