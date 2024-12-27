package com.android.settings.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.spa.app.appinfo.AppInfoSettingsProvider;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.applications.cachedb.AppListCacheIndexingManager;
import com.samsung.android.settings.applications.cachedb.AppListCacheIndexingManager.AnonymousClass2;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EntityHeaderController {
    public int mAction1;
    public int mAction2;
    public final Context mAppContext;
    public final Fragment mFragment;
    public boolean mHasAppInfoLink;
    public final View mHeader;
    public Drawable mIcon;
    public String mIconContentDescription;
    public boolean mIsInstantApp;
    public CharSequence mLabel;
    public final int mMetricsCategory;
    public String mPackageName;
    public CharSequence mSecondSummary;
    public CharSequence mSummary;
    public int mTitleIcon;
    public int mPrefOrder = -1000;
    public int mUid = -10000;

    public EntityHeaderController(FragmentActivity fragmentActivity, Fragment fragment, View view) {
        this.mAppContext = fragmentActivity.getApplicationContext();
        this.mFragment = fragment;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().getClass();
        this.mMetricsCategory = MetricsFeatureProvider.getMetricsCategory(fragment);
        if (view != null) {
            this.mHeader = view;
        } else {
            this.mHeader =
                    LayoutInflater.from(fragment.getContext())
                            .inflate(R.layout.sec_app_info_submenu_header, (ViewGroup) null);
        }
    }

    public final void bindButton(ImageButton imageButton, int i) {
        final Intent intent;
        if (imageButton == null) {
            return;
        }
        if (i == 0) {
            imageButton.setVisibility(8);
            return;
        }
        if (i == 1) {
            imageButton.setVisibility(8);
            return;
        }
        if (i == 2) {
            imageButton.setVisibility(8);
            return;
        }
        if (i != 3) {
            return;
        }
        Intent intent2 =
                new Intent("com.sec.android.intent.action.SEC_APPLICATION_SETTINGS")
                        .setPackage(this.mPackageName);
        ResolveInfo resolveActivity =
                this.mAppContext.getPackageManager().resolveActivity(intent2, 0);
        if (resolveActivity != null) {
            Intent intent3 = new Intent(intent2.getAction());
            ActivityInfo activityInfo = resolveActivity.activityInfo;
            intent = intent3.setClassName(activityInfo.packageName, activityInfo.name);
        } else {
            intent = null;
        }
        ImageButton imageButton2 =
                (ImageButton) this.mHeader.findViewById(R.id.app_preference_button);
        if (intent == null) {
            imageButton2.setVisibility(8);
            return;
        }
        intent.setAction(null);
        intent.putExtra("from_settings", true);
        imageButton2.setContentDescription(
                this.mAppContext.getString(R.string.sec_app_info_more_settings));
        imageButton2.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.widget.EntityHeaderController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        EntityHeaderController entityHeaderController = EntityHeaderController.this;
                        Intent intent4 = intent;
                        LoggingHelper.insertEventLogging(
                                entityHeaderController.mMetricsCategory, 7428);
                        entityHeaderController.mFragment.startActivity(intent4);
                    }
                });
        imageButton2.setVisibility(0);
    }

    public final void bindHeaderButtons() {
        String str;
        View findViewById = this.mHeader.findViewById(R.id.entity_header);
        ImageButton imageButton = (ImageButton) this.mHeader.findViewById(android.R.id.button1);
        ImageButton imageButton2 = (ImageButton) this.mHeader.findViewById(android.R.id.button2);
        if (this.mHasAppInfoLink) {
            if (findViewById == null
                    || (str = this.mPackageName) == null
                    || str.equals("os")
                    || this.mUid == -10000) {
                Log.w("AppDetailFeature", "Missing ingredients to build app info link, skip");
            } else {
                findViewById.setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.widget.EntityHeaderController$$ExternalSyntheticLambda1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                EntityHeaderController entityHeaderController =
                                        EntityHeaderController.this;
                                AppInfoSettingsProvider.startAppInfoSettings(
                                        entityHeaderController.mPackageName,
                                        entityHeaderController.mUid,
                                        entityHeaderController.mFragment,
                                        0,
                                        entityHeaderController.mMetricsCategory);
                            }
                        });
            }
        }
        bindButton(imageButton, this.mAction1);
        bindButton(imageButton2, this.mAction2);
    }

    public final LayoutPreference done(Context context) {
        LayoutPreference layoutPreference = new LayoutPreference(context, done());
        layoutPreference.setOrder(this.mPrefOrder);
        layoutPreference.setSelectable(false);
        layoutPreference.setKey("pref_app_header");
        layoutPreference.mAllowDividerBelow = true;
        return layoutPreference;
    }

    public final void setIcon(Drawable drawable) {
        if (drawable != null) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState != null) {
                drawable = constantState.newDrawable(this.mAppContext.getResources());
            }
            this.mIcon = drawable;
        }
    }

    public final void setLabel(ApplicationsState.AppEntry appEntry) {
        CharSequence loadLabel = appEntry.info.loadLabel(this.mAppContext.getPackageManager());
        this.mLabel = loadLabel;
        if (loadLabel != null) {
            appEntry.label = loadLabel.toString();
            AppListCacheIndexingManager appListCacheIndexingManager =
                    AppListCacheIndexingManager.getInstance(this.mAppContext);
            appListCacheIndexingManager.mWorkerExecutor.submit(
                    appListCacheIndexingManager.new AnonymousClass2(appEntry.info.packageName));
        }
    }

    public final void setText(int i, CharSequence charSequence) {
        TextView textView = (TextView) this.mHeader.findViewById(i);
        if (textView != null) {
            textView.setText(charSequence);
            textView.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        }
    }

    public final View done(boolean z) {
        ImageView imageView = (ImageView) this.mHeader.findViewById(R.id.entity_header_icon);
        if (imageView != null) {
            imageView.setImageDrawable(this.mIcon);
            imageView.setContentDescription(this.mIconContentDescription);
        }
        setText(R.id.entity_header_title, this.mLabel);
        int i = this.mTitleIcon;
        TextView textView = (TextView) this.mHeader.findViewById(R.id.entity_header_title);
        if (textView != null) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, i, 0);
            textView.setCompoundDrawablePadding(
                    this.mAppContext
                            .getResources()
                            .getDimensionPixelSize(R.dimen.wifi_preference_badge_padding));
        }
        setText(R.id.entity_header_summary, this.mSummary);
        setText(R.id.entity_header_second_summary, this.mSecondSummary);
        if (this.mIsInstantApp) {
            setText(
                    R.id.install_type,
                    this.mHeader.getResources().getString(R.string.install_type_instant));
        }
        if (z) {
            bindHeaderButtons();
        }
        return this.mHeader;
    }

    public View done() {
        return done(true);
    }
}
