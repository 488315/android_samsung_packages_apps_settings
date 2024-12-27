package com.android.settings.biometrics.fingerprint2.data.model;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class EnrollStageModel {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Center extends EnrollStageModel {
        public static final Center INSTANCE = new Center();

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Center);
        }

        public final int hashCode() {
            return -554808715;
        }

        public final String toString() {
            return "Center";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Fingertip extends EnrollStageModel {
        public static final Fingertip INSTANCE = new Fingertip();

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Fingertip);
        }

        public final int hashCode() {
            return -95459726;
        }

        public final String toString() {
            return "Fingertip";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Guided extends EnrollStageModel {
        public static final Guided INSTANCE = new Guided();

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Guided);
        }

        public final int hashCode() {
            return -425680120;
        }

        public final String toString() {
            return "Guided";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LeftEdge extends EnrollStageModel {
        public static final LeftEdge INSTANCE = new LeftEdge();

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof LeftEdge);
        }

        public final int hashCode() {
            return 1975164036;
        }

        public final String toString() {
            return "LeftEdge";
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RightEdge extends EnrollStageModel {
        public static final RightEdge INSTANCE = new RightEdge();

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof RightEdge);
        }

        public final int hashCode() {
            return 2108457945;
        }

        public final String toString() {
            return "RightEdge";
        }
    }
}
