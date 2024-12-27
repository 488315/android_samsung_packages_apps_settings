package com.android.settings.development;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.ArrayMap;

import com.android.settings.R;
import com.android.settings.applications.defaultapps.DefaultAppPickerFragment;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.settingslib.widget.CandidateInfo;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DevelopmentAppPicker extends DefaultAppPickerFragment
        implements DeveloperOptionAwareMixin {
    public static final AnonymousClass1 sLabelComparator = new AnonymousClass1();
    public boolean mDebuggableOnly;
    public String mPermissionName;
    public String mSelectingApp;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.development.DevelopmentAppPicker$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return Collator.getInstance()
                    .compare(
                            ((DefaultAppInfo) obj).loadLabel(),
                            ((DefaultAppInfo) obj2).loadLabel());
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        ArrayList arrayList = new ArrayList();
        Context context = getContext();
        for (ApplicationInfo applicationInfo : this.mPm.getInstalledApplications(0)) {
            if (applicationInfo.uid != 1000
                    && (!this.mDebuggableOnly
                            || (applicationInfo.flags & 2) != 0
                            || !"user".equals(Build.TYPE))) {
                if (this.mPermissionName != null) {
                    try {
                        String[] strArr =
                                this.mPm.getPackageInfo(applicationInfo.packageName, 4096)
                                        .requestedPermissions;
                        if (strArr != null) {
                            for (String str : strArr) {
                                if (!str.equals(this.mPermissionName)) {}
                            }
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                }
                arrayList.add(
                        new DefaultAppInfo(
                                context, this.mPm, UserHandle.myUserId(), applicationInfo));
                break;
            }
        }
        Collections.sort(arrayList, sLabelComparator);
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        return this.mSelectingApp;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2057;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.development_app_picker;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment,
              // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        this.mPermissionName = arguments.getString("REQUESTING_PERMISSION");
        this.mDebuggableOnly = arguments.getBoolean("DEBUGGABLE");
        this.mSelectingApp = arguments.getString("SELECTING_APP");
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        PackageItemInfo packageItemInfo;
        DefaultAppInfo defaultAppInfo =
                (DefaultAppInfo) ((CandidateInfo) ((ArrayMap) this.mCandidates).get(str));
        Intent intent = new Intent();
        if (defaultAppInfo != null && (packageItemInfo = defaultAppInfo.packageItemInfo) != null) {
            intent.setAction(packageItemInfo.packageName);
        }
        setResult(-1, intent);
        finish();
        return true;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean shouldShowItemNone() {
        return true;
    }
}
