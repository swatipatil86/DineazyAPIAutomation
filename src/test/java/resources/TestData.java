package resources;

import pojo.*;

public class TestData
{
    public userCred LoginCredentials(String username, String password)
    {
        userCred cred=new userCred();
        cred.setEmail(username);
        cred.setPassword(password);
        return(cred);
    }

    public CreateProfileData CreateProfileData(String firstName, String lastName, String email, String password, String phone)
    {
        CreateProfileData pdata=new CreateProfileData();
        pdata.setFirstName(firstName);
        pdata.setLastName(lastName);
        pdata.setEmail(email);
        pdata.setPassword(password);
        pdata.setPhone(phone);
        return pdata;
    }

    public OTPPayload OTPDetails(String firstName, String lastName, String email,String password,String phone)
    {
        OTPPayload otp=new OTPPayload();
        otp.setFirstName(firstName);
        otp.setLastName(lastName);
        otp.setEmail(email);
        otp.setPassword(password);
        otp.setPhone(phone);
        return otp;
    }

    public VerifyOtp VerifyOtpPayload(String email, String phone,String otp)
    {
        VerifyOtp vo=new VerifyOtp();
        vo.setEmail(email);
        vo.setPhone(phone);
        vo.setOtp(otp);
        return vo;
    }

    public Register RegisterPayload(String firstName, String lastName, String email, String password, String phone)
    {
        Register r=new Register();
        r.setFirstName(firstName);
        r.setLastName(lastName);
        r.setEmail(email);
        r.setPassword(password);
        r.setPhone(phone);
        return r;

    }
}
