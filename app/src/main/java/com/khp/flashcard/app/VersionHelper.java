package com.khp.flashcard.app;

import android.app.Activity;

/**
 * Created by KHP on 05/07/2014.
 */
public class VersionHelper {
    static void refreshActionBarMenu(Activity activity) {
        activity.invalidateOptionsMenu();
    }
}
