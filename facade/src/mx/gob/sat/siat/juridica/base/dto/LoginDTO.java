package mx.gob.sat.siat.juridica.base.dto;

import java.util.Map;

public class LoginDTO extends BaseDataTransferObject {

    /**
     * 
     */
    private static final long serialVersionUID = -2689740777922490140L;
    private Map<String, String> users;
    private Map<String, String> roles;
    private UserProfileDTO userProfile = new UserProfileDTO();

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

    public UserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }

    public Map<String, String> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, String> roles) {
        this.roles = roles;
    }

}
