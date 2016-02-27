package pl.kbtest.rule.induction;

import org.assertj.core.internal.cglib.core.CollectionUtils;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Assert;
import org.junit.matchers.JUnitMatchers;
import org.testng.annotations.Test;
import pl.kbtest.action.DefaultSetAction;
import pl.kbtest.action.SetAction;
import pl.kbtest.contract.SetPremise;
import pl.kbtest.contract.SetRule;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.hamcrest.Matchers;

import static org.testng.Assert.*;

/**
 * Created by Ja on 2016-02-24.
 */
public class SetRuleReaderTest {

    @Test
    public void testReadRules() throws Exception {

        Set delimiters = new HashSet<>(Arrays.asList("=", ":"));
        String conjunctionToken = new String("AND");
        File f = new File(SetRuleReader.class.getClassLoader().getResource("SetRuleReaderTestFile.txt").getFile());

        SetPremise first = new SetPremise("wiek", new HashSet<>(Arrays.asList("kolejna_wartosc", "21")), false, false);
        SetPremise second = new SetPremise("plec", new HashSet<>(Arrays.asList("kobieta")), false, false);
        SetPremise third = new SetPremise("stan_zdrowia", new HashSet<>(Arrays.asList("kolejna_wartosc2", "dobry")), false, false);
        SetAction action = new DefaultSetAction("motywacja", "przyjemność płynąca z uprawiania sportu2 AND troska o wygląd2", true);
        List premises = new ArrayList<>();
        premises.add(first);
        premises.add(second);
        premises.add(third);
        List conclusions = Collections.singletonList(action);


        SetRule rule = new SetRule(premises, conclusions, null);
        List<SetRule> expectedRules = new ArrayList<>();
        expectedRules.add(rule);

        first = new SetPremise("kolumna", new HashSet<>(Arrays.asList("wartosc2", "wartosc1")), false, true);
        second = new SetPremise("kolumna2", new HashSet<>(Arrays.asList("wartosc4", "wartosc3")), false, false);
        third = new SetPremise("kolumna3", new HashSet<>(Arrays.asList("wartosc5", "wartosc6")), false, false);
        action = new DefaultSetAction("fakt", "wartosc7", false);
        premises = new ArrayList<>();
        premises.add(first);
        premises.add(second);
        premises.add(third);
        conclusions = Collections.singletonList(action);


        rule = new SetRule(premises, conclusions, null);
        expectedRules.add(rule);

        SetRuleReader srr = new SetRuleReader(f, delimiters, conjunctionToken);

        List<SetRule> result = srr.readRules();

        for (int i = 0; i < result.size(); i++) {
            SetRule resultRule = (result.get(i));
            SetRule expectedRule = (expectedRules.get(i));
            for (int j = 0; j < resultRule.getPremises().size(); j++) {
                SetPremise resultPremise = resultRule.getPremises().get(j);
                SetPremise expectedPremise = expectedRule.getPremises().get(j);
                assertEquals(resultPremise, expectedPremise);
            }
            for (int j = 0; j < resultRule.getConclusions().size(); j++) {
                SetAction resultAction = resultRule.getConclusions().get(j);
                SetAction expectedAction = expectedRule.getConclusions().get(j);
                assertEquals(resultAction, expectedAction);
            }

        }



        /*for (int i = 0; i < result.size(); i++) {
            Integer r = result.get(i).hashCode();
            Integer exr = expectedRules.get(i).hashCode();
            assertEquals(result.get(i), expectedRules.get(i));
        }*/
    }


}