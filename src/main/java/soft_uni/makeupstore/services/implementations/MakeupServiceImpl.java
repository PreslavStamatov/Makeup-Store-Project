package soft_uni.makeupstore.services.implementations;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import soft_uni.makeupstore.entities.Makeup;
import soft_uni.makeupstore.entities.User;
import soft_uni.makeupstore.repositories.MakeupRepository;
import soft_uni.makeupstore.services.MakeupService;
import soft_uni.makeupstore.services.UserService;
import soft_uni.makeupstore.services.dtos.*;
import soft_uni.makeupstore.validation.ValidatorService;
import soft_uni.makeupstore.validation.ValidatorServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MakeupServiceImpl implements MakeupService {

    private MakeupRepository makeupRepository;
    private UserService userService;
    private ModelMapper modelMapper;

    private ValidatorService validatorService;

    public MakeupServiceImpl(MakeupRepository makeupRepository, UserService userService) {
        this.makeupRepository = makeupRepository;
        this.modelMapper = new ModelMapper();
        this.userService = userService;
        validatorService = new ValidatorServiceImpl();
    }

    public void seedMakeup(AddMakeupDTO addMakeupDTO) {
        Optional<User> loggedUserOptional = userService.getCurrentlyLoggedUser();

        if (loggedUserOptional.isEmpty() || !loggedUserOptional.get().isAdministrator()){
            throw new IllegalArgumentException("Illegal operation");
        }

            Makeup makeupToBeAdded = modelMapper.map(addMakeupDTO, Makeup.class);

            try {
                makeupRepository.saveAndFlush(makeupToBeAdded);
                System.out.println("Successfully added");
            } catch (DataIntegrityViolationException e) {
                System.out.println("Makeup already exists");
            } catch (ConstraintViolationException e) {
                System.out.println("Invalid data");
            }
        }

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Override
    public String editMakeup(EditMakeupDTO editMakeupDTO) {
        Optional<User> loggedUserOptional = userService.getCurrentlyLoggedUser();

        if (loggedUserOptional.isEmpty() || !loggedUserOptional.get().isAdministrator()){
           return "Illegal operation!";
        }

        if (!validatorService.isValid(editMakeupDTO)) {
            return validatorService.validate(editMakeupDTO).stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
        }

        Makeup makeupFromDto = modelMapper.map(editMakeupDTO, Makeup.class);

        Optional<Makeup> optionalMakeupToBeEdited = makeupRepository.findById(makeupFromDto.getId());

        if (optionalMakeupToBeEdited.isEmpty()) {
            return "No item with the provided id was found";
        }

        Makeup makeupToBeEdited = optionalMakeupToBeEdited.get();
        makeupToBeEdited.setPrice(makeupFromDto.getPrice());
        makeupToBeEdited.setQuantity(makeupFromDto.getQuantity());
        makeupRepository.saveAndFlush(makeupToBeEdited);

        return "Makeup with id " + makeupToBeEdited.getId() + " updated successfully";
    }

    @Override
    public String deleteMakeup(DeleteMakeupDTO deleteMakeupDTO) {
        Optional<User> loggedUserOptional = userService.getCurrentlyLoggedUser();

        if (loggedUserOptional.isEmpty() || !loggedUserOptional.get().isAdministrator()){
            return "Illegal operation";
        }

        if (!validatorService.isValid(deleteMakeupDTO)) {
            return validatorService.validate(deleteMakeupDTO).stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
        }

        Makeup makeupFromDto = modelMapper.map(deleteMakeupDTO, Makeup.class);

        Optional<Makeup> optionalMakeupToBeDeleted = makeupRepository.findById(makeupFromDto.getId());

        if (optionalMakeupToBeDeleted.isEmpty()) {
            return "No item with the provided id was found";
        }

        Makeup makeupToBeDeleted = optionalMakeupToBeDeleted.get();
        makeupRepository.delete(makeupToBeDeleted);

        return "Makeup with id " + makeupToBeDeleted.getId() + " successfully deleted";
    }

    public String findMakeup(FindMakeupDTO findMakeupDTO){

        if (!validatorService.isValid(findMakeupDTO)) {
            return validatorService.validate(findMakeupDTO).stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
        }

        Makeup makeupFromDto = modelMapper.map(findMakeupDTO, Makeup.class);

        Optional<Makeup> optionalMakeupToBeShown = makeupRepository.findByBrand(makeupFromDto.getBrand());

        if (optionalMakeupToBeShown.isEmpty()) {
            return "No item with the provided brand was found";
        }

        Makeup makeupToBeShown = optionalMakeupToBeShown.get();
        PrintAllInfoMakeupDTO printAllInfoMakeupDTO = modelMapper.map(makeupToBeShown, PrintAllInfoMakeupDTO.class);

        return printAllInfoMakeupDTO.toString();
    }

    public String findAllMakeup() {
        List<Makeup> makeup = makeupRepository.findAll();

        return makeup.stream()
                .map(m -> modelMapper.map(m, PrintSingleMakeupDTO.class))
                .map(PrintSingleMakeupDTO::toString)
                .collect(Collectors.joining("\n\n"));
    }



    @Modifying
    @Transactional
    public String addToCart(AddMakeupToCartDTO addMakeupToCartDTO) {
        Optional<User> loggedInUserOptional = userService.getCurrentlyLoggedUser();

        if (loggedInUserOptional.isEmpty()) {
            return "No logged in user";
        }

        User loggedInUser = loggedInUserOptional.get();

        Optional<Makeup> makeupEntityFromDTO = makeupRepository.findByBrand(addMakeupToCartDTO.getBrand());

        if (makeupEntityFromDTO.isEmpty()) {
            return "No such makeup found";
        }

        Makeup makeupToBeAdded = makeupEntityFromDTO.get();

        if (loggedInUser.getCart().getContent().contains(makeupToBeAdded)) {
            return "Makeup by " + makeupToBeAdded.getBrand() + " is already in the cart";
        }

        if (loggedInUser.getMakeupSet().contains(makeupToBeAdded)) {
            return "Makeup by " + makeupToBeAdded.getBrand() + " is already owned by " + loggedInUser.getFullName();
        }

        loggedInUser.getCart().addMakeup(makeupToBeAdded);
        userService.getUserRepository().saveAndFlush(loggedInUser);

        return String.format("Makeup by %s successfully added to %s's cart%nCurrent price is %.2f"
                ,makeupToBeAdded.getBrand()
                , loggedInUser.getFullName()
                ,loggedInUser.getCart().getCurrentPrice());
    }

}
