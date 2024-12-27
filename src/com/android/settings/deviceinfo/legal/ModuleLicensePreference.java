package com.android.settings.deviceinfo.legal;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ModuleInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.picker.features.search.InitialSearchUtils$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ModuleLicensePreference extends Preference {
    public final ModuleInfo mModule;

    public ModuleLicensePreference(Context context, ModuleInfo moduleInfo) {
        super(context);
        this.mModule = moduleInfo;
        setKey(moduleInfo.getPackageName());
        setTitle(moduleInfo.getName());
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        Intent intent = new Intent("android.intent.action.VIEW");
        String packageName = this.mModule.getPackageName();
        int i = ModuleLicenseProvider.$r8$clinit;
        try {
            getContext()
                    .startActivity(
                            intent.setDataAndType(
                                            InitialSearchUtils$$ExternalSyntheticOutline0.m(
                                                    "content",
                                                    "com.android.settings.module_licenses",
                                                    packageName,
                                                    "NOTICE.html"),
                                            "text/html")
                                    .putExtra("android.intent.extra.TITLE", this.mModule.getName())
                                    .addFlags(1)
                                    .addFlags(268435456)
                                    .addCategory("android.intent.category.DEFAULT")
                                    .setPackage("com.android.htmlviewer"));
        } catch (ActivityNotFoundException e) {
            Log.e("ModuleLicensePreference", "Failed to find viewer", e);
            Toast.makeText(getContext(), R.string.settings_license_activity_unavailable, 1).show();
        }
    }
}
