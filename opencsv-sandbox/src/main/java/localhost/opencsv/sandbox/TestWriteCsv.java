package localhost.opencsv.sandbox;

// import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;

public class TestWriteCsv {

	static public void Test00WriteCsvSimple() {

		// csv target file
		String filenameStr = null;
		filenameStr = "out_sample00.csv";
		// filenameStr = "out_sample00__simple.csv";
		// filenameStr = "out_sample00__single_quote.csv";
		// filenameStr = "out_sample00__double_quote.csv";
		// filenameStr = "out_sample00__quote_variations.csv";

		System.out.println("Test00WriteCsvSimple start -- file source: " + filenameStr);

		String folderStr = "./__csv_sample";
		String pathStr = folderStr + "/" + filenameStr;
		Path path = null;

		Writer writer = null;
		// FileWriter fileWriter = null;
		CSVWriter csvWriter = null;
		String[] csvLine = null;

		List<Sample00BeanSimple> sampleBeanList = new ArrayList<Sample00BeanSimple>();
		sampleBeanList.add(new Sample00BeanSimple("Z","0"));
		sampleBeanList.add(new Sample00BeanSimple("Y","9"));
		sampleBeanList.add(new Sample00BeanSimple("X","8"));
		sampleBeanList.add(new Sample00BeanSimple("W,WW","7,77"));
		sampleBeanList.add(new Sample00BeanSimple("V'VV","6'66"));
		sampleBeanList.add(new Sample00BeanSimple("U\"UU","5\"55"));

		try {
			path = Paths.get(pathStr);

			writer = Files.newBufferedWriter(path);

			// fileWriter = new FileWriter(path.toString());

			// CsvWriter works fine with both Reader and FileReader
			csvWriter = (CSVWriter)
					new CSVWriterBuilder(writer)
					// new CSVWriterBuilder(fileWriter)
					.withQuoteChar('\'')
					.withSeparator(',')
					.withLineEnd("\r\n")
					.withEscapeChar('\\')
					.build();

			writer.write("'letter','number'" + "\r\n"); // writer / fileWriter

			for (Sample00BeanSimple sBean : sampleBeanList) {

				csvLine = new String[2];
				csvLine[0] = sBean.getLetter();
				csvLine[1] = sBean.getNumber();

				csvWriter.writeNext(csvLine);

			}

			csvWriter.close();

			System.out.println("see target file: " + filenameStr);

		} catch (Exception e) {

			System.err.println("error: " + e);

		}

	}

}
