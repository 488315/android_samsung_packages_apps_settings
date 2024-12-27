package com.samsung.android.settings.theftprotection;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.theftprotection.location.LocationData;
import com.samsung.android.settings.theftprotection.location.LocationManager$$ExternalSyntheticLambda0;
import com.samsung.android.settings.theftprotection.location.LocationStorage;
import com.samsung.android.settings.theftprotection.location.LocationType;
import com.samsung.android.settings.theftprotection.location.LocationValue;
import com.samsung.android.settings.theftprotection.logging.Log;
import com.samsung.android.settings.widget.SecUnclickablePreference;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MandatoryBiometricLocationFragment extends DashboardFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public SecUnclickablePreference mDescriptionPreference;
    public View mRecommendationView;
    public SecPreferenceCategory mTrustedPlaces;
    public final LocationType[] mLocationTypes = {
        LocationType.HOME, LocationType.WORK, LocationType.OTHER
    };
    public AlertDialog mCategoryDialog = null;
    public AlertDialog mReplaceDialog = null;
    public String mSelectedPlaceKey = null;

    public static LocationData getLocationDataFromActivityResult(Intent intent) {
        LocationData locationData = new LocationData();
        locationData.mName = intent.getStringExtra("place_name");
        locationData.mType = intent.getIntExtra("place_category", LocationType.UNKNOWN.value());
        locationData.mLatitude = intent.getDoubleExtra("latitude", Double.NaN);
        locationData.mLongitude = intent.getDoubleExtra("longitude", Double.NaN);
        locationData.mAddress = intent.getStringExtra("address");
        locationData.mSSID = intent.getStringExtra("wifi_ap_name");
        locationData.mBSSID = intent.getStringExtra("wifi_ap_bssid");
        locationData.mRadius = Rune.isChinaModel() ? 600 : 300;
        return locationData;
    }

    public static Intent getLocationInformationIntent(String str, LocationData locationData) {
        Intent intent = new Intent(str);
        intent.putExtra("place_category", locationData.mType);
        intent.putExtra("latitude", locationData.mLatitude);
        intent.putExtra("longitude", locationData.mLongitude);
        intent.putExtra("address", locationData.mAddress);
        intent.putExtra("wifi_ap_name", locationData.mSSID);
        intent.putExtra("wifi_ap_bssid", locationData.mBSSID);
        return intent;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "MandatoryBiometricLocationFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.mandatory_biometric_trusted_places_settings;
    }

    public final boolean isUnavailableName(LocationData locationData, boolean z) {
        if (locationData.mType == LocationType.OTHER.value()) {
            String str = locationData.mName;
            if (getResources().getString(LocationType.HOME.nameResID()).equals(str)
                    || getResources().getString(LocationType.WORK.nameResID()).equals(str)) {
                Toast.makeText(
                                this.mContext,
                                R.string.mandatory_biometric_trusted_places_toast_unavailable_name,
                                0)
                        .show();
                relaunchPlaceActivity(locationData, z);
                return true;
            }
            final String str2 = locationData.mName;
            final String str3 = locationData.mKey;
            final int i = 0;
            final int i2 = 1;
            if (LocationStorage.InstanceHolder.INSTANCE.loadLocationData().stream()
                    .filter(
                            new Predicate() { // from class:
                                              // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationFragment$$ExternalSyntheticLambda7
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    int i3 = i;
                                    String str4 = str3;
                                    LocationData locationData2 = (LocationData) obj;
                                    switch (i3) {
                                        case 0:
                                            int i4 = MandatoryBiometricLocationFragment.$r8$clinit;
                                            return LocationType.OTHER.value() == locationData2.mType
                                                    && !TextUtils.equals(locationData2.mKey, str4);
                                        default:
                                            int i5 = MandatoryBiometricLocationFragment.$r8$clinit;
                                            return TextUtils.equals(locationData2.mName, str4);
                                    }
                                }
                            })
                    .anyMatch(
                            new Predicate() { // from class:
                                              // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationFragment$$ExternalSyntheticLambda7
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    int i3 = i2;
                                    String str4 = str2;
                                    LocationData locationData2 = (LocationData) obj;
                                    switch (i3) {
                                        case 0:
                                            int i4 = MandatoryBiometricLocationFragment.$r8$clinit;
                                            return LocationType.OTHER.value() == locationData2.mType
                                                    && !TextUtils.equals(locationData2.mKey, str4);
                                        default:
                                            int i5 = MandatoryBiometricLocationFragment.$r8$clinit;
                                            return TextUtils.equals(locationData2.mName, str4);
                                    }
                                }
                            })) {
                Toast.makeText(
                                this.mContext,
                                R.string.mandatory_biometric_trusted_places_toast_duplicated_name,
                                0)
                        .show();
                relaunchPlaceActivity(locationData, z);
                return true;
            }
        }
        return false;
    }

    public final void launchPlaceActivity(int i, Intent intent) {
        try {
            startActivityForResult(intent, i);
        } catch (ActivityNotFoundException e) {
            Log.e("MandatoryBiometricLocationFragment", "Activity not found: " + e.getMessage());
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Log.d(
                "MandatoryBiometricLocationFragment",
                "onActivityResult: requestCode=" + i + ", resultCode=" + i2);
        switch (i) {
            case 1001:
                if (i2 == -1) {
                    AlertDialog alertDialog = this.mCategoryDialog;
                    if (alertDialog != null && alertDialog.isShowing()) {
                        Log.w("MandatoryBiometricLocationFragment", "CategoryDialog is showing.");
                        return;
                    }
                    String[] strArr =
                            (String[])
                                    Arrays.stream(this.mLocationTypes)
                                            .map(
                                                    new Function() { // from class:
                                                                     // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationFragment$$ExternalSyntheticLambda4
                                                        @Override // java.util.function.Function
                                                        public final Object apply(Object obj) {
                                                            MandatoryBiometricLocationFragment
                                                                    mandatoryBiometricLocationFragment =
                                                                            MandatoryBiometricLocationFragment
                                                                                    .this;
                                                            int i3 =
                                                                    MandatoryBiometricLocationFragment
                                                                            .$r8$clinit;
                                                            mandatoryBiometricLocationFragment
                                                                    .getClass();
                                                            return mandatoryBiometricLocationFragment
                                                                    .getString(
                                                                            ((LocationType) obj)
                                                                                    .nameResID());
                                                        }
                                                    })
                                            .toArray(
                                                    new MandatoryBiometricLocationFragment$$ExternalSyntheticLambda5());
                    AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
                    builder.setTitle(R.string.mandatory_biometric_trusted_places_add_place);
                    DialogInterface.OnClickListener onClickListener =
                            new DialogInterface.OnClickListener() { // from class:
                                // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationFragment$$ExternalSyntheticLambda6
                                @Override // android.content.DialogInterface.OnClickListener
                                public final void onClick(DialogInterface dialogInterface, int i3) {
                                    final MandatoryBiometricLocationFragment
                                            mandatoryBiometricLocationFragment =
                                                    MandatoryBiometricLocationFragment.this;
                                    LocationType locationType =
                                            mandatoryBiometricLocationFragment.mLocationTypes[i3];
                                    Log.d(
                                            "MandatoryBiometricLocationFragment",
                                            "onCategoryClicked: " + locationType.name());
                                    if (locationType.value() == LocationType.OTHER.value()) {
                                        Intent intent2 =
                                                new Intent(
                                                        "com.samsung.android.samsungaccount.action.LOCATION_PICKER");
                                        intent2.putExtra("place_category", locationType.value());
                                        intent2.putExtra("is_add", true);
                                        mandatoryBiometricLocationFragment.launchPlaceActivity(
                                                EnterpriseContainerCallback
                                                        .CONTAINER_PACKAGE_INFORMATION,
                                                intent2);
                                    } else {
                                        LocationStorage locationStorage =
                                                LocationStorage.InstanceHolder.INSTANCE;
                                        final int value = locationType.value();
                                        final LocationData locationData =
                                                (LocationData)
                                                        locationStorage.mLocationList.stream()
                                                                .filter(
                                                                        new Predicate() { // from
                                                                            // class:
                                                                            // com.samsung.android.settings.theftprotection.location.LocationStorage$$ExternalSyntheticLambda0
                                                                            @Override // java.util.function.Predicate
                                                                            public final boolean
                                                                                    test(
                                                                                            Object
                                                                                                    obj) {
                                                                                return ((LocationData)
                                                                                                        obj)
                                                                                                .mType
                                                                                        == value;
                                                                            }
                                                                        })
                                                                .findFirst()
                                                                .orElse(null);
                                        if (locationData == null) {
                                            Intent intent3 =
                                                    new Intent(
                                                            "com.samsung.android.samsungaccount.action.LOCATION_PICKER");
                                            intent3.putExtra(
                                                    "place_category", locationType.value());
                                            intent3.putExtra("is_add", true);
                                            mandatoryBiometricLocationFragment.launchPlaceActivity(
                                                    EnterpriseContainerCallback
                                                            .CONTAINER_PACKAGE_INFORMATION,
                                                    intent3);
                                        } else {
                                            AlertDialog alertDialog2 =
                                                    mandatoryBiometricLocationFragment
                                                            .mReplaceDialog;
                                            if (alertDialog2 == null || !alertDialog2.isShowing()) {
                                                AlertDialog.Builder builder2 =
                                                        new AlertDialog.Builder(
                                                                mandatoryBiometricLocationFragment
                                                                        .mContext);
                                                int i4 = locationData.mType;
                                                LocationType locationType2 = LocationType.HOME;
                                                builder2.setTitle(
                                                        i4 == locationType2.value()
                                                                ? R.string
                                                                        .mandatory_biometric_trusted_places_replace_home_title
                                                                : R.string
                                                                        .mandatory_biometric_trusted_places_replace_work_title);
                                                builder2.setMessage(
                                                        locationData.mType == locationType2.value()
                                                                ? R.string
                                                                        .mandatory_biometric_trusted_places_replace_home_description
                                                                : R.string
                                                                        .mandatory_biometric_trusted_places_replace_work_description);
                                                builder2.setNegativeButton(
                                                        R.string.cancel_button,
                                                        (DialogInterface.OnClickListener) null);
                                                builder2.setPositiveButton(
                                                        R.string
                                                                .mandatory_biometric_trusted_places_replace,
                                                        new DialogInterface
                                                                .OnClickListener() { // from class:
                                                            // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationFragment$$ExternalSyntheticLambda9
                                                            @Override // android.content.DialogInterface.OnClickListener
                                                            public final void onClick(
                                                                    DialogInterface
                                                                            dialogInterface2,
                                                                    int i5) {
                                                                MandatoryBiometricLocationFragment
                                                                        mandatoryBiometricLocationFragment2 =
                                                                                MandatoryBiometricLocationFragment
                                                                                        .this;
                                                                LocationData locationData2 =
                                                                        locationData;
                                                                int i6 =
                                                                        MandatoryBiometricLocationFragment
                                                                                .$r8$clinit;
                                                                mandatoryBiometricLocationFragment2
                                                                        .getClass();
                                                                Log.d(
                                                                        "MandatoryBiometricLocationFragment",
                                                                        "launchEditPlace: "
                                                                                + locationData2
                                                                                        .mKey);
                                                                mandatoryBiometricLocationFragment2
                                                                                .mSelectedPlaceKey =
                                                                        locationData2.mKey;
                                                                Intent locationInformationIntent =
                                                                        MandatoryBiometricLocationFragment
                                                                                .getLocationInformationIntent(
                                                                                        "com.samsung.android.samsungaccount.action.LOCATION_PICKER",
                                                                                        locationData2);
                                                                locationInformationIntent.putExtra(
                                                                        "is_add", false);
                                                                mandatoryBiometricLocationFragment2
                                                                        .launchPlaceActivity(
                                                                                EnterpriseContainerCallback
                                                                                        .CONTAINER_CHANGE_PWD_SUCCESSFUL,
                                                                                locationInformationIntent);
                                                            }
                                                        });
                                                AlertDialog create = builder2.create();
                                                mandatoryBiometricLocationFragment.mReplaceDialog =
                                                        create;
                                                create.show();
                                            } else {
                                                Log.w(
                                                        "MandatoryBiometricLocationFragment",
                                                        "ReplaceDialog is showing.");
                                            }
                                        }
                                    }
                                    dialogInterface.dismiss();
                                }
                            };
                    AlertController.AlertParams alertParams = builder.P;
                    alertParams.mItems = strArr;
                    alertParams.mOnClickListener = onClickListener;
                    AlertDialog create = builder.create();
                    this.mCategoryDialog = create;
                    create.show();
                    return;
                }
                return;
            case 1002:
                if (i2 == -1) {
                    SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
                    String name = MandatoryBiometricLocationDeleteFragment.class.getName();
                    SubSettingLauncher.LaunchRequest launchRequest =
                            subSettingLauncher.mLaunchRequest;
                    launchRequest.mDestinationName = name;
                    launchRequest.mSourceMetricsCategory = 0;
                    Intent intent2 = subSettingLauncher.toIntent();
                    intent2.putExtra("settings_homekey_mode", "mode_invisible");
                    new SubSettingLauncher(this.mContext).launchWithIntent(intent2);
                    return;
                }
                return;
            case 1003:
                if (i2 == -1) {
                    LocationData loadLocationData =
                            LocationStorage.InstanceHolder.INSTANCE.loadLocationData(
                                    this.mSelectedPlaceKey);
                    if (loadLocationData != null) {
                        Log.d(
                                "MandatoryBiometricLocationFragment",
                                "launchDetailPlace: " + loadLocationData.mKey);
                        this.mSelectedPlaceKey = loadLocationData.mKey;
                        Intent locationInformationIntent =
                                getLocationInformationIntent(
                                        "com.samsung.android.samsungaccount.action.SHOW_LOCATION",
                                        loadLocationData);
                        locationInformationIntent.putExtra("place_name", loadLocationData.mName);
                        launchPlaceActivity(
                                EnterpriseContainerCallback.CONTAINER_CHANGE_PWD_FAILED,
                                locationInformationIntent);
                        break;
                    } else {
                        Log.e("MandatoryBiometricLocationFragment", "LocationData is null");
                        return;
                    }
                }
                break;
            case VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI /* 1004 */:
                if (i2 == -1) {
                    if (intent == null) {
                        Log.e("MandatoryBiometricLocationFragment", "Intent is null");
                        return;
                    }
                    LocationData locationDataFromActivityResult =
                            getLocationDataFromActivityResult(intent);
                    locationDataFromActivityResult.mKey = Long.toString(System.currentTimeMillis());
                    LocationStorage.InstanceHolder.INSTANCE.addLocationData(
                            locationDataFromActivityResult);
                    View view = this.mRecommendationView;
                    if (view != null) {
                        view.setVisibility(8);
                        return;
                    }
                    return;
                }
                return;
            default:
                switch (i) {
                    case EnterpriseContainerCallback.CONTAINER_PACKAGE_INFORMATION /* 1011 */:
                        if (i2 == -1) {
                            if (intent != null) {
                                LocationData locationDataFromActivityResult2 =
                                        getLocationDataFromActivityResult(intent);
                                if (!isUnavailableName(locationDataFromActivityResult2, true)) {
                                    Context context = this.mContext;
                                    Log.d("LocationManager", "unregisterAllGeofence");
                                    LocationStorage locationStorage =
                                            LocationStorage.InstanceHolder.INSTANCE;
                                    ((ArrayList) locationStorage.loadLocationData())
                                            .forEach(
                                                    new LocationManager$$ExternalSyntheticLambda0(
                                                            context, 0));
                                    TheftProtectionUtils.updateInSignificantPlace(context, false);
                                    locationDataFromActivityResult2.mKey =
                                            Long.toString(System.currentTimeMillis());
                                    locationStorage.addLocationData(
                                            locationDataFromActivityResult2);
                                    Context context2 = this.mContext;
                                    if (TheftProtectionUtils.isMandatoryBiometricEnabled(
                                            context2)) {
                                        Log.d("LocationManager", "registerAllGeofence");
                                        ((ArrayList) locationStorage.loadLocationData())
                                                .forEach(
                                                        new LocationManager$$ExternalSyntheticLambda0(
                                                                context2, 1));
                                        break;
                                    }
                                }
                            } else {
                                Log.e("MandatoryBiometricLocationFragment", "Intent is null");
                                break;
                            }
                        }
                        break;
                }
                return;
        }
        if (i2 == -1) {
            if (intent == null) {
                Log.e("MandatoryBiometricLocationFragment", "Intent is null");
                return;
            }
            LocationStorage locationStorage2 = LocationStorage.InstanceHolder.INSTANCE;
            LocationData loadLocationData2 =
                    locationStorage2.loadLocationData(this.mSelectedPlaceKey);
            if (loadLocationData2 == null) {
                Log.e("MandatoryBiometricLocationFragment", "LocationData is null");
                return;
            }
            LocationData locationDataFromActivityResult3 =
                    getLocationDataFromActivityResult(intent);
            locationDataFromActivityResult3.mKey = loadLocationData2.mKey;
            if (isUnavailableName(locationDataFromActivityResult3, false)) {
                return;
            }
            Context context3 = this.mContext;
            Log.d("LocationManager", "unregisterAllGeofence");
            ((ArrayList) locationStorage2.loadLocationData())
                    .forEach(new LocationManager$$ExternalSyntheticLambda0(context3, 0));
            TheftProtectionUtils.updateInSignificantPlace(context3, false);
            locationStorage2.updateLocationData(locationDataFromActivityResult3);
            Context context4 = this.mContext;
            if (TheftProtectionUtils.isMandatoryBiometricEnabled(context4)) {
                Log.d("LocationManager", "registerAllGeofence");
                ((ArrayList) locationStorage2.loadLocationData())
                        .forEach(new LocationManager$$ExternalSyntheticLambda0(context4, 1));
            }
            this.mSelectedPlaceKey = null;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        this.mDescriptionPreference =
                (SecUnclickablePreference) getPreferenceScreen().findPreference("key_description");
        this.mTrustedPlaces =
                (SecPreferenceCategory) getPreferenceScreen().findPreference("trusted_places");
        setAnimationAllowed(false);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        MenuItem add = menu.add(0, 1, 0, R.string.add);
        add.setIcon(R.drawable.ic_add_location);
        add.setShowAsAction(2);
        MenuItem add2 = menu.add(0, 2, 0, R.string.common_remove);
        add2.setIcon(R.drawable.ic_remove_location);
        add2.setShowAsAction(2);
        ArrayList arrayList =
                (ArrayList) LocationStorage.InstanceHolder.INSTANCE.loadLocationData();
        if (arrayList.isEmpty()) {
            add2.setVisible(false);
        } else if (arrayList.size() >= 10) {
            add.setVisible(false);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 1) {
            TheftProtectionUtils.showProtectionPrompt(this, 1001);
        } else if (itemId == 2) {
            TheftProtectionUtils.showProtectionPrompt(this, 1002);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey() != null) {
            Log.d(
                    "MandatoryBiometricLocationFragment",
                    "onPreferenceTreeClick: " + preference.getKey());
            LocationData loadLocationData =
                    LocationStorage.InstanceHolder.INSTANCE.loadLocationData(preference.getKey());
            if (loadLocationData != null) {
                this.mSelectedPlaceKey = loadLocationData.mKey;
                TheftProtectionUtils.showProtectionPrompt(this, 1003);
            }
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }
        List loadLocationData = LocationStorage.InstanceHolder.INSTANCE.loadLocationData();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        if (((ArrayList) loadLocationData).isEmpty()) {
            return;
        }
        preferenceScreen.addPreference(this.mDescriptionPreference);
        preferenceScreen.addPreference(this.mTrustedPlaces);
        this.mTrustedPlaces.removeAll();
        loadLocationData.stream()
                .sorted(
                        Comparator.comparing(
                                new MandatoryBiometricLocationFragment$$ExternalSyntheticLambda0()))
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationFragment$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                MandatoryBiometricLocationFragment
                                        mandatoryBiometricLocationFragment =
                                                MandatoryBiometricLocationFragment.this;
                                LocationData locationData = (LocationData) obj;
                                int i = MandatoryBiometricLocationFragment.$r8$clinit;
                                mandatoryBiometricLocationFragment.getClass();
                                Preference preference =
                                        new Preference(mandatoryBiometricLocationFragment.mContext);
                                preference.setLayoutResource(
                                        R.layout.mandatory_biometric_place_layout);
                                preference.setIcon(
                                        LocationType.fromInt(locationData.mType).iconResID());
                                preference.setIconSpaceReserved(false);
                                preference.setTitle(locationData.mAddress);
                                preference.setSummary(locationData.mName);
                                preference.seslSetDividerStartOffset(
                                        mandatoryBiometricLocationFragment
                                                .mContext
                                                .getResources()
                                                .getDimensionPixelOffset(
                                                        R.dimen
                                                                .sec_app_list_item_icon_widget_width));
                                preference.setKey(locationData.mKey);
                                mandatoryBiometricLocationFragment.mTrustedPlaces.addPreference(
                                        preference);
                            }
                        });
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ViewGroup viewGroup = (ViewGroup) getListView().getParent();
        View inflate =
                getLayoutInflater()
                        .inflate(R.layout.mandatory_biometric_no_place_layout, viewGroup, false);
        viewGroup.addView(inflate);
        setEmptyView(inflate);
        if (getArguments() != null) {
            Context context = getContext();
            ArrayList arrayList = new ArrayList();
            LocationType locationType = LocationType.HOME;
            String valueOf = String.valueOf(locationType.value());
            LocationType locationType2 = LocationType.WORK;
            try {
                Cursor query =
                        context.getContentResolver()
                                .query(
                                        TheftProtectionConstants.SAMSUNG_ACCOUNT_PLACE_URI,
                                        null,
                                        "category=? OR category=?",
                                        new String[] {
                                            valueOf, String.valueOf(locationType2.value())
                                        },
                                        "category ASC");
                if (query != null) {
                    while (query.moveToNext()) {
                        try {
                            LocationValue locationValue = new LocationValue();
                            query.getString(query.getColumnIndexOrThrow("name"));
                            locationValue.address =
                                    query.getString(query.getColumnIndexOrThrow("address"));
                            locationValue.placeType =
                                    query.getInt(query.getColumnIndexOrThrow("category"));
                            locationValue.latitude =
                                    query.getDouble(query.getColumnIndexOrThrow("latitude"));
                            locationValue.longitude =
                                    query.getDouble(query.getColumnIndexOrThrow("longitude"));
                            locationValue.ssid =
                                    query.getString(query.getColumnIndexOrThrow("wifi_name"));
                            locationValue.bssid =
                                    query.getString(query.getColumnIndexOrThrow("wifi_bssid"));
                            arrayList.add(locationValue);
                        } finally {
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e) {
                Log.e("TheftProtectionUtils", "getSamsungAccountPlaces : " + e.getMessage());
            }
            if (arrayList.isEmpty()) {
                Log.d("MandatoryBiometricLocationFragment", "setRecommendationView: No places");
                return;
            }
            final LocationValue locationValue2 = (LocationValue) arrayList.get(0);
            View inflate2 =
                    getLayoutInflater()
                            .inflate(
                                    R.layout.mandatory_biometric_place_header_layout,
                                    viewGroup,
                                    false);
            this.mRecommendationView = inflate2;
            setPinnedHeaderView(inflate2);
            TextView textView =
                    (TextView) this.mRecommendationView.findViewById(R.id.recommend_title);
            TextView textView2 =
                    (TextView) this.mRecommendationView.findViewById(android.R.id.summary);
            TextView textView3 =
                    (TextView) this.mRecommendationView.findViewById(android.R.id.title);
            ImageView imageView =
                    (ImageView) this.mRecommendationView.findViewById(android.R.id.icon);
            if (locationType.value() == locationValue2.placeType) {
                textView.setText(R.string.mandatory_biometric_trusted_places_recommend_home_title);
                textView2.setText(locationType.nameResID());
                imageView.setImageResource(locationType.iconResID());
            } else {
                textView.setText(R.string.mandatory_biometric_trusted_places_recommend_work_title);
                textView2.setText(locationType2.nameResID());
                imageView.setImageResource(locationType2.iconResID());
            }
            textView3.setText(locationValue2.address);
            this.mRecommendationView
                    .findViewById(R.id.close_button)
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationFragment$$ExternalSyntheticLambda2
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    MandatoryBiometricLocationFragment.this.mRecommendationView
                                            .setVisibility(8);
                                }
                            });
            this.mRecommendationView
                    .findViewById(R.id.add_button)
                    .setOnClickListener(
                            new View.OnClickListener() { // from class:
                                // com.samsung.android.settings.theftprotection.MandatoryBiometricLocationFragment$$ExternalSyntheticLambda3
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    MandatoryBiometricLocationFragment
                                            mandatoryBiometricLocationFragment =
                                                    MandatoryBiometricLocationFragment.this;
                                    LocationValue locationValue3 = locationValue2;
                                    int i = MandatoryBiometricLocationFragment.$r8$clinit;
                                    mandatoryBiometricLocationFragment.getClass();
                                    Log.d(
                                            "MandatoryBiometricLocationFragment",
                                            "launchAddRecommendationPlace: "
                                                    + locationValue3.placeType);
                                    Intent intent =
                                            new Intent(
                                                    "com.samsung.android.samsungaccount.action.LOCATION_PICKER");
                                    intent.putExtra("place_category", locationValue3.placeType);
                                    intent.putExtra("latitude", locationValue3.latitude);
                                    intent.putExtra("longitude", locationValue3.longitude);
                                    intent.putExtra("address", locationValue3.address);
                                    intent.putExtra("wifi_ap_name", locationValue3.ssid);
                                    intent.putExtra("wifi_ap_bssid", locationValue3.bssid);
                                    intent.putExtra("is_add", true);
                                    mandatoryBiometricLocationFragment.launchPlaceActivity(
                                            VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI,
                                            intent);
                                }
                            });
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(Utils.getListHorizontalPadding(getContext()), -1);
            this.mRecommendationView
                    .findViewById(R.id.container_left_space)
                    .setLayoutParams(layoutParams);
            this.mRecommendationView
                    .findViewById(R.id.container_right_space)
                    .setLayoutParams(layoutParams);
            View findViewById = this.mRecommendationView.findViewById(R.id.recommend_container);
            findViewById.semSetRoundedCorners(15);
            findViewById.semSetRoundedCornerColor(
                    15, getContext().getColor(R.color.sec_widget_round_and_bgcolor));
        }
    }

    public final void relaunchPlaceActivity(LocationData locationData, boolean z) {
        Log.d(
                "MandatoryBiometricLocationFragment",
                "relaunchAddPlace: name=" + locationData.mName + ", addMode=" + z);
        Intent locationInformationIntent =
                getLocationInformationIntent(
                        "com.samsung.android.samsungaccount.action.LOCATION_PICKER", locationData);
        locationInformationIntent.putExtra("place_name", locationData.mName);
        locationInformationIntent.putExtra("is_add", z);
        launchPlaceActivity(
                z
                        ? EnterpriseContainerCallback.CONTAINER_PACKAGE_INFORMATION
                        : EnterpriseContainerCallback.CONTAINER_CHANGE_PWD_FAILED,
                locationInformationIntent);
    }
}
