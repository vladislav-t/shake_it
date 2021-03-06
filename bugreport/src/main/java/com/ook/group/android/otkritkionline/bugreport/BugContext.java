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

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ook.group.android.otkritkionline.bugreport.reporting.DeviceSnapshot;
import com.ook.group.android.otkritkionline.bugreport.reporting.EnvironmentSnapshot;
import com.ook.group.android.otkritkionline.bugreport.reporting.SessionSnapshot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class BugContext implements Parcelable {
    private final ApiIdentity mApiIdentity;
    private final ArrayList<FileAttachment> mAttachments;
    private final com.ook.group.android.otkritkionline.bugreport.AttributeMap mAttributes;
    private final EnvironmentSnapshot mEnvironmentSnapshot;
    private final DeviceSnapshot mDeviceSnapshot;
    private final SessionSnapshot mSessionSnapshot;

    BugContext(ApiIdentity apiIdentity, @NonNull List<FileAttachment> attachments, @NonNull com.ook.group.android.otkritkionline.bugreport.AttributeMap attributes, SessionSnapshot sessionSnapshot, DeviceSnapshot deviceSnapshot, @NonNull EnvironmentSnapshot environment) {
        mApiIdentity = apiIdentity;
        mAttachments = new ArrayList<>(attachments);
        mAttributes = attributes;
        mSessionSnapshot = sessionSnapshot;
        mDeviceSnapshot = deviceSnapshot;
        mEnvironmentSnapshot = environment;
    }

    void addAttachment(@NonNull FileAttachment attachment) {
        mAttachments.add(attachment);
    }

    List<FileAttachment> getAttachments() {
        return mAttachments;
    }

    List<FileAttachment> getMediaAttachments() {
        ArrayList<FileAttachment> mediaAttachments = new ArrayList<>();

        for (FileAttachment attachment : getAttachments()) {
            if (attachment.isImage() || attachment.isVideo()) {
                mediaAttachments.add(attachment);
            }
        }

        return mediaAttachments;
    }

    EnvironmentSnapshot getEnvironmentSnapshot() {
        return mEnvironmentSnapshot;
    }

    void putAttribute(@NonNull String key, @Nullable String value) {
        mAttributes.put(key, new com.ook.group.android.otkritkionline.bugreport.Attribute(value, com.ook.group.android.otkritkionline.bugreport.Attribute.ValueType.STRING, com.ook.group.android.otkritkionline.bugreport.Attribute.FLAG_CUSTOM));
    }

    @Nullable
    com.ook.group.android.otkritkionline.bugreport.Attribute getAttribute(@NonNull String key) {
        return mAttributes.get(key);
    }

    @NonNull
    com.ook.group.android.otkritkionline.bugreport.AttributeMap getAttributes() {
        return mAttributes;
    }

    DeviceSnapshot getDeviceSnapshot() {
        return mDeviceSnapshot;
    }

    SessionSnapshot getSessionSnapshot() {
        return mSessionSnapshot;
    }

    ApiIdentity getApiIdentity() {
        return mApiIdentity;
    }

    /* Parcelable*/

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mApiIdentity, flags);
        dest.writeInt(this.mAttachments.size());
        dest.writeList(this.mAttachments);
        dest.writeParcelable(this.mAttributes, flags);
        dest.writeParcelable(this.mEnvironmentSnapshot, flags);
        dest.writeParcelable(this.mDeviceSnapshot, flags);
        dest.writeParcelable(this.mSessionSnapshot, flags);
    }

    BugContext(Parcel in) {
        this.mApiIdentity = in.readParcelable(ApiIdentity.class.getClassLoader());
        int attachmentsSize = in.readInt();
        this.mAttachments = new ArrayList<>(attachmentsSize);
        in.readList(this.mAttachments, FileAttachment.class.getClassLoader());
        this.mAttributes = in.readParcelable(com.ook.group.android.otkritkionline.bugreport.AttributeMap.class.getClassLoader());
        this.mEnvironmentSnapshot = in.readParcelable(EnvironmentSnapshot.class.getClassLoader());
        this.mDeviceSnapshot = in.readParcelable(DeviceSnapshot.class.getClassLoader());
        this.mSessionSnapshot = in.readParcelable(SessionSnapshot.class.getClassLoader());
    }

    public static final Creator<BugContext> CREATOR = new Creator<BugContext>() {
        @Override public BugContext createFromParcel(Parcel source) {
            return new BugContext(source);
        }

        @Override public BugContext[] newArray(int size) {
            return new BugContext[size];
        }
    };

    static class Builder {
        private final @NonNull Context mContext;
        private @NonNull ArrayList<FileAttachment> mAttachments = new ArrayList();
        private @NonNull
        com.ook.group.android.otkritkionline.bugreport.AttributeMap mAttributeMap = new com.ook.group.android.otkritkionline.bugreport.AttributeMap();
        private @NonNull String mUserEmail;
        private @NonNull String mUserIdentifier;
        private @NonNull ApiIdentity mApiIdentity;
        private @NonNull InvocationMethod mInvocationMethod;
        private boolean mShouldCollectLocation;

        Builder(@NonNull Context context) {
            mContext = context;
            addLogFileAttachment();
        }

        Builder setAttachments(@NonNull List<FileAttachment> attachments) {
            mAttachments.clear();
            return addAttachments(attachments);
        }

        Builder addAttachments(List<FileAttachment> attachments) {
            mAttachments.addAll(attachments);
            return this;
        }

        Builder setAttributes(@NonNull com.ook.group.android.otkritkionline.bugreport.AttributeMap attributes) {
            mAttributeMap = new com.ook.group.android.otkritkionline.bugreport.AttributeMap(attributes);
            return this;
        }

        Builder setUserEmail(String userEmail) {
            mUserEmail = userEmail;
            return this;
        }

        Builder setUserIdentifier(String userIdentifier) {
            mUserIdentifier = userIdentifier;
            return this;
        }

        Builder setApiIdentity(ApiIdentity apiIdentity) {
            mApiIdentity = apiIdentity;
            return this;
        }

        Builder setInvocationMethod(InvocationMethod method) {
            mInvocationMethod = method;
            return this;
        }

        Builder setCollectLocationIfPossible(boolean shouldCollect) {
            mShouldCollectLocation = shouldCollect;
            return this;
        }

        public BugContext build() {
            SessionSnapshot sessionSnapshot = new SessionSnapshot(mContext, mUserEmail, mUserIdentifier);
            EnvironmentSnapshot environment = new EnvironmentSnapshot(mContext, mInvocationMethod, mShouldCollectLocation);
            DeviceSnapshot deviceSnapshot = new DeviceSnapshot(mContext);
            return new BugContext(mApiIdentity, mAttachments, mAttributeMap, sessionSnapshot, deviceSnapshot, environment);
        }

        private void addLogFileAttachment() {
            try {
                File logFile = new File(mContext.getCacheDir(), "log_" + System.currentTimeMillis());
                LogDumper.dumpToFile(logFile);
                mAttachments.add(new LogFileAttachment(logFile));
            } catch (IOException e) {
                com.ook.group.android.otkritkionline.bugreport.Log.e("Error dumping logs to file!", e);
            }
        }
    }
}
