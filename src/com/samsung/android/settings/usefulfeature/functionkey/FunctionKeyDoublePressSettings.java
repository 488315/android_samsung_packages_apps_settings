package com.samsung.android.settings.usefulfeature.functionkey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyAction;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyApplication;
import com.samsung.android.settings.usefulfeature.functionkey.item.FunctionKeyItem;
import com.samsung.android.settings.widget.SecRadioButtonGearPreferenceControllersHandler;

import java.util.ArrayList;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FunctionKeyDoublePressSettings extends FunctionKeyItemSettings
        implements CompoundButton.OnCheckedChangeListener, OnActivityResultListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialog mAllDisabledDialog;
    public SettingsMainSwitchBar mSwitchBar;
    public final Uri FUNC_KEY_DOUBLE_PRESS_SWITCH =
            Settings.Global.getUriFor("function_key_config_doublepress");
    public final Uri FUNC_KEY_DOUBLE_PRESS_TYPE =
            Settings.Global.getUriFor("function_key_config_doublepress_type");
    public final Uri FUNC_KEY_DOUBLE_PRESS_OPEN_APP_TYPE =
            Settings.Global.getUriFor("function_key_config_doublepress_value");

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        if (settingsActivity != null) {
            SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
            this.mSwitchBar = settingsMainSwitchBar;
            settingsMainSwitchBar.addOnSwitchChangeListener(this);
            this.mSwitchBar.setChecked(
                    Settings.Global.getInt(
                                    getContentResolver(), "function_key_config_doublepress", 1)
                            == 1);
            this.mSwitchBar.show();
        }
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        Bundle arguments = getArguments();
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putInt("pressType", 2);
        super.onAttach(context);
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemSettings,
              // com.samsung.android.settings.widget.SecRadioButtonGearPreferenceControllersHandler.OnChangeListener
    public final void onCheckedChanged(
            final SecRadioButtonGearPreferenceControllersHandler
                    secRadioButtonGearPreferenceControllersHandler) {
        Settings.Global.putString(
                ((FunctionKeyItemSettings) this).mContext.getContentResolver(),
                "function_key_config_doublepress_selected_item",
                secRadioButtonGearPreferenceControllersHandler.mSelectedKey);
        FunctionKeyItem functionKeyItem =
                (FunctionKeyItem)
                        this.mFunctionKeyItems.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyDoublePressSettings$$ExternalSyntheticLambda3
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                SecRadioButtonGearPreferenceControllersHandler
                                                        secRadioButtonGearPreferenceControllersHandler2 =
                                                                SecRadioButtonGearPreferenceControllersHandler
                                                                        .this;
                                                int i = FunctionKeyDoublePressSettings.$r8$clinit;
                                                return TextUtils.equals(
                                                        ((FunctionKeyItem) obj).key,
                                                        secRadioButtonGearPreferenceControllersHandler2
                                                                .mSelectedKey);
                                            }
                                        })
                                .findFirst()
                                .orElse(null);
        if (functionKeyItem instanceof FunctionKeyAction) {
            UsefulfeatureUtils.setSideKeyCustomizationInfo(
                    ((FunctionKeyItemSettings) this).mContext, 2, true);
        } else if ((functionKeyItem instanceof FunctionKeyApplication)
                && FunctionKeyUtils.getFunctionKeyAction(
                                ((FunctionKeyItemSettings) this).mContext,
                                (FunctionKeyApplication) functionKeyItem)
                        != null) {
            UsefulfeatureUtils.setSideKeyCustomizationInfo(
                    ((FunctionKeyItemSettings) this).mContext, 2, true);
        }
        super.onCheckedChanged(secRadioButtonGearPreferenceControllersHandler);
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getActivity() != null) {
            getActivity().setTitle(R.string.sec_function_key_double_press_title);
        }
        SecRadioButtonGearPreferenceControllersHandler
                secRadioButtonGearPreferenceControllersHandler = this.mControllersHandler;
        secRadioButtonGearPreferenceControllersHandler.mSelectedKey =
                Settings.Global.getString(
                        ((FunctionKeyItemSettings) this).mContext.getContentResolver(),
                        "function_key_config_doublepress_selected_item");
        secRadioButtonGearPreferenceControllersHandler.updateStates(false);
        FunctionKeyItemSettings.SettingsObserver settingsObserver = this.mSettingsObserver;
        Uri uri = this.FUNC_KEY_DOUBLE_PRESS_SWITCH;
        if (uri != null) {
            ((ArrayList) settingsObserver.mUris).add(uri);
        } else {
            settingsObserver.getClass();
        }
        FunctionKeyItemSettings.SettingsObserver settingsObserver2 = this.mSettingsObserver;
        Uri uri2 = this.FUNC_KEY_DOUBLE_PRESS_TYPE;
        if (uri2 != null) {
            ((ArrayList) settingsObserver2.mUris).add(uri2);
        } else {
            settingsObserver2.getClass();
        }
        FunctionKeyItemSettings.SettingsObserver settingsObserver3 = this.mSettingsObserver;
        Uri uri3 = this.FUNC_KEY_DOUBLE_PRESS_OPEN_APP_TYPE;
        if (uri3 != null) {
            ((ArrayList) settingsObserver3.mUris).add(uri3);
        } else {
            settingsObserver3.getClass();
        }
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SecRadioButtonGearPreferenceControllersHandler
                secRadioButtonGearPreferenceControllersHandler = this.mControllersHandler;
        secRadioButtonGearPreferenceControllersHandler.mSelectedKey =
                Settings.Global.getString(
                        ((FunctionKeyItemSettings) this).mContext.getContentResolver(),
                        "function_key_config_doublepress_selected_item");
        secRadioButtonGearPreferenceControllersHandler.updateStates(false);
        updateUI$2();
    }

    @Override // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyItemSettings,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setEmptyView((TextView) view.findViewById(android.R.id.empty));
    }

    public final void setFuncKeyDoublePress(boolean z) {
        Settings.Global.putInt(getContentResolver(), "function_key_config_doublepress", z ? 1 : 0);
        UsefulfeatureUtils.setSideKeyCustomizationInfo(
                ((FunctionKeyItemSettings) this).mContext, 2, z);
        updateUI$2();
        LoggingHelper.insertEventLogging(7613, 7614, z);
    }

    public final void updateUI$2() {
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(
                    Settings.Global.getInt(
                                    getContentResolver(), "function_key_config_doublepress", 1)
                            == 1);
        }
        if (getListView() != null) {
            getListView()
                    .setVisibility(
                            Settings.Global.getInt(
                                                    getContentResolver(),
                                                    "function_key_config_doublepress",
                                                    1)
                                            == 1
                                    ? 0
                                    : 8);
        }
        if (getEmptyView() != null) {
            getEmptyView()
                    .setVisibility(
                            Settings.Global.getInt(
                                                    getContentResolver(),
                                                    "function_key_config_doublepress",
                                                    1)
                                            == 1
                                    ? 8
                                    : 0);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        String string;
        if (z) {
            boolean z2 =
                    Settings.System.getInt(getContentResolver(), "pwrkey_owner_status", 0) != 0;
            boolean z3 = Settings.System.getInt(getContentResolver(), "lcd_curtain", 0) != 0;
            if (!z2 && !z3) {
                setFuncKeyDoublePress(true);
                return;
            }
            AlertDialog alertDialog = this.mAllDisabledDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.mAllDisabledDialog = null;
            }
            final boolean z4 =
                    Settings.System.getInt(getContentResolver(), "pwrkey_owner_status", 0) != 0;
            String string2 =
                    ((FunctionKeyItemSettings) this)
                            .mContext.getString(R.string.turn_off_va_dialog_title);
            if (z4) {
                string =
                        ((FunctionKeyItemSettings) this)
                                .mContext.getString(R.string.tvmode_quick_access);
            } else {
                string =
                        ((FunctionKeyItemSettings) this)
                                .mContext.getString(R.string.dark_screen_with_side_key_title);
            }
            AlertDialog create =
                    new AlertDialog.Builder(((FunctionKeyItemSettings) this).mContext)
                            .setTitle(String.format(string2, string))
                            .setMessage(
                                    ((FunctionKeyItemSettings) this)
                                            .mContext.getString(
                                                    R.string.sec_function_key_enable_popup_msg))
                            .setPositiveButton(
                                    R.string.turn_off_button,
                                    new DialogInterface
                                            .OnClickListener() { // from class:
                                                                 // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyDoublePressSettings$$ExternalSyntheticLambda0
                                        @Override // android.content.DialogInterface.OnClickListener
                                        public final void onClick(
                                                DialogInterface dialogInterface, int i) {
                                            FunctionKeyDoublePressSettings
                                                    functionKeyDoublePressSettings =
                                                            FunctionKeyDoublePressSettings.this;
                                            boolean z5 = z4;
                                            int i2 = FunctionKeyDoublePressSettings.$r8$clinit;
                                            if (z5) {
                                                Settings.System.putInt(
                                                        functionKeyDoublePressSettings
                                                                .getContentResolver(),
                                                        "pwrkey_owner_status",
                                                        0);
                                            } else {
                                                Settings.System.putInt(
                                                        functionKeyDoublePressSettings
                                                                .getContentResolver(),
                                                        "lcd_curtain",
                                                        0);
                                            }
                                            functionKeyDoublePressSettings.setFuncKeyDoublePress(
                                                    true);
                                        }
                                    })
                            .setNegativeButton(
                                    R.string.cancel,
                                    new DialogInterface
                                            .OnClickListener() { // from class:
                                                                 // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyDoublePressSettings$$ExternalSyntheticLambda1
                                        @Override // android.content.DialogInterface.OnClickListener
                                        public final void onClick(
                                                DialogInterface dialogInterface, int i) {
                                            FunctionKeyDoublePressSettings
                                                    functionKeyDoublePressSettings =
                                                            FunctionKeyDoublePressSettings.this;
                                            int i2 = FunctionKeyDoublePressSettings.$r8$clinit;
                                            AlertDialog alertDialog2 =
                                                    functionKeyDoublePressSettings
                                                            .mAllDisabledDialog;
                                            if (alertDialog2 != null) {
                                                alertDialog2.dismiss();
                                                functionKeyDoublePressSettings.mAllDisabledDialog =
                                                        null;
                                            }
                                        }
                                    })
                            .create();
            this.mAllDisabledDialog = create;
            create.setOnDismissListener(
                    new DialogInterface
                            .OnDismissListener() { // from class:
                                                   // com.samsung.android.settings.usefulfeature.functionkey.FunctionKeyDoublePressSettings$$ExternalSyntheticLambda2
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            FunctionKeyDoublePressSettings functionKeyDoublePressSettings =
                                    FunctionKeyDoublePressSettings.this;
                            int i = FunctionKeyDoublePressSettings.$r8$clinit;
                            functionKeyDoublePressSettings.updateUI$2();
                        }
                    });
            this.mAllDisabledDialog.show();
            return;
        }
        setFuncKeyDoublePress(false);
    }
}
