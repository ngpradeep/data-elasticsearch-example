package com.my.study;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface Carrepositary extends ElasticsearchRepository<Car, Long> {
	Iterable<Car> findByMakeIgnoringCase(String make);
}
