package tupt.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-07T13:46:43")
@StaticMetamodel(Registration.class)
public class Registration_ { 

    public static volatile SingularAttribute<Registration, String> password;
    public static volatile SingularAttribute<Registration, Integer> roleID;
    public static volatile SingularAttribute<Registration, Boolean> active;
    public static volatile SingularAttribute<Registration, Integer> id;
    public static volatile SingularAttribute<Registration, String> fullname;
    public static volatile SingularAttribute<Registration, String> email;

}