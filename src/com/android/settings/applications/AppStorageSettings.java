package com.android.settings.applications;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.GrantedUriPermission;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.provider.Settings;
import android.util.Log;
import android.util.MutableInt;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.internal.util.Preconditions;
import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.deviceinfo.StorageWizardMoveConfirm;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.applications.StorageStatsSource;
import com.android.settingslib.utils.StringUtil;
import com.android.settingslib.widget.ActionButtonsPreference;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settingslib.applications.AppFileSizeFormatter;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppStorageSettings extends AppInfoWithHeader
        implements View.OnClickListener,
                DialogInterface.OnClickListener,
                LoaderManager.LoaderCallbacks {
    public static String INTERNAL_STORAGE_TEXT = null;
    public Button mButton1;
    public Button mButton2;
    ActionButtonsPreference mButtonsPref;
    public boolean mCacheCleared;
    public VolumeInfo[] mCandidates;
    public Button mChangeStorageButton;
    public ClearCacheObserver mClearCacheObserver;
    public ClearCacheObserver mClearDataObserver;
    public LayoutPreference mClearUri;
    public Button mClearUriButton;
    public boolean mDataCleared;
    public AlertDialog.Builder mDialogBuilder;
    public ApplicationInfo mInfo;
    AppStorageSizesController mSizeController;
    public Preference mStorageUsed;
    public PreferenceCategory mUri;
    public boolean mCanClearData = true;
    public final AnonymousClass3 mHandler =
            new Handler() { // from class: com.android.settings.applications.AppStorageSettings.3
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    AppStorageSettings appStorageSettings = AppStorageSettings.this;
                    if (appStorageSettings.getView() == null) {
                        return;
                    }
                    int i = message.what;
                    if (i != 1) {
                        if (i != 3) {
                            return;
                        }
                        appStorageSettings.mCacheCleared = true;
                        appStorageSettings.updateSize();
                        return;
                    }
                    appStorageSettings.mDataCleared = true;
                    appStorageSettings.mCacheCleared = true;
                    appStorageSettings.getClass();
                    int i2 = message.arg1;
                    String str = appStorageSettings.mAppEntry.info.packageName;
                    appStorageSettings.mButton1.setText(R.string.clear_user_data_text);
                    appStorageSettings.mButton1.setCompoundDrawablesWithIntrinsicBounds(
                            (Drawable) null,
                            appStorageSettings
                                    .getContext()
                                    .getDrawable(R.drawable.sec_app_info_button_clear_storage),
                            (Drawable) null,
                            (Drawable) null);
                    if (i2 != 1) {
                        appStorageSettings.setEnabledClearDataBtn(true);
                        return;
                    }
                    Log.i("AppStorageSettings", "Cleared user data for package : " + str);
                    if ("com.samsung.android.universalswitch".equals(str)) {
                        Intent intent =
                                new Intent("com.samsung.android.universalSwitch.CLEARDATA_ACTION");
                        Log.i("AppStorageSettings", "Sending Broadcast to Universal Switch");
                        appStorageSettings
                                .getActivity()
                                .sendBroadcast(
                                        intent,
                                        "com.samsung.android.permission.universalSwitch_CLEARDATA");
                    }
                    if ("com.samsung.hongbaoassistant".equals(str)) {
                        boolean z =
                                Settings.System.getInt(
                                                appStorageSettings
                                                        .getActivity()
                                                        .getContentResolver(),
                                                "hongbao_assistant",
                                                0)
                                        != 0;
                        if (z) {
                            appStorageSettings
                                    .getActivity()
                                    .startService(
                                            DisplaySettings$$ExternalSyntheticOutline0.m(
                                                    "com.samsung.hongbaoassistant",
                                                    "com.samsung.hongbaoassistant.HongbaoNotificationService"));
                        }
                        Log.i(
                                "AppStorageSettings",
                                "isHongbaoAssistantOn = "
                                        + z
                                        + ", Restart HongbaoAssistant if true.");
                    }
                    appStorageSettings.updateSize();
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ClearCacheObserver extends IPackageDataObserver.Stub {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AppStorageSettings this$0;

        public /* synthetic */ ClearCacheObserver(AppStorageSettings appStorageSettings, int i) {
            this.$r8$classId = i;
            this.this$0 = appStorageSettings;
        }

        public final void onRemoveCompleted(String str, boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    Message obtainMessage = obtainMessage(3);
                    obtainMessage.arg1 = z ? 1 : 2;
                    sendMessage(obtainMessage);
                    break;
                default:
                    Message obtainMessage2 = obtainMessage(1);
                    obtainMessage2.arg1 = z ? 1 : 2;
                    sendMessage(obtainMessage2);
                    break;
            }
        }
    }

    /* renamed from: -$$Nest$minitiateClearUserData, reason: not valid java name */
    public static void m691$$Nest$minitiateClearUserData(AppStorageSettings appStorageSettings) {
        boolean z = false;
        appStorageSettings.mMetricsFeatureProvider.action(
                appStorageSettings.getContext(), 876, new Pair[0]);
        appStorageSettings.setEnabledClearDataBtn(false);
        String str = appStorageSettings.mAppEntry.info.packageName;
        DynamicDenylistManager.getInstance(appStorageSettings.getContext())
                .resetDenylistIfNeeded(str, false);
        Log.i("AppStorageSettings", "Clearing user data for package : " + str);
        if (appStorageSettings.mClearDataObserver == null) {
            appStorageSettings.mClearDataObserver = new ClearCacheObserver(appStorageSettings, 1);
        }
        try {
            z =
                    ((ActivityManager)
                                    appStorageSettings.getActivity().getSystemService("activity"))
                            .clearApplicationUserData(str, appStorageSettings.mClearDataObserver);
        } catch (SecurityException e) {
            Log.i("AppStorageSettings", "Failed to clear application user data: " + e);
        }
        if (!z) {
            Log.i("AppStorageSettings", "Couldn't clear application user data for package:" + str);
            appStorageSettings.showDialogInner$1(2);
            return;
        }
        appStorageSettings.mButton1.setText(R.string.recompute_size);
        appStorageSettings.mButton1.setCompoundDrawablesWithIntrinsicBounds(
                (Drawable) null,
                appStorageSettings
                        .getContext()
                        .getDrawable(R.drawable.sec_app_info_button_manage_storage),
                (Drawable) null,
                (Drawable) null);
        if (appStorageSettings.getView() != null) {
            appStorageSettings
                    .getView()
                    .announceForAccessibility(
                            appStorageSettings.getString(
                                    R.string.sec_storage_cache_celared,
                                    appStorageSettings.getString(R.string.sec_data)));
        }
    }

    public static void setEnableButton$1(Button button, Boolean bool) {
        button.setEnabled(bool.booleanValue());
        button.setAlpha(bool.booleanValue() ? 1.0f : 0.4f);
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        if (i == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.P.mMessage = getActivity().getText(R.string.clear_data_dlg_text);
            final int i2 = 0;
            builder.setPositiveButton(
                    R.string.dlg_delete,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.applications.AppStorageSettings.1
                        public final /* synthetic */ AppStorageSettings this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            switch (i2) {
                                case 0:
                                    AppStorageSettings.m691$$Nest$minitiateClearUserData(
                                            this.this$0);
                                    break;
                                default:
                                    AppStorageSettings appStorageSettings = this.this$0;
                                    String str = AppStorageSettings.INTERNAL_STORAGE_TEXT;
                                    appStorageSettings.setEnabledClearDataBtn(false);
                                    this.this$0.setIntentAndFinish(false);
                                    break;
                            }
                        }
                    });
            builder.setNegativeButton(R.string.dlg_cancel, (DialogInterface.OnClickListener) null);
            return builder.create();
        }
        if (i != 2) {
            return null;
        }
        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
        CharSequence text = getActivity().getText(R.string.clear_user_data_text);
        AlertController.AlertParams alertParams = builder2.P;
        alertParams.mTitle = text;
        alertParams.mMessage = getActivity().getText(R.string.clear_failed_dlg_text);
        final int i3 = 1;
        builder2.setNeutralButton(
                R.string.dlg_ok,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.android.settings.applications.AppStorageSettings.1
                    public final /* synthetic */ AppStorageSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i32) {
                        switch (i3) {
                            case 0:
                                AppStorageSettings.m691$$Nest$minitiateClearUserData(this.this$0);
                                break;
                            default:
                                AppStorageSettings appStorageSettings = this.this$0;
                                String str = AppStorageSettings.INTERNAL_STORAGE_TEXT;
                                appStorageSettings.setEnabledClearDataBtn(false);
                                this.this$0.setIntentAndFinish(false);
                                break;
                        }
                    }
                });
        return builder2.create();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.storage_label;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 19;
    }

    public void handleClearCacheClick() {
        LoggingHelper.insertEventLogging(19, 3864);
        if (this.mAppsControlDisallowedAdmin != null && !this.mAppsControlDisallowedBySystem) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                    getActivity(), this.mAppsControlDisallowedAdmin);
            return;
        }
        if (this.mClearCacheObserver == null) {
            this.mClearCacheObserver = new ClearCacheObserver(this, 0);
        }
        this.mMetricsFeatureProvider.action(getContext(), 877, new Pair[0]);
        this.mPm.deleteApplicationCacheFiles(this.mPackageName, this.mClearCacheObserver);
        if (getView() != null) {
            getView()
                    .announceForAccessibility(
                            getString(
                                    R.string.sec_storage_cache_celared,
                                    getString(R.string.sec_storage_cache)));
        }
    }

    public void handleClearDataClick() {
        LoggingHelper.insertEventLogging(19, 3863);
        if (this.mAppsControlDisallowedAdmin != null && !this.mAppsControlDisallowedBySystem) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                    getActivity(), this.mAppsControlDisallowedAdmin);
            return;
        }
        ApplicationsState.AppEntry appEntry = this.mAppEntry;
        if (appEntry == null || appEntry.info.manageSpaceActivityName == null) {
            showDialogInner$1(1);
        } else {
            if (ActivityManager.isUserAMonkey()) {
                return;
            }
            Intent intent = new Intent("android.intent.action.VIEW");
            ApplicationInfo applicationInfo = this.mAppEntry.info;
            intent.setClassName(
                    applicationInfo.packageName, applicationInfo.manageSpaceActivityName);
            startActivityForResult(intent, 2);
        }
    }

    public final void initMoveDialog() {
        Preference preference;
        Preference preference2;
        ApplicationsState.AppEntry appEntry = this.mAppEntry;
        if (appEntry == null || appEntry.info == null) {
            Log.e("AppStorageSettings", "initMoveDialog : mAppEntry or mAppEntry.info is null");
            return;
        }
        FragmentActivity activity = getActivity();
        StorageManager storageManager =
                (StorageManager) activity.getSystemService(StorageManager.class);
        List packageCandidateVolumes =
                activity.getPackageManager().getPackageCandidateVolumes(this.mAppEntry.info);
        Log.d("AppStorageSettings", "candidates: " + packageCandidateVolumes.size());
        if (packageCandidateVolumes.size() <= 1 || UserHandle.myUserId() != 0) {
            removePreference("storage_used");
            removePreference("change_storage_button");
            removePreference("storage_space");
            return;
        }
        Collections.sort(packageCandidateVolumes, VolumeInfo.getDescriptionComparator());
        CharSequence[] charSequenceArr = new CharSequence[packageCandidateVolumes.size()];
        int size = packageCandidateVolumes.size();
        StorageVolume[] volumeList = storageManager.getVolumeList();
        this.mCandidates = new VolumeInfo[size];
        int i = -1;
        int i2 = 0;
        for (int i3 = 0; i3 < packageCandidateVolumes.size(); i3++) {
            String bestVolumeDescription =
                    storageManager.getBestVolumeDescription(
                            (VolumeInfo) packageCandidateVolumes.get(i3));
            if (i3 >= volumeList.length || !"usb".equals(volumeList[i3].getSubSystem())) {
                if (!bestVolumeDescription.equals(INTERNAL_STORAGE_TEXT)
                        ? !((preference = this.mStorageUsed) == null
                                || preference.getSummary().equals(INTERNAL_STORAGE_TEXT))
                        : !((preference2 = this.mStorageUsed) == null
                                || !preference2.getSummary().equals(INTERNAL_STORAGE_TEXT))) {
                    i = i3;
                }
                int i4 = i3 - i2;
                charSequenceArr[i4] = bestVolumeDescription;
                this.mCandidates[i4] = (VolumeInfo) packageCandidateVolumes.get(i3);
            } else {
                Log.d("AppStorageSettings", "AppStorage is USB");
                i2++;
                size--;
            }
        }
        if (packageCandidateVolumes.size() == 2 && i2 != 0) {
            Log.d("AppStorageSettings", "only 2 items with USB");
            removePreference("storage_used");
            removePreference("change_storage_button");
            removePreference("storage_space");
            findPreference("storage_space_inset_category").seslSetSubheaderRoundedBackground(0);
            findPreference("storage_category").seslSetSubheaderRoundedBackground(3);
            return;
        }
        CharSequence[] charSequenceArr2 = new CharSequence[size];
        for (int i5 = 0; i5 < size; i5++) {
            charSequenceArr2[i5] = charSequenceArr[i5];
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.change_storage);
        builder.setSingleChoiceItems(charSequenceArr2, i, this);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        this.mDialogBuilder = builder;
    }

    @Override // com.android.settings.applications.AppInfoWithHeader,
              // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setAutoRemoveInsetCategory(false);
        if (getListView() != null) {
            getListView().mDrawLastRoundedCorner = false;
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view == this.mChangeStorageButton && this.mDialogBuilder != null) {
            try {
                AppGlobals.getPackageManager()
                        .checkPackageStartable(this.mPackageName, UserHandle.myUserId());
                LoggingHelper.insertEventLogging(19, 3875);
                this.mDialogBuilder.show();
                return;
            } catch (RemoteException | SecurityException unused) {
            }
        }
        if (view == this.mClearUriButton) {
            if (this.mAppsControlDisallowedAdmin != null && !this.mAppsControlDisallowedBySystem) {
                RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                        getActivity(), this.mAppsControlDisallowedAdmin);
                return;
            }
            FragmentActivity activity = getActivity();
            ((ActivityManager) activity.getSystemService("activity"))
                    .clearGrantedUriPermissions(this.mAppEntry.info.packageName);
            refreshGrantedUriPermissions();
        }
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mCacheCleared = bundle.getBoolean("cache_cleared", false);
            boolean z = bundle.getBoolean("data_cleared", false);
            this.mDataCleared = z;
            this.mCacheCleared = this.mCacheCleared || z;
        }
        getActivity().setTitle(getResources().getString(R.string.storage_settings));
        INTERNAL_STORAGE_TEXT = getActivity().getString(17043178);
        addPreferencesFromResource(R.xml.sec_app_storage_settings);
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final Loader onCreateLoader(int i, Bundle bundle) {
        Context context = getContext();
        return new FetchPackageStorageAsyncLoader(
                context, new StorageStatsSource(context), this.mInfo, UserHandle.of(this.mUserId));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mSizeController =
                new AppStorageSizesController(
                        (Preference) Preconditions.checkNotNull(findPreference("total_size")),
                        (Preference) Preconditions.checkNotNull(findPreference("app_size")),
                        (Preference) Preconditions.checkNotNull(findPreference("data_size")),
                        (Preference) Preconditions.checkNotNull(findPreference("cache_size")));
        this.mButtonsPref = (ActionButtonsPreference) findPreference("header_view");
        this.mStorageUsed = findPreference("storage_used");
        LayoutPreference layoutPreference =
                (LayoutPreference) findPreference("change_storage_button");
        if (layoutPreference != null) {
            Button button = (Button) layoutPreference.mRootView.findViewById(R.id.button);
            this.mChangeStorageButton = button;
            button.setText(R.string.change);
            this.mChangeStorageButton.setOnClickListener(this);
        }
        Log.d("AppStorageSettings", "initBottomButtonBar");
        ViewGroup viewGroup2 = (ViewGroup) getActivity().findViewById(R.id.button_bar);
        if (viewGroup2 != null) {
            viewGroup2.setVisibility(0);
            View inflate =
                    getLayoutInflater()
                            .inflate(
                                    R.layout.sec_bottom_bar_preference,
                                    (ViewGroup) getView(),
                                    true);
            ((LinearLayout) inflate.findViewById(R.id.bottom_bar))
                    .setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            this.mButton1 = (Button) inflate.findViewById(R.id.button1);
            this.mButton2 = (Button) inflate.findViewById(R.id.button2);
            this.mButton1.setVisibility(0);
            this.mButton2.setVisibility(0);
            viewGroup2.removeAllViews();
            ((RelativeLayout) viewGroup2).addView(inflate);
        } else {
            Log.e("AppStorageSettings", "initBottomButtonBar buttonBar null");
        }
        String string = getActivity().getString(R.string.storage_settings);
        Preference preference = this.mStorageUsed;
        if (preference != null) {
            preference.setTitle(
                    getActivity().getString(R.string.sec_used_template, new Object[] {string}));
        }
        this.mButton2.setText(R.string.clear_cache_btn_text);
        this.mButton2.setCompoundDrawablesWithIntrinsicBounds(
                (Drawable) null,
                getContext().getDrawable(R.drawable.sec_app_info_button_clear),
                (Drawable) null,
                (Drawable) null);
        this.mButton2.semSetButtonShapeEnabled(true);
        PreferenceCategory preferenceCategory = (PreferenceCategory) findPreference("uri_category");
        this.mUri = preferenceCategory;
        LayoutPreference layoutPreference2 =
                (LayoutPreference) preferenceCategory.findPreference("clear_uri_button");
        this.mClearUri = layoutPreference2;
        Button button2 = (Button) layoutPreference2.mRootView.findViewById(R.id.button);
        this.mClearUriButton = button2;
        button2.setText(R.string.clear_uri_btn_text);
        this.mClearUriButton.setOnClickListener(this);
        initMoveDialog();
        refreshUi();
        setAutoRemoveInsetCategory(false);
        return onCreateView;
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoadFinished(Loader loader, Object obj) {
        StorageStatsSource.AppStorageStats appStorageStats =
                (StorageStatsSource.AppStorageStats) obj;
        AppStorageSizesController appStorageSizesController = this.mSizeController;
        appStorageSizesController.mLastResult = appStorageStats;
        appStorageSizesController.mLastResultFailed = appStorageStats == null;
        updateUiWithSize(appStorageStats);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x006b, code lost:

       if (r8 != false) goto L20;
    */
    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            r12 = this;
            super.onResume()
            java.lang.String r0 = "false"
            java.lang.String[] r0 = new java.lang.String[]{r0}
            androidx.fragment.app.FragmentActivity r1 = r12.getActivity()
            java.lang.String r2 = "content://com.sec.knox.provider/RestrictionPolicy3"
            java.lang.String r3 = "isSDCardMoveAllowed"
            int r0 = com.android.settings.Utils.getEnterprisePolicyEnabled(r1, r2, r3, r0)
            java.lang.String r1 = "isSDCardMoveAllowed: "
            java.lang.String r2 = "AppStorageSettings"
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m1m(r0, r1, r2)
            r1 = -1
            if (r0 == r1) goto L72
            android.widget.Button r1 = r12.mChangeStorageButton
            r3 = 1
            r4 = 0
            if (r0 != r3) goto L6e
            int r0 = android.os.UserHandle.getCallingUserId()
            boolean r0 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r0)
            if (r0 != 0) goto L6e
            androidx.fragment.app.FragmentActivity r0 = r12.getActivity()
            android.os.UserManager r5 = android.os.UserManager.get(r0)
            java.util.List r5 = r5.getUserProfiles()
            int r6 = r5.size()
            r8 = r3
            r7 = r4
        L41:
            if (r7 >= r6) goto L6b
            java.lang.Object r9 = r5.get(r7)
            android.os.UserHandle r9 = (android.os.UserHandle) r9
            int r10 = r9.getIdentifier()
            boolean r10 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r10)
            if (r10 == 0) goto L68
            android.content.pm.PackageManager r10 = r0.getPackageManager()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            java.lang.String r11 = r12.mPackageName     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            int r9 = r9.getIdentifier()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            r10.getPackageInfoAsUser(r11, r4, r9)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L62
            r8 = r4
            goto L68
        L62:
            r9 = move-exception
            java.lang.String r10 = "Could not find package"
            android.util.Log.e(r2, r10, r9)
        L68:
            int r7 = r7 + 1
            goto L41
        L6b:
            if (r8 == 0) goto L6e
            goto L6f
        L6e:
            r3 = r4
        L6f:
            r1.setEnabled(r3)
        L72:
            r12.updateSize()
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.applications.AppStorageSettings.onResume():void");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("cache_cleared", this.mCacheCleared);
        bundle.putBoolean("data_cleared", this.mDataCleared);
    }

    public final void refreshGrantedUriPermissions() {
        for (int preferenceCount = this.mUri.getPreferenceCount() - 1;
                preferenceCount >= 0;
                preferenceCount--) {
            Preference preference = this.mUri.getPreference(preferenceCount);
            if (preference != this.mClearUri) {
                this.mUri.removePreference(preference);
            }
        }
        List list =
                ((ActivityManager) getActivity().getSystemService("activity"))
                        .getGrantedUriPermissions(this.mAppEntry.info.packageName)
                        .getList();
        if (list.isEmpty()) {
            this.mClearUriButton.setVisibility(8);
            this.mUri.setVisible(false);
            this.mClearUri.setVisible(false);
            return;
        }
        PackageManager packageManager = getActivity().getPackageManager();
        TreeMap treeMap = new TreeMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ProviderInfo resolveContentProvider =
                    packageManager.resolveContentProvider(
                            ((GrantedUriPermission) it.next()).uri.getAuthority(), 0);
            if (resolveContentProvider != null) {
                CharSequence loadLabel =
                        resolveContentProvider.applicationInfo.loadLabel(packageManager);
                MutableInt mutableInt = (MutableInt) treeMap.get(loadLabel);
                if (mutableInt == null) {
                    treeMap.put(loadLabel, new MutableInt(1));
                } else {
                    mutableInt.value++;
                }
            }
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            int i = ((MutableInt) entry.getValue()).value;
            Preference preference2 = new Preference(getPrefContext());
            preference2.setTitle((CharSequence) entry.getKey());
            preference2.setSummary(
                    StringUtil.getIcuPluralsString(
                            this.mUri.getContext(), i, R.string.uri_permissions_text));
            preference2.setSelectable(false);
            preference2.setSelectable(false);
            preference2.setLayoutResource(R.layout.sec_horizontal_preference);
            preference2.setOrder(0);
            Log.v("AppStorageSettings", "Adding preference '" + preference2 + "' at order 0");
            this.mUri.addPreference(preference2);
        }
        if (this.mAppsControlDisallowedBySystem) {
            this.mClearUriButton.setEnabled(false);
        }
        this.mClearUri.setOrder(0);
        this.mClearUriButton.setVisibility(0);
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        retrieveAppEntry$1();
        if (this.mAppEntry == null) {
            return false;
        }
        updateUiWithSize(this.mSizeController.mLastResult);
        refreshGrantedUriPermissions();
        Preference preference = this.mStorageUsed;
        if (preference != null) {
            preference.setSummary(
                    (this.mAppEntry.info.flags & 262144) != 0
                            ? getActivity().getString(R.string.storage_type_external)
                            : INTERNAL_STORAGE_TEXT);
        }
        initMoveDialog();
        ApplicationsState.AppEntry appEntry = this.mAppEntry;
        if (appEntry != null) {
            boolean z = appEntry.info.manageSpaceActivityName != null;
            boolean z2 =
                    (this.mAppEntry.info.flags & 65) == 1
                            || this.mDpm.packageHasActiveAdmins(this.mPackageName);
            Intent intent = new Intent("android.intent.action.VIEW");
            if (z) {
                ApplicationInfo applicationInfo = this.mAppEntry.info;
                intent.setClassName(
                        applicationInfo.packageName, applicationInfo.manageSpaceActivityName);
            }
            boolean z3 = getPackageManager().resolveActivity(intent, 0) != null;
            if ((z || !z2) && z3) {
                this.mButton1.setText(R.string.clear_user_data_text);
                this.mButton1.setCompoundDrawablesWithIntrinsicBounds(
                        (Drawable) null,
                        getContext().getDrawable(R.drawable.sec_app_info_button_clear_storage),
                        (Drawable) null,
                        (Drawable) null);
                this.mButton1.setOnClickListener(
                        new AppStorageSettings$$ExternalSyntheticLambda0(this, 2));
                this.mButton1.semSetButtonShapeEnabled(true);
            } else {
                this.mButton1.setText(R.string.clear_user_data_text);
                this.mButton1.setCompoundDrawablesWithIntrinsicBounds(
                        (Drawable) null,
                        getContext().getDrawable(R.drawable.sec_app_info_button_clear_storage),
                        (Drawable) null,
                        (Drawable) null);
                this.mButton1.semSetButtonShapeEnabled(true);
                setEnabledClearDataBtn(false);
                this.mCanClearData = false;
            }
            if (this.mAppsControlDisallowedBySystem) {
                setEnabledClearDataBtn(false);
            } else if ("com.samsung.android.themecenter".equals(this.mAppEntry.info.packageName)
                    || "com.samsung.android.localeoverlaymanager"
                            .equals(this.mAppEntry.info.packageName)) {
                setEnabledClearDataBtn(false);
            }
            if ("com.wssyncmldm".equals(this.mAppEntry.info.packageName)
                    || "com.ws.dm".equals(this.mAppEntry.info.packageName)) {
                setEnabledClearDataBtn(false);
                Button button = this.mButton1;
                Boolean bool = Boolean.FALSE;
                setEnableButton$1(button, bool);
                setEnableButton$1(this.mButton2, bool);
            }
            if ("com.sec.android.app.setupwizardlegalprovider"
                    .equals(this.mAppEntry.info.packageName)) {
                setEnabledClearDataBtn(false);
            }
        }
        return true;
    }

    public final void setEnabledClearDataBtn(boolean z) {
        if ("com.samsung.android.themecenter".equals(this.mAppEntry.info.packageName)
                || "com.samsung.android.localeoverlaymanager"
                        .equals(this.mAppEntry.info.packageName)) {
            setEnableButton$1(this.mButton1, Boolean.FALSE);
        } else {
            setEnableButton$1(this.mButton1, Boolean.valueOf(z));
        }
    }

    public final void updateSize() {
        try {
            this.mInfo = getPackageManager().getApplicationInfo(this.mPackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AppStorageSettings", "Could not find package", e);
        }
        if (this.mInfo == null) {
            return;
        }
        getLoaderManager().restartLoader(1, Bundle.EMPTY, this);
    }

    public void updateUiWithSize(StorageStatsSource.AppStorageStats appStorageStats) {
        ApplicationsState.AppEntry appEntry = this.mAppEntry;
        if (appEntry == null || appEntry.info == null) {
            Log.e("AppStorageSettings", "updateUiWithSize : mAppEntry or mAppEntry.info is null");
            return;
        }
        if (this.mCacheCleared) {
            this.mSizeController.mCachedCleared = true;
        }
        if (this.mDataCleared) {
            this.mSizeController.mDataCleared = true;
        }
        AppStorageSizesController appStorageSizesController = this.mSizeController;
        Context context = getContext();
        StorageStatsSource.AppStorageStats appStorageStats2 = appStorageSizesController.mLastResult;
        Preference preference = appStorageSizesController.mTotalSize;
        Preference preference2 = appStorageSizesController.mCacheSize;
        Preference preference3 = appStorageSizesController.mDataSize;
        Preference preference4 = appStorageSizesController.mAppSize;
        if (appStorageStats2 == null) {
            int i =
                    appStorageSizesController.mLastResultFailed
                            ? appStorageSizesController.mError
                            : appStorageSizesController.mComputing;
            preference4.setSummary(i);
            preference3.setSummary(i);
            preference2.setSummary(i);
            preference.setSummary(i);
        } else {
            long appBytes =
                    ((StorageStatsSource.AppStorageStatsImpl) appStorageStats2)
                            .mStats.getAppBytes();
            long dataBytes =
                    appStorageSizesController.mDataCleared
                            ? 0L
                            : ((StorageStatsSource.AppStorageStatsImpl)
                                                    appStorageSizesController.mLastResult)
                                            .mStats.getDataBytes()
                                    - ((StorageStatsSource.AppStorageStatsImpl)
                                                    appStorageSizesController.mLastResult)
                                            .mStats.getCacheBytes();
            if (appStorageSizesController.mLastCodeSize != appBytes) {
                appStorageSizesController.mLastCodeSize = appBytes;
                preference4.setSummary(AppFileSizeFormatter.formatFileSize(0, appBytes, context));
            }
            if (appStorageSizesController.mLastDataSize != dataBytes) {
                appStorageSizesController.mLastDataSize = dataBytes;
                preference3.setSummary(AppFileSizeFormatter.formatFileSize(0, dataBytes, context));
            }
            long cacheBytes =
                    (appStorageSizesController.mDataCleared
                                    || appStorageSizesController.mCachedCleared)
                            ? 0L
                            : ((StorageStatsSource.AppStorageStatsImpl)
                                            appStorageSizesController.mLastResult)
                                    .mStats.getCacheBytes();
            if (appStorageSizesController.mLastCacheSize != cacheBytes) {
                appStorageSizesController.mLastCacheSize = cacheBytes;
                preference2.setSummary(AppFileSizeFormatter.formatFileSize(0, cacheBytes, context));
            }
            long j = appBytes + dataBytes + cacheBytes;
            if (appStorageSizesController.mLastTotalSize != j) {
                appStorageSizesController.mLastTotalSize = j;
                preference.setSummary(AppFileSizeFormatter.formatFileSize(0, j, context));
            }
        }
        if (appStorageStats == null) {
            setEnabledClearDataBtn(false);
            setEnableButton$1(this.mButton2, Boolean.FALSE);
        } else {
            StorageStatsSource.AppStorageStatsImpl appStorageStatsImpl =
                    (StorageStatsSource.AppStorageStatsImpl) appStorageStats;
            long cacheBytes2 = appStorageStatsImpl.mStats.getCacheBytes();
            if (appStorageStatsImpl.mStats.getDataBytes() - cacheBytes2 <= 0
                    || !this.mCanClearData
                    || this.mDataCleared) {
                setEnabledClearDataBtn(false);
            } else {
                setEnabledClearDataBtn(this.mAppEntry.info.enabled);
                this.mButton1.setOnClickListener(
                        new AppStorageSettings$$ExternalSyntheticLambda0(this, 0));
            }
            if (cacheBytes2 <= 0 || this.mCacheCleared) {
                setEnableButton$1(this.mButton2, Boolean.FALSE);
            } else {
                setEnableButton$1(this.mButton2, Boolean.TRUE);
                this.mButton2.setOnClickListener(
                        new AppStorageSettings$$ExternalSyntheticLambda0(this, 1));
            }
        }
        if (this.mAppsControlDisallowedBySystem
                || AppUtils.isMainlineModule(this.mPm, this.mPackageName)) {
            setEnabledClearDataBtn(false);
            setEnableButton$1(this.mButton2, Boolean.FALSE);
        }
        if (Utils.getEnterprisePolicyEnabled(
                        getActivity().getApplicationContext(),
                        "content://com.sec.knox.provider2/ApplicationPolicy",
                        "isApplicationClearCacheDisabled",
                        new String[] {
                            this.mPackageName,
                            String.valueOf(UserHandle.getCallingUserId()),
                            "false"
                        })
                == 1) {
            setEnableButton$1(this.mButton2, Boolean.FALSE);
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        FragmentActivity activity = getActivity();
        VolumeInfo volumeInfo = this.mCandidates[i];
        activity.getPackageManager().getPackageCurrentVolume(this.mAppEntry.info);
        if (!((StorageManager) getContext().getSystemService(StorageManager.class))
                .getBestVolumeDescription(volumeInfo)
                .equals(INTERNAL_STORAGE_TEXT)) {
            Preference preference = this.mStorageUsed;
            if (preference != null && preference.getSummary().equals(INTERNAL_STORAGE_TEXT)) {
                Log.d("AppStorageSettings", "internal storage => external storage");
                Intent intent = new Intent(activity, (Class<?>) StorageWizardMoveConfirm.class);
                intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
                intent.putExtra(
                        "android.intent.extra.PACKAGE_NAME", this.mAppEntry.info.packageName);
                startActivity(intent);
            }
        } else {
            Preference preference2 = this.mStorageUsed;
            if (preference2 != null && !preference2.getSummary().equals(INTERNAL_STORAGE_TEXT)) {
                Log.d(
                        "AppStorageSettings",
                        "external storage => internal storage : "
                                + ((Object) this.mStorageUsed.getSummary()));
                Intent intent2 = new Intent(activity, (Class<?>) StorageWizardMoveConfirm.class);
                intent2.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
                intent2.putExtra(
                        "android.intent.extra.PACKAGE_NAME", this.mAppEntry.info.packageName);
                startActivity(intent2);
            }
        }
        dialogInterface.dismiss();
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoaderReset(Loader loader) {}

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settingslib.applications.ApplicationsState.Callbacks
    public final void onPackageSizeChanged(String str) {}
}
