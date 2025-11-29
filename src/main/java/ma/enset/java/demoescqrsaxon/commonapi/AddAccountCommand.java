package ma.enset.java.demoescqrsaxon.commonapi.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter @AllArgsConstructor
public class AddAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    private double initialBalance;
    private String currency;

}
