package com.android.settings.applications.credentials;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Slog;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.SettingsActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CredentialsPickerActivity extends SettingsActivity {
    @VisibleForTesting
    public static void injectFragmentIntoIntent(Context context, Intent intent) {
        int myUserId = UserHandle.myUserId();
        UserManager userManager = UserManager.get(context);
        int i = DefaultCombinedPickerWork.$r8$clinit;
        if (userManager.isManagedProfile(myUserId)) {
            Slog.d("CredentialsPickerActivity", "Creating picker fragment using work profile");
            intent.putExtra(":settings:show_fragment", DefaultCombinedPickerWork.class.getName());
            return;
        }
        int i2 = DefaultCombinedPickerPrivate.$r8$clinit;
        if (Flags.allowPrivateProfile()
                && android.multiuser.Flags.enablePrivateSpaceFeatures()
                && userManager.isPrivateProfile()) {
            Slog.d("CredentialsPickerActivity", "Creating picker fragment using private profile");
            intent.putExtra(
                    ":settings:show_fragment", DefaultCombinedPickerPrivate.class.getName());
        } else {
            Slog.d("CredentialsPickerActivity", "Creating picker fragment using normal profile");
            intent.putExtra(":settings:show_fragment", DefaultCombinedPicker.class.getName());
        }
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return super.isValidFragment(str)
                || DefaultCombinedPicker.class.getName().equals(str)
                || DefaultCombinedPickerWork.class.getName().equals(str)
                || DefaultCombinedPickerPrivate.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        String callingPackage = getCallingPackage();
        Intent intent = getIntent();
        intent.putExtra("package_name", callingPackage);
        injectFragmentIntoIntent(this, intent);
        super.onCreate(bundle);
    }
}
