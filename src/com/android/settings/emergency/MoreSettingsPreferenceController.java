package com.android.settings.emergency;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MoreSettingsPreferenceController extends BasePreferenceController
        implements View.OnClickListener {
    private static final String EXTRA_KEY_ATTRIBUTION = "attribution";
    private static final String TAG = "MoreSettingsPrefCtrl";
    Intent mIntent;
    private LayoutPreference mPreference;

    public MoreSettingsPreferenceController(Context context, String str) {
        super(context, str);
        String string =
                this.mContext.getResources().getString(R.string.config_emergency_package_name);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        this.mIntent = new Intent("android.intent.action.MAIN").setPackage(string);
        List<ResolveInfo> queryIntentActivities =
                this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 1048576);
        if (queryIntentActivities == null || queryIntentActivities.isEmpty()) {
            this.mIntent = null;
        } else {
            this.mIntent.setClassName(string, queryIntentActivities.get(0).activityInfo.name);
        }
    }

    private static Bitmap convertToBitmap(Drawable drawable, int i, int i2) {
        if (drawable == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, i, i2);
        drawable.draw(canvas);
        return createBitmap;
    }

    private CharSequence getButtonText() {
        String string =
                this.mContext.getResources().getString(R.string.config_emergency_package_name);
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            return this.mContext.getString(
                    R.string.open_app_button,
                    packageManager.getApplicationInfo(string, 33280).loadLabel(packageManager));
        } catch (Exception unused) {
            Log.d(TAG, "Failed to get open app button text, falling back.");
            return ApnSettings.MVNO_NONE;
        }
    }

    private Drawable getIcon() {
        try {
            ApplicationInfo applicationInfo =
                    this.mContext
                            .getPackageManager()
                            .getApplicationInfo(
                                    this.mContext
                                            .getResources()
                                            .getString(R.string.config_emergency_package_name),
                                    33280);
            Context context = this.mContext;
            return getScaledDrawable(
                    context, Utils.getBadgedIcon(context, applicationInfo), 24, 24);
        } catch (Exception e) {
            Log.d(TAG, "Failed to get open app button icon", e);
            return null;
        }
    }

    private static Drawable getScaledDrawable(Context context, Drawable drawable, int i, int i2) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new BitmapDrawable(
                context.getResources(),
                convertToBitmap(
                        drawable,
                        (int) TypedValue.applyDimension(1, i, displayMetrics),
                        (int) TypedValue.applyDimension(1, i2, displayMetrics)));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = layoutPreference;
        Button button = (Button) layoutPreference.mRootView.findViewById(R.id.button);
        Drawable icon = getIcon();
        button.setText(getButtonText());
        if (icon != null) {
            button.setCompoundDrawablesWithIntrinsicBounds(
                    icon, (Drawable) null, (Drawable) null, (Drawable) null);
            button.setVisibility(0);
        }
        button.setOnClickListener(this);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIntent == null ? 3 : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl
                .getMetricsFeatureProvider()
                .logClickedPreference(this.mPreference, getMetricsCategory());
        Intent flags =
                new Intent(this.mIntent)
                        .addCategory("android.intent.category.LAUNCHER")
                        .setFlags(335544320);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_KEY_ATTRIBUTION, this.mContext.getPackageName());
        this.mContext.startActivity(flags, bundle);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
