package com.app.main.api.init;

import com.app.main.api.models.CountryInfo;
import com.app.main.api.PhoneNumberRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final PhoneNumberRepository repository;

    @Value("${app.wiki.url}")
    private String wikiUrl;

    @EventListener
    public void initTable(ContextRefreshedEvent event) {
        Document WikiDocument;
        try {
            WikiDocument = Jsoup.connect(wikiUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
            throw new TargetUnreachableException("Can't get information from " + wikiUrl + ", " +
                    "please check wikiUrl correctness and try again later");
        }
        Element countryCodesTable = WikiDocument.body().select("table").get(1);
        repository.saveAll(extractTableInfo(countryCodesTable));
    }

    private List<CountryInfo> extractTableInfo(Element countryCodesTable) {
        Elements tableCells = countryCodesTable.select("tr > td");
        Pattern countryCodePattern = Pattern.compile("\\+[1-9][\\d ]*");
        List<CountryInfo> countriesInfo = new ArrayList<>(310);
        for (int i = 0; i < tableCells.size(); i += 4) {
            String countryName = tableCells.get(i).text();
            String countryCodes = tableCells.get(i + 1).text();
            Matcher countryCodesMatcher = countryCodePattern.matcher(countryCodes);
            while (countryCodesMatcher.find()) {
                Long countryCode = Long.parseLong(countryCodesMatcher.group().replaceAll("[ +]", ""));
                countriesInfo.add(new CountryInfo(countryName, countryCode));
            }
        }
        return countriesInfo;
    }
}
