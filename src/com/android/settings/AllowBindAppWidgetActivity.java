package com.android.settings;

import android.R;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class AllowBindAppWidgetActivity extends AlertActivity
        implements DialogInterface.OnClickListener {
    public CheckBox mAlwaysUse;
    public int mAppWidgetId;
    public AppWidgetManager mAppWidgetManager;
    public Bundle mBindOptions;
    public String mCallingPackage;
    public boolean mClicked;
    public ComponentName mComponentName;
    public UserHandle mProfile;

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2;
        ComponentName componentName;
        this.mClicked = true;
        if (i == -1
                && (i2 = this.mAppWidgetId) != -1
                && (componentName = this.mComponentName) != null
                && this.mCallingPackage != null) {
            try {
                if (this.mAppWidgetManager.bindAppWidgetIdIfAllowed(
                        i2, this.mProfile, componentName, this.mBindOptions)) {
                    Intent intent = new Intent();
                    intent.putExtra("appWidgetId", this.mAppWidgetId);
                    setResult(-1, intent);
                }
            } catch (Exception unused) {
                Log.v(
                        "BIND_APPWIDGET",
                        "Error binding widget with id "
                                + this.mAppWidgetId
                                + " and component "
                                + this.mComponentName);
            }
            boolean isChecked = this.mAlwaysUse.isChecked();
            if (isChecked
                    != this.mAppWidgetManager.hasBindAppWidgetPermission(this.mCallingPackage)) {
                this.mAppWidgetManager.setBindAppWidgetPermission(this.mCallingPackage, isChecked);
            }
        }
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        CharSequence charSequence;
        super.onCreate(bundle);
        getWindow().addPrivateFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        setResult(0);
        Intent intent = getIntent();
        String str = ApnSettings.MVNO_NONE;
        if (intent != null) {
            try {
                this.mAppWidgetId = intent.getIntExtra("appWidgetId", -1);
                UserHandle userHandle =
                        (UserHandle) intent.getParcelableExtra("appWidgetProviderProfile");
                this.mProfile = userHandle;
                if (userHandle == null) {
                    this.mProfile = Process.myUserHandle();
                }
                this.mComponentName =
                        (ComponentName) intent.getParcelableExtra("appWidgetProvider");
                this.mBindOptions = (Bundle) intent.getParcelableExtra("appWidgetOptions");
                this.mCallingPackage = getCallingPackage();
                PackageManager packageManager = getPackageManager();
                charSequence =
                        packageManager.getApplicationLabel(
                                packageManager.getApplicationInfo(this.mCallingPackage, 0));
            } catch (Exception unused) {
                this.mAppWidgetId = -1;
                this.mComponentName = null;
                this.mCallingPackage = null;
                Log.v("BIND_APPWIDGET", "Error getting parameters");
                finish();
                return;
            }
        } else {
            charSequence = ApnSettings.MVNO_NONE;
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        this.mAppWidgetManager = appWidgetManager;
        Iterator<AppWidgetProviderInfo> it = appWidgetManager.getInstalledProviders().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            AppWidgetProviderInfo next = it.next();
            if (next.provider.equals(this.mComponentName)) {
                str = next.loadLabel(getPackageManager());
                break;
            }
        }
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        alertParams.mTitle = getString(R.string.allow_bind_app_widget_activity_allow_bind_title);
        alertParams.mMessage =
                getString(
                        R.string.allow_bind_app_widget_activity_allow_bind,
                        new Object[] {charSequence, str});
        alertParams.mPositiveButtonText = getString(R.string.create);
        alertParams.mNegativeButtonText = getString(R.string.cancel);
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonListener = this;
        View inflate =
                ((LayoutInflater) getSystemService("layout_inflater"))
                        .inflate(R.layout.app_not_authorized, (ViewGroup) null);
        alertParams.mView = inflate;
        CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.ampm_layout);
        this.mAlwaysUse = checkBox;
        checkBox.setText(
                getString(
                        R.string.allow_bind_app_widget_activity_always_allow_bind,
                        new Object[] {charSequence}));
        CheckBox checkBox2 = this.mAlwaysUse;
        checkBox2.setPadding(
                checkBox2.getPaddingLeft(),
                this.mAlwaysUse.getPaddingTop(),
                this.mAlwaysUse.getPaddingRight(),
                (int)
                        (getResources()
                                        .getDimension(
                                                R.dimen
                                                        .bind_app_widget_dialog_checkbox_bottom_padding)
                                + this.mAlwaysUse.getPaddingBottom()));
        this.mAlwaysUse.setChecked(
                this.mAppWidgetManager.hasBindAppWidgetPermission(
                        this.mCallingPackage, this.mProfile.getIdentifier()));
        setupAlert();
    }

    public final void onPause() {
        if (!this.mClicked) {
            finish();
        }
        super.onPause();
    }
}
