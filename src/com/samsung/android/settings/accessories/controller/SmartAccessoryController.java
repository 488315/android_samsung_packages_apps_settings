package com.samsung.android.settings.accessories.controller;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SmartAccessoryController extends BasePreferenceController
        implements PreferenceControllerMixin {
    private static final String GALAXY_APPS_URI_FORMAT =
            "samsungapps://ProductDetail/%s/?source=%s";
    private static final String KEY_SMART_ACCESSORY_SETTINGS = "smart_accessory_settings";
    private SecPreferenceScreen mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.accessories.controller.SmartAccessoryController$1, reason: invalid class name */
    public final class AnonymousClass1 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    public SmartAccessoryController(Context context) {
        this(context, KEY_SMART_ACCESSORY_SETTINGS);
    }

    public static Intent getGalaxyAppsIntent(Context context, String str, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtra("accessoryDataBundle", bundle);
        }
        intent.setData(
                Uri.parse(
                        "samsungapps://ProductDetail/"
                                + str
                                + "/?source="
                                + getStringByLocal(
                                        context, R.string.download_galaxy_friends_app_name, "en")));
        intent.addFlags(335544352);
        return intent;
    }

    private static String getStringByLocal(Context context, int i, String str) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(str));
        return context.createConfigurationContext(configuration).getResources().getString(i);
    }

    private void showDownloadGalaxyFriendsAppDialog() {
        String string =
                this.mContext.getResources().getString(R.string.download_galaxy_friends_app_name);
        String string2 =
                this.mContext
                        .getResources()
                        .getString(R.string.download_galaxy_friends_app_title, string);
        new AlertDialog.Builder(this.mContext)
                .setTitle(string2)
                .setMessage(
                        this.mContext
                                .getResources()
                                .getString(R.string.download_galaxy_friends_app_msg, string))
                .setCancelable(true)
                .setPositiveButton(
                        R.string.monotype_dialog_button,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.accessories.controller.SmartAccessoryController.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                Intent galaxyAppsIntent =
                                        SmartAccessoryController.getGalaxyAppsIntent(
                                                ((AbstractPreferenceController)
                                                                SmartAccessoryController.this)
                                                        .mContext,
                                                "com.samsung.android.mateagent",
                                                null);
                                if (galaxyAppsIntent.resolveActivity(
                                                ((AbstractPreferenceController)
                                                                SmartAccessoryController.this)
                                                        .mContext.getPackageManager())
                                        != null) {
                                    try {
                                        ((AbstractPreferenceController)
                                                        SmartAccessoryController.this)
                                                .mContext.startActivity(galaxyAppsIntent);
                                    } catch (ActivityNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        })
                .setNegativeButton(R.string.cancel, new AnonymousClass1())
                .show();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecPreferenceScreen) preferenceScreen.findPreference(KEY_SMART_ACCESSORY_SETTINGS);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "mate_setting_activation", 0)
                        == 1
                ? 1
                : 3;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (preference.equals(this.mPreference)) {
            if (Utils.hasPackage(this.mContext, "com.samsung.android.mateagent")) {
                Intent className =
                        new Intent()
                                .setClassName(
                                        "com.samsung.android.mateagent",
                                        "com.samsung.android.mateagent.setting.SettingActivity");
                if (Utils.isIntentAvailable(this.mContext, className)) {
                    this.mContext.startActivity(className);
                }
            } else {
                showDownloadGalaxyFriendsAppDialog();
            }
        }
        return super.handlePreferenceTreeClick(preference);
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

    public SmartAccessoryController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
