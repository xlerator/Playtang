package com.playtang.android.login;

import android.net.Uri;
/**
 * Created by prem.k1 on 9/12/2015.
 */
public class FaceBookProfile {

    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String name;
    private Uri linkUri;

    public FaceBookProfile() {

    }
    /**
     * Contructor.
     * @param id         The id of the profile.
     * @param firstName  The first name of the profile. Can be null.
     * @param middleName The middle name of the profile. Can be null.
     * @param lastName   The last name of the profile. Can be null.
     * @param name       The name of the profile. Can be null.
     * @param linkUri    The link for this profile. Can be null.
     */
    public FaceBookProfile(
            String id,
            String firstName,
            String middleName,
            String lastName,
            String name,
            Uri linkUri) {
        //Validate.notNullOrEmpty(id, "id");

        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.name = name;
        this.linkUri = linkUri;
    }

    public void setId(String id){this.id = id;}
    public void setFirstName(String firstName){this.firstName = firstName;}
    public void setMiddleName(String middleName){this.middleName = middleName;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public void setName(String name){this.name = name;}
    public void setLinkUri(Uri linkUri){this.linkUri = linkUri;}

    public String getId() {return id;}
    public String getFirstName() {return firstName;}
    public String getMiddleName() {return middleName;}
    public String getLastName() {return lastName;}
    public String getName() {return name;}
    public Uri getLinkUri() {return linkUri;}

    @Override
    public String toString() {
        return "id=="+id+", firstName=="+firstName+", middleName=="+middleName+
                "lastaname == "+lastName+", name="+name+"linkuri=="+linkUri;
    }
}
