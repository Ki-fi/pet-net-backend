package petnet.com.models;

import jakarta.persistence.*;
import java.util.List;
import static petnet.com.models.UserRole.USER;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String preposition;
    private String lastName;
    private String avatar;
    @OneToOne(mappedBy="user", cascade = CascadeType.ALL)
    private Account account;
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Post> posts;
    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Response> responses;
    @Enumerated(EnumType.STRING)
    private UserRole role = USER;

    public Long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPreposition() {
        return preposition;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() { return avatar; }

    public void setAvatar(String avatar) { this.avatar = avatar; }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Post> getPosts() { return posts; }

    public void setPosts(List<Post> posts) { this.posts = posts; }

    public List<Response> getResponses() { return responses; }

    public void setResponses(List<Response> responses) { this.responses = responses; }

    public UserRole getUserRole() { return role; }

    public void setRole(UserRole role) { this.role = role; }
}
