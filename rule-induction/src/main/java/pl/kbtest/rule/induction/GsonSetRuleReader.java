package pl.kbtest.rule.induction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.kbtest.action.SetAction;
import pl.kbtest.action.SetActionAdapter;
import pl.kbtest.contract.SetRule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomasz on 11.11.16.
 */
public class GsonSetRuleReader {

    File file = null;

    public GsonSetRuleReader(File file) {
        this.file = file;
    }

    public List<SetRule> readRules(){
        Gson gs = new GsonBuilder().serializeNulls().disableHtmlEscaping()/*.setPrettyPrinting().*/
                .registerTypeAdapter(SetAction.class, new SetActionAdapter())
                .create();

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

        return rulesFromFile;
    }
}
