package com.android.settings.deviceinfo;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.UserManager;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.google.android.setupdesign.GlifLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageWizardInit extends StorageWizardBase {
    public final AnonymousClass1 listener = new AnonymousClass1();
    public boolean mIsPermittedToAdopt;
    public boolean mPortable;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.deviceinfo.StorageWizardInit$1, reason: invalid class name */
    public final class AnonymousClass1 implements View.OnTouchListener {
        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 1) {
                return motionEvent.getX() < 0.0f
                        || motionEvent.getY() < 0.0f
                        || motionEvent.getX() > ((float) view.getMeasuredWidth())
                        || motionEvent.getY() > ((float) view.getMeasuredHeight());
            }
            return false;
        }
    }

    public static Spannable styleFont(String str) {
        Spannable spannable = (Spannable) Html.fromHtml(str);
        for (URLSpan uRLSpan :
                (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class)) {
            spannable.setSpan(
                    new TypefaceSpan("sans-serif-medium"),
                    spannable.getSpanStart(uRLSpan),
                    spannable.getSpanEnd(uRLSpan),
                    0);
        }
        return spannable;
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.mDisk == null) {
            finish();
            return;
        }
        boolean z = UserManager.get(this).isAdminUser() && !ActivityManager.isUserAMonkey();
        this.mIsPermittedToAdopt = z;
        if (!z) {
            Toast.makeText(getApplicationContext(), R.string.storage_wizard_guest, 1).show();
            finish();
            return;
        }
        setContentView(R.layout.storage_wizard_init);
        TextView textView = (TextView) findViewById(R.id.storage_wizard_init_external_text);
        TextView textView2 = (TextView) findViewById(R.id.storage_wizard_init_internal_text);
        String string = getResources().getString(R.string.storage_wizard_init_v2_external_summary);
        String string2 = getResources().getString(R.string.storage_wizard_init_v2_internal_summary);
        Spannable styleFont = styleFont(string);
        Spannable styleFont2 = styleFont(string2);
        textView.setText(styleFont);
        textView2.setText(styleFont2);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setOnTouchListener(this.listener);
        textView2.setOnTouchListener(this.listener);
        this.mPortable = true;
        GlifLayout glifLayout = (GlifLayout) requireViewById(R.id.setup_wizard_layout);
        if (glifLayout != null) {
            glifLayout.setVisibility(4);
        }
        onNavigateExternal(null);
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase
    public void onNavigateBack(View view) {
        if (!this.mIsPermittedToAdopt) {
            view.setEnabled(false);
        } else {
            if (!this.mPortable) {
                throw null;
            }
            throw null;
        }
    }

    public void onNavigateExternal(View view) {
        if (view != null) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl.getMetricsFeatureProvider().action(this, 1407, new Pair[0]);
        }
        String id = this.mDisk.getId();
        StorageWizardFormatConfirm.mCallerActivity = this;
        StorageWizardFormatConfirm.show(this, id, false);
    }

    public void onNavigateInternal(View view) {
        if (view != null) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl.getMetricsFeatureProvider().action(this, 1408, new Pair[0]);
        }
        StorageWizardFormatConfirm.show(this, this.mDisk.getId(), true);
    }

    @Override // com.android.settings.deviceinfo.StorageWizardBase
    public void onNavigateNext(View view) {
        if (this.mPortable) {
            onNavigateExternal(view);
        } else {
            onNavigateInternal(view);
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("IS_PORTABLE", this.mPortable);
        super.onSaveInstanceState(bundle);
    }
}
