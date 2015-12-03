package com.isengardcode.votenorestaurante.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Restaurant entity.
 */
public class RestaurantDTO implements Serializable {

    private Long id;

    private String name;

    private Long votes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RestaurantDTO restaurantDTO = (RestaurantDTO) o;

        if ( ! Objects.equals(id, restaurantDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RestaurantDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", votes='" + votes + "'" +
            '}';
    }
}
