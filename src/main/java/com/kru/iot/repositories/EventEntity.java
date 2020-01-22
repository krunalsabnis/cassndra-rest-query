package com.kru.iot.repositories;


import com.datastax.driver.core.DataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.Date;
import java.util.Map;

/**
 * @author kru on 19-1-20
 * @project events-query-service
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("events")
public class EventEntity {
    //@PrimaryKey
    @PrimaryKeyColumn(name = "deviceType", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private DeviceType deviceType;


    @Column("deviceId")
    @PrimaryKeyColumn(name = "deviceId", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String deviceId;

    @Column("eventTime")
    @PrimaryKeyColumn(name = "eventTime", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Date eventTime;

    @Column("sensorReadings")
    @CassandraType(type = DataType.Name.UDT, userTypeName = "value")
    private Map<String, Value> sensorReadings;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @UserDefinedType("value")
    public static class Value {
        @Column("dataType")
        private String dataType;

        @Column("reading")
        private String reading;
    }
}
