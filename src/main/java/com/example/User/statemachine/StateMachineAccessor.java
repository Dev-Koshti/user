package com.example.User.statemachine;

import com.example.User.statemachine.states.AppEvents;
import com.example.User.statemachine.states.AppState;
import org.springframework.statemachine.StateMachine;

public interface StateMachineAccessor {
    StateMachine<AppState, AppEvents> getStateMachine();
}
