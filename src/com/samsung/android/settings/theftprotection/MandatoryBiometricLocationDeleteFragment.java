package com.samsung.android.settings.theftprotection;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
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
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.core.SettingsBaseActivity;
import com.android.settings.dashboard.DashboardFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.theftprotection.location.LocationData;
import com.samsung.android.settings.theftprotection.location.LocationManager$$ExternalSyntheticLambda0;
import com.samsung.android.settings.theftprotection.location.LocationStorage;
import com.samsung.android.settings.theftprotection.location.LocationType;
import com.samsung.android.settings.theftprotection.logging.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MandatoryBiometricLocationDeleteFragment extends DashboardFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ActionBar mActionBar;
    public RelativeLayout mRemoveButtonLayout;
    public PreferenceCategory mTrustedPlacesCategory;
    public CheckBox mSelectAll = null;
    public TextView mSelectedCountTextView = null;
    public List mCheckBoxList = null;

    public final void createActionbarLayout() {
        this.mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        View inflate =
                ((LayoutInflater) getActivity().getSystemService("layout_inflater"))
                        .inflate(
                                R.layout.mandatory_biometric_edit_place_actionbar_layout,
                                (ViewGroup) null);
        if (this.mActionBar != null) {
            ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(16);
            this.mActionBar.setDisplayShowCustomEnabled(true);
            this.mActionBar.setCustomView(inflate, layoutParams);
            ((Toolbar) inflate.getParent()).setContentInsetsAbsolute(0, 0);
            TextView textView = (TextView) inflate.findViewById(R.id.select_all_textview);
            textView.setSelected(true);
            final int i = 0;
            textView.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda2
                        public final /* synthetic */ MandatoryBiometricLocationDeleteFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i2 = i;
                            MandatoryBiometricLocationDeleteFragment
                                    mandatoryBiometricLocationDeleteFragment = this.f$0;
                            switch (i2) {
                                case 0:
                                    mandatoryBiometricLocationDeleteFragment.mSelectAll.setChecked(
                                            !r3.isChecked());
                                    boolean isChecked =
                                            mandatoryBiometricLocationDeleteFragment.mSelectAll
                                                    .isChecked();
                                    List list =
                                            mandatoryBiometricLocationDeleteFragment.mCheckBoxList;
                                    if (list != null) {
                                        Iterator it = ((ArrayList) list).iterator();
                                        while (it.hasNext()) {
                                            ((CheckBoxPreference) it.next()).setChecked(isChecked);
                                        }
                                    }
                                    mandatoryBiometricLocationDeleteFragment.updateActionbarState();
                                    break;
                                default:
                                    boolean isChecked2 =
                                            mandatoryBiometricLocationDeleteFragment.mSelectAll
                                                    .isChecked();
                                    List list2 =
                                            mandatoryBiometricLocationDeleteFragment.mCheckBoxList;
                                    if (list2 != null) {
                                        Iterator it2 = ((ArrayList) list2).iterator();
                                        while (it2.hasNext()) {
                                            ((CheckBoxPreference) it2.next())
                                                    .setChecked(isChecked2);
                                        }
                                    }
                                    mandatoryBiometricLocationDeleteFragment.updateActionbarState();
                                    break;
                            }
                        }
                    });
            CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.select_all_checkbox);
            this.mSelectAll = checkBox;
            final int i2 = 1;
            checkBox.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda2
                        public final /* synthetic */ MandatoryBiometricLocationDeleteFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i22 = i2;
                            MandatoryBiometricLocationDeleteFragment
                                    mandatoryBiometricLocationDeleteFragment = this.f$0;
                            switch (i22) {
                                case 0:
                                    mandatoryBiometricLocationDeleteFragment.mSelectAll.setChecked(
                                            !r3.isChecked());
                                    boolean isChecked =
                                            mandatoryBiometricLocationDeleteFragment.mSelectAll
                                                    .isChecked();
                                    List list =
                                            mandatoryBiometricLocationDeleteFragment.mCheckBoxList;
                                    if (list != null) {
                                        Iterator it = ((ArrayList) list).iterator();
                                        while (it.hasNext()) {
                                            ((CheckBoxPreference) it.next()).setChecked(isChecked);
                                        }
                                    }
                                    mandatoryBiometricLocationDeleteFragment.updateActionbarState();
                                    break;
                                default:
                                    boolean isChecked2 =
                                            mandatoryBiometricLocationDeleteFragment.mSelectAll
                                                    .isChecked();
                                    List list2 =
                                            mandatoryBiometricLocationDeleteFragment.mCheckBoxList;
                                    if (list2 != null) {
                                        Iterator it2 = ((ArrayList) list2).iterator();
                                        while (it2.hasNext()) {
                                            ((CheckBoxPreference) it2.next())
                                                    .setChecked(isChecked2);
                                        }
                                    }
                                    mandatoryBiometricLocationDeleteFragment.updateActionbarState();
                                    break;
                            }
                        }
                    });
            this.mSelectedCountTextView =
                    (TextView) inflate.findViewById(R.id.selected_items_count);
        }
    }

    public final int getCheckedCount() {
        List list = this.mCheckBoxList;
        int i = 0;
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (((CheckBoxPreference) it.next()).mChecked) {
                    i++;
                }
            }
        }
        return i;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final String getLogTag() {
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.mandatory_biometric_edit_place_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        createActionbarLayout();
        updateActionbarState();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mTrustedPlacesCategory =
                (PreferenceCategory) getPreferenceScreen().findPreference("trusted_places");
        this.mCheckBoxList = new ArrayList();
        LocationStorage.InstanceHolder.INSTANCE.loadLocationData().stream()
                .sorted(
                        Comparator.comparing(
                                new MandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda0()))
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                MandatoryBiometricLocationDeleteFragment
                                        mandatoryBiometricLocationDeleteFragment =
                                                MandatoryBiometricLocationDeleteFragment.this;
                                LocationData locationData = (LocationData) obj;
                                int i = MandatoryBiometricLocationDeleteFragment.$r8$clinit;
                                mandatoryBiometricLocationDeleteFragment.getClass();
                                CheckBoxPreference checkBoxPreference =
                                        new CheckBoxPreference(
                                                mandatoryBiometricLocationDeleteFragment
                                                        .getContext(),
                                                null);
                                checkBoxPreference.setLayoutResource(
                                        R.layout.mandatory_biometric_place_layout);
                                checkBoxPreference.setIcon(
                                        LocationType.fromInt(locationData.mType).iconResID());
                                checkBoxPreference.setIconSpaceReserved(false);
                                checkBoxPreference.setTitle(locationData.mAddress);
                                checkBoxPreference.setSummary(locationData.mName);
                                checkBoxPreference.setPersistent();
                                checkBoxPreference.setKey(locationData.mKey);
                                mandatoryBiometricLocationDeleteFragment.mTrustedPlacesCategory
                                        .addPreference(checkBoxPreference);
                                ((ArrayList) mandatoryBiometricLocationDeleteFragment.mCheckBoxList)
                                        .add(checkBoxPreference);
                            }
                        });
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        createActionbarLayout();
        updateActionbarState();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        SettingsBaseActivity settingsBaseActivity = (SettingsBaseActivity) getActivity();
        settingsBaseActivity.disableExtendedAppBar();
        RelativeLayout relativeLayout =
                (RelativeLayout) settingsBaseActivity.findViewById(R.id.button_bar);
        this.mRemoveButtonLayout = relativeLayout;
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView)
                        layoutInflater.inflate(
                                R.layout.sec_bottom_naviagtion_delete_layout,
                                (ViewGroup) relativeLayout,
                                false);
        bottomNavigationView.selectedListener =
                new BottomNavigationView
                        .OnNavigationItemSelectedListener() { // from class:
                                                              // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda4
                    @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
                    public final boolean onNavigationItemSelected(MenuItem menuItem) {
                        int i = MandatoryBiometricLocationDeleteFragment.$r8$clinit;
                        final MandatoryBiometricLocationDeleteFragment
                                mandatoryBiometricLocationDeleteFragment =
                                        MandatoryBiometricLocationDeleteFragment.this;
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(
                                        mandatoryBiometricLocationDeleteFragment.getContext());
                        MandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda5
                                mandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda5 =
                                        new MandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda5();
                        AlertController.AlertParams alertParams = builder.P;
                        alertParams.mOnDismissListener =
                                mandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda5;
                        builder.setNegativeButton(
                                R.string.cancel,
                                new MandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda6());
                        builder.setPositiveButton(
                                R.string.delete,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda7
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i2) {
                                        MandatoryBiometricLocationDeleteFragment
                                                mandatoryBiometricLocationDeleteFragment2 =
                                                        MandatoryBiometricLocationDeleteFragment
                                                                .this;
                                        int i3 =
                                                MandatoryBiometricLocationDeleteFragment.$r8$clinit;
                                        Context context =
                                                mandatoryBiometricLocationDeleteFragment2
                                                        .getContext();
                                        Log.d("LocationManager", "unregisterAllGeofence");
                                        LocationStorage locationStorage =
                                                LocationStorage.InstanceHolder.INSTANCE;
                                        ((ArrayList) locationStorage.loadLocationData())
                                                .forEach(
                                                        new LocationManager$$ExternalSyntheticLambda0(
                                                                context, 0));
                                        TheftProtectionUtils.updateInSignificantPlace(
                                                context, false);
                                        ((ArrayList)
                                                        mandatoryBiometricLocationDeleteFragment2
                                                                .mCheckBoxList)
                                                .forEach(
                                                        new MandatoryBiometricLocationDeleteFragment$$ExternalSyntheticLambda8());
                                        Context context2 =
                                                mandatoryBiometricLocationDeleteFragment2
                                                        .getContext();
                                        if (TheftProtectionUtils.isMandatoryBiometricEnabled(
                                                context2)) {
                                            Log.d("LocationManager", "registerAllGeofence");
                                            ((ArrayList) locationStorage.loadLocationData())
                                                    .forEach(
                                                            new LocationManager$$ExternalSyntheticLambda0(
                                                                    context2, 1));
                                        }
                                        mandatoryBiometricLocationDeleteFragment2.finish();
                                    }
                                });
                        int checkedCount =
                                mandatoryBiometricLocationDeleteFragment.getCheckedCount();
                        if (checkedCount
                                == ((ArrayList)
                                                mandatoryBiometricLocationDeleteFragment
                                                        .mCheckBoxList)
                                        .size()) {
                            alertParams.mMessage =
                                    mandatoryBiometricLocationDeleteFragment
                                            .getResources()
                                            .getQuantityString(
                                                    R.plurals
                                                            .mandatory_biometric_trusted_places_delete_dialog_all,
                                                    checkedCount);
                        }
                        alertParams.mTitle =
                                mandatoryBiometricLocationDeleteFragment
                                        .getResources()
                                        .getQuantityString(
                                                R.plurals
                                                        .mandatory_biometric_trusted_places_delete_dialog_title,
                                                checkedCount,
                                                Integer.valueOf(checkedCount));
                        AlertDialog create = builder.create();
                        create.show();
                        create.getButton(-1)
                                .setTextColor(
                                        mandatoryBiometricLocationDeleteFragment
                                                .getContext()
                                                .getColor(R.color.sesl_error_color));
                        return false;
                    }
                };
        this.mRemoveButtonLayout.addView(bottomNavigationView);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!(preference instanceof CheckBoxPreference)) {
            return super.onPreferenceTreeClick(preference);
        }
        updateActionbarState();
        return true;
    }

    public final void updateActionbarState() {
        int checkedCount = getCheckedCount();
        if (checkedCount > 0) {
            this.mSelectedCountTextView.setText(
                    getResources()
                            .getQuantityString(
                                    R.plurals.mandatory_biometric_trusted_places_delete_title_count,
                                    checkedCount,
                                    Integer.valueOf(checkedCount)));
            this.mRemoveButtonLayout.setVisibility(0);
        } else {
            this.mSelectedCountTextView.setText(
                    R.string.mandatory_biometric_trusted_places_delete_title);
            this.mRemoveButtonLayout.setVisibility(8);
        }
        this.mSelectAll.setChecked(checkedCount == this.mCheckBoxList.size());
    }
}
