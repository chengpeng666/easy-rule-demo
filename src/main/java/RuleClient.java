import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;

import java.util.HashMap;
import java.util.Map;


public class RuleClient {
    public static void main(String[] args) {
        // create a rules engine
        RulesEngineParameters parameters = new RulesEngineParameters()
                .skipOnFirstAppliedRule(true);
        RulesEngine myEngine = new DefaultRulesEngine(parameters);

        // create rules
        Rules rules = new Rules();


        for (int i = 0; i < 2; i++) {
            Rule rule = new MVELRule()
                    .name("age rule")
                    .description("Check if person's age is > 18 and marks the person as adult")
                    .priority(i)
                    .when("param.number == " + i+"&&true")
                    .then("param.result = " + i);
            rules.register(rule);
        }


        // fire rules
        Facts facts = new Facts();
        facts.put("resultCode", "");

        Map<String, Object> param = new HashMap<>(16);
        param.put("number", 0);
        param.put("result", "");
        facts.put("param", param);

        myEngine.fire(rules, facts);

        Map<String, Object> resultMap = facts.get("param");

        System.out.println(resultMap.get("result"));
    }
}