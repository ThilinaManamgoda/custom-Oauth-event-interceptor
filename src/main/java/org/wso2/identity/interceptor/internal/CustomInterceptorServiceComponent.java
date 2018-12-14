package org.wso2.identity.interceptor.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.event.stream.core.EventStreamService;
import org.wso2.carbon.identity.core.handler.HandlerComparator;
import org.wso2.carbon.identity.oauth.common.OAuthConstants;
import org.wso2.carbon.identity.oauth.event.OAuthEventInterceptor;
import org.wso2.identity.interceptor.impl.CustomOAuthTokenInterceptor;

import java.util.Collections;
@Component(
        name = "custom-oauth",
        immediate = true

)
public class CustomInterceptorServiceComponent {
    private static Log log = LogFactory.getLog(CustomInterceptorServiceComponent.class);

    @Activate
    protected void activate(ComponentContext context) {
        BundleContext bundleContext = context.getBundleContext();
        try {
            bundleContext.registerService(OAuthEventInterceptor.class, new CustomOAuthTokenInterceptor(), null);
        } catch (Throwable e) {
            log.error("Error occurred while activating Custom Oauth event interceptor, ", e);
        }
    }

    protected void setEventStreamService(EventStreamService publisherService) {
        if(log.isDebugEnabled()) {
            log.debug("Registering EventStreamService");
        }
        OAuthDataPublisherServiceHolder.getInstance().setPublisherService(publisherService);
    }

    protected void unsetEventStreamService(EventStreamService publisherService) {
        if(log.isDebugEnabled()) {
            log.debug("Un-registering EventStreamService");
        }
        OAuthDataPublisherServiceHolder.getInstance().setPublisherService(null);
    }

    protected void setAuthEventInterceptor(OAuthEventInterceptor oAuthEventInterceptor) {

        if (oAuthEventInterceptor == null) {
            log.warn("Null OAuthEventListener received, hence not registering");
            return;
        }

        if (OAuthConstants.OAUTH_INTERCEPTOR_PROXY.equalsIgnoreCase(oAuthEventInterceptor.getName())) {
            log.debug("Oauth intercepter Proxy is getting registered, Hence skipping");
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("Setting OAuthEventListener :" + oAuthEventInterceptor.getClass().getName());
        }
        OAuthDataPublisherServiceHolder.getInstance().addOauthEventListener(oAuthEventInterceptor);
        Collections.sort(OAuthDataPublisherServiceHolder.getInstance().getOAuthEventInterceptors(),
                new HandlerComparator());
        Collections.reverse(OAuthDataPublisherServiceHolder.getInstance().getOAuthEventInterceptors());
    }

    protected void unsetOauthEventInterceptor(OAuthEventInterceptor oAuthEventInterceptor) {

        if (oAuthEventInterceptor == null) {
            log.warn("Null Oauth event interceptor received, hence not un-registering");
            return;
        }

        if (OAuthConstants.OAUTH_INTERCEPTOR_PROXY.equalsIgnoreCase(oAuthEventInterceptor.getName())) {
            log.debug("Proxy is un-registering, Hence skipping");
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("Un-setting oAuthEventInterceptor:" + oAuthEventInterceptor.getClass().getName());
        }
        OAuthDataPublisherServiceHolder.getInstance().removeOauthEventListener(oAuthEventInterceptor);
    }
}
