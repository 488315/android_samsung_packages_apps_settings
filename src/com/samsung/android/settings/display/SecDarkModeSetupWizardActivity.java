package com.samsung.android.settings.display;

import android.app.UiModeManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.util.Consumer;

import com.android.settings.R;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.wifi.SetupWizardLogMsg;
import com.sec.android.secsetupwizardlib.SuwBaseActivity;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDarkModeSetupWizardActivity extends SuwBaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum UiMode {
        LIGHT(1, R.drawable.sec_suw_darkmode_help_light, "LIGHT"),
        DARK(2, R.drawable.sec_suw_darkmode_help_dark, "DARK"),
        /* JADX INFO: Fake field, exist only in values array */
        EF50(1, R.drawable.sec_suw_darkmode_help_light_fold, "LIGHT_FOLD"),
        /* JADX INFO: Fake field, exist only in values array */
        EF62(2, R.drawable.sec_suw_darkmode_help_dark_fold, "DARK_FOLD");

        final int drawableRes;
        final int stringRes;
        final int value;

        UiMode(int i, int i2, String str) {
            this.stringRes = r2;
            this.value = i;
            this.drawableRes = i2;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UiModeManagerAdapter implements UiModeUpdater {
        public final UiModeManager mUiModeManager;

        public UiModeManagerAdapter(UiModeManager uiModeManager) {
            this.mUiModeManager = uiModeManager;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface UiModeUpdater {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewHolder {
        public final ImageView imageView;
        public final View itemView;
        public final TextView titleView;

        public ViewHolder(View view) {
            this.itemView = view;
            this.imageView = (ImageView) view.findViewById(R.id.image);
            this.titleView = (TextView) view.findViewById(R.id.title);
        }

        public final void bind(UiMode uiMode, UiMode uiMode2, Consumer consumer) {
            this.titleView.setText(uiMode.stringRes);
            this.imageView.setImageDrawable(
                    this.itemView.getContext().getDrawable(uiMode.drawableRes));
            this.itemView.setOnClickListener(
                    new SecDarkModeSetupWizardActivity$$ExternalSyntheticLambda3(
                            consumer, uiMode, 1));
            this.itemView.setBackgroundResource(
                    uiMode == uiMode2
                            ? R.drawable.sec_setupwizard_selector
                            : R.drawable.sec_setupwizard_background);
            this.itemView.setOnTouchListener(
                    new SecDarkModeSetupWizardActivity$ViewHolder$$ExternalSyntheticLambda1());
        }
    }

    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        SetupWizardLogMsg.out("DarkModeSetupWizard", "onCreate");
        super.onCreate(bundle);
        int i = 0;
        setContentView(R.layout.sec_dark_setupwizard, false);
        setHeaderIcon(getDrawable(R.drawable.ic_suw_tips));
        setHeaderTitle(R.string.night_display_setupwizard_title);
        List list =
                (List)
                        Stream.of(
                                        (Object[])
                                                new Integer[] {
                                                    Integer.valueOf(R.id.item1),
                                                    Integer.valueOf(R.id.item2)
                                                })
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.settings.display.SecDarkModeSetupWizardActivity$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                SecDarkModeSetupWizardActivity
                                                        secDarkModeSetupWizardActivity =
                                                                SecDarkModeSetupWizardActivity.this;
                                                int i2 = SecDarkModeSetupWizardActivity.$r8$clinit;
                                                secDarkModeSetupWizardActivity.getClass();
                                                return secDarkModeSetupWizardActivity.findViewById(
                                                        ((Integer) obj).intValue());
                                            }
                                        })
                                .map(new SecDarkModeSetupWizardActivity$$ExternalSyntheticLambda1())
                                .collect(Collectors.toList());
        UiModeManager uiModeManager = (UiModeManager) getSystemService(UiModeManager.class);
        final UiModeManagerAdapter uiModeManagerAdapter = new UiModeManagerAdapter(uiModeManager);
        int nightMode = uiModeManager.getNightMode();
        UiMode uiMode = UiMode.DARK;
        UiMode uiMode2 = UiMode.LIGHT;
        UiMode uiMode3 = nightMode == 1 ? uiMode2 : uiMode;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        ((ViewHolder) list.get(0))
                .bind(
                        uiMode2,
                        uiMode3,
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.display.SecDarkModeSetupWizardActivity$$ExternalSyntheticLambda2
                            @Override // androidx.core.util.Consumer
                            public final void accept(Object obj) {
                                SecDarkModeSetupWizardActivity.UiModeManagerAdapter
                                        uiModeManagerAdapter2 =
                                                (SecDarkModeSetupWizardActivity
                                                                .UiModeManagerAdapter)
                                                        uiModeManagerAdapter;
                                int nightMode2 =
                                        uiModeManagerAdapter2.mUiModeManager.getNightMode();
                                int i2 = ((SecDarkModeSetupWizardActivity.UiMode) obj).value;
                                if (nightMode2 == i2) {
                                    return;
                                }
                                uiModeManagerAdapter2.mUiModeManager.setNightMode(i2);
                            }
                        });
        ((ViewHolder) list.get(1))
                .bind(
                        uiMode,
                        uiMode3,
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.display.SecDarkModeSetupWizardActivity$$ExternalSyntheticLambda2
                            @Override // androidx.core.util.Consumer
                            public final void accept(Object obj) {
                                SecDarkModeSetupWizardActivity.UiModeManagerAdapter
                                        uiModeManagerAdapter2 =
                                                (SecDarkModeSetupWizardActivity
                                                                .UiModeManagerAdapter)
                                                        uiModeManagerAdapter;
                                int nightMode2 =
                                        uiModeManagerAdapter2.mUiModeManager.getNightMode();
                                int i2 = ((SecDarkModeSetupWizardActivity.UiMode) obj).value;
                                if (nightMode2 == i2) {
                                    return;
                                }
                                uiModeManagerAdapter2.mUiModeManager.setNightMode(i2);
                            }
                        });
        setPrimaryActionButton(
                R.string.next_button_label,
                new SecDarkModeSetupWizardActivity$$ExternalSyntheticLambda3(this, uiMode3, i));
    }
}
