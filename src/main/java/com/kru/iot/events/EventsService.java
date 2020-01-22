package com.kru.iot.events;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.select.Selector;
import com.kru.iot.repositories.EventEntity;
import com.kru.iot.repositories.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.selectFrom;
import static com.datastax.oss.driver.api.querybuilder.select.Selector.column;


/**
 * @author kru on 20-1-20
 * @project events-query-service
 */
@Service
@Slf4j
public class EventsService {
    private final EventRepository eventRepository;

    private final CassandraTemplate cassandraTemplate;

    private static final String EVENTS_TABLE = "events";
    private static final String EVENTS_COLUMN = "sensorreadings";

    @Value("${spring.data.cassandra.keyspace:placeholder}")
    private String keyspaceName;

    private final SimpleDateFormat sdf;

    public EventsService(EventRepository eventRepository, CassandraTemplate cassandraTemplate,
                         SimpleDateFormat sdf) {
        this.eventRepository = eventRepository;
        this.cassandraTemplate = cassandraTemplate;
        this.sdf = sdf;
    }

    /**
     * Get all events paginated response
     *
     * @param pageable
     * @return
     */
    Slice<EventEntity> getEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }


    /**
     * Perform queries using QueryBuilder
     * A very basic implementation as dynamic queries on cassandra is not what it is known for.
     * Idea is to enhance this to build kind of ElasticSearch sort of REST API on Cassandra
     *
     * @param queryRequest
     * @return
     */
    public Row queryEvents(Query queryRequest) {
        ResultSet rs = null;
        Row row = null;
        try (CqlSession session = CqlSession.builder().build()) {
            Select query = selectFrom(keyspaceName, EVENTS_TABLE)

                    .function(queryRequest.getAggregateFunction(),
                            Selector.function(keyspaceName, "\"getasdouble\"",
                                    QueryBuilder.literal(queryRequest.getField()), column(EVENTS_COLUMN)))
                    .as(queryRequest.getAggregateFunction() + "_OF_" +  queryRequest.getField())
                    .whereColumn("deviceId").isEqualTo(QueryBuilder.literal(queryRequest.getDeviceId()))
                    .whereColumn("eventTime").isGreaterThanOrEqualTo(QueryBuilder.literal(sdf.format(queryRequest.getFromDate())))
                    .whereColumn("eventTime").isLessThanOrEqualTo(QueryBuilder.literal(sdf.format(queryRequest.getToDate())))
                    .allowFiltering();

            if (queryRequest.getGroupBy() != null) {
                query = query.groupBy(queryRequest.getGroupBy());
            }

            SimpleStatement statement = query.build();
            log.debug("executing statement {} ", statement);

            row = session.execute(statement).one();
            log.debug("executing statement {} ", statement);

        }
        return row;
    }
}
