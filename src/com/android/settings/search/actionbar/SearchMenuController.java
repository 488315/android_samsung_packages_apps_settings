package com.android.settings.search.actionbar;

import android.view.MenuItem;

import androidx.fragment.app.Fragment;

import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.homepage.SettingsHomepageActivity;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreateOptionsMenu;

import com.samsung.android.settings.core.SecSettingsBaseActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SearchMenuController implements LifecycleObserver, OnCreateOptionsMenu {
    public final Fragment mHost;
    public final boolean mIsEmbeddingActivityEnabled;
    public boolean mKnoxCustomIsProKioskMode = false;
    public final int mPageId;
    public MenuItem mSearchItem;

    public SearchMenuController(Fragment fragment, int i) {
        this.mHost = fragment;
        this.mPageId = i;
        boolean isEmbeddingActivityEnabled =
                ActivityEmbeddingUtils.isEmbeddingActivityEnabled(fragment.getContext());
        this.mIsEmbeddingActivityEnabled = isEmbeddingActivityEnabled;
        if (isEmbeddingActivityEnabled
                && (fragment.getActivity() instanceof SecSettingsBaseActivity)) {
            ((SecSettingsBaseActivity) fragment.getActivity()).mOnConfigurationChangedListener =
                    new SearchMenuController$$ExternalSyntheticLambda1(this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00c1  */
    @Override // com.android.settingslib.core.lifecycle.events.OnCreateOptionsMenu
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreateOptionsMenu(android.view.Menu r8, android.view.MenuInflater r9) {
        /*
            r7 = this;
            androidx.fragment.app.Fragment r9 = r7.mHost
            androidx.fragment.app.FragmentActivity r0 = r9.getActivity()
            r1 = 2132019747(0x7f140a23, float:1.9677838E38)
            java.lang.String r1 = r0.getString(r1)
            int r2 = android.os.UserHandle.getCallingUserId()
            boolean r2 = com.samsung.android.knox.SemPersonaManager.isSecureFolderId(r2)
            r3 = 11
            java.lang.String r4 = "SearchMenuController"
            r5 = 0
            if (r2 == 0) goto L24
            java.lang.String r9 = "isSearchAvailable : Secure folder"
            android.util.Log.i(r4, r9)
        L21:
            r9 = r5
            goto Lb9
        L24:
            boolean r2 = com.google.android.setupcompat.util.WizardManagerHelper.isDeviceProvisioned(r0)
            if (r2 == 0) goto Lb2
            android.content.Intent r2 = r0.getIntent()
            boolean r2 = com.google.android.setupcompat.util.WizardManagerHelper.isAnySetupWizard(r2)
            if (r2 == 0) goto L36
            goto Lb2
        L36:
            boolean r2 = com.android.settings.Utils.isPackageEnabled(r0, r1)
            if (r2 != 0) goto L42
            java.lang.String r9 = "isSearchAvailable : SettingsIntelligence is not enabled"
            android.util.Log.i(r4, r9)
            goto L21
        L42:
            if (r8 != 0) goto L4a
            java.lang.String r9 = "isSearchAvailable : Menu is null"
            android.util.Log.i(r4, r9)
            goto L21
        L4a:
            android.os.Bundle r2 = r9.getArguments()
            if (r2 == 0) goto Lab
            java.lang.String r6 = "need_search_icon_in_action_bar"
            boolean r2 = r2.getBoolean(r6, r5)
            if (r2 != 0) goto L59
            goto Lab
        L59:
            android.view.MenuItem r2 = r8.findItem(r3)
            if (r2 == 0) goto L65
            java.lang.String r9 = "isSearchAvailable() : menu does not contains search item"
            android.util.Log.i(r4, r9)
            goto L21
        L65:
            com.samsung.android.knox.custom.ProKioskManager r2 = com.samsung.android.knox.custom.ProKioskManager.getInstance()
            if (r2 == 0) goto L71
            boolean r2 = r2.getProKioskState()
            r7.mKnoxCustomIsProKioskMode = r2
        L71:
            android.content.Context r9 = r9.getContext()
            boolean r2 = com.samsung.android.settings.knox.KnoxUtils.appRestrictionState
            if (r2 == 0) goto L9a
            if (r9 == 0) goto L80
            com.samsung.android.knox.appconfig.ApplicationRestrictionsManager r9 = com.samsung.android.knox.appconfig.ApplicationRestrictionsManager.getInstance(r9)
            goto L81
        L80:
            r9 = 0
        L81:
            if (r9 == 0) goto L9a
            android.os.Bundle r9 = r9.getApplicationRestrictions(r1, r5)
            java.lang.String r1 = "Settings_Search"
            boolean r2 = r9.containsKey(r1)
            if (r2 == 0) goto L9a
            android.os.Bundle r9 = r9.getBundle(r1)
            java.lang.String r1 = "hide"
            boolean r9 = r9.getBoolean(r1)
            goto L9b
        L9a:
            r9 = r5
        L9b:
            if (r9 != 0) goto La4
            boolean r9 = r7.mKnoxCustomIsProKioskMode
            if (r9 == 0) goto La2
            goto La4
        La2:
            r9 = 1
            goto Lb9
        La4:
            java.lang.String r9 = "isSearchAvailable() : KNOX restriction"
            android.util.Log.i(r4, r9)
            goto L21
        Lab:
            java.lang.String r9 = "isSearchAvailable() : arguments is null or needSearchIconInActionBar is false"
            android.util.Log.i(r4, r9)
            goto L21
        Lb2:
            java.lang.String r9 = "isSearchAvailable : Device is not provisioned or Setup wizard is running"
            android.util.Log.i(r4, r9)
            goto L21
        Lb9:
            if (r9 != 0) goto Lc1
            java.lang.String r7 = "onCreateOptionsMenu(). Not support search menu"
            android.util.Log.w(r4, r7)
            return
        Lc1:
            r9 = 2132025429(0x7f142055, float:1.9689362E38)
            android.view.MenuItem r8 = r8.add(r5, r3, r5, r9)
            r9 = 2131233345(0x7f080a41, float:1.8082825E38)
            r8.setIcon(r9)
            r9 = 2
            r8.setShowAsAction(r9)
            com.android.settings.search.actionbar.SearchMenuController$$ExternalSyntheticLambda0 r9 = new com.android.settings.search.actionbar.SearchMenuController$$ExternalSyntheticLambda0
            r9.<init>()
            r8.setOnMenuItemClickListener(r9)
            boolean r9 = r7.mIsEmbeddingActivityEnabled
            if (r9 == 0) goto Le3
            r7.mSearchItem = r8
            r7.updateSearchItemVisibility()
        Le3:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.search.actionbar.SearchMenuController.onCreateOptionsMenu(android.view.Menu,"
                    + " android.view.MenuInflater):void");
    }

    public final void updateSearchItemVisibility() {
        MenuItem menuItem = this.mSearchItem;
        if (menuItem != null) {
            Fragment fragment = this.mHost;
            menuItem.setVisible(
                    (fragment.getActivity() instanceof SettingsHomepageActivity)
                            || !ActivityEmbeddingUtils.isAlreadyEmbedded(fragment.getActivity()));
        }
    }
}
