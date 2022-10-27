package com.jared.BTCAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
@EnableScheduling

public class BtcapiApplication {
private static final Logger log = LoggerFactory.getLogger(BtcapiApplication.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");



	public static void main(String[] args) {
		SpringApplication.run(BtcapiApplication.class, args);

	}

	@Bean

	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate)throws Exception{
		return args -> {
			//Crypto[] crypto = restTemplate.getForObject("https://api.n.exchange/en/api/v1/price/BTCLTC/latest/?market_code=nex", Crypto[].class);
			//Crypto[] crypto = restTemplate.("https://api.n.exchange/en/api/v1/price/BTCLTC/latest/?market_code=nex", Crypto[].class);

			ResponseEntity<Crypto[]> cryptos = restTemplate.getForEntity("https://api.n.exchange/en/api/v1/price/BTCLTC/latest/?market_code=nex",Crypto[].class);

			//String coin = String.valueOf(cryptos.getBody());
			 log.info("As of this time" + dateFormat.format(new Date())+  Arrays.toString(cryptos.getBody()));
		};


	}
	@Scheduled(fixedRate = 3000)
	public void report()
	{
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Crypto[]> cryptos = restTemplate.getForEntity("https://api.n.exchange/en/api/v1/price/BTCLTC/latest/?market_code=nex",Crypto[].class);
		log.info("As of this time" + dateFormat.format(new Date())+  Arrays.toString(cryptos.getBody()));
	}


}
