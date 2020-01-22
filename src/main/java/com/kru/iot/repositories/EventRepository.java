package com.kru.iot.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * @author kru on 20-1-20
 * @project events-query-service
 */

@Repository
public interface EventRepository extends CassandraRepository<EventEntity, DeviceType> {
}
