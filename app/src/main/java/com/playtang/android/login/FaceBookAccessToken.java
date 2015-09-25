package com.playtang.android.login;

import com.facebook.AccessTokenSource;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.*;

/**
 * Created by prem.k1 on 9/12/2015.
 */
public class FaceBookAccessToken {

    private Date expires;
    private Collection<String> permissions;
    private Collection<String> declinedPermissions;
    private String token;
    private AccessTokenSource source;
    private Date lastRefresh;
    private String applicationId;
    private String userId;

    private static final Date MAX_DATE = new Date(Long.MAX_VALUE);
    private static final Date DEFAULT_EXPIRATION_TIME = MAX_DATE;
    private static final Date DEFAULT_LAST_REFRESH_TIME = new Date();
    private static final AccessTokenSource DEFAULT_ACCESS_TOKEN_SOURCE =
            AccessTokenSource.FACEBOOK_APPLICATION_WEB;

    public FaceBookAccessToken(){}

    public FaceBookAccessToken(
            String accessToken,
            String applicationId,
            String userId,
            Collection<String> permissions,
            Collection<String> declinedPermissions,
            AccessTokenSource accessTokenSource,
            Date expirationTime,
            Date lastRefreshTime
    ) {

        this.expires = expirationTime != null ? expirationTime : DEFAULT_EXPIRATION_TIME;
        this.permissions = Collections.unmodifiableSet(
                permissions != null ? new HashSet<String>(permissions) : new HashSet<String>());
        this.declinedPermissions = Collections.unmodifiableSet(
                declinedPermissions != null
                        ? new HashSet<String>(declinedPermissions)
                        : new HashSet<String>());
        this.token = accessToken;
        this.source = accessTokenSource != null ? accessTokenSource : DEFAULT_ACCESS_TOKEN_SOURCE;
        this.lastRefresh = lastRefreshTime != null ? lastRefreshTime : DEFAULT_LAST_REFRESH_TIME;
        this.applicationId = applicationId;
        this.userId = userId;
    }

    public void setAccessToken(String accessToken){this.token = accessToken;}
    public void setApplicationId(String applicationId){this.applicationId = applicationId;}
    public void setUserId(String userId){this.userId = userId;}
    public void setPermissions(Collection<String> permissions){this.permissions = permissions;}
    public void setDeclinedPermissions(Collection<String> declinedPermissions){this.declinedPermissions = declinedPermissions;}
    public void setAccessTokenSource(AccessTokenSource accessTokenSource){this.source = accessTokenSource;}
    public void setExpirationTime(Date expirationTime){this.expires = expirationTime;}
    public void setLastRefreshTime(Date lastRefreshTime){this.lastRefresh = lastRefreshTime;}

    public String getAccessToken() {return token;}
    public String getApplicationId() {return applicationId;}
    public String getUserId() {return userId;}
    public Collection<String> getPermissions() {return permissions;}
    public Collection<String> getDeclinedPermissions() {return declinedPermissions;}
    public AccessTokenSource getAccessTokenSource() {return source;}
    public Date getExpirationTime() {return expires;}
    public Date getLastRefreshTime() {return lastRefresh;}


    @Override
    public String toString() {
        return "permissions=="+permissions+",declinedPermissions== "+declinedPermissions+
                "token =="+token+", applicationId"+applicationId+
                "userId"+userId+", AccessTokenSource"+source+
                "expires"+expires+", lastRefresh"+lastRefresh;
    }
}
