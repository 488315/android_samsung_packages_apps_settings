package com.android.settings.security;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ScreenPinningSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener, DialogInterface.OnClickListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass3();
    public LockPatternUtils mLockPatternUtils;
    public AnonymousClass1 mScreenPinningObserver;
    public SettingsMainSwitchBar mSwitchBar;
    public SwitchPreference mUseScreenLock;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.security.ScreenPinningSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getDynamicRawDataToIndex(Context context) {
            List dynamicRawDataToIndex = super.getDynamicRawDataToIndex(context);
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            Resources resources = context.getResources();
            new LockPatternUtils(context);
            ((SearchIndexableData) searchIndexableRaw).key = "use_screen_lock";
            searchIndexableRaw.title =
                    resources.getString(R.string.sec_screen_pinning_lock_after_unpinning);
            searchIndexableRaw.screenTitle = resources.getString(R.string.screen_pinning_title);
            ((ArrayList) dynamicRawDataToIndex).add(searchIndexableRaw);
            return dynamicRawDataToIndex;
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 86;
    }

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.settings.security.ScreenPinningSettings$1] */
    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        settingsActivity.setTitle(R.string.screen_pinning_title);
        this.mLockPatternUtils = new LockPatternUtils(settingsActivity);
        addPreferencesFromResource(R.xml.sec_screen_pinning_settings);
        SwitchPreference switchPreference = (SwitchPreference) findPreference("use_screen_lock");
        this.mUseScreenLock = switchPreference;
        switchPreference.seslSetRoundedBg(3);
        this.mScreenPinningObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.android.settings.security.ScreenPinningSettings.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        ScreenPinningSettings screenPinningSettings = ScreenPinningSettings.this;
                        SettingsMainSwitchBar settingsMainSwitchBar =
                                screenPinningSettings.mSwitchBar;
                        if (settingsMainSwitchBar != null) {
                            settingsMainSwitchBar.setChecked(
                                    Settings.System.getInt(
                                                    screenPinningSettings
                                                            .getContext()
                                                            .getContentResolver(),
                                                    "lock_to_app_enabled",
                                                    0)
                                            != 0);
                        }
                    }
                };
        View inflate =
                LayoutInflater.from(settingsActivity)
                        .inflate(R.layout.sec_screen_pinning_instructions, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.screen_pinning_description);
        TextView textView2 = (TextView) inflate.findViewById(R.id.screen_pinning_description_sub);
        String string = getResources().getString(R.string.screen_pinning_title);
        StringBuilder m =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                        Rune.isJapanDCMModel()
                                ? getResources()
                                        .getString(R.string.screen_pinning_description_msg_dcm)
                                : getResources().getString(R.string.screen_pinning_description_msg),
                        "\n\n");
        m.append(getResources().getString(R.string.screen_pinning_description_0));
        textView.setText(m.toString());
        String string2 =
                getResources().getBoolean(android.R.bool.config_sms_decode_gsm_8bit_data)
                        ? getResources().getString(R.string.screen_pinning_description_3_button)
                        : getResources().getString(R.string.screen_pinning_description_3);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d.", 1));
        sb.append(Utils.isRTL((SettingsActivity) getActivity()) ? " \u200f" : " ");
        sb.append(getResources().getString(R.string.screen_pinning_description_1, string));
        sb.append("\n");
        sb.append(String.format("%d.", 2));
        sb.append(" ");
        sb.append(getResources().getString(R.string.screen_pinning_description_2));
        sb.append("\n");
        sb.append(String.format("%d.", 3));
        sb.append(" ");
        sb.append(string2);
        sb.append("\n");
        sb.append(String.format("%d.", 4));
        sb.append(Utils.isRTL((SettingsActivity) getActivity()) ? " \u200f" : " ");
        sb.append(getResources().getString(R.string.screen_pinning_description_4));
        String sb2 = sb.toString();
        textView2.setContentDescription(String.format(sb2, string));
        textView2.setText(sb2);
        setHeaderView(inflate, false);
        SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        this.mSwitchBar.setChecked(
                Settings.System.getInt(getActivity().getContentResolver(), "lock_to_app_enabled", 0)
                        != 0);
        this.mSwitchBar.addOnSwitchChangeListener(this);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 43) {
            boolean z =
                    new LockPatternUtils(getActivity())
                                    .getKeyguardStoredPasswordQuality(UserHandle.myUserId())
                            != 0;
            setScreenLockUsed(z);
            this.mUseScreenLock.setChecked(z);
        } else if (i == 1000 && i2 == -1) {
            setScreenLockUsedSetting(false);
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        LoggingHelper.insertEventLogging(86, 4521, z);
        Settings.System.putInt(getContentResolver(), "lock_to_app_enabled", z ? 1 : 0);
        if (z) {
            setScreenLockUsedSetting(
                    Settings.Secure.getInt(
                                    getContentResolver(),
                                    "lock_to_app_exit_locked",
                                    this.mLockPatternUtils.isSecure(UserHandle.myUserId()) ? 1 : 0)
                            != 0);
        }
        updateDisplay();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            Settings.System.putInt(getContentResolver(), "lock_to_app_enabled", 1);
            setScreenLockUsedSetting(
                    Settings.Secure.getInt(
                                    getContentResolver(),
                                    "lock_to_app_exit_locked",
                                    this.mLockPatternUtils.isSecure(UserHandle.myUserId()) ? 1 : 0)
                            != 0);
        } else {
            this.mSwitchBar.setChecked(false);
        }
        updateDisplay();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mSwitchBar.removeOnSwitchChangeListener(this);
        this.mSwitchBar.hide();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (Rune.isSamsungDexMode(getContext())) {
            finish();
        }
        updateDisplay();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("lock_to_app_enabled"),
                        false,
                        this.mScreenPinningObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        getContentResolver().unregisterContentObserver(this.mScreenPinningObserver);
    }

    public final boolean setScreenLockUsed(boolean z) {
        int keyguardStoredPasswordQuality =
                new LockPatternUtils(getActivity())
                        .getKeyguardStoredPasswordQuality(UserHandle.myUserId());
        if (z) {
            if (keyguardStoredPasswordQuality == 0) {
                Intent intent = new Intent("android.app.action.SET_NEW_PASSWORD");
                intent.setPackage(getContext().getPackageName());
                intent.putExtra("hide_insecure_options", true);
                intent.putExtra("hide_biometrics_menu", true);
                intent.putExtra("from_sec_non_biometrics", true);
                startActivityForResult(intent, 43);
                return false;
            }
        } else if (keyguardStoredPasswordQuality != 0) {
            ChooseLockSettingsHelper.Builder builder =
                    new ChooseLockSettingsHelper.Builder(getActivity(), this);
            builder.mRequestCode = 1000;
            return builder.show();
        }
        setScreenLockUsedSetting(z);
        return true;
    }

    public final void setScreenLockUsedSetting(boolean z) {
        Settings.Secure.putInt(getContentResolver(), "lock_to_app_exit_locked", z ? 1 : 0);
        if (z) {
            Settings.Secure.putInt(getContentResolver(), "interaction_control_exit_locked", 1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void updateDisplay() {
        if ((Settings.System.getInt(getActivity().getContentResolver(), "lock_to_app_enabled", 0)
                        != 0)
                == true) {
            this.mUseScreenLock.setEnabled(true);
        } else {
            this.mUseScreenLock.setEnabled(false);
        }
        if (Rune.isJapanDCMModel()) {
            removePreference("use_screen_lock");
            getListView().seslSetFillBottomEnabled(true);
            getListView().mDrawLastRoundedCorner = false;
            getListView()
                    .seslSetFillBottomColor(
                            getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
        this.mUseScreenLock.setOnPreferenceChangeListener(
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.android.settings.security.ScreenPinningSettings.2
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        ScreenPinningSettings screenPinningSettings = ScreenPinningSettings.this;
                        boolean isSecure =
                                screenPinningSettings.mLockPatternUtils.isSecure(
                                        UserHandle.myUserId());
                        screenPinningSettings.getClass();
                        Boolean bool = (Boolean) obj;
                        LoggingHelper.insertEventLogging(
                                86, isSecure ? 4522 : 4523, bool.booleanValue());
                        return screenPinningSettings.setScreenLockUsed(bool.booleanValue());
                    }
                });
        this.mUseScreenLock.setChecked(
                Settings.Secure.getInt(
                                getContentResolver(),
                                "lock_to_app_exit_locked",
                                this.mLockPatternUtils.isSecure(UserHandle.myUserId()) ? 1 : 0)
                        != 0);
        if (Rune.isShopDemo(getContext()) || Rune.isLDUModel()) {
            this.mUseScreenLock.setEnabled(false);
        }
        String[] strArr = {"false"};
        int enterprisePolicyEnabled =
                Utils.getEnterprisePolicyEnabled(
                        getActivity(),
                        "content://com.sec.knox.provider/RestrictionPolicy3",
                        "isScreenPinningAllowedAsUser",
                        strArr);
        int enterprisePolicyEnabled2 =
                Utils.getEnterprisePolicyEnabled(
                        getContext(),
                        "content://com.sec.knox.provider/RestrictionPolicy2",
                        "isLockScreenEnabled",
                        strArr);
        if (enterprisePolicyEnabled != -1) {
            if (enterprisePolicyEnabled == 0) {
                this.mSwitchBar.setChecked(false);
                this.mSwitchBar.setEnabled(false);
            } else {
                this.mSwitchBar.setEnabled(true);
            }
        }
        if (enterprisePolicyEnabled2 == -1 || enterprisePolicyEnabled2 != 0) {
            return;
        }
        this.mUseScreenLock.setChecked(false);
        this.mUseScreenLock.setEnabled(false);
    }
}
