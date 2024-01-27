package localhost.opencsv.sandbox;

// import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class TestReadCsv {

	static public void Test00ReadCsvSimple() {

		// csv source file variations
		String filenameStr = null;
		// filenameStr = "in_sample00__A_simple.csv";
		// filenameStr = "in_sample00__B_single_quote.csv";
		// filenameStr = "in_sample00__C_double_quote.csv";
		// filenameStr = "in_sample00__D_quote_variations.csv";
		filenameStr = "in_sample00__E_empty_lines_incomplete_fields.csv";

		System.out.println("Test00ReadCsvSimple start -- file source: " + filenameStr);

		String folderStr = "./__csv_sample";
		String pathStr = folderStr + "/" + filenameStr;
		Path path = null;

		Reader reader = null;
		// FileReader fileReader = null;
		CSVParser csvParser = null;
		CSVReader csvReader = null;
		String[] csvLine = null;

		List<Sample00BeanSimple> sampleBeanList = null;

		try {
			path = Paths.get(pathStr);

			reader = Files.newBufferedReader(path);

			// fileReader = new FileReader(path.toString());

			csvParser = new CSVParserBuilder()
					.withSeparator(',')
					.withIgnoreQuotations(false)
					.withQuoteChar('\'')
					.withEscapeChar('\\')
					.build();

			// CsvReader works fine with both Reader and FileReader
			csvReader =
					new CSVReaderBuilder(reader)
					// new CSVReaderBuilder(fileReader)
					.withSkipLines(0) // implemented with index i condition (continue)
					.withCSVParser(csvParser)
					.build();

			sampleBeanList = new ArrayList<Sample00BeanSimple>();

			int i = 0;

			csvLine = csvReader.readNext();

			while ( csvLine != null ) {

				i++;

				if (i == 1) {
					csvLine = csvReader.readNext();
					continue;
				}

				Sample00BeanSimple sampleBean = new Sample00BeanSimple();

				try {

					sampleBean.setLetter(csvLine[0]);
					sampleBean.setNumber(csvLine[1]);
					sampleBeanList.add(sampleBean);

				} catch (Exception e) {
					if ( csvLine.length == 0 ) {
						// empty line, this may be considered normal (not error)
						System.out.println("line " + i + " empty in csv source file ( csvLine.length == 0 ) -- " + e);
					} else if ( csvLine.length == 1 && csvLine[0].isEmpty() ) {
						// empty line, this may be considered normal (not error)
						System.out.println("line " + i + " empty in csv source file ( csvLine.length == 1 && csvLine[0].isEmpty() ) -- " + e);
					} else if ( csvLine.length < 2 ) {
						// error, incomplete fields
						System.err.println("line " + i + " in csv source with incomplete fields ( csvLine.length < 2 ) -- " + e);
					} else {
						// error, other
						System.err.println("error at csvLine capture -- " + e);
					}
					csvLine = csvReader.readNext();
					continue;
				}


				// error, field null or empty
				if ( sampleBean.getLetter() == null ||
						sampleBean.getLetter().isEmpty() ||
						sampleBean.getNumber() == null ||
						sampleBean.getNumber().isEmpty() ) {
					System.err.println("line " + i + " has empty or null fields");
					csvLine = csvReader.readNext();
					continue;
				}

				// more logic regarding csv to pojo processing may be present here

				// line OK
				System.out.println("line " + i + " OK");
				csvLine = csvReader.readNext();

			}

			csvReader.close();

			System.out.println("sampleBeanList: " + sampleBeanList);

		} catch (Exception e) {

			System.err.println("error: " + e);

		}

	}

}
