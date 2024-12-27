package com.samsung.android.settings.display;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.secutil.Log;

import androidx.preference.Preference;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.biometrics.combination.CombinedBiometricSearchIndexProvider;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.display.widget.SecDarkModeTimePickerDialog;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRadioButtonPreference;
import com.samsung.android.settings.widget.SecUnclickablePreference;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDarkModeSettingsFragment extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener,
                SecDarkModeTimePickerDialog.TimePickerChangeListener,
                SecRadioButtonPreference.OnClickListener {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1();
    public AlertDialog mAutoLocationDialog;
    public Context mContext;
    public SecRadioButtonPreference mDarkModeAutoSchedule;
    public SecPreferenceScreen mDarkModeSchedule;
    public SecDarkModeTimePickerDialog mDarkModeTimePickerDialog;
    public SecSwitchPreference mDarkModeTurnOnAsScheduled;
    public SecRadioButtonPreference mDarkModeUserSchedule;
    public LayoutPreference mDivider;
    public DefaultItemAnimator mItemAnimator;
    public RecyclerView mRecyclerView;
    public final SettingsObserver mSettingsObserver = new SettingsObserver(new Handler());
    public UiModeManager mUiModeManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.display.SecDarkModeSettingsFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (Settings.System.getInt(
                                    context.getContentResolver(),
                                    "display_night_theme_scheduled",
                                    0)
                            == 0
                    || Settings.System.getInt(
                                    context.getContentResolver(),
                                    "display_night_theme_scheduled_type",
                                    0)
                            != 2) {
                ((ArrayList) nonIndexableKeys).add("sec_dark_mode_schedule");
            }
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add("sec_dark_mode_desc");
            arrayList.add("sec_dark_mode_auto_schedule");
            arrayList.add("sec_dark_mode_user_schedule");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.sec_display_dark_mode_settings;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (SecDisplayUtils.canChangeNightMode(context)) {
                return !(this instanceof CombinedBiometricSearchIndexProvider);
            }
            return false;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri NIGHT_THEME_SCHEDULED_TYPE_URI;
        public final Uri NIGHT_THEME_SCHEDULED_URI;
        public final Uri NIGHT_THEME_URI;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.NIGHT_THEME_URI = Settings.System.getUriFor("display_night_theme");
            this.NIGHT_THEME_SCHEDULED_URI =
                    Settings.System.getUriFor("display_night_theme_scheduled");
            this.NIGHT_THEME_SCHEDULED_TYPE_URI =
                    Settings.System.getUriFor("display_night_theme_scheduled_type");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (this.NIGHT_THEME_SCHEDULED_URI.equals(uri)) {
                SecDarkModeSettingsFragment secDarkModeSettingsFragment =
                        SecDarkModeSettingsFragment.this;
                if (secDarkModeSettingsFragment.mDarkModeTurnOnAsScheduled != null) {
                    SecDarkModeSettingsFragment.this.mDarkModeTurnOnAsScheduled.setChecked(
                            Settings.System.getInt(
                                            secDarkModeSettingsFragment.getContentResolver(),
                                            "display_night_theme_scheduled",
                                            0)
                                    != 0);
                }
                SecDarkModeSettingsFragment.this.updateDarkModeScheduledTypePrefrences();
                return;
            }
            if (this.NIGHT_THEME_SCHEDULED_TYPE_URI.equals(uri)) {
                SecDarkModeSettingsFragment secDarkModeSettingsFragment2 =
                        SecDarkModeSettingsFragment.this;
                Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                        SecDarkModeSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
                secDarkModeSettingsFragment2.updateDarkModeScheduledTypePrefrences();
            }
        }

        public final void setListening(boolean z) {
            SecDarkModeSettingsFragment secDarkModeSettingsFragment =
                    SecDarkModeSettingsFragment.this;
            Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                    SecDarkModeSettingsFragment.SEARCH_INDEX_DATA_PROVIDER;
            ContentResolver contentResolver = secDarkModeSettingsFragment.getContentResolver();
            if (!z) {
                contentResolver.unregisterContentObserver(this);
                return;
            }
            contentResolver.registerContentObserver(this.NIGHT_THEME_URI, false, this);
            contentResolver.registerContentObserver(this.NIGHT_THEME_SCHEDULED_URI, false, this);
            contentResolver.registerContentObserver(
                    this.NIGHT_THEME_SCHEDULED_TYPE_URI, false, this);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_dark_mode_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return "com.android.settings.DisplaySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7449;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_display";
    }

    public final void initUI$5() {
        addPreferencesFromResource(R.xml.sec_display_dark_mode_settings);
        setAutoRemoveInsetCategory(false);
        boolean canChangeNightMode = SecDisplayUtils.canChangeNightMode(this.mContext);
        ((SecUnclickablePreference) findPreference("sec_dark_mode_desc")).mPositionMode = 1;
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) findPreference("sec_dark_mode_turn_on_as_scheduled");
        this.mDarkModeTurnOnAsScheduled = secSwitchPreference;
        secSwitchPreference.setOnPreferenceChangeListener(this);
        this.mDarkModeTurnOnAsScheduled.setEnabled(canChangeNightMode);
        SecRadioButtonPreference secRadioButtonPreference =
                (SecRadioButtonPreference) findPreference("sec_dark_mode_auto_schedule");
        this.mDarkModeAutoSchedule = secRadioButtonPreference;
        secRadioButtonPreference.mListener = this;
        secRadioButtonPreference.setEnabled(canChangeNightMode);
        SecRadioButtonPreference secRadioButtonPreference2 =
                (SecRadioButtonPreference) findPreference("sec_dark_mode_user_schedule");
        this.mDarkModeUserSchedule = secRadioButtonPreference2;
        secRadioButtonPreference2.mListener = this;
        secRadioButtonPreference2.setEnabled(canChangeNightMode);
        this.mDivider = (LayoutPreference) findPreference("divider");
        SecPreferenceScreen secPreferenceScreen =
                (SecPreferenceScreen) findPreference("sec_dark_mode_schedule");
        this.mDarkModeSchedule = secPreferenceScreen;
        secPreferenceScreen.getClass();
        SecPreferenceUtils.applySummaryColor(secPreferenceScreen, true);
        this.mDarkModeSchedule.setEnabled(canChangeNightMode);
        updateDarkModeScheduledTypePrefrences();
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean isPreferenceAnimationAllowed() {
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mRecyclerView = getListView();
        this.mItemAnimator = new DefaultItemAnimator();
        if (this.mRecyclerView != null) {
            if (getResources().getConfiguration().orientation == 2) {
                this.mRecyclerView.setItemAnimator(null);
            } else if (getResources().getConfiguration().orientation == 1) {
                this.mRecyclerView.setItemAnimator(this.mItemAnimator);
            }
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getPreferenceScreen().removeAll();
        initUI$5();
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            int i = configuration.orientation;
            if (i == 1) {
                recyclerView.setItemAnimator(this.mItemAnimator);
            } else if (i == 2) {
                recyclerView.setItemAnimator(null);
            }
        }
        SecDarkModeTimePickerDialog secDarkModeTimePickerDialog = this.mDarkModeTimePickerDialog;
        if (secDarkModeTimePickerDialog != null && secDarkModeTimePickerDialog.isShowing()) {
            this.mDarkModeTimePickerDialog.dismiss();
        }
        AlertDialog alertDialog = this.mAutoLocationDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mAutoLocationDialog.dismiss();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.e("SecDarkModeSettingsFragment", "onCreate");
        super.onCreate(bundle);
        this.mContext = getContext();
        setAnimationAllowed(true);
        this.mUiModeManager = (UiModeManager) this.mContext.getSystemService(UiModeManager.class);
        initUI$5();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        if (i != 1) {
            throw new IllegalArgumentException();
        }
        SecDarkModeTimePickerDialog secDarkModeTimePickerDialog = this.mDarkModeTimePickerDialog;
        if (secDarkModeTimePickerDialog != null && secDarkModeTimePickerDialog.isShowing()) {
            this.mDarkModeTimePickerDialog.dismiss();
        }
        LocalTime customNightModeStart = this.mUiModeManager.getCustomNightModeStart();
        calendar.set(11, customNightModeStart.getHour());
        calendar.set(12, customNightModeStart.getMinute());
        LocalTime customNightModeEnd = this.mUiModeManager.getCustomNightModeEnd();
        calendar2.set(11, customNightModeEnd.getHour());
        calendar2.set(12, customNightModeEnd.getMinute());
        SecDarkModeTimePickerDialog secDarkModeTimePickerDialog2 =
                new SecDarkModeTimePickerDialog(
                        this.mContext,
                        customNightModeStart.getMinute() + (customNightModeStart.getHour() * 60),
                        customNightModeEnd.getMinute() + (customNightModeEnd.getHour() * 60));
        this.mDarkModeTimePickerDialog = secDarkModeTimePickerDialog2;
        if (secDarkModeTimePickerDialog2.mTimePickerChangeListeners == null) {
            secDarkModeTimePickerDialog2.mTimePickerChangeListeners = new ArrayList();
        }
        if (!((ArrayList) secDarkModeTimePickerDialog2.mTimePickerChangeListeners).contains(this)) {
            ((ArrayList) secDarkModeTimePickerDialog2.mTimePickerChangeListeners).add(this);
        }
        this.mDarkModeTimePickerDialog.getWindow().setSoftInputMode(16);
        return this.mDarkModeTimePickerDialog;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        AlertDialog alertDialog = this.mAutoLocationDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mAutoLocationDialog.dismiss();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        Log.e("SecDarkModeSettingsFragment", "onPause");
        super.onPause();
        this.mSettingsObserver.setListening(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        if (!preference.equals(this.mDarkModeTurnOnAsScheduled)) {
            return false;
        }
        this.mDarkModeTurnOnAsScheduled.setChecked(booleanValue);
        Settings.System.putInt(
                getContentResolver(), "display_night_theme_scheduled", booleanValue ? 1 : 0);
        LoggingHelper.insertEventLogging(7449, 7492, booleanValue ? 1L : 0L);
        if (!booleanValue) {
            this.mUiModeManager.setNightMode(1);
        }
        updateDarkModeScheduledTypePrefrences();
        if (booleanValue) {
            if (Settings.Secure.getInt(getContentResolver(), "location_mode", 0) != 0) {
                int nightMode = this.mUiModeManager.getNightMode();
                if (nightMode == 0) {
                    this.mDarkModeAutoSchedule.onClick();
                } else if (nightMode == 3) {
                    this.mDarkModeUserSchedule.onClick();
                }
            } else {
                this.mDarkModeUserSchedule.onClick();
            }
        }
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (preference.equals(this.mDarkModeSchedule)) {
            removeDialog(1);
            showDialog(1);
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.samsung.android.settings.widget.SecRadioButtonPreference.OnClickListener
    public final void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference) {
        if (!secRadioButtonPreference.equals(this.mDarkModeAutoSchedule)) {
            if (secRadioButtonPreference.equals(this.mDarkModeUserSchedule)) {
                Settings.System.putInt(
                        getContentResolver(), "display_night_theme_scheduled_type", 2);
                updateDarkModeScheduledTypePrefrences();
                LoggingHelper.insertEventLogging(7449, 7493, 2L);
                return;
            }
            return;
        }
        if (Settings.Secure.getInt(getContentResolver(), "location_mode", 0) != 0) {
            Settings.System.putInt(getContentResolver(), "display_night_theme_scheduled_type", 1);
            updateDarkModeScheduledTypePrefrences();
        } else {
            final int i = 1;
            AlertDialog.Builder negativeButton =
                    new AlertDialog.Builder(getActivity())
                            .setCancelable(true)
                            .setTitle(R.string.sec_blue_light_filter_dlg_turn_on_location_title)
                            .setMessage(R.string.sec_blue_light_filter_dlg_turn_on_location)
                            .setNegativeButton(
                                    R.string.dlg_cancel,
                                    new DialogInterface.OnClickListener(
                                            this) { // from class:
                                                    // com.samsung.android.settings.display.SecDarkModeSettingsFragment.3
                                        public final /* synthetic */ SecDarkModeSettingsFragment
                                                this$0;

                                        {
                                            this.this$0 = this;
                                        }

                                        @Override // android.content.DialogInterface.OnClickListener
                                        public final void onClick(
                                                DialogInterface dialogInterface, int i2) {
                                            switch (i) {
                                                case 0:
                                                    SecDarkModeSettingsFragment
                                                            secDarkModeSettingsFragment =
                                                                    this.this$0;
                                                    Indexable$SearchIndexProvider
                                                            indexable$SearchIndexProvider =
                                                                    SecDarkModeSettingsFragment
                                                                            .SEARCH_INDEX_DATA_PROVIDER;
                                                    Settings.Secure.putInt(
                                                            secDarkModeSettingsFragment
                                                                    .getContentResolver(),
                                                            "location_mode",
                                                            3);
                                                    this.this$0.mDarkModeAutoSchedule.onClick();
                                                    break;
                                                default:
                                                    SecDarkModeSettingsFragment
                                                            secDarkModeSettingsFragment2 =
                                                                    this.this$0;
                                                    if (secDarkModeSettingsFragment2.mUiModeManager
                                                                    .getNightMode()
                                                            == 3) {
                                                        secDarkModeSettingsFragment2
                                                                .mDarkModeUserSchedule.onClick();
                                                    } else {
                                                        secDarkModeSettingsFragment2
                                                                .mDarkModeTurnOnAsScheduled
                                                                .setChecked(false);
                                                        secDarkModeSettingsFragment2.mUiModeManager
                                                                .setNightMode(1);
                                                    }
                                                    dialogInterface.dismiss();
                                                    break;
                                            }
                                        }
                                    });
            final int i2 = 0;
            AlertDialog create =
                    negativeButton
                            .setPositiveButton(
                                    R.string.sec_dlg_turn_on,
                                    new DialogInterface.OnClickListener(
                                            this) { // from class:
                                                    // com.samsung.android.settings.display.SecDarkModeSettingsFragment.3
                                        public final /* synthetic */ SecDarkModeSettingsFragment
                                                this$0;

                                        {
                                            this.this$0 = this;
                                        }

                                        @Override // android.content.DialogInterface.OnClickListener
                                        public final void onClick(
                                                DialogInterface dialogInterface, int i22) {
                                            switch (i2) {
                                                case 0:
                                                    SecDarkModeSettingsFragment
                                                            secDarkModeSettingsFragment =
                                                                    this.this$0;
                                                    Indexable$SearchIndexProvider
                                                            indexable$SearchIndexProvider =
                                                                    SecDarkModeSettingsFragment
                                                                            .SEARCH_INDEX_DATA_PROVIDER;
                                                    Settings.Secure.putInt(
                                                            secDarkModeSettingsFragment
                                                                    .getContentResolver(),
                                                            "location_mode",
                                                            3);
                                                    this.this$0.mDarkModeAutoSchedule.onClick();
                                                    break;
                                                default:
                                                    SecDarkModeSettingsFragment
                                                            secDarkModeSettingsFragment2 =
                                                                    this.this$0;
                                                    if (secDarkModeSettingsFragment2.mUiModeManager
                                                                    .getNightMode()
                                                            == 3) {
                                                        secDarkModeSettingsFragment2
                                                                .mDarkModeUserSchedule.onClick();
                                                    } else {
                                                        secDarkModeSettingsFragment2
                                                                .mDarkModeTurnOnAsScheduled
                                                                .setChecked(false);
                                                        secDarkModeSettingsFragment2.mUiModeManager
                                                                .setNightMode(1);
                                                    }
                                                    dialogInterface.dismiss();
                                                    break;
                                            }
                                        }
                                    })
                            .setOnCancelListener(
                                    new DialogInterface
                                            .OnCancelListener() { // from class:
                                                                  // com.samsung.android.settings.display.SecDarkModeSettingsFragment.2
                                        @Override // android.content.DialogInterface.OnCancelListener
                                        public final void onCancel(
                                                DialogInterface dialogInterface) {
                                            SecDarkModeSettingsFragment
                                                    secDarkModeSettingsFragment =
                                                            SecDarkModeSettingsFragment.this;
                                            if (secDarkModeSettingsFragment.mUiModeManager
                                                            .getNightMode()
                                                    == 3) {
                                                secDarkModeSettingsFragment.mDarkModeUserSchedule
                                                        .onClick();
                                            } else {
                                                secDarkModeSettingsFragment
                                                        .mDarkModeTurnOnAsScheduled.setChecked(
                                                        false);
                                                secDarkModeSettingsFragment.mUiModeManager
                                                        .setNightMode(1);
                                            }
                                            dialogInterface.dismiss();
                                        }
                                    })
                            .create();
            this.mAutoLocationDialog = create;
            SecRadioButtonPreference secRadioButtonPreference2 = this.mDarkModeAutoSchedule;
            if (secRadioButtonPreference2 != null && create != null) {
                Rect rect = new Rect();
                secRadioButtonPreference2.seslGetPreferenceBounds(rect);
                create.semSetAnchor((rect.left + rect.right) / 2, rect.bottom);
            }
            this.mAutoLocationDialog.show();
        }
        LoggingHelper.insertEventLogging(7449, 7493, 1L);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        Log.e("SecDarkModeSettingsFragment", "onResume");
        super.onResume();
        this.mSettingsObserver.setListening(true);
        updateDarkModeScheduledTypePrefrences();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        Dialog dialog;
        SettingsPreferenceFragment.SettingsDialogFragment dialogFragment = getDialogFragment();
        if (dialogFragment != null
                && (dialog = dialogFragment.mDialog) != null
                && !dialog.isShowing()) {
            dialogFragment.mShowsDialog = false;
        }
        super.onSaveInstanceState(bundle);
    }

    public final void updateDarkModeSchdeuledTimePreferences(boolean z) {
        this.mDarkModeSchedule.setVisible(z);
        if (z) {
            SecPreferenceScreen secPreferenceScreen = this.mDarkModeSchedule;
            Context context = this.mContext;
            int i =
                    (int)
                            Settings.System.getLong(
                                    getContentResolver(), "display_night_theme_on_time", 1140L);
            int i2 =
                    (int)
                            Settings.System.getLong(
                                    getContentResolver(), "display_night_theme_off_time", 420L);
            int i3 = i / 60;
            int i4 = i % 60;
            int i5 = i2 / 60;
            int i6 = i2 % 60;
            StringBuilder sb = new StringBuilder();
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(DateFormat.is24HourFormat(context) ? 11 : 10, i3);
            calendar.set(12, i4);
            sb.append(
                    DateFormat.getTimeFormat(context).format(new Date(calendar.getTimeInMillis())));
            sb.append(" - ");
            calendar.clear();
            calendar.set(DateFormat.is24HourFormat(context) ? 11 : 10, i5);
            calendar.set(12, i6);
            if (i >= i2) {
                sb.append(
                        context.getResources()
                                .getString(
                                        R.string
                                                .sec_blue_light_filter_off_time_next_day_summary_format,
                                        DateFormat.getTimeFormat(context)
                                                .format(new Date(calendar.getTimeInMillis()))));
            } else {
                sb.append(
                        DateFormat.getTimeFormat(context)
                                .format(new Date(calendar.getTimeInMillis())));
            }
            secPreferenceScreen.setSummary(sb.toString());
        }
    }

    public final void updateDarkModeScheduledTypePrefrences() {
        boolean z =
                Settings.System.getInt(getContentResolver(), "display_night_theme_scheduled", 0)
                        != 0;
        this.mDarkModeTurnOnAsScheduled.setChecked(z);
        if (!z) {
            this.mDarkModeAutoSchedule.setVisible(false);
            this.mDarkModeUserSchedule.setVisible(false);
            this.mDivider.setVisible(false);
            updateDarkModeSchdeuledTimePreferences(false);
            return;
        }
        this.mDarkModeAutoSchedule.setVisible(true);
        this.mDarkModeUserSchedule.setVisible(true);
        this.mDivider.setVisible(true);
        int i =
                Settings.System.getInt(
                        getContentResolver(), "display_night_theme_scheduled_type", 2);
        if (i == 1) {
            this.mDarkModeAutoSchedule.setChecked(true);
            this.mDarkModeUserSchedule.setChecked(false);
            this.mUiModeManager.setNightMode(0);
            updateDarkModeSchdeuledTimePreferences(false);
            return;
        }
        if (i == 2) {
            this.mDarkModeAutoSchedule.setChecked(false);
            this.mDarkModeUserSchedule.setChecked(true);
            this.mUiModeManager.setNightMode(3);
            updateDarkModeSchdeuledTimePreferences(true);
        }
    }
}
