package pl.kbtest.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReadCVS {

  private List<List<String>> rows = new LinkedList<>();
	private List<String> columns = new LinkedList<>();
	String separator = ",";
  public static void main(String[] args) {

	ReadCVS obj = new ReadCVS();
	obj.getRows();

  }

  public void parse(String csvFile){
	  BufferedReader br = null;
	  String line = "";


	  try {
		  int counter = 0;
		  br = new BufferedReader(new FileReader(csvFile));
		  while ((line = br.readLine()) != null) {

			  List<String> row = new LinkedList<>();
			  String[] cells = line.split(separator);

			  for(int i =0;i<cells.length;i++){
				  cells[i] = cells[i].replaceAll("\t", "");
			  }

			  row.addAll(Arrays.asList(cells));

			  if(counter == 0){
				  columns.addAll(row);
			  }else{
				  rows.add(row);
			  }

			  counter++;
		  }

	  } catch (FileNotFoundException e) {
		  e.printStackTrace();
	  } catch (IOException e) {
		  e.printStackTrace();
	  } finally {
		  if (br != null) {
			  try {
				  br.close();
			  } catch (IOException e) {
				  e.printStackTrace();
			  }
		  }
	  }

	  System.out.println("Done");

  }

	public List<List<String>> getRows() {
		return rows;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setSeparator(String separator){
		this.separator = separator;
	}
}