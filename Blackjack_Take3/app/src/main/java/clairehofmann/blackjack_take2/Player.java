// Joleen Powers // Joleen Powers (and Claire Hofmann)
// Blackjack: DatabaseHelper.java

// Joleen

package clairehofmann.blackjack_take2;


import java.io.Serializable;

public class Player implements Serializable
{
    private String  email;          // primary key
    private String  password;
    private String  adminCheck;     // "Is player an admin?"
    private String  firstName;
    private String  lastName;
    private String  cash;

    public String getEmail() {  return email;  }

    public void setEmail(String email) {  this.email = email;  }

    public String getPassword() {  return password;  }

    public void setPassword(String password) {  this.password = password;  }

    public String getAdminCheck() {  return adminCheck;  }

    public void setAdminCheck(String adminCheck) {  this.adminCheck = adminCheck;  }

    public String getFirstName() {  return firstName;  }

    public void setFirstName(String firstName) {  this.firstName = firstName; }

    public String getLastName() {  return lastName;  }

    public void setLastName(String lastName) {  this.lastName = lastName;  }

    public String getCash() {  return cash;  }

    public void setCash(String cash) {  this.cash = cash;  }
}
