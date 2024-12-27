package com.android.settings.development;

import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class OverlayCategoryPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public static final Comparator OVERLAY_INFO_COMPARATOR =
            Comparator.comparingInt(
                    new OverlayCategoryPreferenceController$$ExternalSyntheticLambda2());
    static final String PACKAGE_DEVICE_DEFAULT = "package_device_default";
    public final boolean mAvailable;
    public final String mCategory;
    public final IOverlayManager mOverlayManager;
    public final PackageManager mPackageManager;
    public ListPreference mPreference;

    public OverlayCategoryPreferenceController(
            Context context,
            PackageManager packageManager,
            IOverlayManager iOverlayManager,
            String str) {
        super(context);
        this.mOverlayManager = iOverlayManager;
        this.mPackageManager = packageManager;
        this.mCategory = str;
        this.mAvailable =
                (iOverlayManager == null || ((ArrayList) getOverlayInfos()).isEmpty())
                        ? false
                        : true;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        setPreference((ListPreference) preferenceScreen.findPreference(getPreferenceKey()));
    }

    public final List getOverlayInfos() {
        ArrayList arrayList = new ArrayList();
        try {
            for (OverlayInfo overlayInfo :
                    this.mOverlayManager.getOverlayInfosForTarget(
                            RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME, 0)) {
                if (this.mCategory.equals(overlayInfo.category)) {
                    arrayList.add(overlayInfo);
                }
            }
            arrayList.sort(OVERLAY_INFO_COMPARATOR);
            return arrayList;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return this.mCategory;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mAvailable;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        setOverlay(PACKAGE_DEVICE_DEFAULT);
        updateState(this.mPreference);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        setOverlay((String) obj);
        return true;
    }

    public final void setOverlay(final String str) {
        final String str2 =
                (String)
                        getOverlayInfos().stream()
                                .filter(
                                        new OverlayCategoryPreferenceController$$ExternalSyntheticLambda0())
                                .map(
                                        new OverlayCategoryPreferenceController$$ExternalSyntheticLambda1())
                                .findFirst()
                                .orElse(null);
        if ((PACKAGE_DEVICE_DEFAULT.equals(str) && TextUtils.isEmpty(str2))
                || TextUtils.equals(str, str2)) {
            return;
        }
        new AsyncTask() { // from class:
                          // com.android.settings.development.OverlayCategoryPreferenceController.1
            @Override // android.os.AsyncTask
            public final Object doInBackground(Object[] objArr) {
                try {
                    return OverlayCategoryPreferenceController.PACKAGE_DEVICE_DEFAULT.equals(str)
                            ? Boolean.valueOf(
                                    OverlayCategoryPreferenceController.this.mOverlayManager
                                            .setEnabled(str2, false, 0))
                            : Boolean.valueOf(
                                    OverlayCategoryPreferenceController.this.mOverlayManager
                                            .setEnabledExclusiveInCategory(str, 0));
                } catch (RemoteException | IllegalStateException | SecurityException e) {
                    Log.w("OverlayCategoryPC", "Error enabling overlay.", e);
                    return Boolean.FALSE;
                }
            }

            @Override // android.os.AsyncTask
            public final void onPostExecute(Object obj) {
                OverlayCategoryPreferenceController overlayCategoryPreferenceController =
                        OverlayCategoryPreferenceController.this;
                overlayCategoryPreferenceController.updateState(
                        overlayCategoryPreferenceController.mPreference);
                if (((Boolean) obj).booleanValue()) {
                    return;
                }
                Toast.makeText(
                                ((AbstractPreferenceController)
                                                OverlayCategoryPreferenceController.this)
                                        .mContext,
                                R.string.overlay_toast_failed_to_apply,
                                1)
                        .show();
            }
        }.execute(new Void[0]);
    }

    public void setPreference(ListPreference listPreference) {
        this.mPreference = listPreference;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        String string = this.mContext.getString(R.string.overlay_option_device_default);
        String str = PACKAGE_DEVICE_DEFAULT;
        arrayList.add(PACKAGE_DEVICE_DEFAULT);
        arrayList2.add(string);
        Iterator it = ((ArrayList) getOverlayInfos()).iterator();
        while (it.hasNext()) {
            OverlayInfo overlayInfo = (OverlayInfo) it.next();
            arrayList.add(overlayInfo.packageName);
            try {
                arrayList2.add(
                        this.mPackageManager
                                .getApplicationInfo(overlayInfo.packageName, 0)
                                .loadLabel(this.mPackageManager)
                                .toString());
            } catch (PackageManager.NameNotFoundException unused) {
                arrayList2.add(overlayInfo.packageName);
            }
            if (overlayInfo.isEnabled()) {
                str = (String) AlertController$$ExternalSyntheticOutline0.m(1, arrayList);
                string = (String) AlertController$$ExternalSyntheticOutline0.m(1, arrayList2);
            }
        }
        this.mPreference.setEntries(
                (CharSequence[]) arrayList2.toArray(new String[arrayList2.size()]));
        this.mPreference.mEntryValues =
                (CharSequence[]) arrayList.toArray(new String[arrayList.size()]);
        this.mPreference.setValue(str);
        this.mPreference.setSummary(string);
    }
}
