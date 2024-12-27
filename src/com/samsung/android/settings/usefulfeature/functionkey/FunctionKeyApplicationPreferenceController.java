package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ProviderInfo;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyAction;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyApplication;
import com.samsung.android.settings.widget.SecRadioButtonGearPreference;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000X\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0006\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b\u0017\u0018\u00002\u00020\u0001B)\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020"
                + "\t¢\u0006\u0002\u0010\n"
                + "J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\n"
                + "\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u0002J\u0006\u0010\u001a\u001a\u00020\u0016J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u000fJ\u0010\u0010"
                + " \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\"H\u0016J\u0012\u0010#\u001a\u00020\u00162\b\u0010!\u001a\u0004\u0018\u00010$H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u000b\u0010\fR\u001f\u0010\r"
                + "\u001a\u0010\u0012\f\u0012\n"
                + " \u0010*\u0004\u0018\u00010\u000f0\u000f0\u000e¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006%"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/functionkey/FunctionKeyApplicationPreferenceController;",
            "Lcom/samsung/android/settings/usefulfeature/functionkey/FunctionKeyItemPreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "application",
            "Lcom/samsung/android/settings/usefulfeature/functionkey/item/FunctionKeyApplication;",
            "parent",
            "Landroidx/fragment/app/Fragment;",
            "(Landroid/content/Context;Ljava/lang/String;Lcom/samsung/android/settings/usefulfeature/functionkey/item/FunctionKeyApplication;Landroidx/fragment/app/Fragment;)V",
            "getApplication",
            "()Lcom/samsung/android/settings/usefulfeature/functionkey/item/FunctionKeyApplication;",
            "launcher",
            "Landroidx/activity/result/ActivityResultLauncher;",
            "Landroid/content/Intent;",
            "kotlin.jvm.PlatformType",
            "getLauncher",
            "()Landroidx/activity/result/ActivityResultLauncher;",
            "getParent",
            "()Landroidx/fragment/app/Fragment;",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getSelectedActionKey",
            "launch",
            "onActivityResult",
            ApnSettings.MVNO_NONE,
            "resultCode",
            ApnSettings.MVNO_NONE,
            "data",
            "onRadioButtonClicked",
            "preference",
            "Lcom/samsung/android/settings/widget/SecRadioButtonGearPreference;",
            "updateState",
            "Landroidx/preference/Preference;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public class FunctionKeyApplicationPreferenceController
        extends FunctionKeyItemPreferenceController {
    public static final int $stable = 8;
    private final FunctionKeyApplication application;
    private final ActivityResultLauncher launcher;
    private final Fragment parent;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionKeyApplicationPreferenceController(
            Context context, String str, FunctionKeyApplication application, Fragment parent) {
        super(context, str, application);
        Intrinsics.checkNotNullParameter(application, "application");
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.application = application;
        this.parent = parent;
        ActivityResultLauncher registerForActivityResult =
                parent.registerForActivityResult(
                        new ActivityResultContracts$StartActivityForResult(0),
                        new FunctionKeyApplicationPreferenceController$launcher$1(this));
        Intrinsics.checkNotNullExpressionValue(
                registerForActivityResult, "registerForActivityResult(...)");
        this.launcher = registerForActivityResult;
    }

    private final String getSelectedActionKey() {
        Map map;
        String string =
                Settings.Global.getString(
                        this.mContext.getContentResolver(),
                        "function_key_config_doublepress_selected_actions");
        if (string == null
                || (map =
                                (Map)
                                        new Gson()
                                                .fromJson(
                                                        string,
                                                        new TypeToken<
                                                                Map<
                                                                        String,
                                                                        ? extends
                                                                                String>>() { // from
                                                                                             // class: com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyApplicationPreferenceController$getSelectedActionKey$actions$1
                                                        }.getType()))
                        == null) {
            return null;
        }
        return (String) map.get(this.application.key);
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        SecRadioButtonGearPreference secRadioButtonGearPreference = this.mPreference;
        if (secRadioButtonGearPreference != null) {
            secRadioButtonGearPreference.mIsSummaryColorPrimaryDark = true;
            SecPreferenceUtils.applySummaryColor(secRadioButtonGearPreference, true);
        }
        SecRadioButtonGearPreference secRadioButtonGearPreference2 = this.mPreference;
        if (secRadioButtonGearPreference2 != null) {
            secRadioButtonGearPreference2.mOnGearClickListener =
                    new FunctionKeyApplicationPreferenceController$launcher$1(this);
            secRadioButtonGearPreference2.notifyChanged();
        }
    }

    public final FunctionKeyApplication getApplication() {
        return this.application;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    public final ActivityResultLauncher getLauncher() {
        return this.launcher;
    }

    public final Fragment getParent() {
        return this.parent;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    public final void launch() {
        Intent intent = this.application.launchIntent;
        Map map =
                (Map)
                        new Gson()
                                .fromJson(
                                        Settings.Global.getString(
                                                this.mContext.getContentResolver(),
                                                "function_key_config_doublepress_selected_actions"),
                                        new TypeToken<
                                                Map<
                                                        String,
                                                        ? extends
                                                                String>>() { // from class:
                                                                             // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyApplicationPreferenceController$launch$actions$1
                                        }.getType());
        if (map == null) {
            map = new HashMap();
        }
        if (!TextUtils.isEmpty((CharSequence) map.get(this.application.key))) {
            intent.putExtra("selected_key", (String) map.get(this.application.key));
        }
        if (!TextUtils.isEmpty(this.application.title)) {
            intent.putExtra(":settings:show_fragment_title", this.application.title);
        }
        this.launcher.launch(intent);
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public final boolean onActivityResult(int resultCode, Intent data) {
        if (resultCode == -1) {
            String stringExtra =
                    data != null
                            ? data.getStringExtra(GoodSettingsContract.EXTRA_NAME.POLICY_KEY)
                            : null;
            if (TextUtils.isEmpty(stringExtra)) {
                Log.e("FunctionKeyApplicationPreferenceController", "action key is null.");
            } else {
                ArrayList loadDynamicFunctionKeyActions =
                        FunctionKeyUtils.loadDynamicFunctionKeyActions(
                                this.mContext,
                                (ProviderInfo) this.application.componentInfo,
                                new ArrayList(
                                        CollectionsKt__CollectionsKt.mutableListOf(stringExtra)));
                if (loadDynamicFunctionKeyActions.size() == 1) {
                    Settings.Global.putString(
                            this.mContext.getContentResolver(),
                            "function_key_config_doublepress_selected_item",
                            this.application.key);
                    Map map =
                            (Map)
                                    new Gson()
                                            .fromJson(
                                                    Settings.Global.getString(
                                                            this.mContext.getContentResolver(),
                                                            "function_key_config_doublepress_selected_actions"),
                                                    new TypeToken<
                                                            Map<
                                                                    String,
                                                                    ? extends String>>() { // from
                                                        // class:
                                                        // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyApplicationPreferenceController$onActivityResult$actions$1
                                                    }.getType());
                    if (map == null) {
                        map = new HashMap();
                    }
                    map.put(
                            this.application.key,
                            ((FunctionKeyAction) loadDynamicFunctionKeyActions.get(0)).key);
                    Settings.Global.putString(
                            this.mContext.getContentResolver(),
                            "function_key_config_doublepress_selected_actions",
                            new Gson().toJson(map));
                    UsefulfeatureUtils.setSideKeyCustomizationInfo(
                            this.mContext,
                            ((FunctionKeyAction) loadDynamicFunctionKeyActions.get(0)).pressType,
                            true);
                } else {
                    Log.e("FunctionKeyApplicationPreferenceController", "action is null.");
                }
            }
        }
        return true;
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController,
              // com.samsung.android.settings.widget.SecRadioButtonGearPreference.OnClickListener
    public void onRadioButtonClicked(SecRadioButtonGearPreference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (preference.mChecked
                || FunctionKeyUtils.getFunctionKeyAction(this.mContext, this.application) != null) {
            super.onRadioButtonClicked(preference);
        } else {
            launch();
        }
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        SecRadioButtonGearPreference secRadioButtonGearPreference;
        super.updateState(preference);
        FunctionKeyAction functionKeyAction =
                FunctionKeyUtils.getFunctionKeyAction(this.mContext, this.application);
        if (functionKeyAction == null
                || (secRadioButtonGearPreference = this.mPreference) == null) {
            return;
        }
        secRadioButtonGearPreference.setSummary(functionKeyAction.title);
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemPreferenceController, com.samsung.android.settings.widget.SecRadioButtonGearPreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
