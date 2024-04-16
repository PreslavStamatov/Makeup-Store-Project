package soft_uni.makeupstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soft_uni.makeupstore.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmailAndPassword(String email, String password);

    @Query("FROM User u WHERE u.isLogged IS true")
    public Optional<User> findLoggedInUser();
}
