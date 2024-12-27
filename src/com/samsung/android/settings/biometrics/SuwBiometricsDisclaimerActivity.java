package com.samsung.android.settings.biometrics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultRegistry;

import com.android.settings.R;

import com.sec.android.secsetupwizardlib.SuwBaseActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SuwBiometricsDisclaimerActivity extends SuwBaseActivity
        implements View.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mIsDescExpanded = true;
    public RelativeLayout mMoreAboutGuideDesc;
    public LinearLayout mMoreAboutSpinner;
    public ImageView mMoreAboutSpinnerImage;
    public TextView mMoreAboutSpinnerText;
    public int mRequestBiometricsType;
    public ActivityResultRegistry.AnonymousClass2 mSetScreenLockResult;
    public int mUserId;

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.biometrics_disclaimer_more_about_face
                || id == R.id.biometrics_disclaimer_more_about_finger) {
            Log.d("SuwBiometricsDisclaimer", "More about face");
            if (this.mMoreAboutGuideDesc.getVisibility() == 8) {
                setMoreAboutDesc$1(true);
            } else {
                setMoreAboutDesc$1(false);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0094  */
    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r4) {
        /*
            r3 = this;
            super.onCreate(r4)
            android.content.Intent r0 = r3.getIntent()
            java.lang.String r1 = "android.intent.extra.USER_ID"
            int r2 = android.os.UserHandle.myUserId()
            int r1 = r0.getIntExtra(r1, r2)
            r3.mUserId = r1
            java.lang.String r1 = "BIOMETRICS_LOCK_TYPE"
            r2 = 0
            int r0 = r0.getIntExtra(r1, r2)
            r3.mRequestBiometricsType = r0
            if (r4 == 0) goto L26
            java.lang.String r0 = "is_desc_expanded"
            boolean r4 = r4.getBoolean(r0)
            r3.mIsDescExpanded = r4
        L26:
            java.lang.String r4 = "setTitleForVoiceAssistant"
            java.lang.String r0 = "SuwBiometricsDisclaimer"
            android.util.Log.d(r0, r4)
            int r4 = r3.mRequestBiometricsType
            r1 = 256(0x100, float:3.59E-43)
            r2 = 1
            if (r4 != r2) goto L40
            r4 = 2132025612(0x7f14210c, float:1.9689733E38)
            java.lang.String r4 = r3.getString(r4)
            r3.setTitle(r4)
            goto L53
        L40:
            if (r4 != r1) goto L4d
            r4 = 2132025611(0x7f14210b, float:1.9689731E38)
            java.lang.String r4 = r3.getString(r4)
            r3.setTitle(r4)
            goto L53
        L4d:
            java.lang.String r4 = "setTitleForVoiceAssistant : Wrong case"
            android.util.Log.e(r0, r4)
        L53:
            int r4 = r3.mRequestBiometricsType
            if (r4 == r2) goto L64
            if (r4 == r1) goto L5d
            r3.finish()
            goto L84
        L5d:
            r4 = 2131233023(0x7f0808ff, float:1.8082172E38)
            r0 = 2132025629(0x7f14211d, float:1.9689768E38)
            goto L6a
        L64:
            r4 = 2131233025(0x7f080901, float:1.8082176E38)
            r0 = 2132026244(0x7f142384, float:1.9691015E38)
        L6a:
            android.graphics.drawable.Drawable r4 = r3.getDrawable(r4)
            android.content.res.Resources r1 = r3.getResources()
            r2 = 2131100990(0x7f06053e, float:1.7814377E38)
            int r1 = r1.getColor(r2)
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.SRC_IN
            r4.setColorFilter(r1, r2)
            r3.setHeaderIcon(r4)
            r3.setHeaderTitle(r0)
        L84:
            com.samsung.android.settings.biometrics.SuwBiometricsDisclaimerActivity$$ExternalSyntheticLambda1 r4 = new com.samsung.android.settings.biometrics.SuwBiometricsDisclaimerActivity$$ExternalSyntheticLambda1
            r0 = 0
            r4.<init>(r3)
            r0 = 2132025592(0x7f1420f8, float:1.9689693E38)
            r3.setPrimaryActionButton(r0, r4)
            boolean r4 = com.samsung.android.settings.Rune.LOCKSCREEN_SECURITY_HIDE_SKIP_SUW_BUTTON
            if (r4 != 0) goto La0
            com.samsung.android.settings.biometrics.SuwBiometricsDisclaimerActivity$$ExternalSyntheticLambda1 r4 = new com.samsung.android.settings.biometrics.SuwBiometricsDisclaimerActivity$$ExternalSyntheticLambda1
            r0 = 1
            r4.<init>(r3)
            r0 = 2132025608(0x7f142108, float:1.9689725E38)
            r3.setSecondaryActionButton(r0, r4)
        La0:
            androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult r4 = new androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult
            r0 = 0
            r4.<init>(r0)
            com.samsung.android.settings.biometrics.SuwBiometricsDisclaimerActivity$$ExternalSyntheticLambda0 r0 = new com.samsung.android.settings.biometrics.SuwBiometricsDisclaimerActivity$$ExternalSyntheticLambda0
            r0.<init>()
            androidx.activity.result.ActivityResultLauncher r4 = r3.registerForActivityResult(r4, r0)
            androidx.activity.result.ActivityResultRegistry$2 r4 = (androidx.activity.result.ActivityResultRegistry.AnonymousClass2) r4
            r3.mSetScreenLockResult = r4
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.biometrics.SuwBiometricsDisclaimerActivity.onCreate(android.os.Bundle):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            Method dump skipped, instructions count: 419
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.biometrics.SuwBiometricsDisclaimerActivity.onResume():void");
    }

    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity,
              // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("is_desc_expanded", this.mIsDescExpanded);
    }

    public final void setMoreAboutDesc$1(boolean z) {
        int i;
        ImageView imageView = this.mMoreAboutSpinnerImage;
        if (imageView == null || this.mMoreAboutGuideDesc == null) {
            return;
        }
        this.mIsDescExpanded = z;
        if (z) {
            imageView.setScaleY(-1.0f);
            this.mMoreAboutGuideDesc.setVisibility(0);
            i = R.string.sec_biometrics_disclaimer_expanded;
        } else {
            imageView.setScaleY(1.0f);
            this.mMoreAboutGuideDesc.setVisibility(8);
            i = R.string.sec_biometrics_disclaimer_collapsed;
        }
        TextView textView = this.mMoreAboutSpinnerText;
        if (textView == null || this.mMoreAboutSpinner == null) {
            return;
        }
        StringBuilder sb = new StringBuilder(textView.getText());
        sb.append(", ");
        sb.append(getString(i));
        sb.append(", ");
        sb.append(getString(R.string.button_tts));
        this.mMoreAboutSpinner.setContentDescription(sb);
        this.mMoreAboutSpinner.announceForAccessibility(getString(i));
    }
}
