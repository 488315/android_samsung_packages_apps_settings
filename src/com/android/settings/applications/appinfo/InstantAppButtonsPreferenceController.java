package com.android.settings.applications.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.applications.AppStoreUtil;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreateOptionsMenu;
import com.android.settingslib.core.lifecycle.events.OnOptionsItemSelected;
import com.android.settingslib.core.lifecycle.events.OnPrepareOptionsMenu;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InstantAppButtonsPreferenceController extends BasePreferenceController
        implements LifecycleObserver,
                OnCreateOptionsMenu,
                OnPrepareOptionsMenu,
                OnOptionsItemSelected {
    private static final String KEY_INSTANT_APP_BUTTONS = "instant_app_buttons";
    private static final String META_DATA_DEFAULT_URI = "default-url";
    private MenuItem mInstallMenu;
    private String mLaunchUri;
    private final String mPackageName;
    private final AppInfoDashboardFragment mParent;
    private LayoutPreference mPreference;

    public InstantAppButtonsPreferenceController(
            Context context,
            AppInfoDashboardFragment appInfoDashboardFragment,
            String str,
            Lifecycle lifecycle) {
        super(context, KEY_INSTANT_APP_BUTTONS);
        this.mParent = appInfoDashboardFragment;
        this.mPackageName = str;
        this.mLaunchUri = getDefaultLaunchUri();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    private String getDefaultLaunchUri() {
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(this.mPackageName);
        Iterator<ResolveInfo> it = packageManager.queryIntentActivities(intent, 8388736).iterator();
        while (it.hasNext()) {
            Bundle bundle = it.next().activityInfo.metaData;
            if (bundle != null) {
                String string = bundle.getString(META_DATA_DEFAULT_URI);
                if (!TextUtils.isEmpty(string)) {
                    return string;
                }
            }
        }
        return null;
    }

    private void initButtons(View view) {
        Button button = (Button) view.findViewById(R.id.install);
        Button button2 = (Button) view.findViewById(R.id.clear_data);
        Button button3 = (Button) view.findViewById(R.id.launch);
        if (TextUtils.isEmpty(this.mLaunchUri)) {
            button3.setVisibility(8);
            Context context = this.mContext;
            String str = this.mPackageName;
            final Intent appStoreLink =
                    AppStoreUtil.getAppStoreLink(
                            context,
                            (String)
                                    AppStoreUtil.getInstallerPackageNameAndInstallSourceInfo(
                                                    context, str)
                                            .first,
                            str);
            if (appStoreLink != null) {
                final int i = 1;
                button.setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.applications.appinfo.InstantAppButtonsPreferenceController$$ExternalSyntheticLambda0
                            public final /* synthetic */ InstantAppButtonsPreferenceController f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                switch (i) {
                                    case 0:
                                        this.f$0.lambda$initButtons$0(appStoreLink, view2);
                                        break;
                                    default:
                                        this.f$0.lambda$initButtons$1(appStoreLink, view2);
                                        break;
                                }
                            }
                        });
            } else {
                button.setEnabled(false);
            }
        } else {
            button.setVisibility(8);
            final Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setPackage(this.mPackageName);
            intent.setData(Uri.parse(this.mLaunchUri));
            intent.addFlags(268435456);
            final int i2 = 0;
            button3.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.applications.appinfo.InstantAppButtonsPreferenceController$$ExternalSyntheticLambda0
                        public final /* synthetic */ InstantAppButtonsPreferenceController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            switch (i2) {
                                case 0:
                                    this.f$0.lambda$initButtons$0(intent, view2);
                                    break;
                                default:
                                    this.f$0.lambda$initButtons$1(intent, view2);
                                    break;
                            }
                        }
                    });
        }
        button2.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.applications.appinfo.InstantAppButtonsPreferenceController$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        InstantAppButtonsPreferenceController.this.lambda$initButtons$2(view2);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initButtons$0(Intent intent, View view) {
        this.mParent.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initButtons$1(Intent intent, View view) {
        this.mParent.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initButtons$2(View view) {
        showDialog();
    }

    private void showDialog() {
        String str = this.mPackageName;
        InstantAppButtonDialogFragment instantAppButtonDialogFragment =
                new InstantAppButtonDialogFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString("packageName", str);
        instantAppButtonDialogFragment.setArguments(bundle);
        instantAppButtonDialogFragment.setTargetFragment(this.mParent, 0);
        instantAppButtonDialogFragment.show(
                this.mParent.getFragmentManager(), KEY_INSTANT_APP_BUTTONS);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(KEY_INSTANT_APP_BUTTONS);
        this.mPreference = layoutPreference;
        initButtons(layoutPreference.mRootView.findViewById(R.id.instant_app_button_container));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return AppUtils.isInstant(this.mParent.mPackageInfo.applicationInfo) ? 0 : 4;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnCreateOptionsMenu
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (TextUtils.isEmpty(this.mLaunchUri)) {
            return;
        }
        menu.add(0, 3, 2, R.string.install_text).setShowAsAction(0);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnOptionsItemSelected
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 3) {
            return false;
        }
        Context context = this.mContext;
        String str = this.mPackageName;
        Intent appStoreLink =
                AppStoreUtil.getAppStoreLink(
                        context,
                        (String)
                                AppStoreUtil.getInstallerPackageNameAndInstallSourceInfo(
                                                context, str)
                                        .first,
                        str);
        if (appStoreLink == null) {
            return true;
        }
        this.mParent.startActivity(appStoreLink);
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPrepareOptionsMenu
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem findItem = menu.findItem(3);
        this.mInstallMenu = findItem;
        if (findItem != null) {
            Context context = this.mContext;
            String str = this.mPackageName;
            if (AppStoreUtil.getAppStoreLink(
                            context,
                            (String)
                                    AppStoreUtil.getInstallerPackageNameAndInstallSourceInfo(
                                                    context, str)
                                            .first,
                            str)
                    == null) {
                this.mInstallMenu.setEnabled(false);
            }
        }
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

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
