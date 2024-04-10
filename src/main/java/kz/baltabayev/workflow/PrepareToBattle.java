package kz.baltabayev.workflow;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class PrepareToBattle implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int warriors = (int) delegateExecution.getVariable("warriors");
        int enemyWarriors = (int) (Math.random() * 100);
        boolean isWin = false;
        String battleStatus = "undefined";

        if (warriors < 1 || warriors > 100) {
            throw new BpmnError("warriorsError");
        }

        if (warriors - enemyWarriors > 0) {
            isWin = true;
            battleStatus = "win :)";
        } else {
            battleStatus = "fail :(";
        }

        delegateExecution.setVariable("enemyWarriors", enemyWarriors);
        delegateExecution.setVariable("battleStatus", battleStatus);
        delegateExecution.setVariable("isWin", isWin);
    }
}
