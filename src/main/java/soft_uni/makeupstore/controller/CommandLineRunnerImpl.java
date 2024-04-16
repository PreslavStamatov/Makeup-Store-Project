package soft_uni.makeupstore.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import soft_uni.makeupstore.services.MakeupService;
import soft_uni.makeupstore.services.UserService;
import soft_uni.makeupstore.services.dtos.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private static UserService userService;
    private static MakeupService makeupService;
    private static BufferedReader bufferedReader;

    public CommandLineRunnerImpl(UserService userService, MakeupService makeupService) {
        this.userService = userService;

        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.makeupService = makeupService;
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            String[] input = bufferedReader.readLine().split(" ");
            String command = input[0];

            String[] userData = Arrays.copyOfRange(input, 1, input.length);

            switch (command) {
                case "RegisterUser":
                    registerUser(userData);
                    break;
                case "LoginUser":
                    logInUser(userData);
                    break;
                case "GetLoggedUser":
                    printLoggedUser();
                    break;
                case "Logout":
                    logoutUser();
                    break;
                case "AddMakeup":
                    addMakeup(userData);
                    break;
                case "EditMakeup":
                    editMakeup(userData);
                    break;
                case "DeleteMakeup":
                    deleteMakeup(userData);
                    break;
                case "ViewMakeup":
                    displayMakeup(userData);
                    break;
                case "ViewAll":
                    displayAll();
                    break;
                case "AddToCart":
                    addToCart(userData);
                    break;
                case "BuyAll":
                    buyAll();
                    break;
                case "ViewCart":
                    viewCart();
                    break;
                case "ViewCollection":
                    viewCollection();
                    break;
                case "End":
                    return;
            }
        }
    }

    private void viewCollection() {
        System.out.println(userService.viewOwnedMakeup());
    }

    private void viewCart() {
        System.out.println(userService.viewCartContent());
    }

    private void buyAll() {
        System.out.println(userService.buyMakeup());
    }

    private void addToCart(String[] userInput) {
        System.out.println(makeupService.addToCart(new AddMakeupToCartDTO(userInput[0])));
    }
    private void displayAll() {
        System.out.println(makeupService.findAllMakeup());
    }

    private void displayMakeup(String[] userData) {
        System.out.println(makeupService.findMakeup(new FindMakeupDTO(userData[0])));
    }

    private void deleteMakeup(String[] userData) {
        System.out.println(makeupService.deleteMakeup(new DeleteMakeupDTO(Integer.parseInt(userData[0]))));
    }


    private void editMakeup(String[] userData) {
        System.out.println(makeupService.editMakeup(createEditMakeupDTO(userData)));
    }

    private void addMakeup(String[] userData) {
        AddMakeupDTO addMakeupDTO = createAddMakeupDTO(userData);

        try {
            makeupService.seedMakeup(addMakeupDTO);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    private EditMakeupDTO createEditMakeupDTO(String[] userData) {
        int id = Integer.parseInt(userData[0]);
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(userData[1].split("=")[1]));
        int size = Integer.parseInt(userData[2].split("=")[1]);

        return new EditMakeupDTO(id, price, size);
    }

    private AddMakeupDTO createAddMakeupDTO(String[] userData) {
        String brand = userData[0];
        String type = userData[1];
        String imageURL = userData[2];
        int quantity = Integer.parseInt(userData[3]);
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(userData[4]));
        String description = String.join(" ", Arrays.copyOfRange(userData, 5, userData.length));

        return new AddMakeupDTO(brand,type, imageURL, quantity, price, description);
    }

    private UserRegistrationDTO createUserRegistrationDTO(String[] userData) {
        String email = userData[0];
        String password = userData[1];
        String fullName = userData[3];


        return new UserRegistrationDTO(email, password, fullName);
    }

    private UserLogInDTO createUserLogInDTO(String[] userData) {
        String email = userData[0];
        String password = userData[1];

        return new UserLogInDTO(email, password);
    }

    private void registerUser(String[] userData) {
        if (isValidInfo(userData)) {
            userService.seedUser(createUserRegistrationDTO(userData));
        }
    }

    private void logInUser(String[] userData) {
        UserLogInDTO userLogInDTO = createUserLogInDTO(userData);

        try {
            userService.logIn(userLogInDTO);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void logoutUser() {
        try {
            userService.logout();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printLoggedUser() {

        try {
            UserPrintDto currentlyLoggedUser = userService.getCurrentlyLoggedUserAsUserPrintDTO();
            System.out.println(currentlyLoggedUser);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isValidInfo(String[] userData) {
        String password = userData[1];
        String confirmedPassword = userData[2];

        if (!password.equals(confirmedPassword)) {
            System.out.println("Password confirmation does not match your password");
            return false;
        }

        return true;
    }



}
