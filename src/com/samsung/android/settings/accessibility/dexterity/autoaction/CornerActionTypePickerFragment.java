package com.samsung.android.settings.accessibility.dexterity.autoaction;

import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.core.InstrumentedPreferenceFragment;

import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CornerActionTypePickerFragment extends InstrumentedPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public HashMap mActionList;
    public String mCornerActions;
    public int mCornerPos;
    public final int[] mCornerTitlesRes = {
        R.string.accessibility_corner_action_position_top_left,
        R.string.accessibility_corner_action_position_top_right,
        R.string.accessibility_corner_action_position_bottom_left,
        R.string.accessibility_corner_action_position_bottom_right
    };
    public int mPauseAutoClickWithCorner;
    public ArrayList mPreferences;
    public MenuItem mResetMenu;

    public final String addAction(String str, String str2) {
        ArrayList arrayList = new ArrayList(Arrays.asList(str.split(",")));
        if (arrayList.size() == 1 && ((String) arrayList.get(0)).equals(SignalSeverity.NONE)) {
            return str2;
        }
        arrayList.add(str2);
        arrayList.sort(
                new Comparator() { // from class:
                                   // com.samsung.android.settings.accessibility.dexterity.autoaction.CornerActionTypePickerFragment$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        CornerActionTypePickerFragment cornerActionTypePickerFragment =
                                CornerActionTypePickerFragment.this;
                        String str3 = (String) obj2;
                        int i = CornerActionTypePickerFragment.$r8$clinit;
                        Preference findPreference =
                                cornerActionTypePickerFragment.findPreference((String) obj);
                        int i2 = Preference.DEFAULT_ORDER;
                        int order =
                                findPreference == null
                                        ? Integer.MAX_VALUE
                                        : findPreference.getOrder();
                        Preference findPreference2 =
                                cornerActionTypePickerFragment.findPreference(str3);
                        if (findPreference2 != null) {
                            i2 = findPreference2.getOrder();
                        }
                        if (order < i2) {
                            return -1;
                        }
                        return order > i2 ? 1 : 0;
                    }
                });
        return String.join(",", arrayList);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.corner_action_type;
    }

    public final boolean isInitState() {
        String[] split = this.mCornerActions.split(":");
        return SignalSeverity.NONE.equals(split[this.mCornerPos])
                || "pause_resume_auto_click".equals(split[this.mCornerPos]);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updatePreferencesState$3();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.reset_option_menu, menu);
        MenuItem findItem = menu.findItem(R.id.menu_reset);
        this.mResetMenu = findItem;
        findItem.setShowAsAction(5);
        this.mResetMenu.setVisible(!isInitState());
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00cc, code lost:

       if (com.android.settings.Utils.hasPackage(r4, "com.samsung.android.emergency") != false) goto L36;
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0104, code lost:

       if (com.android.settings.Utils.isCMCAvailable(r4) != false) goto L48;
    */
    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View onCreateView(
            android.view.LayoutInflater r11, android.view.ViewGroup r12, android.os.Bundle r13) {
        /*
            Method dump skipped, instructions count: 390
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.accessibility.dexterity.autoaction.CornerActionTypePickerFragment.onCreateView(android.view.LayoutInflater,"
                    + " android.view.ViewGroup, android.os.Bundle):android.view.View");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        SemLog.d("CornerActionTypePickerFragment", "onOptionsItemSelected");
        if (menuItem.getItemId() == R.id.menu_reset) {
            String[] split = this.mCornerActions.split(":");
            for (String str : split[this.mCornerPos].split(",")) {
                this.mActionList.put(str, -1);
            }
            boolean z =
                    Settings.Secure.getInt(
                                    getActivity().getContentResolver(),
                                    "accessibility_auto_action_type",
                                    0)
                            != 0;
            int i = this.mCornerPos;
            split[i] =
                    (z && i == this.mPauseAutoClickWithCorner)
                            ? "pause_resume_auto_click"
                            : SignalSeverity.NONE;
            this.mCornerActions = String.join(":", split);
            Settings.Secure.putString(
                    getContext().getContentResolver(),
                    "accessibility_corner_actions",
                    this.mCornerActions);
            if ("none:none:none:none".equals(this.mCornerActions)) {
                Settings.Secure.putInt(
                        getContext().getContentResolver(),
                        "accessibility_corner_action_enabled",
                        0);
            }
            updatePreferencesState$3();
            menuItem.setVisible(false);
            View view = getView();
            Context context = getContext();
            if (view != null && context != null) {
                view.announceForAccessibility(
                        context.getString(R.string.accessibility_corner_action_reset));
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String join;
        boolean booleanValue = ((Boolean) obj).booleanValue();
        String key = preference.getKey();
        if ("send_sos_messages".equals(key)
                && Settings.Secure.getInt(
                                getContext().getContentResolver(), "emergency_gesture_enabled", 1)
                        == 0) {
            Toast.makeText(getContext(), R.string.send_sos_toast_warning, 0).show();
            return false;
        }
        if (booleanValue && "none:none:none:none".equals(this.mCornerActions)) {
            Settings.Secure.putInt(
                    getContext().getContentResolver(), "accessibility_corner_action_enabled", 1);
            this.mActionList.put(key, Integer.valueOf(this.mCornerPos));
            String[] split = this.mCornerActions.split(":");
            int i = this.mCornerPos;
            split[i] = addAction(split[i], key);
            this.mCornerActions = String.join(":", split);
            Settings.Secure.putString(
                    getActivity().getContentResolver(),
                    "accessibility_corner_actions",
                    this.mCornerActions);
            this.mResetMenu.setVisible(!isInitState());
            updatePreferencesState$3();
            return false;
        }
        this.mActionList.put(key, Integer.valueOf(booleanValue ? this.mCornerPos : -1));
        String[] split2 = this.mCornerActions.split(":");
        if (booleanValue) {
            int i2 = this.mCornerPos;
            split2[i2] = addAction(split2[i2], key);
        } else {
            int i3 = this.mCornerPos;
            ArrayList arrayList = new ArrayList(Arrays.asList(split2[i3].split(",")));
            if (arrayList.size() == 1) {
                join = SignalSeverity.NONE;
            } else {
                arrayList.remove(key);
                join = String.join(",", arrayList);
            }
            split2[i3] = join;
        }
        this.mCornerActions = String.join(":", split2);
        Settings.Secure.putString(
                getActivity().getContentResolver(),
                "accessibility_corner_actions",
                this.mCornerActions);
        this.mResetMenu.setVisible(!isInitState());
        if (!booleanValue && "none:none:none:none".equals(this.mCornerActions)) {
            Settings.Secure.putInt(
                    getContext().getContentResolver(), "accessibility_corner_action_enabled", 0);
        }
        updatePreferencesState$3();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updatePreferencesState$3();
        MenuItem menuItem = this.mResetMenu;
        if (menuItem != null) {
            menuItem.setVisible(!isInitState());
        }
    }

    public final void updatePreferencesState$3() {
        Iterator it = this.mPreferences.iterator();
        while (it.hasNext()) {
            Preference preference = (Preference) it.next();
            if (preference instanceof SwitchPreferenceCompat) {
                HashMap hashMap = this.mActionList;
                if (hashMap != null
                        && ((Integer) hashMap.get(preference.getKey())).intValue()
                                == this.mCornerPos) {
                    ((SwitchPreferenceCompat) preference).setChecked(true);
                } else if ("pause_resume_auto_click".equals(preference.getKey())) {
                    boolean z =
                            Settings.Secure.getInt(
                                                    getActivity().getContentResolver(),
                                                    "accessibility_auto_action_type",
                                                    0)
                                            != 0
                                    && this.mCornerPos == this.mPauseAutoClickWithCorner;
                    PreferenceCategory preferenceCategory =
                            (PreferenceCategory) getPreferenceScreen().findPreference("category");
                    preference.setVisible(z);
                    if (preferenceCategory != null) {
                        preferenceCategory.setVisible(z);
                    }
                } else {
                    HashMap hashMap2 = this.mActionList;
                    if (hashMap2 == null
                            || ((Integer) hashMap2.get(preference.getKey())).intValue() >= 0) {
                        preference.setEnabled(false);
                    } else {
                        ((SwitchPreferenceCompat) preference).setChecked(false);
                    }
                }
            }
        }
    }
}
