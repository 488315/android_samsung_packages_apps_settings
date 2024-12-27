package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MainlineModuleVersionPreferenceController extends BasePreferenceController {
    private static final String TAG = "MainlineModuleControl";
    private String mModuleVersion;
    private final PackageManager mPackageManager;
    static final Intent MODULE_UPDATE_INTENT =
            new Intent("android.settings.MODULE_UPDATE_SETTINGS");
    static final Intent MODULE_UPDATE_V2_INTENT =
            new Intent("android.settings.MODULE_UPDATE_VERSIONS");
    private static final List<String> VERSION_NAME_DATE_PATTERNS =
            Arrays.asList("yyyy-MM-dd", "yyyy-MM");

    public MainlineModuleVersionPreferenceController(Context context, String str) {
        super(context, str);
        this.mPackageManager = this.mContext.getPackageManager();
        String string = this.mContext.getString(R.string.config_mainline_module_update_package);
        MODULE_UPDATE_INTENT.setPackage(string);
        MODULE_UPDATE_V2_INTENT.setPackage(string);
        initModules();
    }

    private void initModules() {
        String string = this.mContext.getString(android.R.string.default_audio_route_category_name);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        try {
            this.mModuleVersion = this.mPackageManager.getPackageInfo(string, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to get mainline version.", e);
            this.mModuleVersion = null;
        }
    }

    private Optional<Date> parseDateFromVersionName(String str) {
        Iterator<String> it = VERSION_NAME_DATE_PATTERNS.iterator();
        while (it.hasNext()) {
            try {
                SimpleDateFormat simpleDateFormat =
                        new SimpleDateFormat(it.next(), Locale.getDefault());
                simpleDateFormat.setTimeZone(TimeZone.getDefault());
                return Optional.of(simpleDateFormat.parse(str));
            } catch (ParseException unused) {
            }
        }
        return Optional.empty();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !TextUtils.isEmpty(this.mModuleVersion) ? 0 : 3;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        if (TextUtils.isEmpty(this.mModuleVersion)) {
            return this.mModuleVersion;
        }
        Optional<Date> parseDateFromVersionName = parseDateFromVersionName(this.mModuleVersion);
        if (parseDateFromVersionName.isPresent()) {
            return DateFormat.format(
                    DateFormat.getBestDateTimePattern(Locale.getDefault(), "dMMMMyyyy"),
                    parseDateFromVersionName.get());
        }
        Log.w("Could not parse mainline versionName (%s) as date.", this.mModuleVersion);
        return this.mModuleVersion;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        PackageManager packageManager = this.mPackageManager;
        Intent intent = MODULE_UPDATE_V2_INTENT;
        if (packageManager.resolveActivity(intent, 0) != null) {
            preference.setIntent(intent);
            preference.setSelectable(true);
            return;
        }
        PackageManager packageManager2 = this.mPackageManager;
        Intent intent2 = MODULE_UPDATE_INTENT;
        if (packageManager2.resolveActivity(intent2, 0) != null) {
            preference.setIntent(intent2);
            preference.setSelectable(true);
        } else {
            Log.d(TAG, "The ResolveInfo of the update intent is null.");
            preference.setIntent(null);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
