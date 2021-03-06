/*
 * Copyright (C) 2017 Buglife, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ook.group.android.otkritkionline.bugreport;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

interface ScreenshotObserver {
    void start(Activity currentActivity, ScreenshotObserverPermissionListener permissionListener);
    void stop();

    interface ScreenshotObserverPermissionListener {
        void onPermissionDenied();
    }

    class Factory {
        private Factory() {/* No instances */}
        public static ScreenshotObserver newInstance(Context context, com.ook.group.android.otkritkionline.bugreport.OnScreenshotTakenListener callback) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return new com.ook.group.android.otkritkionline.bugreport.ScreenshotContentObserver(context, callback);
            } else {
                return new ScreenshotFileObserver(callback);
            }
        }
    }
}
