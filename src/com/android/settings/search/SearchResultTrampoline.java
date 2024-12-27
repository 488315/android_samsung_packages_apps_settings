package com.android.settings.search;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.FeatureFlagUtils;
import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

import com.android.settings.DefaultRingtonePreference$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsApplication;
import com.android.settings.SubSettings;
import com.android.settings.activityembedding.ActivityEmbeddingRulesController;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.activityembedding.EmbeddedDeepLinkUtils;
import com.android.settings.homepage.DeepLinkHomepageActivityInternal;
import com.android.settings.homepage.SettingsHomepageActivity;
import com.android.settings.homepage.TopLevelSettings;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.net.URISyntaxException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SearchResultTrampoline extends Activity {
    public static void requestHighlight(
            SettingsHomepageActivity settingsHomepageActivity, String str, String str2) {
        if (!TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            AppBarLayout appBarLayout = settingsHomepageActivity.mAppBarLayout;
            if (appBarLayout != null) {
                appBarLayout.setExpanded(false);
            }
            settingsHomepageActivity.mMainFragment.setHighlightMenuKey(str);
            return;
        }
        AppBarLayout appBarLayout2 = settingsHomepageActivity.mAppBarLayout;
        if (appBarLayout2 != null) {
            appBarLayout2.setExpanded(false);
        }
        TopLevelSettings topLevelSettings = settingsHomepageActivity.mMainFragment;
        topLevelSettings.setHighlightMenuKey(topLevelSettings.findPreferenceKey(str2));
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        String str;
        String sb;
        super.onCreate(bundle);
        String launchedFromPackage = getLaunchedFromPackage();
        try {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                try {
                    throw new UnsupportedOperationException("No feature factory configured");
                } catch (IllegalArgumentException | SecurityException e) {
                    e = e;
                    Log.e("SearchResultTrampoline", e.toString());
                    finish();
                    return;
                }
            }
            featureFactoryImpl
                    .getSearchFeatureProvider()
                    .verifyLaunchSearchResultPageCaller(this, launchedFromPackage);
            Intent intent = getIntent();
            String stringExtra =
                    intent.getStringExtra(
                            "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY");
            String stringExtra2 = intent.getStringExtra(":settings:show_fragment");
            String stringExtra3 = intent.getStringExtra("targetAction");
            String stringExtra4 = intent.getStringExtra("targetPackage");
            String stringExtra5 = intent.getStringExtra("targetClass");
            String stringExtra6 = intent.getStringExtra("query");
            String stringExtra7 = intent.getStringExtra("type");
            String stringExtra8 = intent.getStringExtra(":settings:fragment_args_key");
            StringBuilder m =
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            "highlightMenuKey: ",
                            stringExtra,
                            ", fragment",
                            stringExtra2,
                            ", action: ");
            ConstraintWidget$$ExternalSyntheticOutline0.m(m, stringExtra3, ", ", stringExtra4, "/");
            m.append(stringExtra5);
            Log.d("SearchResultTrampoline", m.toString());
            String stringExtra9 =
                    intent.getExtras() == null
                            ? ApnSettings.MVNO_NONE
                            : intent.getStringExtra(":settings:hierarchy_parent_title");
            if (TextUtils.isEmpty(stringExtra2) || !TextUtils.isEmpty(stringExtra3)) {
                if (TextUtils.isEmpty(stringExtra3)) {
                    str = ":settings:show_fragment_args";
                    sb = null;
                } else {
                    StringBuilder m2 =
                            ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                    "intent:#Intent;action=", stringExtra3, ";");
                    if (TextUtils.isEmpty(stringExtra4) || TextUtils.isEmpty(stringExtra5)) {
                        str = ":settings:show_fragment_args";
                        if (!TextUtils.isEmpty(stringExtra4)) {
                            m2.append("package=");
                            m2.append(stringExtra4);
                            m2.append(";");
                        }
                    } else {
                        str = ":settings:show_fragment_args";
                        ConstraintWidget$$ExternalSyntheticOutline0.m(
                                m2, "component=", stringExtra4, "/", stringExtra5);
                        m2.append(";");
                    }
                    if (!TextUtils.isEmpty(stringExtra6)) {
                        m2.append("S.query=");
                        m2.append(stringExtra6);
                        m2.append(";");
                    }
                    m2.append(NetworkAnalyticsConstants.DataPoints.CLOSE_TIME);
                    sb = m2.toString();
                }
                Uri uri =
                        (Uri)
                                intent.getParcelableExtra(
                                        "settings_large_screen_deep_link_intent_data", Uri.class);
                if (!TextUtils.isEmpty(sb)) {
                    try {
                        intent = Intent.parseUri(sb, 1);
                        intent.setData(uri);
                    } catch (URISyntaxException e2) {
                        Log.e("SearchResultTrampoline", "Failed to parse deep link intent: " + e2);
                    }
                }
                if (TextUtils.isEmpty(sb)
                        || ("android.intent.action.VIEW".equals(intent.getAction())
                                && !"android.intent.action.VIEW".equals(stringExtra3))) {
                    StringBuilder sb2 =
                            new StringBuilder(
                                    "Failed build intent by intentUriString.  intentUriString : ");
                    if (TextUtils.isEmpty(sb)) {
                        sb = " null ";
                    }
                    sb2.append(sb);
                    Log.e("SearchResultTrampoline", sb2.toString());
                    StringBuilder sb3 =
                            new StringBuilder("buildIntentForCompatibility() action : ");
                    sb3.append(TextUtils.isEmpty(stringExtra3) ? "null" : stringExtra3);
                    sb3.append(" packageName : ");
                    sb3.append(TextUtils.isEmpty(stringExtra4) ? "null" : stringExtra4);
                    sb3.append(" className : ");
                    sb3.append(TextUtils.isEmpty(stringExtra5) ? "null" : stringExtra5);
                    Log.w("SearchResultTrampoline", sb3.toString());
                    intent.setAction(stringExtra3);
                    if (TextUtils.isEmpty(stringExtra4) || TextUtils.isEmpty(stringExtra5)) {
                        intent.setComponent(null);
                    } else {
                        DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                                stringExtra4, stringExtra5, intent);
                    }
                }
                intent.putExtra(":settings:fragment_args_key", stringExtra8);
                intent.putExtra(":settings:show_fragment", stringExtra2);
                intent.putExtra("type", stringExtra7);
                int intExtra = intent.getIntExtra(":settings:show_fragment_tab", 0);
                Bundle bundle2 = new Bundle();
                bundle2.putString(":settings:fragment_args_key", stringExtra8);
                bundle2.putInt(":settings:show_fragment_tab", intExtra);
                intent.putExtra(str, bundle2);
            } else {
                int intExtra2 = intent.getIntExtra(":settings:show_fragment_tab", 0);
                Bundle bundle3 = new Bundle();
                bundle3.putString(":settings:fragment_args_key", stringExtra8);
                bundle3.putInt(":settings:show_fragment_tab", intExtra2);
                intent.putExtra(":settings:show_fragment_args", bundle3);
                intent.setClass(this, SubSettings.class);
            }
            SettingsHomepageActivity homeActivity =
                    ((SettingsApplication) getApplicationContext()).getHomeActivity();
            intent.addFlags(33554432);
            if (!ActivityEmbeddingUtils.isEmbeddingActivityEnabled(this)
                    || ActivityEmbeddingUtils.isAlreadyEmbedded(this)) {
                startActivityInternal(intent);
                if (homeActivity != null && ActivityEmbeddingUtils.isAlreadyEmbedded(this)) {
                    requestHighlight(homeActivity, stringExtra, stringExtra9);
                }
            } else {
                FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
                if (featureFactoryImpl2 == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                featureFactoryImpl2.getSearchFeatureProvider().getClass();
                if (!TextUtils.equals(
                        launchedFromPackage,
                        getString(R.string.config_settingsintelligence_package_name))) {
                    Intent trampolineIntent =
                            EmbeddedDeepLinkUtils.getTrampolineIntent(stringExtra, intent);
                    trampolineIntent.putExtra(
                            "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_TITLE",
                            stringExtra9);
                    startActivityInternal(trampolineIntent.addFlags(268435456));
                } else if (FeatureFlagUtils.isEnabled(this, "settings_search_always_expand")) {
                    startActivityInternal(
                            EmbeddedDeepLinkUtils.getTrampolineIntent(stringExtra, intent)
                                    .setClass(this, DeepLinkHomepageActivityInternal.class)
                                    .addFlags(276824064));
                } else {
                    ActivityEmbeddingRulesController.registerSubSettingsPairRule(this, false);
                    intent.setFlags(intent.getFlags() & (-268435457));
                    startActivity(intent);
                    if (homeActivity != null) {
                        requestHighlight(homeActivity, stringExtra, stringExtra9);
                    }
                }
            }
            finish();
        } catch (IllegalArgumentException | SecurityException e3) {
            e = e3;
        }
    }

    public final void startActivityInternal(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Log.e(
                    "SearchResultTrampoline",
                    "startActivityInternal() - ActivityNotFoundException. Action "
                            + intent.getAction());
        }
    }
}
