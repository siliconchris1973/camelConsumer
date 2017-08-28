// *********************************************************************************************************************
// * (C) 2016 Immosolve GmbH, Tegelbarg 43, 24576 Bad Bramstedt
// *********************************************************************************************************************

package siliconchris1973;

import lombok.Setter;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.camel.rabbitmq")
@Setter
public class RabbitMQRouteBuilder extends SpringRouteBuilder {

	private String host;
	private String port;
	private String username;
	private String password;
	private boolean autoAck;

	@Override
	public void configure() throws Exception {

		onException(Throwable.class).process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				exchange.getOut().getHeaders().put("rabbitmq.REQUEUE", true);
			}
		});

		from("rabbitmq://" + host + ":" + port + "/testExchange" //
				 + "?username=" + username //
				 + "&password=" + password //
				 + "&autoAck=" + autoAck
				 + "&queue=test"
				 + "&autoDelete=false"
				 + "&transferException=true")

			.routeId("rabbit-mq")
			.id("sout")
			.convertBodyTo(String.class)
			.process(new Processor() {
				@Override
				public void process(Exchange exchange) throws Exception {
					System.out.println(exchange.getIn().getBody());
					//throw new Exception("test");
					//System.exit(1);
				}
			});
	}

}
