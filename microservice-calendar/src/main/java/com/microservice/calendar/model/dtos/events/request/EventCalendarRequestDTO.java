package com.microservice.calendar.model.dtos.events.request;

import com.microservice.calendar.model.dtos.scouter.request.ScouterRequestDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventCalendarRequestDTO {

    @NotNull(message = "ID cannot be null")
    @NotEmpty(message = "ID cannot be empty")
    private Long id;
    @NotNull(message = "title cannot be null")
    @NotEmpty(message = "title cannot be empty")
    private String title;
    @NotNull(message = "dateInit cannot be null")
    private Date dateInit;
    @NotNull(message = "dateEnd cannot be null")
    private Date dateEnd;
    @NotNull(message = "description cannot be null")
    @NotEmpty(message = "description cannot be empty")
    private String description;
    @NotNull(message = "teamId cannot be null")
    private Long teamId;
    @NotNull(message = "scouters cannot be null")
    @NotEmpty(message = "scouters cannot be empty")
    private List<ScouterRequestDTO> scouters;
}
