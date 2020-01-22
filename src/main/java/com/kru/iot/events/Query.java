package com.kru.iot.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author kru on 23-1-20
 * @project events-query-service
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Query {
    @NotNull
    String deviceId;

    @NotNull
    String field;

    @NotNull
    String groupBy;

    @NotNull
    String aggregateFunction;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    Date fromDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    Date toDate;
}
