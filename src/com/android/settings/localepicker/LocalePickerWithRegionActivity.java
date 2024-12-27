package com.android.settings.localepicker;

import android.content.Intent;
import android.os.Bundle;
import android.os.LocaleList;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.android.internal.app.LocalePickerWithRegion;
import com.android.internal.app.LocaleStore;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SettingsBaseActivity;

import com.samsung.android.knox.ex.peripheral.PeripheralConstants;

import java.io.Serializable;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocalePickerWithRegionActivity extends SettingsBaseActivity
        implements LocalePickerWithRegion.LocaleSelectedListener, MenuItem.OnActionExpandListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public LocalePickerWithRegion mSelector;

    @Override // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        LocaleList localeList;
        super.onCreate(bundle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        disableExtendedAppBar();
        setTitle(R.string.add_a_language);
        if (Settings.Global.getInt(getContentResolver(), "device_demo_mode", 0) == 1) {
            Bundle extras = getIntent().getExtras();
            LocaleList localeList2 =
                    extras == null
                            ? null
                            : (LocaleList)
                                    extras.getParcelable(
                                            "android.provider.extra.EXPLICIT_LOCALES",
                                            LocaleList.class);
            Log.i("LocalePickerWithRegionActivity", "Has explicit locales : " + localeList2);
            localeList = localeList2;
        } else {
            localeList = null;
        }
        this.mSelector =
                LocalePickerWithRegion.createLanguagePicker(
                        this, this, false, localeList, (String) null, this, 1);
        getFragmentManager()
                .beginTransaction()
                .setTransition(PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_NOT_FOUND)
                .replace(R.id.content_frame, this.mSelector)
                .addToBackStack("localeListEditor")
                .commit();
        getOnBackPressedDispatcher()
                .addCallback(
                        this,
                        new OnBackPressedCallback() { // from class:
                                                      // com.android.settings.localepicker.LocalePickerWithRegionActivity.1
                            @Override // androidx.activity.OnBackPressedCallback
                            public final void handleOnBackPressed() {
                                int i = LocalePickerWithRegionActivity.$r8$clinit;
                                LocalePickerWithRegionActivity localePickerWithRegionActivity =
                                        LocalePickerWithRegionActivity.this;
                                if (localePickerWithRegionActivity
                                                .getFragmentManager()
                                                .getBackStackEntryCount()
                                        > 1) {
                                    localePickerWithRegionActivity
                                            .getFragmentManager()
                                            .popBackStack();
                                } else {
                                    localePickerWithRegionActivity.setResult(0);
                                    localePickerWithRegionActivity.finish();
                                }
                                if (localePickerWithRegionActivity
                                                .getFragmentManager()
                                                .getBackStackEntryCount()
                                        > 1) {
                                    return;
                                }
                                setEnabled(false);
                                localePickerWithRegionActivity
                                        .getOnBackPressedDispatcher()
                                        .onBackPressed();
                            }
                        });
        int listHorizontalPadding = Utils.getListHorizontalPadding(getApplicationContext());
        View findViewById = findViewById(R.id.content_layout);
        findViewById.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        findViewById.setBackgroundColor(
                getResources().getColor(R.color.sesl_round_and_bgcolor_light, null));
        findViewById(R.id.content_frame)
                .setBackgroundColor(
                        getResources().getColor(R.color.list_item_background_color, null));
        View findViewById2 = findViewById(R.id.round_corner);
        if (findViewById2 != null) {
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) findViewById2.getLayoutParams();
            layoutParams.setMarginsRelative(listHorizontalPadding, 0, listHorizontalPadding, 0);
            findViewById2.setLayoutParams(layoutParams);
            findViewById2.semSetRoundedCorners(12);
            findViewById2.semSetRoundedCornerColor(
                    12, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        }
    }

    public final void onLocaleSelected(LocaleStore.LocaleInfo localeInfo) {
        Intent intent = new Intent();
        intent.putExtra("localeInfo", (Serializable) localeInfo);
        setResult(-1, intent);
        finish();
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        this.mAppBarLayout.setExpanded(false, false, true);
        ListView listView = this.mSelector.getListView();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setNestedScrollingEnabled(listView, true);
        return true;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        this.mAppBarLayout.setExpanded(false, false, true);
        ListView listView = this.mSelector.getListView();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setNestedScrollingEnabled(listView, false);
        return true;
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            setResult(0);
            finish();
        }
        return true;
    }
}
