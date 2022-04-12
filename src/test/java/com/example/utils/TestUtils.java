package com.example.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// boiler-plate code

public class TestUtils {

	public static final String TEXT_RESET = "\033[0m";
	public static final String RED_BOLD_BRIGHT = "\033[1;91m"; // RED
	public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
	public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
	public static final String BLUE_BOLD_BRIGHT = "\033[1;94m"; // BLUE

	public static String testResult;

	public static int total;
	public static int passed;
	public static int failed;

	public static File businessTestFile;
	public static File boundaryTestFile;
	public static File exceptionTestFile;
	public static File xmlFile;

	public static final String GUID = "55250a8b-72cb-4666-8285-1a8ad9145aab";
	public static String customData;
	public static final String URL = "https://yaksha-stage-sbfn.azurewebsites.net/api/YakshaMFAEnqueue?code=JSssTES1yvRyHXshDwx6m405p0uSwbqnA937NaLAGX7zazwdLPC4jg==";
	static {
		total = 0;
		passed = 0;
		failed = 0;

		testResult = "";

//			xmlFile = new File("./Notes-app-boot-mysql.xml");
//			xmlFile.delete();
//			
		businessTestFile = new File("./output_revised.txt");
		businessTestFile.delete();

		boundaryTestFile = new File("./output_boundary_revised.txt");
		boundaryTestFile.delete();

		exceptionTestFile = new File("./output_exception_revised.txt");
		exceptionTestFile.delete();
	}

	private static String readData(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();

		try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return contentBuilder.toString();
	}

	public static void yakshaAssert(String testName, Object result, File file) throws IOException {
		TestResults testResults = new TestResults();
		Map<String, TestCaseResultDto> testCaseResults = new HashMap<String, TestCaseResultDto>();

		customData = readData("./custom.ih");
		String resultStatus = "Failed";
		int resultScore = 0;
		if (result.toString().equals("true")) {
			resultScore = 1;
			resultStatus = "Passed";
		}
		try {
			String testType = "functional";
			if (file.getName().contains("boundary"))
				testType = "boundary";
			if (file.getName().contains("exception"))
				testType = "exception";
			testCaseResults.put(GUID,
					new TestCaseResultDto(testName, testType, 1, resultScore, resultStatus, true, ""));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		testResults.setTestCaseResults(asJsonString(testCaseResults));
		testResults.setCustomData(customData);

		String finalResult = asJsonString(testResults);
		System.out.println(finalResult);
//		HttpClient client = HttpClient.newHttpClient();
//	        HttpRequest request = HttpRequest.newBuilder()
//	                .uri(URI.create(URL))
//	                .POST(HttpRequest.BodyPublishers.ofString(finalResult, Charset.defaultCharset()))
//	                .build();
//
//	        try {
//				HttpResponse<String> response = client.send(request,
//				        HttpResponse.BodyHandlers.ofString());
//			} catch (IOException | InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.println(e);
//			}

		URL url = new URL(URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		String input = asJsonString(testResults);

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		//String output;
		//System.out.println("Output from Server .... \n");
		//while ((output = br.readLine()) != null) {
			//System.out.println(output);
		//}

		total++;
		String[] r = testName.split("(?=\\p{Upper})");
		System.out.print("\n" + BLUE_BOLD_BRIGHT + "=>");
		// testResult = testResult + "\n" + BLUE_BOLD_BRIGHT + "=>";

		System.out.print(YELLOW_BOLD_BRIGHT + "Test For : ");
		// testResult = testResult + YELLOW_BOLD_BRIGHT + "Test For : ";

		for (int i = 1; i < r.length; i++) {
			System.out.print(YELLOW_BOLD_BRIGHT + r[i] + " ");
			// testResult = testResult + YELLOW_BOLD_BRIGHT + r[i] + " ";
		}
		System.out.print(" : ");
		// testResult = testResult + " : ";
		if (result.toString().equals("true")) {
			System.out.println(GREEN_BOLD_BRIGHT + "PASSED" + TEXT_RESET);
			// testResult = testResult + GREEN_BOLD_BRIGHT + "PASSED" + TEXT_RESET;
			passed++;
		} else {
			System.out.println(RED_BOLD_BRIGHT + "FAILED" + TEXT_RESET);
			// testResult = testResult + RED_BOLD_BRIGHT + "FAILED" + TEXT_RESET;
			failed++;
		}
//			FileWriter writer = new FileWriter(file,true);
//			writer.append("\n" + testName + "=" + result);
//			writer.flush();
//			writer.close();

		// createXML(testName, file);

	}

	public static void testReport() {
		// System.out.println(testResult);
		// System.out.print("\n" + BLUE_BOLD_BRIGHT + "TEST CASES EVALUATED : " + total
		// + TEXT_RESET);
		// System.out.print("\n" + GREEN_BOLD_BRIGHT + "PASSED : " + passed +
		// TEXT_RESET);
		// System.out.println("\n" + RED_BOLD_BRIGHT + "FAILED : " + failed +
		// TEXT_RESET);

	}

	public static void createXML(String testName, File file) throws IOException {
		FileWriter writer = new FileWriter(xmlFile, true);
		if (file.getName().contains("output_revised")) {

			writer.append(
					"\r\n<cases xmlns:java=\"http://java.sun.com\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"java:com.assessment.data.TestCase\">\r\n"
							+ "		<test-case-type>Functional</test-case-type>\r\n"
							+ "		<expected-ouput>true</expected-ouput>\r\n" + "		<name>" + testName
							+ "</name>\r\n" + "		<weight>8</weight>\r\n" + "		<mandatory>true</mandatory>\r\n"
							+ "		<desc>" + testName + "</desc>\r\n" + "	</cases>");
			writer.flush();
			writer.close();
		}
		if (file.getName().contains("boundary")) {

			writer.append(
					"\r\n<cases xmlns:java=\"http://java.sun.com\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"java:com.assessment.data.TestCase\">\r\n"
							+ "		<test-case-type>Boundary</test-case-type>\r\n"
							+ "		<expected-ouput>true</expected-ouput>\r\n" + "		<name>" + testName
							+ "</name>\r\n" + "		<weight>3</weight>\r\n" + "		<mandatory>true</mandatory>\r\n"
							+ "		<desc>" + testName + "</desc>\r\n" + "	</cases>");
			writer.flush();
			writer.close();
		}
		if (file.getName().contains("exception")) {

			writer.append(
					"\r\n<cases xmlns:java=\"http://java.sun.com\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"java:com.assessment.data.TestCase\">\r\n"
							+ "		<test-case-type>Exception</test-case-type>\r\n"
							+ "		<expected-ouput>true</expected-ouput>\r\n" + "		<name>" + testName
							+ "</name>\r\n" + "		<weight>5</weight>\r\n" + "		<mandatory>true</mandatory>\r\n"
							+ "		<desc>" + testName + "</desc>\r\n" + "	</cases>");
			writer.flush();
			writer.close();
		}
	}

	public static String currentTest() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	// convert object into JSON
	public static String asJsonString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;

	}

}

