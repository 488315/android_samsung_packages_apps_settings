package com.samsung.android.settings.galaxysystemupdate;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.accessibility.ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.voiceinput.Constants;
import com.samsung.android.settings.widget.SecButtonPreference;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;
import com.samsung.android.settings.widget.SecRoundButtonView;
import com.samsung.android.settings.widget.SecUnclickablePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GalaxySystemUpdateFragment extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener, Preference.OnPreferenceChangeListener {
    public static boolean isUpdateSupportedDevice = false;
    public SettingsMainSwitchBar mSwitchBar = null;
    public SecRestrictedSwitchPreference mUseWifiOnlyPreference = null;
    public SecUnclickablePreference mSystemRebootDescriptionPreference = null;
    public SecButtonPreference mSystemUpdate = null;
    public AlertDialog mUpdateInfoDialog = null;
    public final AnonymousClass1 mainHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.samsung.android.settings.galaxysystemupdate.GalaxySystemUpdateFragment.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    super.handleMessage(message);
                    if (message.what == 1) {
                        GalaxySystemUpdateFragment galaxySystemUpdateFragment =
                                GalaxySystemUpdateFragment.this;
                        galaxySystemUpdateFragment.setUpdateButtonState(false);
                        if (message.arg1 <= 0) {
                            galaxySystemUpdateFragment.getClass();
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(
                                            galaxySystemUpdateFragment.getContext());
                            builder.setMessage(
                                            galaxySystemUpdateFragment.getString(
                                                    R.string.galaxy_system_apps_are_up_to_date))
                                    .setPositiveButton(
                                            android.R.string.ok,
                                            new ScreenFlashNotificationColorDialogFragment$$ExternalSyntheticLambda0());
                            AlertDialog create = builder.create();
                            galaxySystemUpdateFragment.mUpdateInfoDialog = create;
                            create.show();
                            return;
                        }
                        galaxySystemUpdateFragment.getClass();
                        try {
                            Intent intent = new Intent();
                            intent.setData(Uri.parse("samsungapps://SystemUpdate/"));
                            intent.addFlags(335544352);
                            galaxySystemUpdateFragment.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 57011;
    }

    public final boolean isGalaxyDeeplinkSupported() {
        int i = -1;
        try {
            i =
                    getContext()
                            .getPackageManager()
                            .getApplicationInfo(Constants.GALAXY_STORE_PACKAGE_NAME, 128)
                            .metaData
                            .getInt("com.sec.android.app.samsungapps.systemupdate.version", -1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return i > 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        SecRestrictedSwitchPreference secRestrictedSwitchPreference;
        super.onActivityCreated(bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        if (isUpdateSupportedDevice) {
            SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
            this.mSwitchBar = settingsMainSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.setChecked(
                        Settings.Global.getInt(
                                        getContext().getContentResolver(),
                                        "galaxy_system_update",
                                        1)
                                == 1);
                this.mSwitchBar.addOnSwitchChangeListener(this);
                this.mSwitchBar.show();
            }
        }
        boolean z =
                Settings.Global.getInt(getContext().getContentResolver(), "galaxy_system_update", 1)
                        == 1;
        if (isUpdateSupportedDevice
                && (secRestrictedSwitchPreference = this.mUseWifiOnlyPreference) != null) {
            secRestrictedSwitchPreference.setEnabled(z);
            this.mUseWifiOnlyPreference.setChecked(
                    Settings.Global.getInt(
                                    getContext().getContentResolver(),
                                    "galaxy_system_update_use_wifi_only",
                                    1)
                            == 1);
        }
        for (PackageInstaller.SessionInfo sessionInfo :
                getContext().getPackageManager().getPackageInstaller().getActiveStagedSessions()) {
            String appPackageName = sessionInfo.getAppPackageName();
            if (appPackageName != null && appPackageName.startsWith("com.samsung.")) {
                Log.i(
                        "GalaxySystemAppsInfoManager",
                        "samsung apex : " + sessionInfo.getAppPackageName() + " is staged");
                Context context = getContext();
                Intent intent = new Intent();
                intent.setClass(context, GalaxySystemUpdateRebootAlertDialog.class);
                startActivity(intent);
                return;
            }
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        SecRestrictedSwitchPreference secRestrictedSwitchPreference;
        LoggingHelper.insertEventLogging(57011, 57010, z ? 1L : -1L);
        Settings.Global.putInt(
                getContext().getContentResolver(), "galaxy_system_update", z ? 1 : -1);
        if (!isUpdateSupportedDevice
                || (secRestrictedSwitchPreference = this.mUseWifiOnlyPreference) == null) {
            return;
        }
        secRestrictedSwitchPreference.setEnabled(z);
        this.mUseWifiOnlyPreference.setChecked(
                Settings.Global.getInt(
                                getContext().getContentResolver(),
                                "galaxy_system_update_use_wifi_only",
                                1)
                        == 1);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_galaxy_system_update);
        if (Settings.Global.getInt(
                        getContext().getContentResolver(), "galaxy_system_update_block", -1)
                == -1) {
            isUpdateSupportedDevice = true;
        } else {
            isUpdateSupportedDevice = false;
        }
        SecRestrictedSwitchPreference secRestrictedSwitchPreference =
                (SecRestrictedSwitchPreference) findPreference("use_wifi_only");
        this.mUseWifiOnlyPreference = secRestrictedSwitchPreference;
        if (isUpdateSupportedDevice) {
            secRestrictedSwitchPreference.setOnPreferenceChangeListener(this);
            this.mUseWifiOnlyPreference.setChecked(
                    Settings.Global.getInt(
                                    getContext().getContentResolver(),
                                    "galaxy_system_update_use_wifi_only",
                                    1)
                            == 1);
            this.mUseWifiOnlyPreference.setEnabled(
                    Settings.Global.getInt(
                                    getContext().getContentResolver(), "galaxy_system_update", 1)
                            == 1);
        } else {
            secRestrictedSwitchPreference.setVisible(false);
        }
        SecUnclickablePreference secUnclickablePreference =
                (SecUnclickablePreference) findPreference("galaxy_system_update_description");
        this.mSystemRebootDescriptionPreference = secUnclickablePreference;
        if (isUpdateSupportedDevice) {
            String str = getString(R.string.galaxy_system_update_description) + "\n\n";
            if (Utils.isTablet()) {
                SecUnclickablePreference secUnclickablePreference2 =
                        this.mSystemRebootDescriptionPreference;
                StringBuilder m =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                str);
                m.append(getString(R.string.galaxy_system_reboot_tablet_info));
                secUnclickablePreference2.setTitle(m.toString());
            } else {
                SecUnclickablePreference secUnclickablePreference3 =
                        this.mSystemRebootDescriptionPreference;
                StringBuilder m2 =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                str);
                m2.append(getString(R.string.galaxy_system_reboot_phone_info));
                secUnclickablePreference3.setTitle(m2.toString());
            }
        } else {
            secUnclickablePreference.setVisible(false);
        }
        if (isGalaxyDeeplinkSupported()) {
            SecButtonPreference secButtonPreference = new SecButtonPreference(getContext());
            this.mSystemUpdate = secButtonPreference;
            secButtonPreference.setLayoutResource(R.layout.sec_galaxy_system_update_loading);
            getPreferenceScreen().addPreference(this.mSystemUpdate);
            this.mSystemUpdate.mOnClickListener =
                    new View.OnClickListener() { // from class:
                        // com.samsung.android.settings.galaxysystemupdate.GalaxySystemUpdateFragment$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            final GalaxySystemUpdateFragment galaxySystemUpdateFragment =
                                    GalaxySystemUpdateFragment.this;
                            galaxySystemUpdateFragment.setUpdateButtonState(true);
                            new Thread(
                                            new Runnable() { // from class:
                                                // com.samsung.android.settings.galaxysystemupdate.GalaxySystemUpdateFragment$$ExternalSyntheticLambda1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    int i;
                                                    GalaxySystemUpdateFragment
                                                            galaxySystemUpdateFragment2 =
                                                                    GalaxySystemUpdateFragment.this;
                                                    if (galaxySystemUpdateFragment2
                                                            .isGalaxyDeeplinkSupported()) {
                                                        Cursor query =
                                                                galaxySystemUpdateFragment2
                                                                        .getContentResolver()
                                                                        .query(
                                                                                Uri.parse(
                                                                                        "content://com.sec.android.app.samsungapps.systemupdate"),
                                                                                null,
                                                                                null,
                                                                                null);
                                                        if (query != null) {
                                                            try {
                                                                i = query.getCount();
                                                            } catch (Exception unused) {
                                                            } finally {
                                                                query.close();
                                                            }
                                                            Message message = new Message();
                                                            message.what = 1;
                                                            message.arg1 = i;
                                                            galaxySystemUpdateFragment2.mainHandler
                                                                    .sendMessageDelayed(
                                                                            message, 100L);
                                                        }
                                                    }
                                                    i = 0;
                                                    Message message2 = new Message();
                                                    message2.what = 1;
                                                    message2.arg1 = i;
                                                    galaxySystemUpdateFragment2.mainHandler
                                                            .sendMessageDelayed(message2, 100L);
                                                }
                                            })
                                    .start();
                        }
                    };
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
        AlertDialog alertDialog = this.mUpdateInfoDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mUpdateInfoDialog = null;
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        SecRestrictedSwitchPreference secRestrictedSwitchPreference = this.mUseWifiOnlyPreference;
        if (secRestrictedSwitchPreference == null
                || !preference.equals(secRestrictedSwitchPreference)) {
            return false;
        }
        LoggingHelper.insertEventLogging(57011, 57012, booleanValue ? 1L : -1L);
        Settings.Global.putInt(
                getContext().getContentResolver(),
                "galaxy_system_update_use_wifi_only",
                booleanValue ? 1 : -1);
        return true;
    }

    public final void setUpdateButtonState(boolean z) {
        try {
            SecRoundButtonView secRoundButtonView =
                    (SecRoundButtonView)
                            this.mSystemUpdate.mButtonContainer.findViewById(R.id.button);
            if (z) {
                secRoundButtonView.setText(ApnSettings.MVNO_NONE);
                this.mSystemUpdate
                        .mButtonContainer
                        .findViewById(R.id.checking_progress)
                        .setVisibility(0);
            } else {
                secRoundButtonView.setText(
                        getString(R.string.galaxy_system_update_check_for_updates));
                this.mSystemUpdate
                        .mButtonContainer
                        .findViewById(R.id.checking_progress)
                        .setVisibility(8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
