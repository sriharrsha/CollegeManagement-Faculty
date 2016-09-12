
package app.management.college.com.collegemanagement.api.Authentication.RegularAuth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegularLoginResponse {

    @SerializedName("AuthenticationResult")
    @Expose
    private Integer authenticationResult;
    @SerializedName("ServiceResult")
    @Expose
    private Integer serviceResult;
    @SerializedName("Token")
    @Expose
    private String token;

    /**
     * 
     * @return
     *     The authenticationResult
     */
    public Integer getAuthenticationResult() {
        return authenticationResult;
    }

    /**
     * 
     * @param authenticationResult
     *     The AuthenticationResult
     */
    public void setAuthenticationResult(Integer authenticationResult) {
        this.authenticationResult = authenticationResult;
    }

    /**
     * 
     * @return
     *     The serviceResult
     */
    public Integer getServiceResult() {
        return serviceResult;
    }

    /**
     * 
     * @param serviceResult
     *     The ServiceResult
     */
    public void setServiceResult(Integer serviceResult) {
        this.serviceResult = serviceResult;
    }

    /**
     * 
     * @return
     *     The token
     */
    public String getToken() {
        return token;
    }

    /**
     * 
     * @param token
     *     The Token
     */
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "RegularLoginResponse{" +
                "authenticationResult=" + authenticationResult +
                ", serviceResult=" + serviceResult +
                ", token='" + token + '\'' +
                '}';
    }
}
