package com.samsung.android.settings.notification.zen;

import android.app.AutomaticZenRule;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.notification.zen.ZenModeBackend;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecZenModeDeleteRulesSetting extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public ActionBar mActionBar;
    public ZenModeBackend mBackend;
    public Context mContext;
    public Map.Entry[] mRules;
    public String mSelectedId;
    public RelativeLayout removeButtonLayout;
    public ArrayList mSelectionChecklist = null;
    public CheckBox mSelectAllCheckbox = null;
    public TextView mSelectedZenRuleTextView = null;
    public boolean isAppRule = false;
    public final AnonymousClass1 checkBoxRunnable =
            new Runnable() { // from class:
                             // com.samsung.android.settings.notification.zen.SecZenModeDeleteRulesSetting.1
                @Override // java.lang.Runnable
                public final void run() {
                    if (SecZenModeDeleteRulesSetting.this.mSelectedId.length() != 0) {
                        if (SecZenModeDeleteRulesSetting.this.mSelectionChecklist.size() == 1) {
                            SecZenModeDeleteRulesSetting.m1264$$Nest$mToggleAllCheck(
                                    SecZenModeDeleteRulesSetting.this, true);
                        } else {
                            int size = SecZenModeDeleteRulesSetting.this.mSelectionChecklist.size();
                            for (int i = 0; i < size; i++) {
                                SecZenModeDeleteRulesSetting secZenModeDeleteRulesSetting =
                                        SecZenModeDeleteRulesSetting.this;
                                if (secZenModeDeleteRulesSetting.mSelectedId.equals(
                                        ((CheckBoxPreference)
                                                        secZenModeDeleteRulesSetting
                                                                .mSelectionChecklist.get(i))
                                                .getKey())) {
                                    ((CheckBoxPreference)
                                                    SecZenModeDeleteRulesSetting.this
                                                            .mSelectionChecklist.get(i))
                                            .setChecked(
                                                    ((CheckBoxPreference)
                                                                    SecZenModeDeleteRulesSetting
                                                                            .this
                                                                            .mSelectionChecklist
                                                                            .get(i))
                                                            .isEnabled());
                                    SecZenModeDeleteRulesSetting.this.updateActionbarState$3();
                                }
                            }
                        }
                        SecZenModeDeleteRulesSetting.this.mSelectedId = ApnSettings.MVNO_NONE;
                    }
                }
            };
    public final Handler mHandler = new Handler();

    /* renamed from: -$$Nest$mToggleAllCheck, reason: not valid java name */
    public static void m1264$$Nest$mToggleAllCheck(
            SecZenModeDeleteRulesSetting secZenModeDeleteRulesSetting, boolean z) {
        int size = secZenModeDeleteRulesSetting.mSelectionChecklist.size();
        for (int i = 0; i < size; i++) {
            if (((CheckBoxPreference) secZenModeDeleteRulesSetting.mSelectionChecklist.get(i))
                    .isEnabled()) {
                ((CheckBoxPreference) secZenModeDeleteRulesSetting.mSelectionChecklist.get(i))
                        .setChecked(z);
            } else {
                ((CheckBoxPreference) secZenModeDeleteRulesSetting.mSelectionChecklist.get(i))
                        .setChecked(false);
                CheckBox checkBox = secZenModeDeleteRulesSetting.mSelectAllCheckbox;
                if (checkBox != null) {
                    checkBox.setChecked(false);
                }
            }
        }
        secZenModeDeleteRulesSetting.updateActionbarState$3();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 4150;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("DeleteRulesPreference", "onCreate");
        this.mContext = getContext();
        Intent intent = getActivity().getIntent();
        Bundle bundleExtra =
                (intent == null || !intent.hasExtra(":settings:show_fragment_args"))
                        ? null
                        : intent.getBundleExtra(":settings:show_fragment_args");
        if (bundleExtra != null && bundleExtra.containsKey("selected_id")) {
            this.mSelectedId = bundleExtra.getString("selected_id", ApnSettings.MVNO_NONE);
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onCreate selectedKey: "),
                    this.mSelectedId,
                    "DeleteRulesPreference");
        }
        if (bundle != null) {
            this.mSelectedId = bundle.getString("selected_id");
        }
        this.mBackend = ZenModeBackend.getInstance(this.mContext);
        this.mSelectionChecklist = new ArrayList();
        setHasOptionsMenu(true);
        PreferenceScreen createPreferenceScreen =
                getPreferenceManager().createPreferenceScreen(getContext());
        setPreferenceScreen(createPreferenceScreen);
        if (this.mRules != null) {
            this.mRules = null;
        }
        Map.Entry[] automaticZenRules = this.mBackend.getAutomaticZenRules();
        this.mRules = automaticZenRules;
        if (automaticZenRules != null) {
            for (Map.Entry entry : automaticZenRules) {
                AutomaticZenRule automaticZenRule = (AutomaticZenRule) entry.getValue();
                String packageName = automaticZenRule.getPackageName();
                CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this.mContext, null);
                checkBoxPreference.setLayoutResource(R.layout.sec_zenmode_checkboxpreference);
                checkBoxPreference.setTitle(((AutomaticZenRule) entry.getValue()).getName());
                checkBoxPreference.setKey((String) entry.getKey());
                checkBoxPreference.setPersistent();
                checkBoxPreference.seslSetDividerStartOffset(
                        (int)
                                this.mContext
                                        .getResources()
                                        .getDimension(
                                                R.dimen.sec_widget_app_list_divider_margin_start));
                createPreferenceScreen.addPreference(checkBoxPreference);
                if (packageName != null
                        && packageName.equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)
                        && automaticZenRule.getZenPolicy() != null) {
                    checkBoxPreference.setEnabled(false);
                    this.isAppRule = true;
                }
                this.mSelectionChecklist.add(checkBoxPreference);
                checkBoxPreference.setOnPreferenceChangeListener(this);
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.dnd_edit_menu, menu);
        menu.findItem(R.id.action_done).setTitle(R.string.cancel);
        View inflate =
                LayoutInflater.from(getActivity())
                        .inflate(R.layout.sec_zen_delete_rules_selection_bar, (ViewGroup) null);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        this.mActionBar = supportActionBar;
        if (supportActionBar != null) {
            supportActionBar.setDisplayOptions(16, 16);
            this.mActionBar.setCustomView(inflate, new ActionBar.LayoutParams(16));
            this.mActionBar.setDisplayHomeAsUpEnabled(false);
            this.mActionBar.setHomeButtonEnabled();
            TextView textView = (TextView) inflate.findViewById(R.id.text_selected_all);
            if (this.mSelectionChecklist.size() != 1
                    || ((CheckBoxPreference) this.mSelectionChecklist.get(0)).isEnabled()) {
                final int i = 0;
                textView.setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.notification.zen.SecZenModeDeleteRulesSetting.2
                            public final /* synthetic */ SecZenModeDeleteRulesSetting this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i) {
                                    case 0:
                                        SecZenModeDeleteRulesSetting secZenModeDeleteRulesSetting =
                                                this.this$0;
                                        CheckBox checkBox =
                                                secZenModeDeleteRulesSetting.mSelectAllCheckbox;
                                        checkBox.setChecked(
                                                (secZenModeDeleteRulesSetting.isAppRule
                                                                || checkBox.isChecked())
                                                        ? false
                                                        : true);
                                        SecZenModeDeleteRulesSetting secZenModeDeleteRulesSetting2 =
                                                this.this$0;
                                        SecZenModeDeleteRulesSetting.m1264$$Nest$mToggleAllCheck(
                                                secZenModeDeleteRulesSetting2,
                                                secZenModeDeleteRulesSetting2.mSelectAllCheckbox
                                                        .isChecked());
                                        break;
                                    default:
                                        SecZenModeDeleteRulesSetting secZenModeDeleteRulesSetting3 =
                                                this.this$0;
                                        SecZenModeDeleteRulesSetting.m1264$$Nest$mToggleAllCheck(
                                                secZenModeDeleteRulesSetting3,
                                                secZenModeDeleteRulesSetting3.mSelectAllCheckbox
                                                        .isChecked());
                                        break;
                                }
                            }
                        });
            } else {
                textView.setClickable(false);
                CheckBox checkBox = this.mSelectAllCheckbox;
                if (checkBox != null) {
                    checkBox.setChecked(false);
                }
            }
            this.mSelectAllCheckbox = (CheckBox) inflate.findViewById(R.id.toggle_selection_check);
            if (this.mSelectionChecklist.size() != 1
                    || ((CheckBoxPreference) this.mSelectionChecklist.get(0)).isEnabled()) {
                final int i2 = 1;
                this.mSelectAllCheckbox.setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.notification.zen.SecZenModeDeleteRulesSetting.2
                            public final /* synthetic */ SecZenModeDeleteRulesSetting this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i2) {
                                    case 0:
                                        SecZenModeDeleteRulesSetting secZenModeDeleteRulesSetting =
                                                this.this$0;
                                        CheckBox checkBox2 =
                                                secZenModeDeleteRulesSetting.mSelectAllCheckbox;
                                        checkBox2.setChecked(
                                                (secZenModeDeleteRulesSetting.isAppRule
                                                                || checkBox2.isChecked())
                                                        ? false
                                                        : true);
                                        SecZenModeDeleteRulesSetting secZenModeDeleteRulesSetting2 =
                                                this.this$0;
                                        SecZenModeDeleteRulesSetting.m1264$$Nest$mToggleAllCheck(
                                                secZenModeDeleteRulesSetting2,
                                                secZenModeDeleteRulesSetting2.mSelectAllCheckbox
                                                        .isChecked());
                                        break;
                                    default:
                                        SecZenModeDeleteRulesSetting secZenModeDeleteRulesSetting3 =
                                                this.this$0;
                                        SecZenModeDeleteRulesSetting.m1264$$Nest$mToggleAllCheck(
                                                secZenModeDeleteRulesSetting3,
                                                secZenModeDeleteRulesSetting3.mSelectAllCheckbox
                                                        .isChecked());
                                        break;
                                }
                            }
                        });
            } else {
                this.mSelectAllCheckbox.setClickable(false);
                this.mSelectAllCheckbox.setChecked(false);
            }
            this.mSelectedZenRuleTextView =
                    (TextView) inflate.findViewById(R.id.number_selected_text);
            AppBarLayout appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar);
            if (appBarLayout != null) {
                appBarLayout.addOnOffsetChangedListener(
                        new AppBarLayout
                                .OnOffsetChangedListener() { // from class:
                                                             // com.samsung.android.settings.notification.zen.SecZenModeDeleteRulesSetting.4
                            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
                            public final void onOffsetChanged(AppBarLayout appBarLayout2, int i3) {
                                SecZenModeDeleteRulesSetting secZenModeDeleteRulesSetting =
                                        SecZenModeDeleteRulesSetting.this;
                                TextView textView2 =
                                        secZenModeDeleteRulesSetting.mSelectedZenRuleTextView;
                                if (textView2 != null) {
                                    secZenModeDeleteRulesSetting.getClass();
                                    int height = appBarLayout2.getHeight();
                                    float f = 1.0f;
                                    if (height > ((int) appBarLayout2.seslGetCollapsedHeight())) {
                                        float f2 = height;
                                        float abs =
                                                (Math.abs(appBarLayout2.getTop()) - (f2 * 0.35f))
                                                        * (150.0f / (0.17999999f * f2));
                                        double d = abs;
                                        if (d >= 0.0d && d <= 255.0d) {
                                            f = abs / 255.0f;
                                        } else if (d < 0.0d) {
                                            f = 0.0f;
                                        }
                                    }
                                    textView2.setAlpha(f);
                                }
                            }
                        });
            }
            Toolbar toolbar = (Toolbar) inflate.getParent();
            if (toolbar != null) {
                toolbar.setContentInsetsAbsolute(0, 0);
            }
        } else {
            Log.d("DeleteRulesPreference", "updateSelectMenu null!!");
        }
        String str = this.mSelectedId;
        if (str == null || str.length() == 0) {
            updateActionbarState$3();
        } else {
            this.mHandler.postDelayed(this.checkBoxRunnable, 100L);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacks(this.checkBoxRunnable);
        this.mBackend = null;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_done) {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!(preference instanceof CheckBoxPreference)) {
            return super.onPreferenceTreeClick(preference);
        }
        updateActionbarState$3();
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mRules = this.mBackend.getAutomaticZenRules();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("selected_id", this.mSelectedId);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        this.removeButtonLayout = (RelativeLayout) getActivity().findViewById(R.id.button_bar);
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView)
                        ((LayoutInflater) getActivity().getSystemService("layout_inflater"))
                                .inflate(
                                        R.layout.sec_bottom_naviagtion_delete_layout,
                                        (ViewGroup) this.removeButtonLayout,
                                        false);
        bottomNavigationView.menu.findItem(R.id.delete_btn).setTitle(R.string.delete);
        bottomNavigationView.selectedListener =
                new BottomNavigationView
                        .OnNavigationItemSelectedListener() { // from class:
                                                              // com.samsung.android.settings.notification.zen.SecZenModeDeleteRulesSetting.5
                    @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
                    public final boolean onNavigationItemSelected(MenuItem menuItem) {
                        SecZenModeDeleteRulesSetting secZenModeDeleteRulesSetting =
                                SecZenModeDeleteRulesSetting.this;
                        int size = secZenModeDeleteRulesSetting.mSelectionChecklist.size();
                        for (int i = 0; i < size; i++) {
                            if (((CheckBoxPreference)
                                            secZenModeDeleteRulesSetting.mSelectionChecklist.get(i))
                                    .mChecked) {
                                secZenModeDeleteRulesSetting.mBackend.removeZenRule(
                                        ((CheckBoxPreference)
                                                        secZenModeDeleteRulesSetting
                                                                .mSelectionChecklist.get(i))
                                                .getKey());
                            }
                        }
                        int size2 = secZenModeDeleteRulesSetting.mSelectionChecklist.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            ((CheckBoxPreference)
                                            secZenModeDeleteRulesSetting.mSelectionChecklist.get(
                                                    i2))
                                    .setChecked(false);
                        }
                        secZenModeDeleteRulesSetting.mSelectionChecklist.clear();
                        secZenModeDeleteRulesSetting.getActivity().onBackPressed();
                        return false;
                    }
                };
        this.removeButtonLayout.addView(bottomNavigationView);
    }

    public final void updateActionbarState$3() {
        int i;
        String str;
        ArrayList arrayList = this.mSelectionChecklist;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            i = 0;
            while (it.hasNext()) {
                CheckBoxPreference checkBoxPreference = (CheckBoxPreference) it.next();
                boolean z = checkBoxPreference.mChecked;
                if (z) {
                    i++;
                }
                if (!z
                        && (str = this.mSelectedId) != null
                        && str.equals(checkBoxPreference.getKey())) {
                    this.mSelectedId = ApnSettings.MVNO_NONE;
                }
            }
        } else {
            i = 0;
        }
        ActionBar actionBar = this.mActionBar;
        if (actionBar == null || actionBar.getCustomView() == null) {
            Log.d("DeleteRulesPreference", "updateActionbarState: no custom actionbar");
            return;
        }
        this.mSelectedZenRuleTextView.setText(
                i > 0
                        ? this.mContext
                                .getResources()
                                .getQuantityString(
                                        R.plurals.sec_zen_mode_selected_rules,
                                        i,
                                        Integer.valueOf(i))
                        : getActivity().getString(R.string.sec_zen_mode_select_schedule));
        getActivity().setTitle(this.mSelectedZenRuleTextView.getText());
        this.mSelectAllCheckbox.setChecked(i == this.mSelectionChecklist.size());
        if (i == 0) {
            this.removeButtonLayout.setVisibility(8);
        } else {
            this.removeButtonLayout.setVisibility(0);
        }
    }
}
