package sstu.lora.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sstu.lora.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
