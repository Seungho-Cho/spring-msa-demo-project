package com.csh.user_service.entity;

//import auth.UserInterface;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "service_user")
public class UserEntity /*implements UserInterface*/ {

    @Id
    private String userId;

    @NonNull
    private String password;

    @NonNull
    @Column(nullable = false)
    private String userName;

    @NonNull
    @Column(unique = true, nullable = false)
    private String email;

    @NonNull
    private String role;

    public void copyForm(UserEntity userEntity) {
        BeanUtils.copyProperties(userEntity, this, "userId");
    }

}
