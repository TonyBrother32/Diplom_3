package praktikum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientCredentials {

    private String email;
    private String password;

    public ClientCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
