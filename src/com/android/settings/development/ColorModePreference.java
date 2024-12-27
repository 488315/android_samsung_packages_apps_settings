package com.android.settings.development;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.util.AttributeSet;
import android.view.Display;

import androidx.preference.TwoStatePreference;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ColorModePreference extends TwoStatePreference
        implements DisplayManager.DisplayListener {
    public int mCurrentIndex;
    public List mDescriptions;
    public Display mDisplay;
    public final DisplayManager mDisplayManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ColorModeDescription {
        public int colorMode;
    }

    public ColorModePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDisplayManager = (DisplayManager) getContext().getSystemService(DisplayManager.class);
    }

    public static List getColorModeDescriptions(Context context) {
        ArrayList arrayList = new ArrayList();
        Resources resources = context.getResources();
        int[] intArray = resources.getIntArray(R.array.color_mode_ids);
        String[] stringArray = resources.getStringArray(R.array.color_mode_names);
        String[] stringArray2 = resources.getStringArray(R.array.color_mode_descriptions);
        for (int i = 0; i < intArray.length; i++) {
            int i2 = intArray[i];
            if (i2 != -1 && i != 1) {
                ColorModeDescription colorModeDescription = new ColorModeDescription();
                colorModeDescription.colorMode = i2;
                String str = stringArray[i];
                String str2 = stringArray2[i];
                arrayList.add(colorModeDescription);
            }
        }
        return arrayList;
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayAdded(int i) {
        if (i == 0) {
            updateCurrentAndSupported();
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayChanged(int i) {
        if (i == 0) {
            updateCurrentAndSupported();
        }
    }

    @Override // androidx.preference.Preference
    public final boolean persistBoolean(boolean z) {
        if (((ArrayList) this.mDescriptions).size() != 2) {
            return true;
        }
        ColorModeDescription colorModeDescription =
                (ColorModeDescription) ((ArrayList) this.mDescriptions).get(z ? 1 : 0);
        this.mDisplay.requestColorMode(colorModeDescription.colorMode);
        this.mCurrentIndex = ((ArrayList) this.mDescriptions).indexOf(colorModeDescription);
        return true;
    }

    public final void updateCurrentAndSupported() {
        this.mDisplay = this.mDisplayManager.getDisplay(0);
        this.mDescriptions = getColorModeDescriptions(getContext());
        int colorMode = this.mDisplay.getColorMode();
        this.mCurrentIndex = -1;
        int i = 0;
        while (true) {
            if (i >= this.mDescriptions.size()) {
                break;
            }
            if (((ColorModeDescription) this.mDescriptions.get(i)).colorMode == colorMode) {
                this.mCurrentIndex = i;
                break;
            }
            i++;
        }
        setChecked(this.mCurrentIndex == 1);
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayRemoved(int i) {}
}
