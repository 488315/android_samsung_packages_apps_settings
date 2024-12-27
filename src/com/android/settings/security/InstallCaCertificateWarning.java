package com.android.settings.security;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InstallCaCertificateWarning extends SettingsPreferenceFragment {
    public View mContentView;
    public Context mContext;
    public LinearLayout mFooterButtonContainer;
    public Button mFooterButtonNegative;
    public Button mFooterButtonPositive;
    public ArrayList mFooterButtonViews;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("InstallCaCertificateWarning", "onAttach, setTitle");
        activity.setTitle(R.string.ca_certificate);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContentView =
                layoutInflater.inflate(
                        R.layout.sec_ca_certificate_warning_layout, (ViewGroup) null);
        Log.i("InstallCaCertificateWarning", "onCreateView");
        this.mContext = getContext();
        getActivity()
                .getWindow()
                .addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        final LinearLayout linearLayout =
                (LinearLayout) this.mContentView.findViewById(R.id.setup_wizard_layout);
        this.mFooterButtonContainer =
                (LinearLayout) this.mContentView.findViewById(R.id.button_container);
        Button button = (Button) this.mContentView.findViewById(R.id.button_positive);
        this.mFooterButtonPositive = button;
        final int i = 1;
        button.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.security.InstallCaCertificateWarning$$ExternalSyntheticLambda0
                    public final /* synthetic */ InstallCaCertificateWarning f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i2 = i;
                        InstallCaCertificateWarning installCaCertificateWarning = this.f$0;
                        switch (i2) {
                            case 0:
                                Toast.makeText(
                                                installCaCertificateWarning.mContext,
                                                R.string.cert_not_installed,
                                                0)
                                        .show();
                                installCaCertificateWarning.finish();
                                break;
                            default:
                                installCaCertificateWarning.getClass();
                                Intent intent = new Intent();
                                intent.setAction("android.credentials.INSTALL");
                                intent.setPackage("com.android.certinstaller");
                                intent.putExtra("certificate_install_usage", "ca");
                                installCaCertificateWarning.startActivity(intent);
                                installCaCertificateWarning.finish();
                                break;
                        }
                    }
                });
        this.mFooterButtonPositive.setFilterTouchesWhenObscured(true);
        Button button2 = (Button) this.mContentView.findViewById(R.id.button_negative);
        this.mFooterButtonNegative = button2;
        final int i2 = 0;
        button2.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.security.InstallCaCertificateWarning$$ExternalSyntheticLambda0
                    public final /* synthetic */ InstallCaCertificateWarning f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i22 = i2;
                        InstallCaCertificateWarning installCaCertificateWarning = this.f$0;
                        switch (i22) {
                            case 0:
                                Toast.makeText(
                                                installCaCertificateWarning.mContext,
                                                R.string.cert_not_installed,
                                                0)
                                        .show();
                                installCaCertificateWarning.finish();
                                break;
                            default:
                                installCaCertificateWarning.getClass();
                                Intent intent = new Intent();
                                intent.setAction("android.credentials.INSTALL");
                                intent.setPackage("com.android.certinstaller");
                                intent.putExtra("certificate_install_usage", "ca");
                                installCaCertificateWarning.startActivity(intent);
                                installCaCertificateWarning.finish();
                                break;
                        }
                    }
                });
        this.mFooterButtonNegative.setFilterTouchesWhenObscured(true);
        this.mFooterButtonViews = new ArrayList();
        for (int i3 = 0; i3 < this.mFooterButtonContainer.getChildCount(); i3++) {
            this.mFooterButtonViews.add(this.mFooterButtonContainer.getChildAt(i3));
        }
        linearLayout
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        new ViewTreeObserver
                                .OnGlobalLayoutListener() { // from class:
                                                            // com.android.settings.security.InstallCaCertificateWarning.1
                            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                            public final void onGlobalLayout() {
                                InstallCaCertificateWarning.this.orderingFooterButton();
                                linearLayout
                                        .getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                            }
                        });
        return this.mContentView;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.i("InstallCaCertificateWarning", "onResume");
        orderingFooterButton();
    }

    public final void orderingFooterButton() {
        this.mFooterButtonContainer.removeAllViews();
        int measuredWidth = this.mFooterButtonPositive.getMeasuredWidth();
        int measuredWidth2 = this.mFooterButtonNegative.getMeasuredWidth();
        int measuredWidth3 = this.mFooterButtonContainer.getMeasuredWidth();
        int dimensionPixelOffset =
                this.mContext
                        .getResources()
                        .getDimensionPixelOffset(R.dimen.sec_ca_cert_warning_footer_padding);
        this.mFooterButtonPositive.setPadding(dimensionPixelOffset, 0, dimensionPixelOffset, 0);
        this.mFooterButtonNegative.setPadding(dimensionPixelOffset, 0, dimensionPixelOffset, 0);
        if (measuredWidth + measuredWidth2 > measuredWidth3) {
            for (int size = this.mFooterButtonViews.size() - 1; size >= 0; size--) {
                this.mFooterButtonContainer.addView((View) this.mFooterButtonViews.get(size));
            }
            this.mFooterButtonContainer.setOrientation(1);
        } else {
            for (int i = 0; i < this.mFooterButtonViews.size(); i++) {
                this.mFooterButtonContainer.addView((View) this.mFooterButtonViews.get(i));
            }
            this.mFooterButtonContainer.setOrientation(0);
        }
        this.mFooterButtonContainer.invalidate();
    }
}
