package com.samsung.android.settings.homepage;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticLambda4;
import com.android.settings.widget.HomepagePreferenceLayoutHelper;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.PkgUtils;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.goodsettings.policy.PolicyInfo;
import com.samsung.android.settings.goodsettings.policy.PolicyManager;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.util.SemLog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TopLevelSamsungAccountPreferenceController extends TopLevelPreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    public static final String ACCOUNT_FAMILY_NAME = "account_family_name";
    public static final String ACCOUNT_GIVEN_NAME = "account_given_name";
    public static final String ACCOUNT_NICKNAME = "account_nickname";
    private static final String AUTHORITY = "com.samsung.android.mobileservice.profileProvider";
    private static final String CONTACT_PHOTO_BLOB = "contact_photo_blob";
    public static final String EXTRA_ACCOUNT_FULL_NAME = "extra_info_display_full_name";
    private static final String SAMSUNG_ACCOUNT_TYPE = "com.osp.app.signin";
    private static final Uri SINGLE_URI =
            Uri.parse(
                    "content://com.samsung.android.mobileservice.profileProvider/new_profile_single");
    private static final String TAG = "TopLevelSamsungAccountPreferenceController";
    private ContentObserverImpl mContentObserver;
    private Drawable mDefaultIcon;
    private String mDefaultSummary;
    private String mDefaultSummarySALoggingId;
    private String mDefaultTitle;
    private boolean mHasProfilePicture;
    private Drawable mIcon;
    private Preference mPreference;
    private String mSummary;
    private String mTitle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ContentObserverImpl extends ContentObserver {
        public Handler mUpdateHandler;
        public Runnable mUpdateRunnable;

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            Runnable runnable;
            super.onChange(z);
            Handler handler = this.mUpdateHandler;
            if (handler == null || (runnable = this.mUpdateRunnable) == null) {
                return;
            }
            handler.removeCallbacks(runnable);
            this.mUpdateHandler.postDelayed(this.mUpdateRunnable, 1000L);
        }
    }

    public TopLevelSamsungAccountPreferenceController(Context context, String str) {
        super(context, str);
        this.mHasProfilePicture = false;
    }

    private void getDefault() {
        if (this.mDefaultTitle == null) {
            this.mDefaultTitle = this.mContext.getString(R.string.sec_samsung_account);
        }
        if (this.mDefaultSummary == null) {
            this.mDefaultSummary = getDefaultSummary(this.mContext);
        }
        if (this.mDefaultIcon == null) {
            this.mDefaultIcon =
                    this.mContext.getDrawable(R.drawable.sec_ic_samsung_account_default);
        }
        this.mTitle = this.mDefaultTitle;
        this.mSummary = this.mDefaultSummary;
        this.mIcon = this.mDefaultIcon;
        this.mHasProfilePicture = false;
    }

    private String getDefaultSummary(Context context) {
        if (Rune.FEATURE_INTELLIGENCE_SERVICE) {
            this.mDefaultSummarySALoggingId = "ai_function";
            return context.getString(R.string.sec_samsung_account_summary_for_ai);
        }
        String[] stringArray =
                context.getResources()
                        .getStringArray(
                                Utils.isTablet()
                                        ? R.array.sec_samsung_account_benefit_message_tablet
                                        : R.array.sec_samsung_account_benefit_message_phone);
        String[] stringArray2 =
                context.getResources()
                        .getStringArray(
                                Utils.isTablet()
                                        ? R.array
                                                .sec_samsung_account_benefit_message_tablet_sa_logging
                                        : R.array
                                                .sec_samsung_account_benefit_message_phone_sa_logging);
        SharedPreferences sharedPreferences =
                this.mContext.getSharedPreferences("top_level_samsung_account_prefs", 0);
        int i = sharedPreferences.getInt("benefit_message_index", 0) % stringArray.length;
        sharedPreferences
                .edit()
                .putInt("benefit_message_index", (i + 1) % stringArray.length)
                .apply();
        this.mDefaultSummarySALoggingId = stringArray2[i];
        return stringArray[i];
    }

    private ContentObserver getProfileDataObserver() {
        if (this.mContentObserver == null) {
            Runnable runnable =
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.homepage.TopLevelSamsungAccountPreferenceController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            TopLevelSamsungAccountPreferenceController.this
                                    .lambda$getProfileDataObserver$0();
                        }
                    };
            ContentObserverImpl contentObserverImpl = new ContentObserverImpl(new Handler());
            contentObserverImpl.mUpdateHandler = new Handler();
            contentObserverImpl.mUpdateRunnable = runnable;
            this.mContentObserver = contentObserverImpl;
        }
        return this.mContentObserver;
    }

    private void getSamsungAccountProfile(Context context) {
        PolicyInfo policy;
        StringBuilder sb = Utils.sBuilder;
        this.mTitle = context.getString(R.string.app_name_samsung_account);
        this.mSummary = context.getString(R.string.app_name_samsung_account);
        ContentResolver contentResolver = context.getContentResolver();
        String[] strArr = {CONTACT_PHOTO_BLOB, ACCOUNT_FAMILY_NAME, ACCOUNT_GIVEN_NAME};
        if ((Rune.supportGoodSettings(context) || Rune.isSupportGoodSettingsV2(context))
                && (policy =
                                PolicyManager.getInstance(context)
                                        .getPolicy(
                                                this.mContext,
                                                GoodSettingsContract
                                                        .SAMSUNG_ACCOUNT_POLICY_KEY_SHOW_NICK_NAME))
                        != null
                && TextUtils.equals(policy.mType, GoodSettingsContract.POLICY_TYPE.BOOLEAN)
                && Boolean.parseBoolean(policy.mValue)) {
            strArr =
                    new String[] {
                        CONTACT_PHOTO_BLOB,
                        ACCOUNT_FAMILY_NAME,
                        ACCOUNT_GIVEN_NAME,
                        ACCOUNT_NICKNAME
                    };
        }
        try {
            Cursor query = contentResolver.query(SINGLE_URI, strArr, null, null, null);
            if (query != null) {
                try {
                    if (query.getCount() != 0 && query.moveToFirst()) {
                        byte[] blob = query.getBlob(query.getColumnIndex(CONTACT_PHOTO_BLOB));
                        if (blob != null) {
                            SemLog.i(TAG, "Get profile image successfully");
                            RoundedBitmapDrawable21 roundedBitmapDrawable21 =
                                    new RoundedBitmapDrawable21(
                                            context.getResources(),
                                            BitmapFactory.decodeByteArray(blob, 0, blob.length));
                            roundedBitmapDrawable21.setCircular();
                            this.mIcon = roundedBitmapDrawable21;
                            this.mHasProfilePicture = true;
                        } else {
                            SemLog.i(TAG, "profile image is null");
                        }
                        String title = getTitle(context, query);
                        if (TextUtils.isEmpty(title)) {
                            SemLog.i(TAG, "profile title is null");
                        } else {
                            SemLog.i(TAG, "Get profile title successfully");
                            this.mTitle = title;
                        }
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            SemLog.e(TAG, "Failed to get profile, " + e.getMessage());
        }
    }

    private String getTitle(Context context, Cursor cursor) {
        PolicyInfo policy;
        if ((Rune.supportGoodSettings(context) || Rune.isSupportGoodSettingsV2(context))
                && (policy =
                                PolicyManager.getInstance(context)
                                        .getPolicy(
                                                this.mContext,
                                                GoodSettingsContract
                                                        .SAMSUNG_ACCOUNT_POLICY_KEY_SHOW_NICK_NAME))
                        != null
                && TextUtils.equals(policy.mType, GoodSettingsContract.POLICY_TYPE.BOOLEAN)
                && Boolean.parseBoolean(policy.mValue)) {
            String string = cursor.getString(cursor.getColumnIndex(ACCOUNT_NICKNAME));
            if (!TextUtils.isEmpty(string)) {
                SemLog.i(TAG, "Get profile nick name successfully");
                return string;
            }
            SemLog.i(TAG, "profile nick name are null");
        }
        Bundle extras = cursor.getExtras();
        String string2 =
                (extras == null || !extras.containsKey(EXTRA_ACCOUNT_FULL_NAME))
                        ? null
                        : extras.getString(EXTRA_ACCOUNT_FULL_NAME);
        if (!TextUtils.isEmpty(string2)) {
            SemLog.i(TAG, "Get profile full name successfully");
            return string2;
        }
        SemLog.i(TAG, "profile full name is null");
        String string3 = cursor.getString(cursor.getColumnIndex(ACCOUNT_FAMILY_NAME));
        String string4 = cursor.getString(cursor.getColumnIndex(ACCOUNT_GIVEN_NAME));
        if (TextUtils.isEmpty(string3) && TextUtils.isEmpty(string4)) {
            SemLog.i(TAG, "profile family name and given name are null");
            return null;
        }
        SemLog.i(TAG, "Get profile family name and given name successfully");
        if (string3 == null) {
            string3 = ApnSettings.MVNO_NONE;
        }
        if (string4 == null) {
            string4 = ApnSettings.MVNO_NONE;
        }
        return (isFamilyNameFirst(context)
                        ? AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                string3, " ", string4)
                        : AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                string4, " ", string3))
                .trim();
    }

    private boolean isFamilyNameFirst(Context context) {
        List asList = Arrays.asList("ko", "zh", "ja", "th", "vi", "hu");
        String language =
                context.getResources().getConfiguration().getLocales().get(0).getLanguage();
        Stream stream = asList.stream();
        Objects.requireNonNull(language);
        return stream.anyMatch(new Utils$$ExternalSyntheticLambda4(language));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$getProfileDataObserver$0() {
        if (this.mContext == null || this.mPreference == null) {
            return;
        }
        SemLog.i(TAG, "profile data updated!");
        updatePreference(this.mPreference);
    }

    private boolean samsungAccountExists(Context context) {
        Account[] accountsByType =
                AccountManager.get(context).getAccountsByType("com.osp.app.signin");
        if (accountsByType == null || accountsByType.length <= 0) {
            return false;
        }
        SemLog.d(TAG, "Samsung Account is exist");
        return true;
    }

    private void updatePreference(Preference preference) {
        boolean z;
        getDefault();
        if (samsungAccountExists(this.mContext)) {
            getSamsungAccountProfile(this.mContext);
            z = true;
        } else {
            z = false;
        }
        if (preference instanceof SecHomepageAccountPreference) {
            int i = z ? this.mHasProfilePicture ? 3 : 2 : 1;
            SecHomepageAccountPreference secHomepageAccountPreference =
                    (SecHomepageAccountPreference) preference;
            if (secHomepageAccountPreference.mType != i) {
                secHomepageAccountPreference.mType = i;
                secHomepageAccountPreference.setLayoutResource(
                        i == 3
                                ? R.layout.sec_top_level_account_preference_with_profile_picture
                                : R.layout.sec_top_level_account_preference_default);
            }
            HomepagePreferenceLayoutHelper homepagePreferenceLayoutHelper =
                    secHomepageAccountPreference.mHelper;
            boolean z2 = !z;
            homepagePreferenceLayoutHelper.mUseSummaryPrimaryColor = z2;
            TextView textView = homepagePreferenceLayoutHelper.mSummary;
            if (textView != null) {
                if (z2) {
                    textView.setTextColor(homepagePreferenceLayoutHelper.mSummaryTextPrimaryColor);
                } else {
                    ColorStateList colorStateList =
                            homepagePreferenceLayoutHelper.mSummaryTextColor;
                    if (colorStateList != null) {
                        textView.setTextColor(colorStateList);
                    }
                }
            }
        }
        preference.setTitle(this.mTitle);
        preference.setSummary(this.mSummary);
        preference.setIcon(this.mIcon);
        preference.setDotVisibility(
                !z
                        && Settings.Global.getInt(
                                        this.mContext.getContentResolver(),
                                        "first_launch_samsung_account_menu",
                                        0)
                                == 0);
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        ApplicationInfo applicationInfo;
        Bundle bundle;
        Context context = this.mContext;
        if (!PkgUtils.hasPackage(
                context, context.getString(R.string.config_sec_toplevel_samsung_account_package))) {
            return 3;
        }
        Context context2 = this.mContext;
        StringBuilder sb = Utils.sBuilder;
        try {
            applicationInfo =
                    context2.getPackageManager().getApplicationInfo("com.osp.app.signin", 128);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("Settings", "There isn't Samsung account package.");
            applicationInfo = null;
        }
        float f = 0.0f;
        if (applicationInfo != null && (bundle = applicationInfo.metaData) != null) {
            f = bundle.getFloat("ProfileProviderVersion", 0.0f);
        }
        Log.d("Settings", "Samsung account profile provider version : " + f);
        return f >= 1.0f ? 0 : 3;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController
    public int getRequestCode() {
        return 65536;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            HashMap hashMap = new HashMap();
            if (samsungAccountExists(this.mContext)) {
                hashMap.put("Account", "Logged in");
            } else {
                hashMap.put("Account", "Not logged in");
                hashMap.put("Dot", preference.getDotVisibility() ? "Badge" : "No badge");
                hashMap.put(
                        GoodSettingsContract.POLICY_TYPE.STRING, this.mDefaultSummarySALoggingId);
            }
            SemLog.d(TAG, "SA Logging details : " + hashMap);
            SALogging.insertSALog(Integer.toString(35), "9500", hashMap, 0);
            Intent intent = preference.getIntent();
            if (intent != null) {
                Bundle bundle = new Bundle();
                for (Map.Entry entry : hashMap.entrySet()) {
                    bundle.putString((String) entry.getKey(), (String) entry.getValue());
                }
                intent.putExtra("settings_sign_in_status_custom_dimensions", bundle);
                preference.setIntent(intent);
            }
            Settings.Global.putInt(
                    this.mContext.getContentResolver(), "first_launch_samsung_account_menu", 1);
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        try {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(SINGLE_URI, true, getProfileDataObserver());
        } catch (Exception unused) {
            SemLog.d(TAG, "profileProvider register failed.");
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        try {
            this.mContext.getContentResolver().unregisterContentObserver(getProfileDataObserver());
        } catch (Exception unused) {
            SemLog.d(TAG, "profileProvider unregister failed.");
        }
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updatePreference(preference);
    }

    @Override // com.samsung.android.settings.homepage.TopLevelPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
