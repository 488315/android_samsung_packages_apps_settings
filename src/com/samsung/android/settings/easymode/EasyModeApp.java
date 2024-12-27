package com.samsung.android.settings.easymode;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;
import com.android.settings.DisplaySettings;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settingslib.widget.LayoutPreference;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.widget.SecRelativeLinkView;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EasyModeApp extends SettingsPreferenceFragment {
    public ActivityManager mAm;
    public FragmentActivity mContext;
    public AnonymousClass1 mDesktopModeListener;
    public SemDesktopModeManager mDesktopModeManager;
    public AlertDialog mDialog = null;
    public PreferenceCategory mEasyModeEnableCategory;
    public LayoutPreference mEasyModePreviewPreference;
    public SwitchPreferenceCompat mEasyModeSwitch;
    public EasyModeUtils mEasyModeUtils;
    public FontSettingsInEasyMode mFontSettingsInEasyMode;
    public TextView mHelpTextView;
    public SecSwitchPreferenceScreen mHighContrastKeyboardPreference;
    public String mHighContrastKeyboardValue;
    public LinearLayout mImageContainer;
    public int mModeState;
    public ImageView mPreviewImageView;
    public SecRelativeLinkView mRelativeLinkView;
    public Preference mTouchAndHoldPreference;
    public int mTouchAndHoldValue;

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.easy_mode;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return DisplaySettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4620;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    public final void initPreferences$7() {
        addPreferencesFromResource(R.xml.sec_easy_mode_app_settings);
        ((SecPreferenceCategory) findPreference("no_stroke")).setEmptyHeight();
        this.mAm = (ActivityManager) getSystemService("activity");
        getResources().getBoolean(android.R.bool.config_sms_decode_gsm_8bit_data);
        this.mEasyModePreviewPreference = (LayoutPreference) findPreference("easy_mode_preview");
        this.mEasyModeSwitch = (SwitchPreferenceCompat) findPreference("easy_mode_switch");
        this.mEasyModeEnableCategory = (PreferenceCategory) findPreference("easy_mode_enable_category");
        this.mTouchAndHoldPreference = findPreference("touch_hold_preference");
        this.mHighContrastKeyboardPreference = (SecSwitchPreferenceScreen) findPreference("high_contrast_keyboard_preference");
        SwitchPreferenceCompat switchPreferenceCompat = this.mEasyModeSwitch;
        if (switchPreferenceCompat != null) {
            final int i = 0;
            switchPreferenceCompat.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(this) { // from class: com.samsung.android.settings.easymode.EasyModeApp$$ExternalSyntheticLambda0
                public final /* synthetic */ EasyModeApp f$0;

                {
                    this.f$0 = this;
                }

                /* JADX WARN: Code restructure failed: missing block: B:21:0x0081, code lost:
                
                    r3.close();
                 */
                /* JADX WARN: Code restructure failed: missing block: B:23:0x0085, code lost:
                
                    android.util.Log.i("EasyModeUtils", "Exception caught while closing cursor");
                 */
                /* JADX WARN: Code restructure failed: missing block: B:42:0x0092, code lost:
                
                    if (r3 == null) goto L27;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:8:0x007f, code lost:
                
                    if (r3 != null) goto L94;
                 */
                /* JADX WARN: Removed duplicated region for block: B:48:0x00ed A[EXC_TOP_SPLITTER, SYNTHETIC] */
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final boolean onPreferenceChange(final androidx.preference.Preference r17, java.lang.Object r18) {
                    /*
                        Method dump skipped, instructions count: 852
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.easymode.EasyModeApp$$ExternalSyntheticLambda0.onPreferenceChange(androidx.preference.Preference, java.lang.Object):boolean");
                }
            });
        }
        Preference preference = this.mTouchAndHoldPreference;
        if (preference != null) {
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: com.samsung.android.settings.easymode.EasyModeApp$$ExternalSyntheticLambda2
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference2) {
                    EasyModeApp easyModeApp = EasyModeApp.this;
                    easyModeApp.mContext.getSharedPreferences("display_pref", 0).edit().putBoolean("init_easy_mode_enable_values", false).apply();
                    Intent intent = new Intent();
                    intent.setAction("com.samsung.accessibility.TouchAndHold");
                    intent.putExtra("extra_not_return_to_parent_fragment", true);
                    easyModeApp.getActivity().startActivity(intent);
                    return false;
                }
            });
        }
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mHighContrastKeyboardPreference;
        if (secSwitchPreferenceScreen == null) {
            return;
        }
        final int i2 = 1;
        secSwitchPreferenceScreen.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(this) { // from class: com.samsung.android.settings.easymode.EasyModeApp$$ExternalSyntheticLambda0
            public final /* synthetic */ EasyModeApp f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.preference.Preference.OnPreferenceChangeListener
            public final boolean onPreferenceChange(Preference preference2, Object obj) {
                /*
                    Method dump skipped, instructions count: 852
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.easymode.EasyModeApp$$ExternalSyntheticLambda0.onPreferenceChange(androidx.preference.Preference, java.lang.Object):boolean");
            }
        });
        Intent intent = new Intent();
        intent.setClassName(EasyModeUtils.sSamsungKeypadPackageName, EasyModeUtils.sSamsungKeypadHighContrastSettingsActivity);
        this.mHighContrastKeyboardPreference.setIntent(intent);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initViews$2() {
        /*
            Method dump skipped, instructions count: 274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.easymode.EasyModeApp.initViews$2():void");
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        getPreferenceScreen().removeAll();
        initPreferences$7();
        initViews$2();
        updateTouchAndHold(false);
        updateHighContrastKeyboard(this.mEasyModeUtils.isHighContrastKeyboardOn(), true);
        setLinkedDataView(true);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.i("EasyModeApp", "onCreate");
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        this.mFontSettingsInEasyMode = new FontSettingsInEasyMode(activity);
        EasyModeUtils easyModeUtils = new EasyModeUtils();
        easyModeUtils.mContext = activity;
        easyModeUtils.mAccessibilityManager = (AccessibilityManager) activity.getSystemService("accessibility");
        if ("com.samsung.android.honeyboard".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SIP_CONFIG_PACKAGE_NAME", "com.sec.android.inputmethod"))) {
            EasyModeUtils.sSamsungKeypadPackageName = "com.samsung.android.honeyboard";
            EasyModeUtils.sSamsungKeypadProvider = "content://com.samsung.android.honeyboard.provider.KeyboardSettingsProvider";
            EasyModeUtils.sSamsungKeypadHighContrastSettingsActivity = "com.samsung.android.honeyboard".concat(".settings.styleandlayout.highcontrast.HighContrastThemeSettings");
        } else {
            EasyModeUtils.sSamsungKeypadPackageName = "com.sec.android.inputmethod";
            EasyModeUtils.sSamsungKeypadProvider = "content://com.sec.android.inputmethod.implement.setting.provider.KeyboardSettingsProvider";
            EasyModeUtils.sSamsungKeypadHighContrastSettingsActivity = "com.sec.android.inputmethod".concat(".implement.setting.HighContrastThemeSettings");
        }
        for (InputMethodInfo inputMethodInfo : ((InputMethodManager) activity.getSystemService("input_method")).getEnabledInputMethodList()) {
            if (inputMethodInfo != null) {
                String id = inputMethodInfo.getId();
                if (id.equals("com.samsung.inputmethod/.SamsungIME") || id.equals("com.sec.android.inputmethod.iwnnime.japan/.standardcommon.IWnnLanguageSwitcher") || id.equals("com.sec.android.inputmethod/.SamsungKeypad") || id.contains("com.samsung.android.honeyboard")) {
                    EasyModeUtils.sSamsungKeypadInputMethodId = inputMethodInfo.getId();
                    break;
                }
            }
        }
        this.mEasyModeUtils = easyModeUtils;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        initPreferences$7();
        initViews$2();
        updateEasyModeSwitch(Settings.System.getInt(getContentResolver(), "easy_mode_switch", 1));
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) super.onCreateView(layoutInflater, viewGroup, bundle);
        getListView().mDrawLastRoundedCorner = false;
        return viewGroup2;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        Log.i("EasyModeApp", "onPause");
        super.onPause();
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager != null) {
            semDesktopModeManager.unregisterListener(this.mDesktopModeListener);
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.samsung.android.desktopmode.SemDesktopModeManager$DesktopModeListener, com.samsung.android.settings.easymode.EasyModeApp$1] */
    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        Log.i("EasyModeApp", "onResume");
        super.onResume();
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        setLinkedDataView(false);
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("display_pref", 0);
        boolean z = sharedPreferences.getBoolean("init_easy_mode_enable_values", false);
        Log.i("EasyModeApp", "isBeingEnabled = " + z);
        updateTouchAndHold(z);
        updateHighContrastKeyboard(z || this.mEasyModeUtils.isHighContrastKeyboardOn(), false);
        sharedPreferences.edit().putBoolean("init_easy_mode_enable_values", false).apply();
        if (!Utils.isEasyModeEnabled(this.mContext)) {
            finish();
        }
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
        this.mDesktopModeManager = semDesktopModeManager;
        if (semDesktopModeManager != null) {
            ?? r1 = new SemDesktopModeManager.DesktopModeListener() { // from class: com.samsung.android.settings.easymode.EasyModeApp.1
                public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                    TooltipPopup$$ExternalSyntheticOutline0.m(new StringBuilder("changed desktopmode state = "), semDesktopModeState.enabled, "EasyModeApp");
                    if (semDesktopModeState.enabled == 4) {
                        EasyModeApp.this.getActivity().runOnUiThread(new Runnable() { // from class: com.samsung.android.settings.easymode.EasyModeApp.1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                FragmentActivity fragmentActivity = EasyModeApp.this.mContext;
                                Toast.makeText(fragmentActivity, fragmentActivity.getResources().getString(R.string.screen_resolution_disabled_toast, EasyModeApp.this.mContext.getResources().getString(R.string.hdmi_mode_dex)), 0).show();
                                EasyModeApp.this.finish();
                            }
                        });
                    }
                }
            };
            this.mDesktopModeListener = r1;
            semDesktopModeManager.registerListener((SemDesktopModeManager.DesktopModeListener) r1);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("easy_mode_switch_enable", this.mEasyModeSwitch.isEnabled());
        super.onSaveInstanceState(bundle);
    }

    public final void setEasyMode(int i) {
        Settings.System.putInt(this.mContext.getContentResolver(), "easy_mode_switch", i);
        boolean z = i == 0;
        Intent intent = new Intent("com.android.launcher.action.EASY_MODE_CHANGE");
        intent.addFlags(16777216);
        intent.putExtra("easymode", z);
        intent.putExtra("easymode_from", "settings");
        FragmentActivity fragmentActivity = this.mContext;
        UserHandle userHandle = UserHandle.ALL;
        fragmentActivity.sendBroadcastAsUser(intent, userHandle);
        Intent intent2 = new Intent("com.samsung.launcher.action.EASY_MODE_CHANGE");
        intent2.addFlags(16777216);
        intent2.putExtra("easymode", z);
        intent2.putExtra("easymode_from", "settings");
        this.mContext.sendBroadcastAsUser(intent2, userHandle);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x00a0, code lost:
    
        if (r1.hasSystemFeature("com.sec.feature.dual_lcd") == false) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setLinkedDataView(boolean r9) {
        /*
            Method dump skipped, instructions count: 330
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.easymode.EasyModeApp.setLinkedDataView(boolean):void");
    }

    public final void updateEasyModeSwitch(int i) {
        if (i == 1) {
            this.mEasyModeSwitch.setChecked(false);
            this.mModeState = 1;
            getPreferenceScreen().removePreference(this.mEasyModeEnableCategory);
        } else if (i == 0) {
            this.mEasyModeSwitch.setChecked(true);
            this.mModeState = 0;
            getPreferenceScreen().addPreference(this.mEasyModeEnableCategory);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateHighContrastKeyboard(boolean r11, boolean r12) {
        /*
            r10 = this;
            androidx.preference.SecSwitchPreferenceScreen r0 = r10.mHighContrastKeyboardPreference
            if (r0 != 0) goto L5
            return
        L5:
            java.lang.String r0 = "updateHighContrastKeyboard : value = "
            java.lang.String r1 = "EasyModeApp"
            androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0.m(r0, r1, r11)
            androidx.preference.SecSwitchPreferenceScreen r0 = r10.mHighContrastKeyboardPreference
            com.samsung.android.settings.easymode.EasyModeUtils r1 = r10.mEasyModeUtils
            android.content.Context r1 = r1.mContext
            android.content.ContentResolver r1 = r1.getContentResolver()
            java.lang.String r2 = "default_input_method"
            java.lang.String r1 = android.provider.Settings.Secure.getString(r1, r2)
            java.lang.String r2 = com.samsung.android.settings.easymode.EasyModeUtils.sSamsungKeypadInputMethodId
            boolean r1 = r2.equals(r1)
            r0.setEnabled(r1)
            if (r11 == 0) goto Lc1
            androidx.preference.SecSwitchPreferenceScreen r11 = r10.mHighContrastKeyboardPreference
            r0 = 1
            r11.setChecked(r0)
            androidx.preference.SecSwitchPreferenceScreen r11 = r10.mHighContrastKeyboardPreference
            r11.getClass()
            androidx.preference.SecPreferenceUtils.applySummaryColor(r11, r0)
            com.samsung.android.settings.easymode.EasyModeUtils r11 = r10.mEasyModeUtils
            r11.getClass()
            java.lang.String r0 = "NAME"
            java.lang.String r1 = "high_contrast_theme_name"
            java.lang.String[] r6 = new java.lang.String[]{r1}
            r8 = 0
            android.content.Context r11 = r11.mContext     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9b
            android.content.ContentResolver r2 = r11.getContentResolver()     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9b
            java.lang.String r11 = com.samsung.android.settings.easymode.EasyModeUtils.sSamsungKeypadProvider     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9b
            android.net.Uri r3 = android.net.Uri.parse(r11)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9b
            r4 = 0
            r5 = 0
            r7 = 0
            android.database.Cursor r11 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L99 java.lang.Exception -> L9b
            if (r11 == 0) goto L93
        L59:
            boolean r2 = r11.moveToNext()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            if (r2 == 0) goto L93
            int r2 = r11.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r3 = -1
            if (r2 == r3) goto L59
            java.lang.String r2 = r11.getString(r2)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            if (r2 == 0) goto L59
            boolean r2 = r2.isEmpty()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            if (r2 != 0) goto L59
            int r2 = r11.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r2 = r11.getString(r2)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            boolean r2 = r2.equals(r1)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            if (r2 == 0) goto L59
            java.lang.String r2 = "VALUE"
            int r2 = r11.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r2 = r11.getString(r2)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r8 = r2
            goto L59
        L8c:
            r10 = move-exception
            r8 = r11
            goto L9d
        L8f:
            r9 = r8
            r8 = r11
            r11 = r9
            goto La3
        L93:
            if (r11 == 0) goto La9
            r11.close()
            goto La9
        L99:
            r10 = move-exception
            goto L9d
        L9b:
            r11 = r8
            goto La3
        L9d:
            if (r8 == 0) goto La2
            r8.close()
        La2:
            throw r10
        La3:
            if (r8 == 0) goto La8
            r8.close()
        La8:
            r8 = r11
        La9:
            if (r8 != 0) goto Lb4
            androidx.fragment.app.FragmentActivity r11 = r10.mContext
            r0 = 2132019623(0x7f1409a7, float:1.9677586E38)
            java.lang.String r8 = r11.getString(r0)
        Lb4:
            androidx.preference.SecSwitchPreferenceScreen r11 = r10.mHighContrastKeyboardPreference
            r11.setSummary(r8)
            if (r12 == 0) goto Ldd
            androidx.preference.SecSwitchPreferenceScreen r11 = r10.mHighContrastKeyboardPreference
            r10.scrollToPreference(r11)
            goto Ldd
        Lc1:
            androidx.preference.SecSwitchPreferenceScreen r11 = r10.mHighContrastKeyboardPreference
            r12 = 0
            r11.setChecked(r12)
            androidx.preference.SecSwitchPreferenceScreen r11 = r10.mHighContrastKeyboardPreference
            r11.getClass()
            androidx.preference.SecPreferenceUtils.applySummaryColor(r11, r12)
            androidx.preference.SecSwitchPreferenceScreen r11 = r10.mHighContrastKeyboardPreference
            androidx.fragment.app.FragmentActivity r10 = r10.mContext
            r12 = 2132017631(0x7f1401df, float:1.9673546E38)
            java.lang.String r10 = r10.getString(r12)
            r11.setSummary(r10)
        Ldd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.settings.easymode.EasyModeApp.updateHighContrastKeyboard(boolean, boolean):void");
    }

    public final void updatePreferenceState(boolean z) {
        Settings.Secure.putInt(this.mContext.getContentResolver(), "long_press_timeout", this.mTouchAndHoldValue);
        this.mEasyModeUtils.putValueToKeyboard(this.mHighContrastKeyboardValue);
        SharedPreferences.Editor edit = this.mContext.getSharedPreferences("display_pref", 0).edit();
        edit.putBoolean("init_easy_mode_enable_values", z);
        edit.apply();
    }

    public final void updateTouchAndHold(boolean z) {
        String string;
        Preference preference = this.mTouchAndHoldPreference;
        if (preference == null) {
            return;
        }
        preference.setEnabled(!Utils.isDesktopModeEnabled(this.mContext));
        int i = z ? 1500 : Settings.Secure.getInt(this.mContext.getContentResolver(), "long_press_timeout", 500);
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "updateTouchAndHold : value = ", "EasyModeApp");
        SecPreferenceUtils.applySummaryColor(this.mTouchAndHoldPreference, true);
        Preference preference2 = this.mTouchAndHoldPreference;
        EasyModeUtils easyModeUtils = this.mEasyModeUtils;
        Context context = easyModeUtils.mContext;
        if (context == null) {
            string = ApnSettings.MVNO_NONE;
        } else if (Settings.Secure.getInt(context.getContentResolver(), "tap_duration_enabled", 0) == 1) {
            string = i == 1000 ? easyModeUtils.mContext.getString(R.string.longpress_preference_summary_with_tap_duration_one_sec) : easyModeUtils.mContext.getString(R.string.longpress_preference_summary_with_tap_duration, Float.toString(i / 1000.0f));
        } else {
            String[] stringArray = easyModeUtils.mContext.getResources().getStringArray(R.array.long_press_timeout_titles_selector);
            string = i != 300 ? i != 500 ? i != 1000 ? i != 1500 ? easyModeUtils.mContext.getString(R.string.touch_and_hold_custom_seconds, String.format(Locale.getDefault(), "%.2f", Float.valueOf(i / 1000.0f))) : stringArray[3] : stringArray[2] : stringArray[1] : stringArray[0];
        }
        preference2.setSummary(string);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.easymode.EasyModeApp$3, reason: invalid class name */
    public final class AnonymousClass3 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
        }
    }
}
