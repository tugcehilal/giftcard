package com.example.giftcard.client;

import com.example.giftcard.command.api.IssueCmd;
import com.example.giftcard.command.api.RedeemCmd;
import com.example.giftcard.query.GiftCardSummary;
import com.example.giftcard.query.GiftCardSummaryQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor//inject commandsGateway
@Slf4j
@Profile("client")
public class TestRunner implements CommandLineRunner {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(2000);
        /*It is very likely that performing the CommandGateway#send() call in a @PostConstruct is to early for Axon..
As it currently stands, all the command,
event and query handlers typically get registered after
all your beans have been initialized.
Moving the CommandGateway#send() behind a REST endpoint
should give you a big enough time frame
to ensure all the handlers have been registered.  */

        List<Integer> values= Arrays.asList(1,2,3,4,5);

        UUID id = UUID.randomUUID();
        log.debug("Sending issue commands");
        commandGateway.sendAndWait(new IssueCmd(id, 100));

        log.debug("Sending  redeem commands");
        commandGateway.sendAndWait(new RedeemCmd(id, 40));


        log.debug("Sending  redeem commands 2");
        commandGateway.sendAndWait(new RedeemCmd(id, 30));

        Thread.sleep(1500);//eventual consistency

        log.debug("querying");

        GiftCardSummary giftCardSummary = queryGateway.query(new GiftCardSummaryQuery(id),
                ResponseTypes.instanceOf(GiftCardSummary.class)).join();

        log.debug("summary queries {}, ", giftCardSummary);
    }
}
