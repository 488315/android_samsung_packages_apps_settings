package com.android.settings.accessibility;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Preconditions;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.utils.StringUtil;
import com.android.settingslib.widget.LottieColorUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieListener;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AccessibilityShortcutsTutorial {
    public static final AccessibilityShortcutsTutorial$$ExternalSyntheticLambda6 ON_CLICK_LISTENER =
            new AccessibilityShortcutsTutorial$$ExternalSyntheticLambda6();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TutorialPage {
        public final View mIllustrationView;
        public final ImageView mIndicatorIcon;
        public final CharSequence mInstruction;
        public final CharSequence mTitle;
        public final int mType;

        public TutorialPage(
                int i,
                CharSequence charSequence,
                View view,
                ImageView imageView,
                CharSequence charSequence2) {
            this.mType = i;
            this.mTitle = charSequence;
            this.mIllustrationView = view;
            this.mIndicatorIcon = imageView;
            this.mInstruction = charSequence2;
            View findViewById = view.findViewById(R.id.image_background);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 17;
            findViewById.setLayoutParams(layoutParams);
            View findViewById2 = view.findViewById(R.id.image);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
            layoutParams2.gravity = 17;
            findViewById2.setLayoutParams(layoutParams2);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TutorialPagerAdapter extends PagerAdapter {
        public final List mTutorialPages;

        public TutorialPagerAdapter(List list) {
            this.mTutorialPages = list;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView(((TutorialPage) this.mTutorialPages.get(i)).mIllustrationView);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getCount() {
            return this.mTutorialPages.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            View view = ((TutorialPage) this.mTutorialPages.get(i)).mIllustrationView;
            viewGroup.addView(view);
            return view;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }

    public static AlertDialog createAccessibilityTutorialDialog(
            int i,
            final Context context,
            DialogInterface.OnClickListener onClickListener,
            CharSequence charSequence) {
        DialogInterface.OnClickListener onClickListener2 =
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.accessibility.AccessibilityShortcutsTutorial$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
                        String name = AccessibilityButtonFragment.class.getName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        launchRequest.mSourceMetricsCategory = 1873;
                        subSettingLauncher.launch();
                    }
                };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(R.string.accessibility_tutorial_dialog_button, onClickListener);
        builder.setNegativeButton(
                R.string.accessibility_tutorial_dialog_link_button, onClickListener2);
        final AlertDialog create = builder.create();
        final List<TutorialPage> createShortcutTutorialPages =
                createShortcutTutorialPages(context, i, charSequence, false);
        Preconditions.checkArgument(
                "Unexpected tutorial pages size", !createShortcutTutorialPages.isEmpty());
        create.setView$1(
                createShortcutNavigationContentView(
                        context,
                        createShortcutTutorialPages,
                        new AccessibilityShortcutsTutorial$$ExternalSyntheticLambda1(
                                create, createShortcutTutorialPages)));
        create.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.android.settings.accessibility.AccessibilityShortcutsTutorial$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        AccessibilityShortcutsTutorial
                                .updateTutorialNegativeButtonTextAndVisibility(
                                        AlertDialog.this, createShortcutTutorialPages, 0);
                    }
                });
        return create;
    }

    public static AlertDialog createAccessibilityTutorialDialogForSetupWizard(
            int i,
            Context context,
            DialogInterface.OnClickListener onClickListener,
            CharSequence charSequence) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(R.string.accessibility_tutorial_dialog_button, onClickListener);
        AlertDialog create = builder.create();
        List<TutorialPage> createShortcutTutorialPages =
                createShortcutTutorialPages(context, i, charSequence, true);
        Preconditions.checkArgument(
                "Unexpected tutorial pages size", !createShortcutTutorialPages.isEmpty());
        create.setView$1(
                createShortcutNavigationContentView(context, createShortcutTutorialPages, null));
        return create;
    }

    public static View createIllustrationViewWithImageRawResource(Context context, final int i) {
        View inflate =
                ((LayoutInflater) context.getSystemService(LayoutInflater.class))
                        .inflate(R.layout.accessibility_lottie_animation_view, (ViewGroup) null);
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView) inflate.findViewById(R.id.image);
        lottieAnimationView.failureListener =
                new LottieListener() { // from class:
                                       // com.android.settings.accessibility.AccessibilityShortcutsTutorial$$ExternalSyntheticLambda3
                    @Override // com.airbnb.lottie.LottieListener
                    public final void onResult(Object obj) {
                        Log.w(
                                "AccessibilityGestureNavigationTutorial",
                                "Invalid image raw resource id: " + i,
                                (Throwable) obj);
                    }
                };
        lottieAnimationView.setAnimation(i);
        lottieAnimationView.setRepeatCount(-1);
        LottieColorUtils.applyDynamicColors(context, lottieAnimationView);
        lottieAnimationView.playAnimation$1();
        return inflate;
    }

    public static ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.ic_accessibility_page_indicator);
        imageView.setAdjustViewBounds(true);
        return imageView;
    }

    public static View createShortcutNavigationContentView(
            final Context context,
            List list,
            AccessibilityShortcutsTutorial$$ExternalSyntheticLambda1
                    accessibilityShortcutsTutorial$$ExternalSyntheticLambda1) {
        View inflate =
                ((LayoutInflater) context.getSystemService(LayoutInflater.class))
                        .inflate(R.layout.accessibility_shortcut_tutorial_dialog, (ViewGroup) null);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.indicator_container);
        linearLayout.setVisibility(list.size() > 1 ? 0 : 8);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            linearLayout.addView(((TutorialPage) it.next()).mIndicatorIcon);
        }
        ((TutorialPage) list.get(0)).mIndicatorIcon.setEnabled(true);
        TextSwitcher textSwitcher = (TextSwitcher) inflate.findViewById(R.id.title);
        final int i = 0;
        textSwitcher.setFactory(
                new ViewSwitcher
                        .ViewFactory() { // from class:
                                         // com.android.settings.accessibility.AccessibilityShortcutsTutorial$$ExternalSyntheticLambda4
                    @Override // android.widget.ViewSwitcher.ViewFactory
                    public final View makeView() {
                        int i2 = i;
                        Context context2 = context;
                        switch (i2) {
                            case 0:
                                TextView textView = new TextView(context2);
                                textView.setTextAppearance(R.style.AccessibilityDialogTitle);
                                textView.setGravity(17);
                                return textView;
                            default:
                                TextView textView2 = new TextView(context2);
                                textView2.setTextAppearance(R.style.AccessibilityDialogDescription);
                                return textView2;
                        }
                    }
                });
        textSwitcher.setText(((TutorialPage) list.get(0)).mTitle);
        TextSwitcher textSwitcher2 = (TextSwitcher) inflate.findViewById(R.id.instruction);
        final int i2 = 1;
        textSwitcher2.setFactory(
                new ViewSwitcher
                        .ViewFactory() { // from class:
                                         // com.android.settings.accessibility.AccessibilityShortcutsTutorial$$ExternalSyntheticLambda4
                    @Override // android.widget.ViewSwitcher.ViewFactory
                    public final View makeView() {
                        int i22 = i2;
                        Context context2 = context;
                        switch (i22) {
                            case 0:
                                TextView textView = new TextView(context2);
                                textView.setTextAppearance(R.style.AccessibilityDialogTitle);
                                textView.setGravity(17);
                                return textView;
                            default:
                                TextView textView2 = new TextView(context2);
                                textView2.setTextAppearance(R.style.AccessibilityDialogDescription);
                                return textView2;
                        }
                    }
                });
        textSwitcher2.setText(((TutorialPage) list.get(0)).mInstruction);
        ViewPager viewPager = (ViewPager) inflate.findViewById(R.id.view_pager);
        viewPager.setAdapter(new TutorialPagerAdapter(list));
        viewPager.setContentDescription(
                context.getString(
                        R.string.accessibility_tutorial_pager, 1, Integer.valueOf(list.size())));
        viewPager.setImportantForAccessibility(list.size() <= 1 ? 4 : 1);
        new TutorialPageChangeListener(context, viewPager, textSwitcher, textSwitcher2, list)
                        .mOnPageSelectedCallback =
                accessibilityShortcutsTutorial$$ExternalSyntheticLambda1;
        return inflate;
    }

    public static List<TutorialPage> createShortcutTutorialPages(
            Context context, int i, CharSequence charSequence, boolean z) {
        View view;
        ArrayList arrayList = new ArrayList();
        if ((i & 16) == 16) {
            CharSequence text =
                    context.getText(R.string.accessibility_tutorial_dialog_title_quick_setting);
            View inflate =
                    ((LayoutInflater) context.getSystemService(LayoutInflater.class))
                            .inflate(
                                    R.layout.accessibility_lottie_animation_view, (ViewGroup) null);
            ((LottieAnimationView) inflate.findViewById(R.id.image))
                    .setImageResource(R.drawable.accessibility_shortcut_type_quick_settings);
            inflate.findViewById(R.id.image_background).setVisibility(8);
            int i2 = AccessibilityUtil.isTouchExploreEnabled(context) ? 2 : 1;
            ArrayMap arrayMap = new ArrayMap();
            arrayMap.put("count", Integer.valueOf(i2));
            arrayMap.put("featureName", charSequence);
            String icuPluralsString =
                    StringUtil.getIcuPluralsString(
                            context,
                            arrayMap,
                            R.string.accessibility_tutorial_dialog_message_quick_setting);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            if (z) {
                spannableStringBuilder
                        .append(
                                context.getText(
                                        R.string
                                                .accessibility_tutorial_dialog_shortcut_unavailable_in_suw))
                        .append((CharSequence) "\n\n");
            }
            spannableStringBuilder.append((CharSequence) icuPluralsString);
            ImageView createImageView = createImageView(context);
            createImageView.setEnabled(false);
            arrayList.add(
                    new TutorialPage(16, text, inflate, createImageView, spannableStringBuilder));
        }
        if ((i & 1) == 1) {
            boolean isFloatingMenuEnabled = AccessibilityUtil.isFloatingMenuEnabled(context);
            int i3 = R.string.accessibility_tutorial_dialog_title_button;
            if (!isFloatingMenuEnabled && AccessibilityUtil.isGestureNavigateEnabled(context)) {
                i3 = R.string.accessibility_tutorial_dialog_title_gesture;
            }
            CharSequence text2 = context.getText(i3);
            if (AccessibilityUtil.isFloatingMenuEnabled(context)) {
                view =
                        createIllustrationViewWithImageRawResource(
                                context, R.raw.accessibility_shortcut_type_fab);
            } else {
                int i4 =
                        AccessibilityUtil.isGestureNavigateEnabled(context)
                                ? AccessibilityUtil.isTouchExploreEnabled(context)
                                        ? R.drawable
                                                .accessibility_shortcut_type_gesture_touch_explore_on
                                        : R.drawable.accessibility_shortcut_type_gesture
                                : R.drawable.accessibility_shortcut_type_navbar;
                View inflate2 =
                        ((LayoutInflater) context.getSystemService(LayoutInflater.class))
                                .inflate(
                                        R.layout.accessibility_lottie_animation_view,
                                        (ViewGroup) null);
                ((LottieAnimationView) inflate2.findViewById(R.id.image)).setImageResource(i4);
                view = inflate2;
            }
            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
            if (AccessibilityUtil.isFloatingMenuEnabled(context)) {
                spannableStringBuilder2.append(
                        context.getText(
                                R.string.accessibility_tutorial_dialog_message_floating_button));
            } else if (AccessibilityUtil.isGestureNavigateEnabled(context)) {
                spannableStringBuilder2.append(
                        StringUtil.getIcuPluralsString(
                                context,
                                AccessibilityUtil.isTouchExploreEnabled(context) ? 3 : 2,
                                R.string
                                        .accessibility_tutorial_dialog_gesture_shortcut_instruction));
            } else {
                String charSequence2 =
                        context.getText(R.string.accessibility_tutorial_dialog_message_button)
                                .toString();
                SpannableString valueOf = SpannableString.valueOf(charSequence2);
                int indexOf = charSequence2.indexOf("%s");
                ImageView imageView = new ImageView(context);
                imageView.setImageDrawable(context.getDrawable(R.drawable.ic_accessibility_new));
                Drawable mutate = imageView.getDrawable().mutate();
                ImageSpan imageSpan = new ImageSpan(mutate);
                imageSpan.setContentDescription(ApnSettings.MVNO_NONE);
                mutate.setBounds(0, 0, mutate.getIntrinsicWidth(), mutate.getIntrinsicHeight());
                valueOf.setSpan(imageSpan, indexOf, indexOf + 2, 33);
                spannableStringBuilder2.append((CharSequence) valueOf);
            }
            ImageView createImageView2 = createImageView(context);
            createImageView2.setEnabled(false);
            arrayList.add(
                    new TutorialPage(1, text2, view, createImageView2, spannableStringBuilder2));
        }
        if ((i & 2) == 2) {
            CharSequence text3 =
                    context.getText(R.string.accessibility_tutorial_dialog_title_volume);
            View inflate3 =
                    ((LayoutInflater) context.getSystemService(LayoutInflater.class))
                            .inflate(
                                    R.layout.accessibility_lottie_animation_view, (ViewGroup) null);
            ((LottieAnimationView) inflate3.findViewById(R.id.image))
                    .setImageResource(R.drawable.accessibility_shortcut_type_volume_keys);
            ImageView createImageView3 = createImageView(context);
            CharSequence text4 =
                    context.getText(R.string.accessibility_tutorial_dialog_message_volume);
            createImageView3.setEnabled(false);
            arrayList.add(new TutorialPage(2, text3, inflate3, createImageView3, text4));
        }
        if ((i & 4) == 4) {
            CharSequence text5 =
                    context.getText(R.string.accessibility_tutorial_dialog_title_triple);
            View createIllustrationViewWithImageRawResource =
                    createIllustrationViewWithImageRawResource(
                            context, R.raw.accessibility_shortcut_type_tripletap);
            String string =
                    context.getString(
                            R.string.accessibility_tutorial_dialog_tripletap_instruction, 3);
            ImageView createImageView4 = createImageView(context);
            createImageView4.setEnabled(false);
            arrayList.add(
                    new TutorialPage(
                            4,
                            text5,
                            createIllustrationViewWithImageRawResource,
                            createImageView4,
                            string));
        }
        return arrayList;
    }

    public static View createTutorialDialogContentView(Context context, int i) {
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService("layout_inflater");
        if (i == 0) {
            return layoutInflater.inflate(
                    R.layout.tutorial_dialog_launch_service_by_accessibility_button,
                    (ViewGroup) null);
        }
        if (i == 1) {
            View inflate =
                    layoutInflater.inflate(
                            R.layout.tutorial_dialog_launch_service_by_gesture_navigation,
                            (ViewGroup) null);
            setupGestureNavigationTextWithImage(context, inflate);
            return inflate;
        }
        if (i != 2) {
            return null;
        }
        View inflate2 =
                layoutInflater.inflate(
                        R.layout.tutorial_dialog_launch_by_gesture_navigation_settings,
                        (ViewGroup) null);
        setupGestureNavigationTextWithImage(context, inflate2);
        return inflate2;
    }

    public static void setupGestureNavigationTextWithImage(Context context, View view) {
        boolean isTouchExploreEnabled = AccessibilityUtil.isTouchExploreEnabled(context);
        ((ImageView) view.findViewById(R.id.image))
                .setImageResource(
                        isTouchExploreEnabled
                                ? R.drawable
                                        .accessibility_shortcut_type_gesture_preview_touch_explore_on
                                : R.drawable.accessibility_shortcut_type_gesture_preview);
        ((TextView) view.findViewById(R.id.gesture_tutorial_message))
                .setText(
                        isTouchExploreEnabled
                                ? R.string
                                        .accessibility_tutorial_dialog_message_gesture_settings_talkback
                                : R.string.accessibility_tutorial_dialog_message_gesture_settings);
    }

    public static void showGestureNavigationTutorialDialog(
            Context context, DialogInterface.OnDismissListener onDismissListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(createTutorialDialogContentView(context, 2));
        builder.setPositiveButton(R.string.accessibility_tutorial_dialog_button, ON_CLICK_LISTENER);
        builder.P.mOnDismissListener = onDismissListener;
        AlertDialog create = builder.create();
        create.requestWindowFeature(1);
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    public static void updateTutorialNegativeButtonTextAndVisibility(
            AlertDialog alertDialog, List list, int i) {
        Button button = alertDialog.getButton(-2);
        int i2 = ((TutorialPage) list.get(i)).mType == 1 ? 0 : 8;
        button.setVisibility(i2);
        if (i2 == 0) {
            button.setText(
                    AccessibilityUtil.isFloatingMenuEnabled(alertDialog.getContext())
                            ? R.string.accessibility_tutorial_dialog_link_button
                            : R.string
                                    .accessibility_tutorial_dialog_configure_software_shortcut_type);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TutorialPageChangeListener implements ViewPager.OnPageChangeListener {
        public final Context mContext;
        public final TextSwitcher mInstruction;
        public int mLastTutorialPagePosition = 0;
        public AccessibilityShortcutsTutorial$$ExternalSyntheticLambda1 mOnPageSelectedCallback =
                null;
        public final TextSwitcher mTitle;
        public final List mTutorialPages;
        public final ViewPager mViewPager;

        public TutorialPageChangeListener(
                Context context,
                ViewPager viewPager,
                ViewGroup viewGroup,
                ViewGroup viewGroup2,
                List list) {
            this.mContext = context;
            this.mViewPager = viewPager;
            this.mTitle = (TextSwitcher) viewGroup;
            this.mInstruction = (TextSwitcher) viewGroup2;
            this.mTutorialPages = list;
            viewPager.addOnPageChangeListener(this);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageSelected(int i) {
            boolean z = this.mLastTutorialPagePosition > i;
            int i2 = z ? android.R.anim.slide_in_left : android.R.anim.wallpaper_intra_open_exit;
            int i3 = z ? android.R.anim.slide_out_right : android.R.anim.window_move_from_decor;
            this.mTitle.setInAnimation(this.mContext, i2);
            this.mTitle.setOutAnimation(this.mContext, i3);
            this.mTitle.setText(((TutorialPage) this.mTutorialPages.get(i)).mTitle);
            this.mInstruction.setInAnimation(this.mContext, i2);
            this.mInstruction.setOutAnimation(this.mContext, i3);
            this.mInstruction.setText(((TutorialPage) this.mTutorialPages.get(i)).mInstruction);
            Iterator it = this.mTutorialPages.iterator();
            while (it.hasNext()) {
                ((TutorialPage) it.next()).mIndicatorIcon.setEnabled(false);
            }
            ((TutorialPage) this.mTutorialPages.get(i)).mIndicatorIcon.setEnabled(true);
            this.mLastTutorialPagePosition = i;
            this.mViewPager.setContentDescription(
                    this.mContext.getString(
                            R.string.accessibility_tutorial_pager,
                            Integer.valueOf(i + 1),
                            Integer.valueOf(this.mTutorialPages.size())));
            AccessibilityShortcutsTutorial$$ExternalSyntheticLambda1
                    accessibilityShortcutsTutorial$$ExternalSyntheticLambda1 =
                            this.mOnPageSelectedCallback;
            if (accessibilityShortcutsTutorial$$ExternalSyntheticLambda1 != null) {
                AccessibilityShortcutsTutorial.updateTutorialNegativeButtonTextAndVisibility(
                        accessibilityShortcutsTutorial$$ExternalSyntheticLambda1.f$0,
                        accessibilityShortcutsTutorial$$ExternalSyntheticLambda1.f$1,
                        i);
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageScrollStateChanged(int i) {}

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageScrolled(int i, int i2, float f) {}
    }
}
