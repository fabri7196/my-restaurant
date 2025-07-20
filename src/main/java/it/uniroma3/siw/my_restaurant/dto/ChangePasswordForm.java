package it.uniroma3.siw.my_restaurant.dto;

import jakarta.validation.constraints.Size;

public class ChangePasswordForm {
    
    private String oldPassword;
    
    @Size(min=8, message = "La password deve contenere almeno 8 caratteri.")
    private String newPassword;
    
    @Size(min=8, message = "La password deve contenere almeno 8 caratteri.")
    private String confirmNewPassword;
    
    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }
    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
    
}
