package org.wso2.identity.interceptor.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.core.handler.InitConfig;
import org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException;
import org.wso2.carbon.identity.oauth.dao.OAuthAppDO;
import org.wso2.carbon.identity.oauth.event.OAuthEventInterceptor;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.authz.OAuthAuthzReqMessageContext;
import org.wso2.carbon.identity.oauth2.dto.*;
import org.wso2.carbon.identity.oauth2.model.AccessTokenDO;
import org.wso2.carbon.identity.oauth2.model.RefreshTokenValidationDataDO;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.identity.interceptor.common.CustomOauthEventUtils;

import java.util.Map;

public class CustomOAuthTokenInterceptor implements OAuthEventInterceptor {
    private static Log log = LogFactory.getLog(CustomOAuthTokenInterceptor.class);
    private static final String CUSTOM_OAUTH_INTERCEPTOR = "Custom-oauth-interceptor";

    @Override
    public void onPreTokenIssue(OAuth2AccessTokenReqDTO oAuth2AccessTokenReqDTO, OAuthTokenReqMessageContext oAuthTokenReqMessageContext, Map<String, Object> map) throws IdentityOAuth2Exception {
        AuthenticatedUser authorizedUser = oAuthTokenReqMessageContext.getAuthorizedUser();
        String userStoreDomain = null;
        if (authorizedUser != null) {
            userStoreDomain = authorizedUser.getUserStoreDomain();
        }

        OAuthAppDO oAuthAppDO;
        try {
            oAuthAppDO = CustomOauthEventUtils.getApplication(oAuth2AccessTokenReqDTO.getClientId());
        } catch (InvalidOAuthClientException e) {
            throw new IdentityOAuth2Exception("Could not retrieve oauth application from given consumer key", e);
        }

        log.info("Userstore: " + userStoreDomain);
        log.info("app: " + oAuthAppDO.getApplicationName());
    }

    @Override
    public void onPostTokenIssue(OAuth2AccessTokenReqDTO oAuth2AccessTokenReqDTO, OAuth2AccessTokenRespDTO oAuth2AccessTokenRespDTO, OAuthTokenReqMessageContext oAuthTokenReqMessageContext, Map<String, Object> map) throws IdentityOAuth2Exception {

    }

    @Override
    public void onPreTokenIssue(OAuthAuthzReqMessageContext oAuthAuthzReqMessageContext, Map<String, Object> map) throws IdentityOAuth2Exception {

    }

    @Override
    public void onPostTokenIssue(OAuthAuthzReqMessageContext oAuthAuthzReqMessageContext, AccessTokenDO accessTokenDO, OAuth2AuthorizeRespDTO oAuth2AuthorizeRespDTO, Map<String, Object> map) throws IdentityOAuth2Exception {

    }

    @Override
    public void onPreTokenRenewal(OAuth2AccessTokenReqDTO oAuth2AccessTokenReqDTO, OAuthTokenReqMessageContext oAuthTokenReqMessageContext, Map<String, Object> map) throws IdentityOAuth2Exception {

    }

    @Override
    public void onPostTokenRenewal(OAuth2AccessTokenReqDTO oAuth2AccessTokenReqDTO, OAuth2AccessTokenRespDTO oAuth2AccessTokenRespDTO, OAuthTokenReqMessageContext oAuthTokenReqMessageContext, Map<String, Object> map) throws IdentityOAuth2Exception {

    }

    @Override
    public void onPreTokenRevocationByClient(OAuthRevocationRequestDTO oAuthRevocationRequestDTO, Map<String, Object> map) throws IdentityOAuth2Exception {

    }

    @Override
    public void onPostTokenRevocationByClient(OAuthRevocationRequestDTO oAuthRevocationRequestDTO, OAuthRevocationResponseDTO oAuthRevocationResponseDTO, AccessTokenDO accessTokenDO, RefreshTokenValidationDataDO refreshTokenValidationDataDO, Map<String, Object> map) throws IdentityOAuth2Exception {

    }

    @Override
    public void onPreTokenRevocationByResourceOwner(org.wso2.carbon.identity.oauth.dto.OAuthRevocationRequestDTO oAuthRevocationRequestDTO, Map<String, Object> map) throws IdentityOAuth2Exception {

    }

    @Override
    public void onPostTokenRevocationByResourceOwner(org.wso2.carbon.identity.oauth.dto.OAuthRevocationRequestDTO oAuthRevocationRequestDTO, org.wso2.carbon.identity.oauth.dto.OAuthRevocationResponseDTO oAuthRevocationResponseDTO, AccessTokenDO accessTokenDO, Map<String, Object> map) throws IdentityOAuth2Exception {

    }

    @Override
    public void onPreTokenValidation(OAuth2TokenValidationRequestDTO oAuth2TokenValidationRequestDTO, Map<String, Object> map) throws IdentityOAuth2Exception {

    }

    @Override
    public void onPostTokenValidation(OAuth2TokenValidationRequestDTO oAuth2TokenValidationRequestDTO, OAuth2TokenValidationResponseDTO oAuth2TokenValidationResponseDTO, Map<String, Object> map) throws IdentityOAuth2Exception {

    }

    @Override
    public void onPostTokenValidation(OAuth2TokenValidationRequestDTO oAuth2TokenValidationRequestDTO, OAuth2IntrospectionResponseDTO oAuth2IntrospectionResponseDTO, Map<String, Object> map) throws IdentityOAuth2Exception {

    }

    @Override
    public void init(InitConfig initConfig) {

    }

    @Override
    public String getName() {
        return CUSTOM_OAUTH_INTERCEPTOR;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
