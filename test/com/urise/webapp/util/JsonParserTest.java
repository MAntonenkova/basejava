package com.urise.webapp.util;

import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.TestData.R1;
import static org.junit.Assert.*;

public class JsonParserTest {

    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(R1);
        System.out.println(json);
        Resume resume2 = JsonParser.read(json, Resume.class);
        Assert.assertEquals(R1, resume2);
    }

    @Test
    public void write() {
        Section section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class );
        Assert.assertEquals(section1, section2);
    }


    // TODO 25.13 min video
}