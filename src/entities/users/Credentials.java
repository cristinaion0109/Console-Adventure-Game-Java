package entities.users;

public class Credentials {
    private String email;
    private String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password =  password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "email : " + email + " password: " + password;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Credentials)) {
            return false;
        }

        Credentials c = (Credentials) obj;

        if(this.email.equals(c.email) && this.password.equals(c.password)) {
            return true;
        }
        else {
            return false;
        }
    }
}
