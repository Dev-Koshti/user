package com.example.User.statemachine;


import com.example.User.statemachine.states.AppEvents;
import com.example.User.statemachine.states.AppState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

@Configuration
public class StateMachineHolder implements StateMachineAccessor {

    private final StateMachine<AppState, AppEvents> stateMachine;

    public StateMachineHolder(StateMachineFactory<AppState, AppEvents> stateMachineFactory) {
        this.stateMachine = stateMachineFactory.getStateMachine("stateMachine");
        this.stateMachine.start();
    }

    @Override
    public StateMachine<AppState, AppEvents> getStateMachine() {
        return stateMachine;
    }
}
