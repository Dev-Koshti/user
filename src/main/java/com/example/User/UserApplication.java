package com.example.User;

import com.example.User.statemachine.StateMachineAccessor;
import com.example.User.statemachine.states.AppEvents;
import com.example.User.statemachine.states.AppState;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Component
    @RequiredArgsConstructor
    class Runner implements ApplicationRunner {
        private final StateMachineAccessor stateMachineAccessor;

        @Override
        public void run(ApplicationArguments args) {
            StateMachine<AppState, AppEvents> stateMachine = stateMachineAccessor.getStateMachine();
            stateMachine.start();
        }
    }
}
