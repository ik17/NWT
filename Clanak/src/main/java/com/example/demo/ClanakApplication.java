package com.example.demo;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.example.demo.Entities.Autor;
import com.example.demo.Entities.Clanak;
import com.example.demo.Entities.Kategorija;
import com.example.demo.Entities.Komentar;
import com.example.demo.Entities.Korisnik;
import com.example.demo.Repositories.AutorRepository;
import com.example.demo.Repositories.ClanakRepository;
import com.example.demo.Repositories.KategorijaRepository;
import com.example.demo.Repositories.KomentarRepository;
import com.example.demo.Repositories.KorisnikRepository;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration
public class ClanakApplication{
@Autowired 
DataSource dataSource;
@Autowired
AutorRepository autorRepository;
@Autowired
ClanakRepository clanakRepository;
@Autowired
KategorijaRepository kategorijaRepository;
@Autowired
KomentarRepository komentarRepository;
@Autowired
KorisnikRepository korisnikRepository;

	public static void main(String[] args) {
		SpringApplication.run(ClanakApplication.class, args);
	}
	
	/*@Override
    public void run(String... arg0) throws Exception {
		Korisnik k3 = new Korisnik("Korisnik");
		Korisnik k32 = new Korisnik("Korisnik2");
		Kategorija k = new Kategorija("kategorija");
		Clanak c = new Clanak("naziv", k, true, k3);
		Autor a = new Autor(c, k3);
		
		Komentar k2 = new Komentar("komentar", c, k3);
		korisnikRepository.save(k3);
		korisnikRepository.save(k32);
		kategorijaRepository.save(k);
		clanakRepository.save(c);
		autorRepository.save(a);
		komentarRepository.save(k2);
		System.out.println("here");
		
		
	}*/
	public static final String topicExchangeName = "fanoutTopic";

	static final String queueName = "spring-boot-7";

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	FanoutExchange exchange() {
		return new FanoutExchange(topicExchangeName);
	}

	@Bean
	Binding binding(Queue queue, FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
											 MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

}
