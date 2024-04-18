package com.microservice.team.dtos.team.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamSubscriptionStatusDTO {

    @NotNull(message = "subscribed cannot be null")
    private Boolean subscribed;

}
