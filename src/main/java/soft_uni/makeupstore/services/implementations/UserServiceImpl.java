package soft_uni.makeupstore.services.implementations;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import soft_uni.makeupstore.entities.Cart;
import soft_uni.makeupstore.entities.Makeup;
import soft_uni.makeupstore.entities.User;
import soft_uni.makeupstore.repositories.UserRepository;
import soft_uni.makeupstore.services.UserService;
import soft_uni.makeupstore.services.dtos.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void seedUser(UserRegistrationDTO userRegistrationDTO) {

        User user = dtoToUserEntity(userRegistrationDTO);
        if (userRepository.count() == 0) {
            user.setAdministrator(true);
        }



        try {
            userRepository.saveAndFlush(user);
            System.out.println("Successfully added user " + user.getFullName());
        } catch (DataIntegrityViolationException e) {
            System.out.println("User with that email already exists");
        } catch (ConstraintViolationException e) {
            System.out.println("Invalid email or password\n" +
                    "Email must contain @\n" +
                    "Password must be at least 6 symbols long and must contain a capital letter, a lowercase letter and a digit");
        }

    }

    public User dtoToUserEntity(BaseDTO baseDTO) {
        return modelMapper.map(baseDTO, User.class);
    }


    public boolean isUserLoggedIn(User user) {
       return user.equals(userRepository.findLoggedInUser());
    }

    @Override
    public Optional<User> getCurrentlyLoggedUser() {
        return userRepository.findLoggedInUser();
    }

    public UserPrintDto getCurrentlyLoggedUserAsUserPrintDTO() {
        Optional<User> loggedInUser = userRepository.findLoggedInUser();


        if (loggedInUser.isEmpty()) {
            throw new IllegalArgumentException("No logged user");
        }

        UserPrintDto userPrintDto = modelMapper.map(loggedInUser.get(), UserPrintDto.class);

        return userPrintDto;
    }


    @Override
    public void logIn(UserLogInDTO userLogInDTO) {

        User user = dtoToUserEntity(userLogInDTO);
        User userToBeLogged = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

        if (null == userToBeLogged) {
            throw new IllegalArgumentException("Wrong username / password");
        } else  if (userToBeLogged.isLogged()){
            throw new IllegalArgumentException("User already logged in");
        } else {
            Optional<User> currentlyLoggedUser = userRepository.findLoggedInUser();
            User userToBeLoggedOut;
            if (currentlyLoggedUser.isPresent()) {
                userToBeLoggedOut = currentlyLoggedUser.get();
                userToBeLoggedOut.setLogged(false);
                userRepository.saveAndFlush(userToBeLoggedOut);
            }

            userToBeLogged.setLogged(true);
            userRepository.saveAndFlush(userToBeLogged);
            System.out.println(userToBeLogged.getFullName() + " was logged successfully");
        }
    }

    @Override
    public void logout() {
        Optional<User> userToBeLoggedOutOptional = userRepository.findLoggedInUser();
        User userToBeLoggedOut;
        if (userToBeLoggedOutOptional.isEmpty()) {
            throw new IllegalArgumentException("There is no logged user to be logged out");
        } else {
            userToBeLoggedOut = userToBeLoggedOutOptional.get();
            userToBeLoggedOut.setLogged(false);
            userRepository.saveAndFlush(userToBeLoggedOut);
            System.out.println("User " + userToBeLoggedOut.getFullName() + " successfully logged out");
        }
    }

    @Override
    @Modifying
    @Transactional
    public String buyMakeup() {
        Optional<User> currentlyLoggedUser = getCurrentlyLoggedUser();
        if (currentlyLoggedUser.isEmpty()) {
            return "No logged user";
        }

        Cart loggedInUserCart = currentlyLoggedUser.get().getCart();

        if (loggedInUserCart.getContent().isEmpty()) {
            return "Cart is empty";
        }

        Set<Makeup> makeupToBeBought = loggedInUserCart.getContent();

        String makeupToBeBoughtPrint = String.format("%s",makeupToBeBought.stream().map(Makeup::toString)
                .collect(Collectors.joining("\n\n")));

        makeupToBeBought.stream().forEach(m -> getCurrentlyLoggedUser().get().getMakeupSet().add(m));

        loggedInUserCart.getContent().clear();
        loggedInUserCart.setCurrentPrice(BigDecimal.ZERO);

        return String.format("Successfully bought makeup:%n%s", makeupToBeBoughtPrint);
    }

    @Override
    public String viewCartContent() {

        Optional<User> userOptional = getCurrentlyLoggedUser();

        if (userOptional.isEmpty()) {
            return "No logged user found";
        } else if (userOptional.get().getCart().getContent().isEmpty()) {
            return "Cart is empty";
        }

        return userOptional.get().getCart().getContent().stream().map(Makeup::toString).collect(Collectors.joining("\n\n"));

    }

    @Override
    public String viewOwnedMakeup() {
        Optional<User> currentlyLoggedUser = getCurrentlyLoggedUser();
        if (currentlyLoggedUser.isEmpty()) {
            return "No logged user";
        }
        if (currentlyLoggedUser.get().getMakeupSet().isEmpty()) {
            return "No makeup in your collection";
        }

        return currentlyLoggedUser.get().getMakeupSet().stream().map(Makeup::toString).collect(Collectors.joining("\n\n"));
    }
}
