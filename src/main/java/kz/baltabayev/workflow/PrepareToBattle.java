package kz.baltabayev.workflow;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PrepareToBattle implements JavaDelegate {

    @Value("${max.warriors}")
    private int maxWarriors;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int warriors = (int) delegateExecution.getVariable("warriors");
        int enemyWarriors = (int) (Math.random() * 100);

        if (warriors < 1 || warriors > maxWarriors) {
            throw new BpmnError("warriorsError");
        }

        List<Boolean> army = new ArrayList<>(Collections.nCopies(warriors, true));

        System.out.printf("Prepare to battle: eneymy army - %s VS our army - %s!\n"
                .formatted(enemyWarriors, warriors)
        );

        delegateExecution.setVariable("army", army);
        delegateExecution.setVariable("enemyWarriors", enemyWarriors);
    }
}
