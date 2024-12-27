package com.android.settings.password;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settingslib.applications.RecentAppOpsAccess;

import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import com.google.android.setupdesign.util.TextViewPartnerStyler;
import com.google.android.setupdesign.util.ThemeHelper;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.Locale;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ForgotPasswordActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        char c;
        int i;
        int i2;
        final int i3 = 1;
        final int i4 = 0;
        super.onCreate(bundle);
        int intExtra = getIntent().getIntExtra("android.intent.extra.USER_ID", -1);
        if (intExtra < 0) {
            Log.e("ForgotPasswordActivity", "No valid userId supplied, exiting");
            finish();
            return;
        }
        ThemeHelper.trySetDynamicColor(this);
        setContentView(R.layout.sec_knox_forgot_password_activity);
        View decorView = getWindow().getDecorView();
        ImageView imageView = (ImageView) decorView.findViewById(R.id.background_image);
        if (imageView != null) {
            imageView.setImageDrawable(
                    KnoxUtils.getInflatedLayoutType(this) == 1000
                            ? getResources().getDrawable(R.drawable.sec_knox_credential_bg)
                            : getResources().getDrawable(R.drawable.sec_knox_credential_bg_land));
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getSystemService("device_policy");
        TextView textView = (TextView) decorView.findViewById(R.id.knoxTitleText);
        int color = getResources().getColor(R.color.work_profile_lock_screen_text_color);
        textView.setTextColor(color);
        textView.setText(getString(R.string.work_title));
        TextView textView2 = (TextView) decorView.findViewById(R.id.knoxTitleSubText);
        CharSequence organizationNameForUser =
                devicePolicyManager.getOrganizationNameForUser(intExtra);
        textView2.setTextColor(color);
        textView2.setText(organizationNameForUser);
        ImageView imageView2 = (ImageView) decorView.findViewById(R.id.knoxLogo);
        Drawable drawable = getResources().getDrawable(R.drawable.knox_basic);
        if (imageView2 != null) {
            imageView2.setImageDrawable(drawable);
            imageView2.setColorFilter(Color.parseColor("#fffafafa"));
        }
        LinearLayout linearLayout = (LinearLayout) decorView.findViewById(R.id.knox_logo_layout);
        if (linearLayout == null) {
            Log.d("ForgotPasswordActivity", "SecuredLogo is NULL");
        } else if (getResources()
                .getBoolean(
                        Resources.getSystem()
                                .getIdentifier(
                                        "config_showNavigationBar",
                                        "bool",
                                        RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME))) {
            linearLayout.setPadding(0, 0, 0, KnoxUtils.getNavigationBarSize(this));
        }
        ((Button) decorView.findViewById(R.id.okayButton))
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.password.ForgotPasswordActivity.1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ForgotPasswordActivity.this.finish();
                            }
                        });
        Window window = getWindow();
        if (window != null) {
            window.setNavigationBarColor(0);
            window.setNavigationBarContrastEnforced(false);
            window.setStatusBarColor(0);
            window.getDecorView().setSystemUiVisibility(768);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.samsungFlags |= 67108864;
            window.setAttributes(attributes);
        }
        setContentView(R.layout.forgot_password_activity);
        DevicePolicyManager devicePolicyManager2 =
                (DevicePolicyManager) getSystemService(DevicePolicyManager.class);
        ((TextView) findViewById(R.id.forgot_password_text))
                .setText(
                        devicePolicyManager2
                                .getResources()
                                .getString(
                                        "Settings.FORGOT_PASSWORD_TEXT",
                                        new Supplier(
                                                this) { // from class:
                                                        // com.android.settings.password.ForgotPasswordActivity$$ExternalSyntheticLambda0
                                            public final /* synthetic */ ForgotPasswordActivity f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                int i5 = i4;
                                                ForgotPasswordActivity forgotPasswordActivity =
                                                        this.f$0;
                                                switch (i5) {
                                                    case 0:
                                                        int i6 = ForgotPasswordActivity.$r8$clinit;
                                                        return forgotPasswordActivity.getString(
                                                                R.string.forgot_password_text);
                                                    default:
                                                        int i7 = ForgotPasswordActivity.$r8$clinit;
                                                        return forgotPasswordActivity.getString(
                                                                R.string.forgot_password_title);
                                                }
                                            }
                                        }));
        GlifLayout glifLayout = (GlifLayout) findViewById(R.id.setup_wizard_layout);
        ((FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class))
                .setPrimaryButton(
                        new FooterButton(
                                getString(android.R.string.ok),
                                new View
                                        .OnClickListener() { // from class:
                                                             // com.android.settings.password.ForgotPasswordActivity$$ExternalSyntheticLambda1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        ForgotPasswordActivity forgotPasswordActivity =
                                                ForgotPasswordActivity.this;
                                        int i5 = ForgotPasswordActivity.$r8$clinit;
                                        forgotPasswordActivity.finish();
                                    }
                                },
                                4,
                                2132083805));
        if (PartnerConfigHelper.shouldApplyMaterialYouStyle(this)) {
            TextView textView3 = (TextView) glifLayout.findViewById(R.id.forgot_password_text);
            if (PartnerStyleHelper.shouldApplyPartnerHeavyThemeResource(textView3)) {
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_CONTENT_TEXT_COLOR;
                PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_CONTENT_LINK_TEXT_COLOR;
                PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_CONTENT_TEXT_SIZE;
                PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_CONTENT_FONT_FAMILY;
                PartnerConfig partnerConfig5 = PartnerConfig.CONFIG_DESCRIPTION_LINK_FONT_FAMILY;
                Context context = textView3.getContext();
                String string =
                        PartnerConfigHelper.get(context)
                                .getString(context, PartnerConfig.CONFIG_CONTENT_LAYOUT_GRAVITY);
                if (string != null) {
                    String lowerCase = string.toLowerCase(Locale.ROOT);
                    lowerCase.getClass();
                    switch (lowerCase.hashCode()) {
                        case -1364013995:
                            if (lowerCase.equals("center")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 100571:
                            if (lowerCase.equals(NetworkAnalyticsConstants.DataPoints.CLOSE_TIME)) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 109757538:
                            if (lowerCase.equals(NetworkAnalyticsConstants.DataPoints.OPEN_TIME)) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            i = 17;
                            i2 = i;
                            break;
                        case 1:
                            i = 8388613;
                            i2 = i;
                            break;
                        case 2:
                            i = 8388611;
                            i2 = i;
                            break;
                    }
                    TextViewPartnerStyler.applyPartnerCustomizationStyle(
                            textView3,
                            new TextViewPartnerStyler.TextPartnerConfigs(
                                    partnerConfig,
                                    partnerConfig2,
                                    partnerConfig3,
                                    partnerConfig4,
                                    null,
                                    partnerConfig5,
                                    null,
                                    null,
                                    i2));
                }
                i2 = 0;
                TextViewPartnerStyler.applyPartnerCustomizationStyle(
                        textView3,
                        new TextViewPartnerStyler.TextPartnerConfigs(
                                partnerConfig,
                                partnerConfig2,
                                partnerConfig3,
                                partnerConfig4,
                                null,
                                partnerConfig5,
                                null,
                                null,
                                i2));
            }
        }
        glifLayout.setHeaderText(
                devicePolicyManager2
                        .getResources()
                        .getString(
                                "Settings.FORGOT_PASSWORD_TITLE",
                                new Supplier(
                                        this) { // from class:
                                                // com.android.settings.password.ForgotPasswordActivity$$ExternalSyntheticLambda0
                                    public final /* synthetic */ ForgotPasswordActivity f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        int i5 = i3;
                                        ForgotPasswordActivity forgotPasswordActivity = this.f$0;
                                        switch (i5) {
                                            case 0:
                                                int i6 = ForgotPasswordActivity.$r8$clinit;
                                                return forgotPasswordActivity.getString(
                                                        R.string.forgot_password_text);
                                            default:
                                                int i7 = ForgotPasswordActivity.$r8$clinit;
                                                return forgotPasswordActivity.getString(
                                                        R.string.forgot_password_title);
                                        }
                                    }
                                }));
        UserManager.get(this).requestQuietModeEnabled(false, UserHandle.of(intExtra), 2);
    }
}
