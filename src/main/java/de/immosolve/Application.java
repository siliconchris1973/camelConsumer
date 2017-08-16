// *********************************************************************************************************************
// * (C) 2017 Immosolve GmbH, Tegelbarg 43, 24576 Bad Bramstedt
// *********************************************************************************************************************

package de.immosolve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBinding(Sink.class)
public class Application {

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@StreamListener(Sink.INPUT)
	public void processString(final String string) {
		System.out.println(string);
	}

	@Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}
}
