package pl.kbtest.rule.induction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.junit.Assert;
import org.junit.Test;
import pl.kbtest.action.SetActionAdapter;
import pl.kbtest.action.DefaultSetAction;
import pl.kbtest.action.SetAction;
import pl.kbtest.contract.GrfIrf;
import pl.kbtest.contract.SetPremise;
import pl.kbtest.contract.SetRule;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by tomasz on 27.10.16.
 */
public class GsonSetRuleTest {

    @Test
    public void convertBothWays() {

        SetPremise first = new SetPremise("wydzial_rodzimy", new HashSet<>(Arrays.asList("informatyka", "elektryk")), false, true);
        SetAction action = new DefaultSetAction("kierunek=informatyka", null, true);
        List<SetPremise> premises = new ArrayList();
        premises.add(first);
        List conclusions = Arrays.asList(action);
        SetRule rule = new SetRule(premises, conclusions, new GrfIrf(BigDecimal.valueOf(0.55), BigDecimal.valueOf(0.75)));

        System.out.println(rule);

        Gson gs = new GsonBuilder().serializeNulls().disableHtmlEscaping()/*.setPrettyPrinting().*/
                .registerTypeAdapter(SetAction.class, new SetActionAdapter())
                .create();

        String json = gs.toJson(rule);

        System.out.println(json);

        SetRule fromJSON = gs.fromJson(json, SetRule.class);

        Javers javers = JaversBuilder.javers().build();
        org.javers.core.diff.Diff diff = javers.compare(rule, fromJSON);
        System.out.println(diff);


        Assert.assertEquals(rule, fromJSON);
        //Assert.assertEquals(0, diff.getChanges().size());


    }

    @Test
    public void readFromFile() {
        Gson gs = new GsonBuilder().serializeNulls().disableHtmlEscaping()/*.setPrettyPrinting().*/
                .registerTypeAdapter(SetAction.class, new SetActionAdapter())
                .create();
        List<SetRule> rules = new ArrayList<>();

        SetPremise first = new SetPremise("wydzial_rodzimy", new HashSet<>(Arrays.asList("informatyka", "elektryk")), false, true);
        SetAction action = new DefaultSetAction("kierunek=informatyka", null, true);
        List<SetPremise> premises = new ArrayList();
        premises.add(first);
        List conclusions = Arrays.asList(action);
        SetRule rule = new SetRule(premises, conclusions, new GrfIrf(BigDecimal.valueOf(0.55), BigDecimal.valueOf(0.75)));
        rules.add(rule);

        List<SetRule> rulesFromFile = new ArrayList<>();

        try {

            File file = new File(SetFactReader.class.getClassLoader().getResource("GsonSetRuleTestFile.txt").getFile());
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String currentLine = bf.readLine();

            while (currentLine != null) {
                SetRule ruleFromFile = gs.fromJson(currentLine,SetRule.class);
                rulesFromFile.add(ruleFromFile);
                currentLine = bf.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        Javers javers = JaversBuilder.javers().build();
        org.javers.core.diff.Diff diff = javers.compareCollections(rules, rulesFromFile, SetRule.class);
        System.out.println(diff);

        Assert.assertEquals(0, diff.getChanges().size());

    }

}
