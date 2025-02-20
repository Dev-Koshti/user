package com.example.User.statemachine;


import com.example.User.statemachine.states.AppEvents;
import com.example.User.statemachine.states.AppState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;


@Configuration
@EnableStateMachineFactory
public class SpringStateMachineConfigurer extends StateMachineConfigurerAdapter<AppState, AppEvents> {

    @Override
    public void configure(StateMachineTransitionConfigurer<AppState, AppEvents> transitions) throws Exception {
        transitions
                .withExternal().source(AppState.ACTIVE).target(AppState.INACTIVE).event(AppEvents.TURN_OFF)
                .and()
                .withExternal().source(AppState.INACTIVE).target(AppState.ACTIVE).event(AppEvents.TURN_ON);
    }

    @Override
    public void configure(StateMachineStateConfigurer<AppState, AppEvents> states) throws Exception {
        states
                .withStates()
                .initial(AppState.INACTIVE)
                .state(AppState.ACTIVE);
    }
}
