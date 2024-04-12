package kz.baltabayev.workflow;

import kz.baltabayev.workflow.domain.Warrior;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.camunda.spin.json.SpinJsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.camunda.spin.Spin.JSON;

@Component
public class PrepareToBattle implements JavaDelegate {

    @Value("${max.warriors}")
    private int maxWarriors;

    @Value("${url}")
    private String url;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int warriors = (int) delegateExecution.getVariable("warriors");
        int enemyWarriors = (int) (Math.random() * 100);

        if (warriors < 1 || warriors > maxWarriors) {
            throw new BpmnError("warriorsError");
        }

        List<Warrior> army = new ArrayList<>();

        for (int i = 0; i <= warriors; i++) {
            army.add(createWarrior());
        }

        System.out.printf("Prepare to battle: eneymy army - %s VS our army - %s!\n"
                .formatted(enemyWarriors, warriors)
        );

        ObjectValue armyJson = Variables.objectValue(army).serializationDataFormat("application/json").create();// или в application.yml

        delegateExecution.setVariable("army", army);
        delegateExecution.setVariable("armyJson", armyJson);
        delegateExecution.setVariable("enemyWarriors", enemyWarriors);
    }


    private Warrior createWarrior() {
        Warrior warrior = new Warrior();
        HttpConnector httpConnector = Connectors.getConnector(HttpConnector.ID);
        HttpRequest request = httpConnector.createRequest().get().url(url);

        // если нужны headers передать в запросе
        // HashMap<String, String> headers = new HashMap<>();
        // headers.put("", "");r
        // request.setRequestParameters("headers", headers);

        HttpResponse response = request.execute();
        if (response.getStatusCode() == 200) {
            SpinJsonNode node = JSON(response.getResponse());
            String firstName = node.jsonPath("$.results[0].name.first").stringValue();
            String gender = node.jsonPath("$.results[0].gender").stringValue();
            Integer hp = node.jsonPath("$.results[0].location.street.number").numberValue().intValue();

            warrior.setName(firstName);
            warrior.setGender(gender);
            warrior.setHp(hp);

            warrior.setAlive(true);
        }
        response.close();

        return warrior;
    }
}
