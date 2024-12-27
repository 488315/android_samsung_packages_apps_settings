package com.android.settings.print;

import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settings.dashboard.profileselector.UserAdapter;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ProfileSettingsPreferenceFragment extends RestrictedDashboardFragment {
    public View mHeader;

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        final UserAdapter createUserAdapter;
        super.onViewCreated(view, bundle);
        UserManager userManager = (UserManager) getSystemService("user");
        UserHandle managedProfile = Utils.getManagedProfile(userManager);
        if (userManager.getUserInfo(userManager.getUserHandle()).isKnoxWorkspace()
                || !(managedProfile == null
                        || ((ArrayList) Utils.getManagedProfiles(userManager)).size() == 0)) {
            FragmentActivity activity = getActivity();
            int i = UserAdapter.$r8$clinit;
            List<UserHandle> userProfiles = userManager.getUserProfiles();
            if (userProfiles.size() < 2) {
                createUserAdapter = null;
            } else {
                UserHandle userHandle = new UserHandle(UserHandle.myUserId());
                userProfiles.remove(userHandle);
                userProfiles.add(0, userHandle);
                createUserAdapter =
                        UserAdapter.createUserAdapter(userManager, activity, userProfiles);
            }
            if (createUserAdapter != null) {
                View pinnedHeaderView = setPinnedHeaderView(R.layout.spinner_view);
                this.mHeader = pinnedHeaderView;
                final Spinner spinner =
                        (Spinner) pinnedHeaderView.findViewById(R.id.profile_spinner);
                this.mHeader.semSetRoundedCorners(15);
                this.mHeader.semSetRoundedCornerColor(
                        15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
                spinner.setAdapter((SpinnerAdapter) createUserAdapter);
                spinner.setOnItemSelectedListener(
                        new AdapterView.OnItemSelectedListener() { // from class:
                            // com.android.settings.print.ProfileSettingsPreferenceFragment.1
                            /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
                            @Override // android.widget.AdapterView.OnItemSelectedListener
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final void onItemSelected(
                                    android.widget.AdapterView r1,
                                    android.view.View r2,
                                    int r3,
                                    long r4) {
                                /*
                                    r0 = this;
                                    com.android.settings.dashboard.profileselector.UserAdapter r1 = r2
                                    if (r3 < 0) goto L18
                                    java.util.ArrayList r2 = r1.mUserDetails
                                    int r2 = r2.size()
                                    if (r3 < r2) goto Ld
                                    goto L1b
                                Ld:
                                    java.util.ArrayList r1 = r1.mUserDetails
                                    java.lang.Object r1 = r1.get(r3)
                                    com.android.settings.dashboard.profileselector.UserAdapter$UserDetails r1 = (com.android.settings.dashboard.profileselector.UserAdapter.UserDetails) r1
                                    android.os.UserHandle r1 = r1.mUserHandle
                                    goto L1c
                                L18:
                                    r1.getClass()
                                L1b:
                                    r1 = 0
                                L1c:
                                    int r2 = r1.getIdentifier()
                                    int r3 = android.os.UserHandle.myUserId()
                                    if (r2 == r3) goto L5d
                                    com.android.settings.print.ProfileSettingsPreferenceFragment r2 = com.android.settings.print.ProfileSettingsPreferenceFragment.this
                                    androidx.fragment.app.FragmentActivity r2 = r2.getActivity()
                                    android.content.Intent r3 = new android.content.Intent
                                    com.android.settings.print.ProfileSettingsPreferenceFragment r4 = com.android.settings.print.ProfileSettingsPreferenceFragment.this
                                    r4.getClass()
                                    java.lang.String r4 = "android.settings.ACTION_PRINT_SETTINGS"
                                    r3.<init>(r4)
                                    com.android.settings.print.ProfileSettingsPreferenceFragment r4 = com.android.settings.print.ProfileSettingsPreferenceFragment.this
                                    android.content.Context r4 = r4.getContext()
                                    java.lang.String r4 = r4.getPackageName()
                                    android.content.Intent r3 = r3.setPackage(r4)
                                    r4 = 268435456(0x10000000, float:2.5243549E-29)
                                    r3.addFlags(r4)
                                    r4 = 32768(0x8000, float:4.5918E-41)
                                    r3.addFlags(r4)
                                    r2.startActivityAsUser(r3, r1)
                                    android.widget.Spinner r0 = r3
                                    r1 = 0
                                    r0.setSelection(r1)
                                    r2.finish()
                                L5d:
                                    return
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.android.settings.print.ProfileSettingsPreferenceFragment.AnonymousClass1.onItemSelected(android.widget.AdapterView,"
                                            + " android.view.View, int, long):void");
                            }

                            @Override // android.widget.AdapterView.OnItemSelectedListener
                            public final void onNothingSelected(AdapterView adapterView) {}
                        });
            }
        }
    }
}
