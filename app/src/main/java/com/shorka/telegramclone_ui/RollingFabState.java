package com.shorka.telegramclone_ui;

import android.support.annotation.IntDef;

/**
 * Created by Kyrylo Avramenko on 7/11/2018.
 */

@IntDef({RollingFabState.IDLE, RollingFabState.ROLLING_OUT, RollingFabState.ROLLING_IN, RollingFabState.ROLLED_OUT})
public @interface RollingFabState {
    int ROLLING_OUT = -1;
    int ROLLING_IN = 0;
    int ROLLED_OUT = 1;
    int IDLE = 2;
}
