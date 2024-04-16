package soft_uni.makeupstore.services;

import soft_uni.makeupstore.services.dtos.*;

public interface MakeupService {

    void seedMakeup(AddMakeupDTO addMakeupDTO);

    String editMakeup(EditMakeupDTO editMakeupDTO);

    String deleteMakeup(DeleteMakeupDTO deleteMakeupDTO);

    String findMakeup(FindMakeupDTO findMakeupDTO);

    String findAllMakeup();

    String addToCart(AddMakeupToCartDTO addMakeupToCartDTO);

}
