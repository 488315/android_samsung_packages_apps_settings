package com.samsung.android.settings.display;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.rune.CoreRune;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRadioButtonPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FrontScreenAppsFragment extends SettingsPreferenceFragment
        implements SecRadioButtonPreference.OnClickListener {
    public SecRadioButtonPreference mAlways;
    public FragmentActivity mContext;
    public String mLastAllAppsOption;
    public SecRadioButtonPreference mNever;
    public SecRadioButtonPreference mSwipeUp;

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return "com.android.settings.DisplaySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7432;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        String string =
                Settings.System.getString(
                        this.mContext.getContentResolver(), "fold_lock_behavior_setting");
        this.mLastAllAppsOption = string;
        if (string == null) {
            Settings.System.putString(
                    this.mContext.getContentResolver(),
                    "fold_lock_behavior_setting",
                    "sleep_on_fold_key");
            this.mLastAllAppsOption = "sleep_on_fold_key";
        }
        if (CoreRune.FW_FOLD_SA_LOGGING) {
            LoggingHelper.insertEventLogging(7432, 7433);
        }
        addPreferencesFromResource(R.xml.sec_front_screen_apps);
        LinearLayout linearLayout =
                (LinearLayout)
                        ((LayoutPreference) findPreference("front_screen_apps_preview"))
                                .mRootView.findViewById(R.id.image_view_container);
        SecRadioButtonPreference secRadioButtonPreference =
                (SecRadioButtonPreference)
                        findPreference("sec_front_screen_apps_radio_button_never");
        this.mNever = secRadioButtonPreference;
        secRadioButtonPreference.mListener = this;
        SecRadioButtonPreference secRadioButtonPreference2 =
                (SecRadioButtonPreference)
                        findPreference("sec_front_screen_apps_radio_button_always");
        this.mAlways = secRadioButtonPreference2;
        secRadioButtonPreference2.mListener = this;
        SecRadioButtonPreference secRadioButtonPreference3 =
                (SecRadioButtonPreference)
                        findPreference("sec_front_screen_apps_radio_button_swipe_up");
        this.mSwipeUp = secRadioButtonPreference3;
        secRadioButtonPreference3.mListener = this;
        if (linearLayout != null) {
            linearLayout.semSetRoundedCorners(15);
            linearLayout.semSetRoundedCornerColor(
                    15, this.mContext.getColor(R.color.sec_widget_round_and_bgcolor));
        }
        refreshUI$2();
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    public final void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference) {
        String str =
                secRadioButtonPreference.equals(this.mSwipeUp)
                        ? "selective_stay_awake_key"
                        : secRadioButtonPreference.equals(this.mAlways)
                                ? "stay_awake_on_fold_key"
                                : "sleep_on_fold_key";
        Settings.System.putString(
                this.mContext.getContentResolver(), "fold_lock_behavior_setting", str);
        if (this.mLastAllAppsOption != str) {
            this.mLastAllAppsOption = str;
        }
        refreshUI$2();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        if (Rune.isSamsungDexMode(this.mContext) || Utils.isDesktopStandaloneMode(this.mContext)) {
            finish();
        }
        super.onResume();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x004a, code lost:

       if (r2.equals("stay_awake_on_fold_key") == false) goto L11;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void refreshUI$2() {
        /*
            r6 = this;
            r0 = 0
            r1 = 1
            androidx.preference.PreferenceScreen r2 = r6.getPreferenceScreen()
            r3 = r0
        L7:
            int r4 = r2.getPreferenceCount()
            if (r3 >= r4) goto L1c
            androidx.preference.Preference r4 = r2.getPreference(r3)
            boolean r5 = r4 instanceof com.samsung.android.settings.widget.SecRadioButtonPreference
            if (r5 == 0) goto L1a
            com.samsung.android.settings.widget.SecRadioButtonPreference r4 = (com.samsung.android.settings.widget.SecRadioButtonPreference) r4
            r4.setChecked(r0)
        L1a:
            int r3 = r3 + r1
            goto L7
        L1c:
            java.lang.String r2 = r6.mLastAllAppsOption
            r2.getClass()
            r3 = -1
            int r4 = r2.hashCode()
            switch(r4) {
                case -2136906911: goto L43;
                case -942980583: goto L37;
                case -675276158: goto L2b;
                default: goto L29;
            }
        L29:
            r0 = r3
            goto L4d
        L2b:
            java.lang.String r0 = "selective_stay_awake_key"
            boolean r0 = r2.equals(r0)
            if (r0 != 0) goto L35
            goto L29
        L35:
            r0 = 2
            goto L4d
        L37:
            java.lang.String r0 = "sleep_on_fold_key"
            boolean r0 = r2.equals(r0)
            if (r0 != 0) goto L41
            goto L29
        L41:
            r0 = r1
            goto L4d
        L43:
            java.lang.String r4 = "stay_awake_on_fold_key"
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L4d
            goto L29
        L4d:
            switch(r0) {
                case 0: goto L5d;
                case 1: goto L57;
                case 2: goto L51;
                default: goto L50;
            }
        L50:
            goto L62
        L51:
            com.samsung.android.settings.widget.SecRadioButtonPreference r6 = r6.mSwipeUp
            r6.setChecked(r1)
            goto L62
        L57:
            com.samsung.android.settings.widget.SecRadioButtonPreference r6 = r6.mNever
            r6.setChecked(r1)
            goto L62
        L5d:
            com.samsung.android.settings.widget.SecRadioButtonPreference r6 = r6.mAlways
            r6.setChecked(r1)
        L62:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.display.FrontScreenAppsFragment.refreshUI$2():void");
    }
}
