package org.wso2.identity.interceptor.common;

import org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException;
import org.wso2.carbon.identity.oauth.dao.OAuthAppDAO;
import org.wso2.carbon.identity.oauth.dao.OAuthAppDO;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;

/**
 * This class contains utils functions required for this bundle
 */
public class CustomOauthEventUtils {

    /**
     * Get the application, for which the token is generated
     * @param consumerID Consumer key
     * @return Instance which holds application info
     * @throws IdentityOAuth2Exception
     * @throws InvalidOAuthClientException
     */
    public static OAuthAppDO getApplication(String consumerID) throws IdentityOAuth2Exception, InvalidOAuthClientException {
        OAuthAppDAO oAuthAppDAO = new OAuthAppDAO();
        OAuthAppDO oAuthAppDO = oAuthAppDAO.getAppInformation(consumerID);
        return oAuthAppDO;
    }
}
