package com.busbooking.BusReservationSystemPortal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer routeId;

    @NotNull(message = "Start point cannot be null!")
    @NotBlank(message = "Start point cannot be blank!")
    private String routeFrom;

    @NotNull(message = "Destination point cannot be null!")
    @NotBlank(message = "Destination point cannot be blank!")
    private String routeTo;

    private Integer distance;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
    private List<Bus> busList = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Route other = (Route) obj;
        return Objects.equals(distance, other.distance) && Objects.equals(routeFrom.toLowerCase(), other.routeFrom.toLowerCase())
                && Objects.equals(routeId, other.routeId) && Objects.equals(routeTo.toLowerCase(), other.routeTo.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, routeFrom, routeId, routeTo);
    }
}
