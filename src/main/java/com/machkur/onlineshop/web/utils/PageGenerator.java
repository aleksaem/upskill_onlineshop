package com.machkur.onlineshop.web.utils;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Collections;
import java.util.Map;

public class PageGenerator {
    public static final String RESOURCES_PATH = "src/main/resources/templates";

    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    private PageGenerator() {
        cfg = new Configuration();
    }

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public void writePage(PrintWriter writer, String filename, Map<String, ?> data) {
        try {
            FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File(RESOURCES_PATH));
            cfg.setTemplateLoader(fileTemplateLoader);
            Template template = cfg.getTemplate(filename);
            template.process(data, writer);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    public void writePage(PrintWriter writer, String filename){
        writePage(writer, filename, Collections.emptyMap());
    }
}
