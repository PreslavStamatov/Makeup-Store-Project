package soft_uni.makeupstore.services;

import soft_uni.makeupstore.entities.User;
import soft_uni.makeupstore.repositories.UserRepository;
import soft_uni.makeupstore.services.dtos.*;

import java.util.Optional;

public interface UserService {

    void seedUser(UserRegistrationDTO userRegistrationDTO);

    void logIn(UserLogInDTO userLogInDTO);

    User dtoToUserEntity(BaseDTO baseDTO);

    boolean isUserLoggedIn(User user);

    UserPrintDto getCurrentlyLoggedUserAsUserPrintDTO();

    Optional<User> getCurrentlyLoggedUser();

    void logout();

    UserRepository getUserRepository();

    String buyMakeup();

    String viewCartContent();

    String viewOwnedMakeup();

}
