package com.app.tncities;

import com.app.tncities.service.TNCitiesService;
import io.spring.guides.gs_producing_web_service.GetStateRequest;
import io.spring.guides.gs_producing_web_service.Tnresponse;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.NumberUtils;
import org.xmlunit.util.Convert;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class TncitiesApplicationTests {

	@Test
	void contextLoads() throws IOException {


		TNCitiesService tnCitiesService= new TNCitiesService();
		List<String> res =tnCitiesService.allStates().getData();
		GetStateRequest request = new GetStateRequest();
		request.setState(res.get(14));

		List<String> villes =tnCitiesService.getCitiesByState(request).getData();

		System.out.println(res);
		System.out.println(villes.size());
		System.out.println(villes);
	}
	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
