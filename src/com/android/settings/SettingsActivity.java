package com.android.settings;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.os.BuildCompat;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.util.ArrayUtils;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.activityembedding.EmbeddedDeepLinkUtils;
import com.android.settings.applications.appinfo.AppButtonsPreferenceController;
import com.android.settings.applications.manageapplications.ManageApplications;
import com.android.settings.core.CategoryMixin;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.core.SettingsBaseActivity;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.gateway.SettingsGateway;
import com.android.settings.homepage.TopLevelSettings;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.PasswordUtils;
import com.android.settings.spa.SpaActivity;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.core.instrumentation.Instrumentable;
import com.android.settingslib.core.instrumentation.SharedPreferencesLogger;
import com.android.settingslib.utils.ThreadUtils;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.Trace;
import com.samsung.android.settings.nfc.NfcSettings;
import com.samsung.android.settings.nfc.PaymentSettings;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class SettingsActivity extends SettingsBaseActivity
        implements PreferenceManager.OnPreferenceTreeClickListener,
                PreferenceFragmentCompat.OnPreferenceStartFragmentCallback,
                ButtonBarHandler,
                FragmentManager.OnBackStackChangedListener {
    public final ArrayList mCategories = new ArrayList();
    public String mFragmentClass;
    public String mHighlightMenuKey;
    public CharSequence mInitialTitle;
    public int mInitialTitleResId;
    public SettingsMainSwitchBar mMainSwitch;
    public Button mNextButton;

    public void createUiFromIntent(Intent intent, Bundle bundle) {
        View findViewById;
        System.currentTimeMillis();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getDashboardFeatureProvider();
        if (intent.hasExtra("settings:ui_options")) {
            getWindow().setUiOptions(intent.getIntExtra("settings:ui_options", 0));
        }
        String initialFragmentName = getInitialFragmentName(intent);
        if (((this instanceof SubSettings)
                        || intent.getBooleanExtra(":settings:show_fragment_as_subsetting", false))
                && !WizardManagerHelper.isAnySetupWizard(getIntent())) {
            setTheme(2132084380);
        }
        ThreadUtils.postOnBackgroundThread(new SettingsActivity$$ExternalSyntheticLambda0(this, 0));
        setContentView(
                (this instanceof Settings.ConnectionsSettingsActivity) ^ true
                        ? R.layout.sec_settings_main_prefs
                        : R.layout.sec_settings_main_prefs_without_switch_bar);
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.mBackStackChangeListeners == null) {
            supportFragmentManager.mBackStackChangeListeners = new ArrayList();
        }
        supportFragmentManager.mBackStackChangeListeners.add(this);
        if (bundle != null) {
            setTitleFromIntent(intent);
            ArrayList parcelableArrayList = bundle.getParcelableArrayList(":settings:categories");
            if (parcelableArrayList != null) {
                this.mCategories.clear();
                this.mCategories.addAll(parcelableArrayList);
                setTitleFromBackStack();
            }
        } else {
            launchSettingFragment(initialFragmentName, intent);
        }
        SettingsMainSwitchBar settingsMainSwitchBar =
                (SettingsMainSwitchBar) findViewById(R.id.switch_bar);
        this.mMainSwitch = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setMetricsCategory(lookupMetricsCategory());
            this.mMainSwitch.setTranslationZ(
                    findViewById(R.id.main_content).getTranslationZ() + 1.0f);
        }
        if (!intent.getBooleanExtra("extra_prefs_show_button_bar", false)
                || (findViewById = findViewById(R.id.button_bar)) == null) {
            return;
        }
        findViewById.setVisibility(0);
        ((RelativeLayout) findViewById)
                .addView(
                        getLayoutInflater()
                                .inflate(
                                        R.layout.sec_widget_settings_main_button_bar,
                                        (ViewGroup) null));
        Button button = (Button) findViewById(R.id.back_button);
        final int i = 0;
        button.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.SettingsActivity$$ExternalSyntheticLambda1
                    public final /* synthetic */ SettingsActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i2 = i;
                        SettingsActivity settingsActivity = this.f$0;
                        switch (i2) {
                            case 0:
                                settingsActivity.setResult(0, null);
                                settingsActivity.finish();
                                break;
                            case 1:
                                settingsActivity.setResult(-1, null);
                                settingsActivity.finish();
                                break;
                            default:
                                settingsActivity.setResult(-1, null);
                                settingsActivity.finish();
                                break;
                        }
                    }
                });
        Button button2 = (Button) findViewById(R.id.skip_button);
        final int i2 = 1;
        button2.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.SettingsActivity$$ExternalSyntheticLambda1
                    public final /* synthetic */ SettingsActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i22 = i2;
                        SettingsActivity settingsActivity = this.f$0;
                        switch (i22) {
                            case 0:
                                settingsActivity.setResult(0, null);
                                settingsActivity.finish();
                                break;
                            case 1:
                                settingsActivity.setResult(-1, null);
                                settingsActivity.finish();
                                break;
                            default:
                                settingsActivity.setResult(-1, null);
                                settingsActivity.finish();
                                break;
                        }
                    }
                });
        Button button3 = (Button) findViewById(R.id.next_button);
        this.mNextButton = button3;
        final int i3 = 2;
        button3.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.SettingsActivity$$ExternalSyntheticLambda1
                    public final /* synthetic */ SettingsActivity f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i22 = i3;
                        SettingsActivity settingsActivity = this.f$0;
                        switch (i22) {
                            case 0:
                                settingsActivity.setResult(0, null);
                                settingsActivity.finish();
                                break;
                            case 1:
                                settingsActivity.setResult(-1, null);
                                settingsActivity.finish();
                                break;
                            default:
                                settingsActivity.setResult(-1, null);
                                settingsActivity.finish();
                                break;
                        }
                    }
                });
        if (intent.hasExtra("extra_prefs_set_next_text")) {
            String stringExtra = intent.getStringExtra("extra_prefs_set_next_text");
            if (TextUtils.isEmpty(stringExtra)) {
                this.mNextButton.setVisibility(8);
            } else {
                this.mNextButton.setText(stringExtra);
            }
        }
        if (intent.hasExtra("extra_prefs_set_back_text")) {
            String stringExtra2 = intent.getStringExtra("extra_prefs_set_back_text");
            if (TextUtils.isEmpty(stringExtra2)) {
                button.setVisibility(8);
            } else {
                button.setText(stringExtra2);
            }
        }
        if (intent.getBooleanExtra("extra_prefs_show_skip", false)) {
            button2.setVisibility(0);
        }
    }

    public final void finishPreferencePanel(Intent intent) {
        setResult(-1, intent);
        if (intent == null
                || !intent.getBooleanExtra(
                        AppButtonsPreferenceController.KEY_REMOVE_TASK_WHEN_FINISHING, false)) {
            finish();
        } else {
            finishAndRemoveTask();
        }
    }

    public final String getInitialCallingPackage() {
        String callingAppPackageName = PasswordUtils.getCallingAppPackageName(getActivityToken());
        if (!TextUtils.equals(callingAppPackageName, getPackageName())) {
            return callingAppPackageName;
        }
        String stringExtra = getIntent().getStringExtra("initial_calling_package");
        return TextUtils.isEmpty(stringExtra) ? callingAppPackageName : stringExtra;
    }

    public String getInitialFragmentName(Intent intent) {
        return intent.getStringExtra(":settings:show_fragment");
    }

    @Override // android.app.Activity
    public Intent getIntent() {
        Intent intent = super.getIntent();
        String str = this.mFragmentClass;
        if (str == null) {
            str = intent.getComponent().getClassName();
            if (str.equals(getClass().getName())
                    || "com.samsung.android.settings.goodsettings.GoodSubSettings".equals(str)) {
                str = null;
            } else if ("com.android.settings.RunningServices".equals(str)
                    || "com.android.settings.applications.StorageUse".equals(str)) {
                str = ManageApplications.class.getName();
            }
        }
        if (str == null) {
            return intent;
        }
        Intent intent2 = new Intent(intent);
        intent2.putExtra(":settings:show_fragment", str);
        Bundle bundleExtra = intent.getBundleExtra(":settings:show_fragment_args");
        Bundle bundle = bundleExtra != null ? new Bundle(bundleExtra) : new Bundle();
        bundle.putParcelable("intent", intent);
        String stringExtra = intent.getStringExtra(":settings:fragment_args_key");
        if (!TextUtils.isEmpty(stringExtra) && !bundle.containsKey(":settings:fragment_args_key")) {
            bundle.putString(":settings:fragment_args_key", stringExtra);
        }
        intent2.putExtra(":settings:show_fragment_args", bundle);
        return intent2;
    }

    public final String getMetricsTag() {
        String initialFragmentName =
                (getIntent() == null || !getIntent().hasExtra(":settings:show_fragment"))
                        ? null
                        : getInitialFragmentName(getIntent());
        if (!TextUtils.isEmpty(initialFragmentName)) {
            return initialFragmentName;
        }
        Log.w("SettingsActivity", "MetricsTag is invalid " + initialFragmentName);
        return getClass().getName();
    }

    @Override // com.android.settings.ButtonBarHandler
    public final Button getNextButton() {
        return this.mNextButton;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final SharedPreferences getSharedPreferences(String str, int i) {
        if (!TextUtils.equals(str, getPackageName() + "_preferences")) {
            return super.getSharedPreferences(str, i);
        }
        String metricsTag = getMetricsTag();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl != null) {
            return new SharedPreferencesLogger(
                    this,
                    metricsTag,
                    featureFactoryImpl.getMetricsFeatureProvider(),
                    lookupMetricsCategory());
        }
        throw new UnsupportedOperationException("No feature factory configured");
    }

    @Override // com.android.settings.ButtonBarHandler
    public final boolean hasNextButton() {
        return this.mNextButton != null;
    }

    public final boolean isActionBarButtonEnabled(Intent intent) {
        if (WizardManagerHelper.isAnySetupWizard(intent)
                || !WizardManagerHelper.isUserSetupComplete(this)
                || "mode_invisible".equals(intent.getStringExtra("settings_homekey_mode"))) {
            return false;
        }
        StringBuilder sb = Utils.sBuilder;
        if (((ActivityManager) getSystemService(ActivityManager.class)).getLockTaskModeState()
                == 1) {
            return false;
        }
        if (ActivityEmbeddingUtils.isAlreadyEmbedded(this)) {
            for (Fragment fragment : getSupportFragmentManager().mFragmentStore.getFragments()) {
                if ((fragment instanceof InstrumentedPreferenceFragment)
                        && TextUtils.equals(
                                TopLevelSettings.class.getName(),
                                ((InstrumentedPreferenceFragment) fragment)
                                        .getHierarchicalParentFragment(this))) {
                    return false;
                }
            }
        }
        boolean booleanExtra = intent.getBooleanExtra(":settings:is_second_layer_page", false);
        int i = BuildCompat.$r8$clinit;
        return !(booleanExtra
                ? ActivityEmbeddingController.getInstance(this).isActivityEmbedded(this)
                : false);
    }

    public boolean isValidFragment(String str) {
        int i = 0;
        while (true) {
            String[] strArr = SettingsGateway.ENTRY_FRAGMENTS;
            if (i >= 206) {
                int i2 = 0;
                while (true) {
                    String[] strArr2 = SettingsGateway.SAMSUNG_ENTRY_FRAGMENTS;
                    if (i2 >= 136) {
                        return false;
                    }
                    if (strArr2[i2].equals(str)) {
                        return true;
                    }
                    i2++;
                }
            } else {
                if (strArr[i].equals(str)) {
                    return true;
                }
                i++;
            }
        }
    }

    public void launchSettingFragment(String str, Intent intent) {
        if (str == null) {
            this.mInitialTitleResId = R.string.dashboard_title;
            switchToFragment(
                    TopLevelSettings.class.getName(),
                    null,
                    false,
                    this.mInitialTitleResId,
                    this.mInitialTitle);
            return;
        }
        Map map = SettingsActivityUtil.FRAGMENT_TO_SPA_DESTINATION_MAP;
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (FeatureFlagUtils.isEnabled(this, "settings_enable_spa")) {
            String str2 = (String) SettingsActivityUtil.FRAGMENT_TO_SPA_DESTINATION_MAP.get(str);
            if (str2 == null) {
                String str3 =
                        (String)
                                SettingsActivityUtil.FRAGMENT_TO_SPA_APP_DESTINATION_PREFIX_MAP.get(
                                        str);
                if (str3 != null) {
                    Uri data = intent.getData();
                    String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                    if (schemeSpecificPart != null) {
                        str2 = str3 + "/" + schemeSpecificPart + "/" + UserHandle.myUserId();
                    }
                }
                str2 = null;
            }
            if (str2 != null) {
                SpaActivity.Companion companion = SpaActivity.Companion;
                SpaActivity.Companion.startSpaActivity(this, str2);
                finish();
                return;
            }
        }
        setTitleFromIntent(intent);
        switchToFragment(
                str,
                intent.getBundleExtra(":settings:show_fragment_args"),
                true,
                this.mInitialTitleResId,
                this.mInitialTitle);
    }

    public final int lookupMetricsCategory() {
        LifecycleOwner targetFragment =
                Utils.getTargetFragment(
                        this,
                        getMetricsTag(),
                        getIntent() != null
                                ? getIntent().getBundleExtra(":settings:show_fragment_args")
                                : null);
        int metricsCategory =
                targetFragment instanceof Instrumentable
                        ? ((Instrumentable) targetFragment).getMetricsCategory()
                        : 0;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                metricsCategory, "MetricsCategory is ", "SettingsActivity");
        return metricsCategory;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        List<Fragment> fragments = getSupportFragmentManager().mFragmentStore.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof OnActivityResultListener) {
                    fragment.onActivityResult(i, i2, intent);
                }
            }
        }
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper
    public void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(R.style.SetupWizardPartnerResource, true);
        super.onApplyThemeResource(theme, i, z);
    }

    @Override // androidx.fragment.app.FragmentManager.OnBackStackChangedListener
    public final void onBackStackChanged() {
        setTitleFromBackStack();
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        boolean isActionBarButtonEnabled = isActionBarButtonEnabled(getIntent());
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(isActionBarButtonEnabled);
            Log.i(
                    "SettingsActivity",
                    "actionBar.setDisplayHomeAsUpEnabled : " + isActionBarButtonEnabled);
            supportActionBar.setHomeButtonEnabled();
            supportActionBar.setDisplayShowTitleEnabled(true);
        }
        super.onConfigurationChanged(configuration);
    }

    @Override // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Bundle bundle2;
        Trace.beginSection("SettingsActivity#onCreate");
        try {
            ActivityInfo activityInfo =
                    getPackageManager().getActivityInfo(getComponentName(), 128);
            if (activityInfo != null && (bundle2 = activityInfo.metaData) != null) {
                this.mFragmentClass = bundle2.getString("com.android.settings.FRAGMENT_CLASS");
                this.mHighlightMenuKey =
                        activityInfo.metaData.getString("com.android.settings.HIGHLIGHT_MENU_KEY");
                if (Flags.walletRoleEnabled()
                        && TextUtils.equals(PaymentSettings.class.getName(), this.mFragmentClass)) {
                    this.mFragmentClass = NfcSettings.class.getName();
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("SettingsActivity", "Cannot get Metadata for: " + getComponentName().toString());
        }
        Intent intent = getIntent();
        if (ActivityEmbeddingUtils.isEmbeddingActivityEnabled(this)) {
            if (!isTaskRoot()) {
                if ((intent.getFlags() & 268435456) == 0) {
                    Log.d(
                            "SettingsActivity",
                            "The activity isn't task root and Intent.FLAG_ACTIVITY_NEW_TASK isn't"
                                + " set");
                } else if (!TextUtils.isEmpty(getCallingPackage())) {
                    Log.d(
                            "SettingsActivity",
                            "The activity isn't task root and Intent.FLAG_ACTIVITY_NEW_TASK is set."
                                + " but, it was called with startActivityForResult().");
                }
            }
            if (intent.getAction() != null) {
                if (intent.resolveActivityInfo(getPackageManager(), 65536).launchMode == 3) {
                    Log.w("SettingsActivity", "launchMode: singleInstance");
                } else if ((intent.getBooleanExtra("is_from_slice", false)
                                || (!(this instanceof SubSettings)
                                        && !intent.getBooleanExtra(
                                                ":settings:show_fragment_as_subsetting", false)
                                        && !intent.getBooleanExtra(
                                                "is_from_settings_homepage", false)
                                        && !TextUtils.equals(
                                                intent.getAction(),
                                                "android.intent.action.CREATE_SHORTCUT")))
                        && EmbeddedDeepLinkUtils.tryStartMultiPaneDeepLink(
                                this, intent, this.mHighlightMenuKey)) {
                    finish();
                    super.onCreate(bundle);
                    Trace.endSection();
                    return;
                }
            }
        }
        super.onCreate(bundle);
        Log.d("SettingsActivity", "Starting onCreate");
        Trace.beginSection("SettingsActivity#createUiFromIntent");
        createUiFromIntent(intent, bundle);
        Trace.endSection();
        Trace.endSection();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mMainSwitch = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.preference.PreferenceFragmentCompat.OnPreferenceStartFragmentCallback
    public void onPreferenceStartFragment(
            PreferenceFragmentCompat preferenceFragmentCompat, Preference preference) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this);
        String fragment = preference.getFragment();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = fragment;
        launchRequest.mArguments = preference.getExtras();
        launchRequest.mSourceMetricsCategory =
                preferenceFragmentCompat instanceof Instrumentable
                        ? ((Instrumentable) preferenceFragmentCompat).getMetricsCategory()
                        : 0;
        subSettingLauncher.setTitleRes(-1, null);
        launchRequest.mTitle = preference.getTitle();
        subSettingLauncher.launch();
    }

    @Override // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        return false;
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        boolean isActionBarButtonEnabled = isActionBarButtonEnabled(getIntent());
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(isActionBarButtonEnabled);
            supportActionBar.setHomeButtonEnabled();
            supportActionBar.setDisplayShowTitleEnabled(true);
        }
        AsyncTask.execute(new SettingsActivity$$ExternalSyntheticLambda0(this, 1));
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        saveState(bundle);
    }

    public void saveState(Bundle bundle) {
        if (this.mCategories.size() > 0) {
            bundle.putParcelableArrayList(":settings:categories", this.mCategories);
        }
    }

    public final boolean setTileEnabled(
            StringBuilder sb, ComponentName componentName, boolean z, boolean z2) {
        boolean z3 = false;
        if (!z2
                && getPackageName().equals(componentName.getPackageName())
                && !ArrayUtils.contains(
                        SettingsGateway.SETTINGS_FOR_RESTRICTED, componentName.getClassName())) {
            z = false;
        }
        PackageManager packageManager = getPackageManager();
        int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentName);
        if ((componentEnabledSetting == 1) != z || componentEnabledSetting == 0) {
            if (z) {
                this.mCategoryMixin.getClass();
                CategoryMixin.sTileDenylist.remove(componentName);
            } else {
                this.mCategoryMixin.getClass();
                CategoryMixin.sTileDenylist.add(componentName);
            }
            packageManager.setComponentEnabledSetting(componentName, z ? 1 : 2, 1);
            z3 = true;
        }
        if (z3) {
            sb.append(componentName.toShortString());
            sb.append(",");
        }
        return z3;
    }

    public final void setTitleFromBackStack() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            int i = this.mInitialTitleResId;
            if (i > 0) {
                setTitle(i);
                return;
            } else {
                setTitle(this.mInitialTitle);
                return;
            }
        }
        BackStackRecord backStackRecord =
                (BackStackRecord)
                        getSupportFragmentManager().mBackStack.get(backStackEntryCount - 1);
        int i2 = backStackRecord.mBreadCrumbTitleRes;
        CharSequence text =
                i2 > 0
                        ? getText(i2)
                        : i2 != 0
                                ? backStackRecord.mManager.mHost.mContext.getText(i2)
                                : backStackRecord.mBreadCrumbTitleText;
        if (text != null) {
            setTitle(text);
        }
    }

    public final void setTitleFromIntent(Intent intent) {
        Log.d("SettingsActivity", "Starting to set activity title");
        int intExtra = intent.getIntExtra(":settings:show_fragment_title_resid", -1);
        if (intExtra > 0) {
            this.mInitialTitle = null;
            this.mInitialTitleResId = intExtra;
            String stringExtra =
                    intent.getStringExtra(":settings:show_fragment_title_res_package_name");
            if (stringExtra != null) {
                try {
                    CharSequence text =
                            createPackageContextAsUser(
                                            stringExtra, 0, new UserHandle(UserHandle.myUserId()))
                                    .getResources()
                                    .getText(this.mInitialTitleResId);
                    this.mInitialTitle = text;
                    setTitle(text);
                    this.mInitialTitleResId = -1;
                    return;
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.w("SettingsActivity", "Could not find package".concat(stringExtra));
                } catch (Resources.NotFoundException unused2) {
                    Log.w(
                            "SettingsActivity",
                            "Could not find title resource in ".concat(stringExtra));
                }
            } else {
                setTitle(this.mInitialTitleResId);
            }
        } else {
            this.mInitialTitleResId = -1;
            CharSequence stringExtra2 = intent.getStringExtra(":settings:show_fragment_title");
            if (stringExtra2 == null) {
                stringExtra2 = getTitle();
            }
            this.mInitialTitle = stringExtra2;
            setTitle(stringExtra2);
        }
        Log.d("SettingsActivity", "Done setting title");
    }

    public final void switchToFragment(
            String str, Bundle bundle, boolean z, int i, CharSequence charSequence) {
        DialogFragment$$ExternalSyntheticOutline0.m(
                "Switching to fragment ", str, "SettingsActivity");
        if (z && !isValidFragment(str)) {
            throw new IllegalArgumentException(
                    AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                            "Invalid fragment for this activity: ", str));
        }
        Fragment targetFragment = Utils.getTargetFragment(this, str, bundle);
        if (targetFragment == null) {
            return;
        }
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.getClass();
        BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
        backStackRecord.replace(R.id.main_content, targetFragment, null);
        if (i > 0) {
            backStackRecord.mBreadCrumbTitleRes = i;
            backStackRecord.mBreadCrumbTitleText = null;
        } else if (charSequence != null) {
            backStackRecord.mBreadCrumbTitleRes = 0;
            backStackRecord.mBreadCrumbTitleText = charSequence;
        }
        backStackRecord.commitInternal(true);
        getSupportFragmentManager().executePendingTransactions();
        Log.d("SettingsActivity", "Executed frag manager pendingTransactions");
    }
}
