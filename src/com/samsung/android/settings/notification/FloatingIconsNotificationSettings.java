package com.samsung.android.settings.notification;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.AbstractPreferenceController;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FloatingIconsNotificationSettings extends DashboardFragment
        implements FloatingIconDependentFieldListener {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass2();
    public Context mContext;
    public final List mControllerListeners = new ArrayList();
    public LottieAnimationView mFloatingIconImage;
    public ViewGroup mFloatingPreviewContainer;
    public View mHelptext;
    public View mainView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.FloatingIconsNotificationSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            StatusData statusData;
            StatusData statusData2;
            ArrayList arrayList = new ArrayList();
            int i = Settings.Global.getInt(context.getContentResolver(), "notification_bubbles", 0);
            if (i == -1 || i == 0) {
                String valueOf = String.valueOf(36406);
                statusData = new StatusData();
                statusData.mStatusValue = "off";
                statusData.mStatusKey = valueOf;
            } else if (i == 1) {
                String valueOf2 = String.valueOf(36406);
                statusData = new StatusData();
                statusData.mStatusValue = "bubbles";
                statusData.mStatusKey = valueOf2;
            } else {
                if (i != 2) {
                    statusData2 = null;
                    arrayList.add(statusData2);
                    return arrayList;
                }
                String valueOf3 = String.valueOf(36406);
                statusData = new StatusData();
                statusData.mStatusValue = "smart popup view";
                statusData.mStatusKey = valueOf3;
            }
            statusData2 = statusData;
            arrayList.add(statusData2);
            return arrayList;
        }
    }

    public static void setViewShown(final View view, boolean z, boolean z2) {
        if (!z2) {
            view.clearAnimation();
            view.setVisibility(z ? 0 : 8);
            return;
        }
        Animation loadAnimation =
                AnimationUtils.loadAnimation(
                        view.getContext(), z ? R.anim.fade_in : R.anim.fade_out);
        if (z) {
            view.setVisibility(0);
        } else {
            loadAnimation.setAnimationListener(
                    new Animation
                            .AnimationListener() { // from class:
                                                   // com.samsung.android.settings.notification.FloatingIconsNotificationSettings.1
                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationEnd(Animation animation) {
                            view.setVisibility(8);
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationRepeat(Animation animation) {}

                        @Override // android.view.animation.Animation.AnimationListener
                        public final void onAnimationStart(Animation animation) {}
                    });
        }
        view.startAnimation(loadAnimation);
    }

    public final View createFloatingView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        ViewGroup viewGroup2 =
                (ViewGroup)
                        layoutInflater.inflate(
                                com.android.settings.R.layout.sec_floating_notification_container,
                                viewGroup);
        View inflate =
                layoutInflater.inflate(
                        com.android.settings.R.layout.sec_floating_icon_illustration,
                        (ViewGroup) null);
        this.mFloatingPreviewContainer =
                (ViewGroup)
                        viewGroup2.findViewById(
                                com.android.settings.R.id.floating_icon_preview_container);
        View findViewById =
                viewGroup2.findViewById(com.android.settings.R.id.floating_icon_preview_parent);
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
        findViewById.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        findViewById.semSetRoundedCorners(15);
        findViewById.semSetRoundedCornerColor(
                15,
                this.mContext
                        .getResources()
                        .getColor(com.android.settings.R.color.sec_widget_round_and_bgcolor));
        this.mHelptext =
                layoutInflater.inflate(
                        com.android.settings.R.layout.sec_widget_preference_unclickable,
                        (ViewGroup) null);
        this.mFloatingPreviewContainer.addView(inflate);
        this.mFloatingPreviewContainer.addView(this.mHelptext);
        this.mFloatingPreviewContainer.setVisibility(0);
        ViewGroup viewGroup3 =
                (ViewGroup)
                        viewGroup2.findViewById(
                                com.android.settings.R.id.floating_options_preference_container);
        if (this.mainView.getParent() != null) {
            ((ViewGroup) this.mainView.getParent()).removeView(this.mainView);
        }
        viewGroup3.addView(this.mainView);
        FrameLayout frameLayout =
                (FrameLayout)
                        findViewById.findViewById(
                                com.android.settings.R.id.floating_icon_animation_container);
        frameLayout.semSetRoundedCorners(15);
        frameLayout.semSetRoundedCornerColor(
                15,
                this.mContext
                        .getResources()
                        .getColor(com.android.settings.R.color.sec_widget_round_and_bgcolor));
        this.mFloatingIconImage =
                (LottieAnimationView)
                        frameLayout.findViewById(com.android.settings.R.id.floating_icon_animation);
        updateState();
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) this.mHelptext.getLayoutParams();
        if (marginLayoutParams != null) {
            marginLayoutParams.setMargins(
                    getResources()
                            .getDimensionPixelSize(
                                    com.android.settings.R.dimen
                                            .sec_floating_notifications_setting_helptext_summary_margin_start),
                    getResources()
                            .getDimensionPixelSize(
                                    com.android.settings.R.dimen
                                            .sec_floating_notifications_setting_helptext_summary_margin_top),
                    marginLayoutParams.rightMargin,
                    getResources()
                            .getDimensionPixelSize(
                                    com.android.settings.R.dimen
                                            .sec_widget_body_text_padding_bottom));
            this.mHelptext.setLayoutParams(marginLayoutParams);
        }
        this.mFloatingPreviewContainer.setBackgroundColor(
                getContext()
                        .getResources()
                        .getColor(com.android.settings.R.color.sec_widget_round_and_bgcolor));
        frameLayout.setBackgroundColor(
                getContext()
                        .getResources()
                        .getColor(
                                com.android.settings.R.color
                                        .sec_widget_help_image_backgound_color));
        return viewGroup2;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new BubbleFloatingIconStylePreferenceController(
                        context, "floating_icon_bubble", this));
        arrayList.add(
                new PopupFloatingIconStylePreferenceController(
                        context, "floating_icon_popup", this));
        arrayList.add(
                new NoFloatingIconStylePreferenceController(context, "floating_icon_off", this));
        arrayList.add(new FloatingPopupAppsPreferenceController(context, "included_apps"));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object obj = (AbstractPreferenceController) it.next();
            if (obj instanceof FloatingIconDependentControllerFieldListener) {
                ((ArrayList) this.mControllerListeners)
                        .add((FloatingIconDependentControllerFieldListener) obj);
            }
        }
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "FloatingIconNotiSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1699;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return com.android.settings.R.xml.sec_floating_icon_notification_settings;
    }

    public final void notifyChange(int i) {
        Iterator it = ((ArrayList) this.mControllerListeners).iterator();
        while (it.hasNext()) {
            ((FloatingIconDependentControllerFieldListener) it.next()).updateValues(i);
        }
        updateState();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setAutoRemoveInsetCategory(false);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        createFloatingView(
                LayoutInflater.from(this.mContext),
                (ViewGroup) getActivity().findViewById(com.android.settings.R.id.main_content));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mainView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mContext = getContext();
        return createFloatingView(layoutInflater, null);
    }

    public final void setAnimationResource(String str) {
        this.mFloatingIconImage.setAnimation(str);
        this.mFloatingIconImage.playAnimation$1();
    }

    public final void updateState() {
        int intForUser =
                Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(), "notification_bubbles", 1, -2);
        if (intForUser == -1) {
            intForUser = 1;
        }
        if (intForUser == 0) {
            setViewShown(this.mFloatingPreviewContainer, false, false);
            return;
        }
        if (intForUser == 1) {
            if (Utils.isNightMode(this.mContext)) {
                setAnimationResource("Bubbles_dark.json");
            } else {
                setAnimationResource("Bubbles_light.json");
            }
            ((TextView) this.mHelptext.findViewById(com.android.settings.R.id.title))
                    .setText(
                            this.mContext
                                    .getResources()
                                    .getText(
                                            com.android.settings.R.string
                                                    .notifications_floating_bubble_description));
            setViewShown(this.mFloatingPreviewContainer, true, true);
            return;
        }
        if (intForUser != 2) {
            return;
        }
        if (Utils.isNightMode(this.mContext)) {
            setAnimationResource("Bubbles_Smart_Popup_Help_VI_Dark.json");
        } else {
            setAnimationResource("Bubbles_Smart_Popup_Help_VI_Light.json");
        }
        ((TextView) this.mHelptext.findViewById(com.android.settings.R.id.title))
                .setText(
                        this.mContext
                                .getResources()
                                .getText(
                                        com.android.settings.R.string
                                                .notifications_floating_popup_description));
        setViewShown(this.mFloatingPreviewContainer, true, true);
    }
}
