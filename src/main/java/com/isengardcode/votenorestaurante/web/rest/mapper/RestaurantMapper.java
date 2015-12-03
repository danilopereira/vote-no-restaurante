package com.isengardcode.votenorestaurante.web.rest.mapper;

import com.isengardcode.votenorestaurante.domain.*;
import com.isengardcode.votenorestaurante.web.rest.dto.RestaurantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Restaurant and its DTO RestaurantDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RestaurantMapper {

    RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurant);

    Restaurant restaurantDTOToRestaurant(RestaurantDTO restaurantDTO);
}
