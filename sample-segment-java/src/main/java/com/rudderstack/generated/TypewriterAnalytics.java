/**
 * This client was automatically generated by RudderStack Typewriter. ** Do Not Edit **
 */
package com.rudderstack.generated;

import java.util.*;
import com.rudderstack.android.sdk.core.RudderClient;
import com.rudderstack.android.sdk.core.RudderOption;
import com.rudderstack.android.sdk.core.RudderProperty;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class TypewriterAnalytics {
    private RudderClient rudderClient;

    /**
     * Return a reference to the global default {@link TypewriterAnalytics} instance.
     *
     * This will use your the global default {@link RudderClient} instance.
     *
     * If you want to use a different {@link RudderClient} instance instance, see the
     * {@link TypewriterAnalytics} constructor below.
     */
    public static TypewriterAnalytics with(final Context context) {
        return new TypewriterAnalytics(RudderClient.with(context));
    }

    /**
     * Initializes a new TypewriterAnalytics instance wrapping the provided RudderStack client.
     *
     * You very likely want to use TypewriterAnalytics.with(context) method above instead, which
     * will utilize your existing singleton RudderStack {@link RudderClient} instance.
     *
     * @param rudderClient {@link RudderClient} configured RudderStack android client instance
     * @see <a href="https://docs.rudderstack.com/stream-sources/rudderstack-sdk-integration-guides/rudderstack-android-sdk#installing-the-sdk">Installing Android SDK</a>
     */
    public TypewriterAnalytics(final @NonNull RudderClient rudderClient) {
        this.rudderClient = rudderClient;
    }

    /**
     *Fired after a user's signin attempt fails to pass validation.
     * @param props {@link SignInFailed} to add extra information to this call.
     * @see <a href="https://docs.rudderstack.com/rudderstack-api-spec/http-api-specification#7-track">Track Documentation</a>
     */
    public void signInFailed(final @Nullable SignInFailed props) {
        this.rudderClient.track("Sign In Failed", props.toRudderProperty(), TypewriterUtils.addTypewriterContext());
    }

    /**
     * Fired after a user's signin attempt fails to pass validation.
     * @param props {@link SignInFailed} to add extra information to this call.
     * @see <a href="https://docs.rudderstack.com/rudderstack-api-spec/http-api-specification#7-track">Track Documentation</a>
     */
    public void signInFailed(final @Nullable SignInFailed props, final @Nullable RudderOption options) {
        this.rudderClient.track("Sign In Failed", props.toRudderProperty(), TypewriterUtils.addTypewriterContext(options));
    }
    /**
     *Fired when a user submits a sign in, prior to validating that user's login.
     * @param props {@link SignInSubmitted} to add extra information to this call.
     * @see <a href="https://docs.rudderstack.com/rudderstack-api-spec/http-api-specification#7-track">Track Documentation</a>
     */
    public void signInSubmitted(final @Nullable SignInSubmitted props) {
        this.rudderClient.track("Sign In Submitted", props.toRudderProperty(), TypewriterUtils.addTypewriterContext());
    }

    /**
     * Fired when a user submits a sign in, prior to validating that user's login.
     * @param props {@link SignInSubmitted} to add extra information to this call.
     * @see <a href="https://docs.rudderstack.com/rudderstack-api-spec/http-api-specification#7-track">Track Documentation</a>
     */
    public void signInSubmitted(final @Nullable SignInSubmitted props, final @Nullable RudderOption options) {
        this.rudderClient.track("Sign In Submitted", props.toRudderProperty(), TypewriterUtils.addTypewriterContext(options));
    }
    /**
     *Fired when a user successfully submits a sign in, prior to redirecting into the app.
     * @param props {@link SignInSucceeded} to add extra information to this call.
     * @see <a href="https://docs.rudderstack.com/rudderstack-api-spec/http-api-specification#7-track">Track Documentation</a>
     */
    public void signInSucceeded(final @Nullable SignInSucceeded props) {
        this.rudderClient.track("Sign In Succeeded", props.toRudderProperty(), TypewriterUtils.addTypewriterContext());
    }

    /**
     * Fired when a user successfully submits a sign in, prior to redirecting into the app.
     * @param props {@link SignInSucceeded} to add extra information to this call.
     * @see <a href="https://docs.rudderstack.com/rudderstack-api-spec/http-api-specification#7-track">Track Documentation</a>
     */
    public void signInSucceeded(final @Nullable SignInSucceeded props, final @Nullable RudderOption options) {
        this.rudderClient.track("Sign In Succeeded", props.toRudderProperty(), TypewriterUtils.addTypewriterContext(options));
    }
    /**
     *Fired when a user successfully submits a sign in, prior to redirecting into the app.
     * @param props {@link UserSignedOut} to add extra information to this call.
     * @see <a href="https://docs.rudderstack.com/rudderstack-api-spec/http-api-specification#7-track">Track Documentation</a>
     */
    public void userSignedOut(final @Nullable UserSignedOut props) {
        this.rudderClient.track("User Signed Out", props.toRudderProperty(), TypewriterUtils.addTypewriterContext());
    }

    /**
     * Fired when a user successfully submits a sign in, prior to redirecting into the app.
     * @param props {@link UserSignedOut} to add extra information to this call.
     * @see <a href="https://docs.rudderstack.com/rudderstack-api-spec/http-api-specification#7-track">Track Documentation</a>
     */
    public void userSignedOut(final @Nullable UserSignedOut props, final @Nullable RudderOption options) {
        this.rudderClient.track("User Signed Out", props.toRudderProperty(), TypewriterUtils.addTypewriterContext(options));
    }
}
