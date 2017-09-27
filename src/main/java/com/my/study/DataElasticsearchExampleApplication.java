package com.my.study;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import static org.elasticsearch.index.query.QueryBuilders.fuzzyQuery;

@SpringBootApplication
public class DataElasticsearchExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataElasticsearchExampleApplication.class, args);
	}
	
	
	@Bean
	InitializingBean seeddata (Carrepositary carrepositary) {
		return () -> { 
			carrepositary.deleteAll();
			carrepositary.save(new Car("Honda","Civic",1997));
			carrepositary.save(new Car("Toyoto","Corolla",1998));
			carrepositary.save(new Car("Nissan","Maxima",2001));
		
	};
	
	}
	
	@Bean
	public CommandLineRunner example(Carrepositary repository,
			ElasticsearchTemplate template) {
		return (args) -> {
			System.err.println("From the repository...");
		repository.findByMakeIgnoringCase("Honda").forEach(System.out::println);
		System.err.println("\nFrom the template...");
		SearchQuery query = new NativeSearchQueryBuilder()
				.withQuery(fuzzyQuery("make", "Ronda")).build();
		template.queryForList(query, Car.class).forEach(System.err::println);
	};
		
	}

	
}
